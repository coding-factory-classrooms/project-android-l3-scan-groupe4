package com.example.miamscan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miamscan.data.FoodData
import com.example.miamscan.data.FoodViewModel
import com.example.miamscan.databinding.ActivityFoodListBinding
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FoodListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter : FoodAdapter
    private lateinit var barcode : String


    private lateinit var mFoodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FoodAdapter(listOf())

//        model.getFoodsLiveData().observe(this, Observer { foods -> updateFoods(foods!!) })
        adapter = FoodAdapter(mutableListOf())
        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        binding.scanFloatingButton.setOnClickListener{
            barcode = "3380380078644"//"7613036256698"
            //loadFoodFromApi()
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

            //loadFoodFromApi()

            alertDialog.setTitle("Result")
            alertDialog.setMessage(intentResult.contents)
            Log.i("micael", "onActivityResult: ${intentResult.contents}")
            alertDialog.setPositiveButton("OK") { dialog , which ->
                Toast.makeText(applicationContext,
                        "Barcode scanné", Toast.LENGTH_SHORT).show()
            }
            alertDialog.show()

            insertDataInDatabase()
        } else {
            Toast.makeText(applicationContext,
                    "Erreur", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertDataInDatabase() {
        val name = "Couscous"
        val brand = "Du maroc"
        val imageURL = "https://static.openfoodfacts.org/images/products/761/303/625/6698/front_fr.3.400.jpg"
        val date = "24/10/2012"

        val food = FoodData(0, name, brand, imageURL, date)
        mFoodViewModel.addFood(food)
        Toast.makeText(this, "GG ça a marché", Toast.LENGTH_LONG).show()
    }

//    private fun loadFoodFromApi() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://world.openfoodfacts.org/")
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(Api::class.java)
//
//        val data = service.getFood(barcode)
//
//        data.enqueue(object : Callback<FoodResponse> {
//            override fun onFailure(call: retrofit2.Call<FoodResponse>, t: Throwable) {
//                Log.e("Error", "onFailure: ", t)
//            }
//
//            override fun onResponse(call: retrofit2.Call<FoodResponse>, response: Response<FoodResponse>
//            ) {
//                val responseApi = response.body()
//                listFood.add(responseApi!!.product)
//
//                model.loadFood()
//                Log.i("responseApi", "onResponse:" + listFood.size)
//
//                Log.i("responseApi", "onResponse: $responseApi")
//            }
//        })
//    }


    private fun updateFoods(foods: List<FoodData>) {
    //private fun updateFoods(foods: MutableList<Product>) {
        adapter.updateDataSet(foods)
    }
}