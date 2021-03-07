package com.avalon.vflapp.ui.admin

import androidx.lifecycle.*
import com.avalon.vflapp.domain.User
import com.avalon.vflapp.repository.UserRepository
import com.avalon.vflapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AdminViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
): ViewModel(){
    private val _dataStateAddRes: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val dataStateAddRes: LiveData<DataState<Boolean>> get() = _dataStateAddRes

    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.add(user).collect {
                _dataStateAddRes.postValue(it)
            }
        }
    }
}