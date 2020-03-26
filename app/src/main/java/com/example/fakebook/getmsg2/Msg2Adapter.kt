package com.example.fakebook.getmsg2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.R
import com.example.fakebook.getmsg1.Msg1
import kotlinx.android.synthetic.main.cell_1.view.*

class Msg2Adapter:RecyclerView.Adapter<Msg2Adapter.ViewHolder>() {

    var msgList = mutableListOf<Msg2>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_1, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return msgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(msgList[position])
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val msg1Name = itemView.name_1
        val msg1Msg = itemView.msg_1
        val msg1Reply = itemView.reply_1
        val msg1Time = itemView.time_1

        fun bindViewHolder(msg2: Msg2){

            msg1Name.text = msg2.user.name
            msg1Msg.text = msg2.content
            msg1Time.text = msg2.created_at
        }
    }

    fun updateMSG2(list: MutableList<Msg2>){
        msgList = list
        notifyDataSetChanged()
    }
}
