package com.example.imc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.imc.R
import com.example.imc.calcularImc
import com.example.imc.situacaoPeso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCalcular = findViewById<Button>(R.id.button_calcular)

        val editTextPeso = findViewById<EditText>(R.id.edit_text_peso)
        val editTextAltura = findViewById<EditText>(R.id.edit_text_altura)

        val textViewResultado = findViewById<TextView>(R.id.text_view_resultado)
        val textViewSituacao = findViewById<TextView>(R.id.text_view_situacao)

        buttonCalcular.setOnClickListener {
//            Toast.makeText(this, "Clicou!", Toast.LENGTH_SHORT).show()
            textViewResultado.text = calcularImc(editTextPeso.text.toString().toInt(), editTextAltura.text.toString().toDouble())
            textViewSituacao.text = situacaoPeso(imc = calcularImc(editTextPeso.text.toString().toInt(), editTextAltura.text.toString().toDouble()))

        }
    }

}
