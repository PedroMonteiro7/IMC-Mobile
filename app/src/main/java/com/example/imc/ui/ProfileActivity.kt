package com.example.imc.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.imc.R
import com.example.imc.model.Usuario
import com.example.imc.utils.convertBitmapToBase64
import com.example.imc.utils.convertStringToLocalDate
import kotlinx.android.synthetic.main.activity_profile.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val CODE_IMAGE = 100

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
    lateinit var tvTrocarFoto: TextView
    lateinit var ivFotoPerfil: ImageView
    var imageBitmap: Bitmap? = null

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
        tvTrocarFoto = findViewById(R.id.tv_trocar_foto)
        ivFotoPerfil = findViewById(R.id.iv_foto_perfil)

        //Abrir a galeria de fotos para escolher uma
        tvTrocarFoto.setOnClickListener {
            abrirGaleria()
        }

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

                    Log.i("xpto", _dia.toString())
                    Log.i("xpto", _mes.toString())

                    var diaFinal = _dia
                    var mesFinal = _mes + 1
                    //adicionando 1 ao mês pois começa pelo 0 (janeiro)

                    var diaString = "$diaFinal"
                    var mesString = "$mesFinal"

                    if (dia < 10) {
                        diaString = "0$diaFinal"
                    }

                    if (mes < 10) {
                        mesString = "0$mesFinal"
                    }

                    etDataNascimento.setText("$diaString/$mesString/$_ano")}, ano, mes, dia)
            dp.show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imagem: Intent?) {
        super.onActivityResult(requestCode, resultCode, imagem)

        if (requestCode == CODE_IMAGE && resultCode == -1) {
            // Recuperar a imagem do stream
            val fluxoImagem = contentResolver.openInputStream(imagem!!.data!!)

            // Converter os bits em um bitmap
            imageBitmap = BitmapFactory.decodeStream(fluxoImagem)

            // Colocar o bitmap no ImageView
            ivFotoPerfil.setImageBitmap(imageBitmap)
        }
    }

    private fun abrirGaleria () {

        //Abrir a galeria de imagens do dispositivo
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        // Abrir a Activity responsável por exibir as imagens
        // Esta retornará o conteúdo selecionado para o nosso app
        startActivityForResult(Intent.createChooser(intent, "Escolha uma foto"), CODE_IMAGE)
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
                if (radioFem.isChecked) 'F' else 'M',
                convertBitmapToBase64(imageBitmap!!)
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
            editor.putString("fotoPerfil", usuario.fotoPerfil.toString())

            editor.apply()

            Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro.", Toast.LENGTH_SHORT).show()
        }


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
        if(!radioFem.isChecked && !radioMasc.isChecked){
            radioMasc.error = "Selecione alguma opção."
            valido = false
        }

        //isChecked para os buttons
        return valido
    }

//    fun limparCampos() : String {
//        val limpar = find
//    }
}
