package com.example.miamscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miamscan.data.FoodData
import com.example.miamscan.data.FoodViewModel
import com.example.miamscan.databinding.ActivityFoodListBinding
import com.google.zxing.integration.android.IntentIntegrator

class FoodListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter : FoodAdapter


    private lateinit var mFoodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FoodAdapter(listOf())

        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        binding.scanFloatingButton.setOnClickListener{
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

    private fun updateFoods(foods: List<FoodData>) {
        adapter.updateDataSet(foods)
    }
}