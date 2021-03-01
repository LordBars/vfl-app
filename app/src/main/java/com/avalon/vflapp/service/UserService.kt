package com.avalon.vflapp.service

import com.avalon.vflapp.domain.User
import com.avalon.vflapp.ui.model.AuthUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface UserService {

    fun delete(users: List<User>): Task<String>

    fun add(user: User): Task<String>

    fun getAll(): Any

    fun signIn(user: AuthUser): Task<AuthResult>
}