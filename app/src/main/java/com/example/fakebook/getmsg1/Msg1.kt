package com.example.fakebook.getmsg1

import com.example.fakebook.msg.User

data class Msg1(var id: Int,
                var content: String,
                var user_id: Int,
                var post_id: Int,
                var created_at: String,
                var user: User) {
}