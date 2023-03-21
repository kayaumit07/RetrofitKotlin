package com.example.retrofitkotlin.view

import android.annotation.SuppressLint
import android.os.Bundle
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
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(),CryptoAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter : CryptoAdapter? = null
    private val BASE_URL = "https://api.wazirx.com/sapi/v1/tickers/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var cryptoModelsForChange: ArrayList<CryptoModel>? = null
    var runnable : Runnable = Runnable {  }
    var handler : Handler = Handler(Looper.getMainLooper())

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null
    private var job: Job?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //RecyclerView

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rwCryptoList.layoutManager = layoutManager


       runnable = object : Runnable {
            override fun run() {
               /* //cryptoModels?.clear()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(CryptoAPI::class.java)

                compositeDisposable?.add(retrofit.getData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this@MainActivity::handleResponse))

                handler.postDelayed(this,3000)*/
                loadData()
                handler.postDelayed(this,3000)
            }

        }

        handler.post(runnable)


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData(){
        val retrofit =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        job= CoroutineScope(Dispatchers.IO).launch {
            val respones=retrofit.getData()
            withContext(Dispatchers.Main){
                if (respones.isSuccessful){
                    respones.body()?.let {
                        cryptoModelsForChange= ArrayList(it)
                        cryptoModelsForChange?.let {
                            cryptoModelsForChange!!.removeIf {
                                it.quoteAsset!="usdt"
                            }
                                    recyclerViewAdapter=CryptoAdapter(it,this@MainActivity)
                                    binding.rwCryptoList.adapter=recyclerViewAdapter


                        }
                    }
                }
            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()

        job?.cancel()
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.baseAsset}",Toast.LENGTH_LONG ).show()
    }




}