package com.avalon.vflapp.ui

import androidx.lifecycle.*
import com.avalon.vflapp.repository.UserRepository
import com.avalon.vflapp.ui.model.AuthUser
import com.avalon.vflapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _dataState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val dataState: LiveData<DataState<Boolean>>
        get() = _dataState

    fun signIn(user: AuthUser) {
        viewModelScope.launch {
            userRepository.signIn(user).collect {
                _dataState.postValue(it)
            }
        }
    }
}