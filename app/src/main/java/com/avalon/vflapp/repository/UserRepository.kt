package com.avalon.vflapp.repository

import com.avalon.vflapp.domain.User
import com.avalon.vflapp.service.UserService
import com.avalon.vflapp.ui.model.AuthUser
import com.avalon.vflapp.util.ClaimsMapper
import com.avalon.vflapp.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRepository
@Inject
constructor(
    private val userService: UserService
) {
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

    fun add(user: User): Flow<DataState<Boolean>> = callbackFlow {
        val task = userService.add(user)

        offer(DataState.Loading)
        task.addOnSuccessListener {
            offer(DataState.Success(true))
        }.addOnCanceledListener {
            offer(DataState.Cancel())
        }.addOnFailureListener {
            offer(DataState.Error(it))
        }

        awaitClose { }
    }

    fun delete(user: List<User>): Flow<DataState<Boolean>> = callbackFlow {
        val task = userService.delete(user)

        offer(DataState.Loading)
        task.addOnSuccessListener {
            offer(DataState.Success(true))
        }.addOnCanceledListener {
            offer(DataState.Cancel())
        }.addOnFailureListener {
            offer(DataState.Error(it))
        }

        awaitClose { }
    }


    fun getAll(): Flow<DataState<Any>> = callbackFlow {
        userService.getAll()
    }

    fun getRole(): Flow<DataState<String>> = callbackFlow {
        val task = userService.getRole()

        offer(DataState.Loading)
        task.addOnSuccessListener {
            val role = ClaimsMapper.fromClaims(it.claims)
            offer(DataState.Success(role))
        }.addOnCanceledListener {
            offer(DataState.Cancel())
        }.addOnFailureListener {
            offer(DataState.Error(it))
        }

        awaitClose { }
    }
}