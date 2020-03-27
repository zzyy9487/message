package com.example.fakebook.say

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.R
import com.example.fakebook.msg.Likes
import kotlinx.android.synthetic.main.cell_0.view.*
import kotlinx.android.synthetic.main.cell_1.view.*
import kotlinx.android.synthetic.main.cell_2.view.*

class SayAdapter:RecyclerView.Adapter<SayAdapter.ViewHolder>() {

    var sayList = mutableListOf<Say>()
    private var itemClickListener: clickedListener? = null
    var userid = 0
    var list = mutableListOf<Likes>()

    interface clickedListener{
        fun addGood(likeId: Int)
        fun delGood(likeId: Int)
        fun showMSG1(msg0id: Int)
        fun showMSG2fromcell(msg0id: Int, msg1id: Int)
    }

    fun setclickedListener(checkedListener: clickedListener){
        this.itemClickListener = checkedListener
    }

    override fun getItemViewType(position: Int): Int {
        return sayList[position].viewtype
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            1 -> {
                val view_cell0 = LayoutInflater.from(parent.context).inflate(R.layout.cell_0, parent, false)
                return ViewHolder(view_cell0)
            }
            2 -> {
                val view_cell1 = LayoutInflater.from(parent.context).inflate(R.layout.cell_1, parent, false)
                return ViewHolder(view_cell1)
            }
            else -> {
                val view_cell2 = LayoutInflater.from(parent.context).inflate(R.layout.cell_2, parent, false)
                return ViewHolder(view_cell2)
            }
        }

    }

    override fun getItemCount(): Int {

        return sayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(sayList[position].viewtype){
            1 -> holder.bindcell0ViewHolder(sayList[position])
            2 -> holder.bindcell1ViewHolder(sayList[position])
            else -> holder.bindcell2ViewHolder(sayList[position])
        }
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

        fun bindcell0ViewHolder(say: Say){

            if (say.likes_count == 0){
                goodCount.visibility = View.INVISIBLE
            } else goodCount.visibility = View.VISIBLE

            if (say.comments_count == 0){
                messageCount.visibility = View.INVISIBLE
                message.text = "留言"
            } else {
                messageCount.visibility = View.VISIBLE
                message.text = "則留言"
            }

            name.text = say.user?.name
            time.text = say.created_at
            messsage.text = say.content
            goodCount.text = say.likes_count.toString()
            messageCount.text = say.comments_count.toString()

            middle.setOnClickListener {

            }

            left_good.setOnClickListener {
                list = say.likes
                for (i in 0 until list.size){
                    if (list[i].user_id == userid){
                        itemClickListener?.delGood(list[i].id)
                        return@setOnClickListener
                    }
                }
                itemClickListener?.addGood(say.id)
            }

            goodphoto.setOnClickListener { left_good.performClick() }
            good.setOnClickListener { left_good.performClick() }

            right_message.setOnClickListener {
                itemClickListener?.showMSG1(say.id)
            }

            msgphoto.setOnClickListener { right_message.performClick() }
            msgmsg.setOnClickListener { right_message.performClick() }

        }

        val cell1_name = itemView.name_1
        val cell1_msg = itemView.msg_1
        val cell1_time = itemView.time_1
        val cell1_reply = itemView.reply_1

        fun bindcell1ViewHolder(say: Say){
            cell1_name.text = say.user?.name
            cell1_msg.text = say.content
            cell1_time.text = say.created_at
            cell1_reply.setOnClickListener {
                itemClickListener?.showMSG2fromcell(say.likes_count, say.id)
            }
        }

        val cell2_name = itemView.name_2
        val cell2_msg = itemView.msg_2
        val cell2_time = itemView.time_2

        fun bindcell2ViewHolder(say: Say){
            cell2_name.text = say.id.toString()
            cell2_msg.text = say.content
            cell2_time.text = say.created_at
        }
    }

    fun updateMSG(list: MutableList<Say>){
        sayList = list
        notifyDataSetChanged()
    }

    fun updateUserId(id: Int){
        userid = id
    }
}
