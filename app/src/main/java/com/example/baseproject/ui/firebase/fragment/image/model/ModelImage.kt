package com.example.baseproject.ui.firebase.fragment.image.model


data class ModelImage(
    val name: String,
    val url: String,
    val path: String

) {
    constructor() : this("", "", "")
}