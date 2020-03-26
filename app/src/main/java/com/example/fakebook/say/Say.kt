package com.example.fakebook.say

import com.example.fakebook.msg.User

data class Say(var id: Int,
               var content: String,
               var user_id: Int,
               var created_at: String,
               var likes_count: Int?,
               var comments_count: Int?,
               var user: User?,
               var viewtype: Int ) {
}