package com.kat.startsubscribekotlin.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.kat.startsubscribekotlin.R
import com.kat.startsubscribekotlin.model.Subscribe

/**
 *
 * Created by Wanhar Aderta Daeng Maro on 5/13/2018.
 * Email : wanhardaengmaro@gmail.com
 *
 */

class  SubscribeAdapter(val context: Context, val subscribe:ArrayList<Subscribe>?, val listener:OnClickListener): RecyclerView.Adapter<SubscribeAdapter.Holder>() {

    var row_index= -1

    override fun getItemCount(): Int {
        return if(subscribe != null) subscribe.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribeAdapter.Holder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_subscribe,parent, false)

        return Holder(v)
    }

    override fun onBindViewHolder(holder: SubscribeAdapter.Holder, position: Int) {
        subscribe?.get(position)?.let { holder.bindItem(it,context) }

        holder.itemView.setOnClickListener {
            row_index=position
            notifyDataSetChanged()
            subscribe?.get(position)?.let { it1 -> listener.OnClicked(it1) }
        }

        if (row_index==position) {
            holder.relative?.setBackgroundDrawable(context.getDrawable(R.drawable.orange_background))
            holder.titleC.setTextColor(Color.parseColor("#ffffff"))
            holder.priceC.setTextColor(Color.parseColor("#ffffff"))
        }else {
            holder.relative?.setBackgroundDrawable(context.getDrawable(R.drawable.border_style))
            holder.titleC.setTextColor(Color.parseColor("#212121"))
            holder.priceC.setTextColor(Color.parseColor("#7b7a7a"))
        }


    }

    class Holder(itemView: View?): RecyclerView.ViewHolder(itemView) {
        lateinit var relative: RelativeLayout
        lateinit var titleC: TextView
        lateinit var priceC: TextView

        fun bindItem(subscribe: Subscribe, context: Context)  {

              with(subscribe){

                  relative = itemView.findViewById(R.id.relativelayout)
                  titleC = itemView.findViewById(R.id.title)
                  priceC = itemView.findViewById(R.id.price)

                  titleC.text = subscribe.title
                  if (subscribe.price == 0) {
                      priceC.text = "Min. 2 hari"
                  }else{
                      priceC.text = "Rp ${subscribe.price}/hari"
                  }


              }
          }
    }

    open interface OnClickListener{
        fun OnClicked(subscribe: Subscribe)
    }


}