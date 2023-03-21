package com.example.retrofitkotlin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.databinding.ActivityRecyclerRowBinding
import com.example.retrofitkotlin.model.CryptoModel
import com.squareup.picasso.Picasso
import java.util.*


class CryptoAdapter(private val cryptoList:ArrayList<CryptoModel>, private val listener: Listener):RecyclerView.Adapter<CryptoAdapter.Holder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class Holder(val binding:ActivityRecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val binding=ActivityRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  Holder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }


    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: Holder, position: Int) {

       holder.itemView.setOnClickListener(){
           listener.onItemClick(cryptoList.get(position))
           }





        var imageUrl = "https://res.cloudinary.com/dxi90ksom/image/upload/"
        //holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        holder.binding.txtCryptoName.text = cryptoList.get(position).baseAsset
        holder.binding.textView2.text = String.format(cryptoList.get(position).bidPrice+ " $")

            Picasso.get().load(imageUrl+ cryptoList.get(position).baseAsset.lowercase() +".png").into(holder.binding.imageView)


    }

}