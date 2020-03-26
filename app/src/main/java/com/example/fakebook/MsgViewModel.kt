package com.example.fakebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakebook.getmsg1.Msg1
import com.example.fakebook.getmsg2.Msg2
import com.example.fakebook.msg.Msg

class MsgViewModel:ViewModel() {
    var msg0List: MutableLiveData<MutableList<Msg>>
    var msg1List: MutableLiveData<MutableList<Msg1>>
    var msg2List: MutableLiveData<MutableList<Msg2>>

    init {
        msg0List = MutableLiveData()
        msg1List = MutableLiveData()
        msg2List = MutableLiveData()
    }

    fun updateMsg0List(newList: MutableList<Msg>){
        msg0List.value = newList
    }

    fun updateMsg1List(newList: MutableList<Msg1>){
        msg1List.value = newList
    }

    fun updateMsg2List(newList: MutableList<Msg2>){
        msg2List.value = newList
    }

}