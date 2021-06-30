package com.albert.instagram.models

data class Post(
        var id: Int? = null,
        val caption: String? = null,
        val user: User? = null,
        val image_url: String? = null,
        var likes: MutableMap<Int, User> = mutableMapOf<Int, User>()
)