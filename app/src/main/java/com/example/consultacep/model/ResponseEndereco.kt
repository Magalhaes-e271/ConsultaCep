package com.example.consultacep.model

data class ResponseEndereco(
    val logradouro: String,
    val bairro: String,
    val uf: String,
    val localidade: String,
    val ddd: String
)
