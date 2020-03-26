package com.example.fakebook.msg

data class Msg(var id: Int,
               var content: String,
               var user_id: Int,
               var created_at: String,
               var likes_count: Int,
               var comments_count: Int,
               var user: User,
               var comments: MutableList<Comment>,
               var likes: MutableList<Likes>) {
}