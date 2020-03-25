package com.example.fakebook.msg

data class Comment(var id: Int,
                   var content: String,
                   var user_id: Int,
                   var post_id: Int,
                   var created_at: String,
                   var user: User,
                   var replies: MutableList<Reply>,
                   var likes: MutableList<Likes>) {
}