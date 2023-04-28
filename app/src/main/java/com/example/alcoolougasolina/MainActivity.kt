package com.example.alcoolougasolina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import android.content.Context

class MainActivity : AppCompatActivity() {
    var percentual:Double = 0.7
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrievePercentualValue()
        Log.d("PDM23","No onCreate, $percentual")

        val edAlcool = findViewById<EditText>(R.id.edAlcool)
        val edGasolina = findViewById<EditText>(R.id.edGasolina)

        val mySwitch = findViewById<Switch>(R.id.swPercentual)
        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            percentual = if (isChecked) {
                0.75
            } else {
                0.7
            }
            // do something with the updated variable value
            Log.d("PDM23", "percentual is now $percentual")
        }

        val btCalc: Button = findViewById(R.id.btCalcular)
        btCalc.setOnClickListener(View.OnClickListener {
            val precoAlcool = edAlcool.text.toString().toDoubleOrNull()
            val precoGasolina = edGasolina.text.toString().toDoubleOrNull()

            if (precoAlcool != null && precoGasolina != null) {
                calcularMelhorOpcao(precoAlcool, precoGasolina)
            } else {
                Toast.makeText(this, "Insira os preços do álcool e da gasolina", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun calcularMelhorOpcao(precoAlcool: Double, precoGasolina: Double) {
        val relacaoPreco = precoAlcool / precoGasolina
        if (relacaoPreco < percentual) {
            Toast.makeText(this, "Abasteça com álcool", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Abasteça com gasolina", Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrievePercentualValue() {
        // Retrieve the saved variable value
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        percentual = sharedPref.getInt("percentual", 70).toDouble() / 100

        Log.d("PDM23","Percentual retrieved as ${(percentual * 100).toInt()}")
    }
    private fun savePercentualValue() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putInt("percentual", (percentual * 100).toInt())
            apply()
        }

        Log.d("PDM23","Percentual saved as ${(percentual * 100).toInt()}")
    }

    override fun onResume(){
        super.onResume()
        retrievePercentualValue()
        Log.d("PDM23","No onResume, $percentual")
    }
    override fun onStart(){
        super.onStart()
        Log.d("PDM23","No onStart")
    }
    override fun onPause(){
        super.onPause()
        Log.d("PDM23","No onPause")
    }
    override fun onStop(){
        super.onStop()
        Log.d("PDM23","No onStop")
        savePercentualValue()
    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d("PDM23","No onDestroy")
        savePercentualValue()
    }
}