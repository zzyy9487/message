package com.example.fakebook.getmsg2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.R
import kotlinx.android.synthetic.main.cell_3.view.*

class Msg2Adapter:RecyclerView.Adapter<Msg2Adapter.ViewHolder>() {

    var msgList = mutableListOf<Msg2>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_3, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return msgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(msgList[position])
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val msg3Name = itemView.name_3
        val msg3Msg = itemView.msg_3
        val msg3Time = itemView.time_3

        fun bindViewHolder(msg2: Msg2){

            msg3Name.text = msg2.user.name
            msg3Msg.text = msg2.content
            msg3Time.text = msg2.created_at
        }
    }

    fun updateMSG2(list: MutableList<Msg2>){
        msgList = list
        notifyDataSetChanged()
    }
}
