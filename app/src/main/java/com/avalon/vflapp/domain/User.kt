package com.avalon.vflapp.domain

data class User(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val number: Int = 0,
    val uid: String
)