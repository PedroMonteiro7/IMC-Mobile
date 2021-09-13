package com.example.imc

fun calcularImc(peso : Int, altura : Double) : String {
    val imc = peso / (altura*altura)
    return imc.toString().substring(0, 4)
}

fun situacaoPeso(imc : String) : String {
    var classificacao = ""
    if (imc.toDouble() < 18.5) {
        classificacao = "Abaixo do peso."
    } else if (imc.toDouble() < 25) {
        classificacao = "Peso ideal. Parabéns!"
    } else if (imc.toDouble() < 30) {
        classificacao = "Levemente acima do peso."
    } else if (imc.toDouble() < 35) {
        classificacao = "Obesidade grau I."
    } else if (imc.toDouble() < 40) {
        classificacao = "Obesidade grau II."
    } else {
        classificacao = "Obesidade grau III. Cuidado!"
    }

    return classificacao
}
