package com.example.fakebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakebook.msg.Msg

class MsgViewModel:ViewModel() {
    var msg0List: MutableLiveData<MutableList<Msg>>

    init {
        msg0List = MutableLiveData()
    }

    fun updateMsg0List(newList: MutableList<Msg>){
        msg0List.value = newList
    }
}