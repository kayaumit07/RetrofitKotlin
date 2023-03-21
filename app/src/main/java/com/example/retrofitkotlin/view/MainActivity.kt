package com.example.retrofitkotlin.view

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.adapter.CryptoAdapter
import com.example.retrofitkotlin.databinding.ActivityMainBinding
import com.example.retrofitkotlin.model.CryptoModel
import com.example.retrofitkotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(),CryptoAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter : CryptoAdapter? = null
    private val BASE_URL = "https://api.wazirx.com/sapi/v1/tickers/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    var runnable : Runnable = Runnable {  }
    var handler : Handler = Handler(Looper.getMainLooper())

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        compositeDisposable = CompositeDisposable()

        //RecyclerView

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rwCryptoList.layoutManager = layoutManager




        runnable = object : Runnable {
            override fun run() {
                //cryptoModels?.clear()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(CryptoAPI::class.java)

                compositeDisposable?.add(retrofit.getData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this@MainActivity::handleResponse))

                handler.postDelayed(this,3000)

            }

        }

        handler.post(runnable)



    }


    private fun handleResponse(cryptoList : List<CryptoModel>){


        cryptoModels = ArrayList(cryptoList)
        cryptoModels?.let {

                    cryptoModels!!.removeIf {
                        it.quoteAsset!="usdt"

            }

            recyclerViewAdapter= CryptoAdapter(it,this@MainActivity)
            binding.rwCryptoList.adapter = recyclerViewAdapter


        }


    }

//    override fun onItemClick(cryptoModel: CryptoModel) {
//        Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG ).show()
//    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.baseAsset}",Toast.LENGTH_LONG ).show()
    }




}