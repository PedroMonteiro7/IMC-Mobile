package com.example.imc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.imc.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()

        val criarConta = findViewById<TextView>(R.id.criar_conta)

        criarConta.setOnClickListener {
            val abrirProfileActivity = Intent(this, ProfileActivity::class.java)
            //intent chama outras telas e até mesmo outras aplicações;

            startActivity(abrirProfileActivity)
        }

    }
}