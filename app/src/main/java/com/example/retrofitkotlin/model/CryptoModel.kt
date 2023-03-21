package com.example.retrofitkotlin.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    val symbol:String,
    val baseAsset:String,
    val quoteAsset:String,
    val openPrice:String,
    val lowPrice:String,
    val highPrice:String,
    val lastPrice:String,
    val volume:String,
    val bidPrice:String,
    val askPrice:String,
    val at:String
){

}
