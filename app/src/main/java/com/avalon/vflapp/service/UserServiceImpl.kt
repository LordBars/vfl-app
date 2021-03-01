package com.avalon.vflapp.service

import android.util.Log
import com.avalon.vflapp.domain.User
import com.avalon.vflapp.ui.model.AuthUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import javax.inject.Inject

private const val TAG = "UserServiceImpl"

class UserServiceImpl
@Inject
constructor(
    private val functions: FirebaseFunctions,
    private val auth: FirebaseAuth,
): UserService {

    override fun delete(users: List<User>): Task<String> {
        return functions.getHttpsCallable("deleteUser").call(users).continueWith {
            task -> task.result?.data as String
        }
    }

    override fun add(user: User): Task<String> {
        return functions.getHttpsCallable("addUser").call(user).continueWith {
            task -> task.result?.data as String
        }
    }

    override fun getAll(): Task<Any> {
        return functions.getHttpsCallable("fetchUsers").call().continueWith {
            Log.d(TAG, "getAll: ${it.result?.data}")
            it.result?.data
        }
    }

    override fun signIn(user: AuthUser): Task<AuthResult> {
        Log.d(TAG, "signIn: Email: ${user.email}, Password: ${user.password}")
        return auth.signInWithEmailAndPassword(user.email, user.password)
    }

}