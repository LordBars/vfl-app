package com.avalon.vflapp.ui

import androidx.lifecycle.*
import com.avalon.vflapp.repository.LoginRepository
import com.avalon.vflapp.ui.model.AuthUser
import com.avalon.vflapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var _dataState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val dataState: LiveData<DataState<Boolean>>
    get() = _dataState

    @ExperimentalCoroutinesApi
    fun signIn(user: AuthUser) {
        viewModelScope.launch {
            loginRepository.signIn(user).collect {
                _dataState.postValue(it)
            }
        }
    }
}