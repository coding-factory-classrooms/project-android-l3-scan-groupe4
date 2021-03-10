package com.example.miamscan

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity : AppCompatActivity() {
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.scanButton);


        button.setOnClickListener {
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
}