package com.example.consultacep.api

import com.example.consultacep.model.ResponseEndereco
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("{cep}/json/")
    suspend fun buscarEndereco(
        @Path("cep") cep: String
    ): ResponseEndereco
}