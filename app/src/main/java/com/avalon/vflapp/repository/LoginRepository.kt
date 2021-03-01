package com.avalon.vflapp.repository

import com.avalon.vflapp.service.UserService
import com.avalon.vflapp.ui.model.AuthUser
import com.avalon.vflapp.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "LoginRepository"

class LoginRepository
@Inject
constructor(
    private val userService: UserService
)
{
    @ExperimentalCoroutinesApi
    fun signIn(user: AuthUser): Flow<DataState<Boolean>> = callbackFlow {
        val task = userService.signIn(user)
        offer(DataState.Loading)
        task.addOnSuccessListener {
            offer(DataState.Success(true))
        }.addOnCanceledListener {
            offer(DataState.Cancel())
        }.addOnFailureListener {
            offer(DataState.Error(it))
        }
        awaitClose {}
    }
}