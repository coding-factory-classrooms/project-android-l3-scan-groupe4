package com.example.miamscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miamscan.databinding.ActivityFoodListBinding
import com.google.zxing.integration.android.IntentIntegrator

class FoodListActivity : AppCompatActivity() {

    private val model : FoodListViewModel by viewModels()
    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter : FoodAdapter

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

    private fun updateFoods(foods: List<Food>) {
        adapter.updateDataSet(foods)
    }
}