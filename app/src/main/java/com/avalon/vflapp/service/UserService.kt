package com.avalon.vflapp.service

import com.avalon.vflapp.domain.User
import com.avalon.vflapp.ui.model.AuthUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GetTokenResult

interface UserService  {

    fun delete(users: List<User>): Task<Any>

    fun add(user: User): Task<Any>

    fun getAll(): Any

    fun signIn(user: AuthUser): Task<AuthResult>

    fun getRole(): Task<GetTokenResult>
}