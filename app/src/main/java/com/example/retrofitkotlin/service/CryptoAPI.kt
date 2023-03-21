package com.example.retrofitkotlin.service

import com.example.retrofitkotlin.model.CryptoModel
import com.example.retrofitkotlin.model.USD
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

    interface CryptoAPI {

    //GET, POST, UPDATE, DELETE


    @GET("24hr?API-KEY=DCejZafEOoBBkwLP3hjQkVTpptypXlgyN6tSMCz8PDyuyKdjRRPJsiOXLroPEomI")
    suspend fun getData(): Response<ArrayList<CryptoModel>>


    //fun getData(): Call<List<CryptoModel>>


}