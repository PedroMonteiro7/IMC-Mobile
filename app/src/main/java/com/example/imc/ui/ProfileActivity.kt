package com.example.imc.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.imc.R
import com.example.imc.model.Usuario
import com.example.imc.utils.convertStringToLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
            //Criar o objeto usuário
            val nascimento = convertStringToLocalDate(editDataNascimento.text.toString())
            val usuario = Usuario(
                1,
                editNome.text.toString(),
                editEmail.text.toString(),
                editSenha.text.toString(),
                0,
                editAltura.text.toString().toDouble(),
                LocalDate.of(nascimento.year, nascimento.monthValue, nascimento.dayOfMonth),
                editProfissao.text.toString(),
                if (radioFem.isChecked) {
                    'F'
                } else {
                    'M'
                }
            )

            //Salvar o registro em um SharedPreferences
            //A instrução abaixo criará um arquivo SharedPreferences se não existir
            //Se existir, ele será aberto para edição
            val dados = getSharedPreferences("usuario", Context.MODE_PRIVATE)

            //Vamos criar o objeto que permitirá a edição dos dados do arquivo
            //edição dos dados do arquivo SharedPreferences
            val editor = dados.edit()
            editor.putInt("id", usuario.id)
            editor.putString("nome", usuario.nome)
            editor.putString("email", usuario.email)
            editor.putString("senha", usuario.senha)
            editor.putInt("peso", usuario.peso)
            editor.putFloat("altura", usuario.altura.toFloat())
            editor.putString("dataNascimento", usuario.dataNascimento.toString())
            editor.putString("profissao", usuario.profissao)
            editor.putString("sexo", usuario.sexo.toString())

            editor.apply()

        }

        Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT).show()

        return true

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
