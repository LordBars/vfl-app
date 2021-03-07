package com.avalon.vflapp.service

import com.avalon.vflapp.domain.User
import com.avalon.vflapp.ui.model.AuthUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.Gson
import javax.inject.Inject

class UserServiceImpl
@Inject
constructor(
        private val functions: FirebaseFunctions,
        private val auth: FirebaseAuth,
): UserService {

    override fun delete(users: List<User>): Task<Any> {
        return functions.getHttpsCallable("deleteUser").call(users).continueWith {
            task -> task.result?.data as String
        }
    }

    override fun add(user: User): Task<Any> {
        val gUser = Gson().toJson(user)
        return functions.getHttpsCallable("addUser").call(gUser).continueWith {
            it.result?.data
        }
    }

    override fun getAll(): Task<Any> {
        return functions.getHttpsCallable("fetchUsers").call().continueWith {
            it.result?.data
        }
    }

    override fun signIn(user: AuthUser): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(user.email, user.password)
    }

    override fun getRole(): Task<GetTokenResult> {
        return auth.currentUser!!.getIdToken(true)
    }

}