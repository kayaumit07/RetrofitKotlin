package com.example.retrofitkotlin.service

import com.example.retrofitkotlin.model.CryptoModel
import com.example.retrofitkotlin.model.USD
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoAPI {

    //GET, POST, UPDATE, DELETE

    //https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    //https://api.nomics.com/v1/
    // prices?key=2187154b76945f2373394aa34f7dc98a

    @GET("24hr?API-KEY=DCejZafEOoBBkwLP3hjQkVTpptypXlgyN6tSMCz8PDyuyKdjRRPJsiOXLroPEomI")
    fun getData(): Observable<ArrayList<CryptoModel>>

    @GET("24hr/quotes/USD")
    fun getdata2():Observable<ArrayList<USD>>









    //fun getData(): Call<List<CryptoModel>>


}