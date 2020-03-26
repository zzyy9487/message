package com.example.fakebook.getmsg2

import com.example.fakebook.msg.User

data class Msg2(var id: Int,
                var content: String,
                var user_id: Int,
                var comment_id: Int,
                var created_at: String,
                var user: User
) {
}