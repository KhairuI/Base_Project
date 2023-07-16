package com.example.baseproject.data.network.response

import com.google.gson.annotations.Expose

data class PostResponse(
    @Expose var datas: List<PostsItem>? = null
)
