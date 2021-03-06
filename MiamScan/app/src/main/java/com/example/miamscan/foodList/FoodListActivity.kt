package com.example.miamscan.foodList

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miamscan.foodDetails.FoodDetailsActivity
import com.example.miamscan.viewmodel.FoodViewModel
import com.example.miamscan.api.Api
import com.example.miamscan.capture.Capture
import com.example.miamscan.model.FoodData
import com.example.miamscan.databinding.ActivityFoodListBinding
import com.example.miamscan.model.FoodResponse
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.Serializable
import java.util.*

class FoodListActivity : AppCompatActivity(), onFoodItemClickedListener, Serializable {

    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter : FoodAdapter
    private lateinit var barcode : String

    private lateinit var mFoodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FoodAdapter(listOf(), this)

        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        binding.scanFloatingButton.setOnClickListener{
            barcode = "3400930055953"//"7613036249928" // "7613036256698"
            loadFoodFromApi()
            return@setOnClickListener
            val integrator = IntentIntegrator(this)
            integrator.setBeepEnabled(true)
            integrator.setOrientationLocked(true)
            integrator.setCaptureActivity(Capture::class.java)
            integrator.initiateScan()
        }

        mFoodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        mFoodViewModel.readAllData.observe(this, Observer { food ->
            adapter.updateDataSet(food)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        )

        if(intentResult.contents != null){
            var alertDialog = AlertDialog.Builder(this)

            barcode = intentResult.contents

            loadFoodFromApi()

            alertDialog.setTitle("Result")
            alertDialog.setMessage(intentResult.contents)
            Log.i("micael", "onActivityResult: ${intentResult.contents}")
            alertDialog.setPositiveButton("OK") { dialog , which ->
                Toast.makeText(applicationContext,
                        "Barcode scann??", Toast.LENGTH_SHORT).show()
            }
            alertDialog.show()

        } else {
            Toast.makeText(applicationContext,
                    "Erreur", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadFoodFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(Api::class.java)

        val data = service.getFood(barcode)

        data.enqueue(object : Callback<FoodResponse> {
            override fun onFailure(call: retrofit2.Call<FoodResponse>, t: Throwable) {
                Log.e("Error", "onFailure: ", t)
            }

            override fun onResponse(call: retrofit2.Call<FoodResponse>, response: Response<FoodResponse>
            ) {
                val responseApi = response.body()
                val pattern = "dd.MM.yyyy HH:mm"
                val simpleDateFormat = SimpleDateFormat(pattern)
                val date = simpleDateFormat.format(Date())

                val food = FoodData(0, responseApi!!.product.productName, responseApi.product.brands, responseApi.product.image_url, date, responseApi.product.packaging, responseApi.product.nutrition)
                mFoodViewModel.addFood(food)
            }
        })
    }


    private fun updateFoods(foods: List<FoodData>) {
        adapter.updateDataSet(foods)
    }

    override fun onItemClick(foods: FoodData, position: Int) {
        val intent = Intent(this, FoodDetailsActivity::class.java)
        intent.putExtra("foodName", foods.name)
        intent.putExtra("foodBrand", foods.brand)
        intent.putExtra("foodDate", foods.date)
        intent.putExtra("foodImage", foods.imageURL)
        intent.putExtra("foodCategories", foods.packaging)
        intent.putExtra("foodNutrition", foods.nutrition)
        intent.putExtra("extra_object", foods as Serializable)
        startActivity(intent)
    }
}