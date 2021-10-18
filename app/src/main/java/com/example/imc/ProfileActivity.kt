package com.example.imc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    //para variáveis globais deve-se usar lateinit var
    lateinit var editEmail: EditText
    lateinit var editSenha: EditText
    lateinit var editNome: EditText
    lateinit var editProfissao: EditText
    lateinit var editAltura: EditText
    lateinit var editDataNascimento: EditText
    lateinit var radioFem: RadioButton
    lateinit var radioMasc: RadioButton
    lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar!!.title = "Novo usuário"

        editEmail = findViewById<EditText>(R.id.et_email)
        editSenha = findViewById<EditText>(R.id.et_senha)
        editNome = findViewById<EditText>(R.id.et_nome)
        editProfissao = findViewById<EditText>(R.id.et_profissao)
        editAltura = findViewById<EditText>(R.id.et_altura)
        editDataNascimento = findViewById<EditText>(R.id.et_data)
        radioFem = findViewById<RadioButton>(R.id.radio_fem)
        radioMasc = findViewById<RadioButton>(R.id.radio_masc)
        radioGroup = findViewById<RadioGroup>(R.id.radio_group)


        //Criar um calendário
        val calendario = Calendar.getInstance()

        //Determinar os dados (dia, mês e ano) do calendário
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        //Abrir o componente DatePicker
        val etDataNascimento = findViewById<EditText>(R.id.et_data)

        etDataNascimento.setOnClickListener {
            val dp = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, _ano, _mes, _dia ->
                    etDataNascimento.setText("$_dia/${_mes + 1}/$_ano")}, ano, mes, dia)
            dp.show()
        }

        //Validar preenchimento dos campos
//        fun validar() {
//            val texto_erros = ""
//            val nome = findViewById<EditText>(R.id.et_nome)
//
//            if (nome.text.equals("")) {
//                texto_erros = "Campo nome é obrigatório\n"
//                nome.setError("Este campo é obrigatório")
//            }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_novo_usuario, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (validarCampos()){

        }
        return true
//        when (item.itemId) {
//            R.id.menu_save -> {
//                Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//        return true
    }

    fun validarCampos() : Boolean {

        var valido = true

        if(editEmail.text.isEmpty()){
            editEmail.error = "Este campo é de preenchimento obrigatório."
            valido = false
        }
        if(editSenha.text.isEmpty()){
            editSenha.error = "Este campo é de preenchimento obrigatório."
            valido = false
        }
        if(editNome.text.isEmpty()){
            editNome.error = "Este campo é de preenchimento obrigatório."
            valido = false
        }
        if(editAltura.text.isEmpty()){
            editAltura.error = "Este campo é de preenchimento obrigatório."
            valido = false
        }
        if(editDataNascimento.text.isEmpty()){
            editDataNascimento.error = "Este campo é de preenchimento obrigatório."
            valido = false
        }
        if(editProfissao.text.isEmpty()){
            editProfissao.error = "Este campo é de preenchimento obrigatório."
            valido = false
        }
        if(radioFem.isChecked || radioMasc.isChecked){
            radioFem.error = "Selecione essa"
            radioMasc.error = "ou essa opção."
            valido = false
        }

        //isChecked para os buttons
        return valido
    }

//    fun limparCampos() : String {
//        val limpar = find
//    }
}
