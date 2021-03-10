package com.example.miamscan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miamscan.databinding.ActivityFoodListBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class FoodListActivity : AppCompatActivity() {

    private val model : FoodListViewModel by viewModels()
    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter : FoodAdapter
    private lateinit var barcode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getFoodsLiveData().observe(this, Observer { foods -> updateFoods(foods!!) })

        adapter = FoodAdapter(listOf())

        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        model.loadMovies()

        binding.scanFloatingButton.setOnClickListener{
            barcode = "7613036256698"
            loadFoodFromApi()
            return@setOnClickListener
            val integrator = IntentIntegrator(this)
            integrator.setBeepEnabled(true)
            integrator.setOrientationLocked(true)
            integrator.setCaptureActivity(Capture::class.java)
            integrator.initiateScan()
        }
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
                        "Barcode scanné", Toast.LENGTH_SHORT).show()
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
                Log.e("aie", "onFailure: ", t)
            }

            override fun onResponse(call: retrofit2.Call<FoodResponse>, response: Response<FoodResponse>
            ) {
                  val test = response.body()
                  Log.i("testbody", "onResponse: $test")
            }

        })
    }


    private fun updateFoods(foods: List<Food>) {
        adapter.updateDataSet(foods)
    }
}