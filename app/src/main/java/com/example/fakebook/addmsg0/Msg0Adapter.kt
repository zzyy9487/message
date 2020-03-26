package com.example.fakebook.addmsg0

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.R
import com.example.fakebook.msg.Likes
import com.example.fakebook.msg.Msg
import kotlinx.android.synthetic.main.cell_0.view.*

class Msg0Adapter:RecyclerView.Adapter<Msg0Adapter.ViewHolder>() {

    var msgList = mutableListOf<Msg>()
    private var itemClickListener: clickedListener? = null
    var userid = 0
    var list = mutableListOf<Likes>()

    interface clickedListener{
        fun addGood(likeId: Int)
        fun delGood(likeId: Int)
        fun showMSG1(msg0id: Int)
    }

    fun setclickedListener(checkedListener: clickedListener){
        this.itemClickListener = checkedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_0, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return msgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(msgList[position])
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val name = itemView.name_0
        val time = itemView.time_0
        val messsage = itemView.msg_0
        val goodCount = itemView.good_total
        val messageCount = itemView.msg_total
        val message = itemView.msg_total2
        val middle = itemView.middlelayout
        val left_good = itemView.leftlayout
        val goodphoto = itemView.btn_good_send
        val good = itemView.good
        val right_message = itemView.rightlayout
        val msgphoto = itemView.btn_msg_show
        val msgmsg = itemView.msgmsg

        fun bindViewHolder(msg: Msg){

            if (msg.likes_count == 0){
                goodCount.visibility = View.INVISIBLE
            } else goodCount.visibility = View.VISIBLE

            if (msg.comments_count == 0){
                messageCount.visibility = View.INVISIBLE
                message.text = "留言"
            } else {
                messageCount.visibility = View.VISIBLE
                message.text = "則留言"
            }

            name.text = msg.user.name
            time.text = msg.created_at
            messsage.text = msg.content
            goodCount.text = msg.likes_count.toString()
            messageCount.text = msg.comments_count.toString()

            middle.setOnClickListener {

            }


            left_good.setOnClickListener {
                list = msg.likes
                for (i in 0 until list.size){
                    if (list[i].user_id == userid){
                        itemClickListener?.delGood(list[i].id)
                        return@setOnClickListener
                    }
                }
                itemClickListener?.addGood(msg.id)
            }

            goodphoto.setOnClickListener { left_good.performClick() }
            good.setOnClickListener { left_good.performClick() }

            right_message.setOnClickListener {
                itemClickListener?.showMSG1(msg.id)
            }

            msgphoto.setOnClickListener { right_message.performClick() }
            msgmsg.setOnClickListener { right_message.performClick() }

        }
    }

    fun updateMSG(list: MutableList<Msg>){
        msgList = list
        notifyDataSetChanged()
    }

    fun updateUserId(id: Int){
        userid = id
    }
}
