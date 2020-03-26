package com.example.fakebook.getmsg1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.R
import kotlinx.android.synthetic.main.cell_1.view.*

class Msg1Adapter:RecyclerView.Adapter<Msg1Adapter.ViewHolder>() {

    var msgList = mutableListOf<Msg1>()
    private var itemClickListener: clickedListener? = null

    interface clickedListener{
        fun showMSG2(msg1id: Int)
    }

    fun setclickedListener(checkedListener: clickedListener){
        this.itemClickListener = checkedListener
    }

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

        fun bindViewHolder(msg1: Msg1){

            msg1Name.text = msg1.user.name
            msg1Msg.text = msg1.content
            msg1Time.text = msg1.created_at
            msg1Reply.setOnClickListener {
                itemClickListener?.showMSG2(msg1.id)
            }
        }
    }

    fun updateMSG1(list: MutableList<Msg1>){
        msgList = list
        notifyDataSetChanged()
    }
}
