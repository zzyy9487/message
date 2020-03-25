package com.example.fakebook.msg

data class Reply(var id: Int,
                 var content: String,
                 var user_id: Int,
                 var comment_id: Int,
                 var created_at:String) {
}