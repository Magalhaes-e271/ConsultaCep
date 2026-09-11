package com.example.appconsultacep.api

import com.example.consultacep.api.ViaCepService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ViaCepClient {
    private const val BASE_URL = "https://viacep.com.br/ws/"
    val instance: ViaCepService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepService::class.java)
    }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val viaCepService = retrofit.create(ViaCepService::class.java)

}