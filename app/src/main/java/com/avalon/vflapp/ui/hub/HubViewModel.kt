package com.avalon.vflapp.ui.hub

import android.app.Application
import androidx.lifecycle.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.avalon.vflapp.repository.UserRepository
import com.avalon.vflapp.ui.model.QRModel
import com.avalon.vflapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HubViewModel
@Inject
constructor(
        private val savedStateHandle: SavedStateHandle,
        private val userRepository: UserRepository,
        application: Application,
) : AndroidViewModel(application)
{

    private val _dataStateQR: MutableLiveData<DataState<QRModel>> = MutableLiveData()
    val dataStateQR: LiveData<DataState<QRModel>> get() = _dataStateQR
    private val _dataStateRole: MutableLiveData<DataState<String>> = MutableLiveData()
    val dataStateRole: LiveData<DataState<String>> get() = _dataStateRole

    fun getRole() {
        viewModelScope.launch {
            userRepository.getRole().collect {
                _dataStateRole.postValue(it)
            }
        }
    }

    fun getQR() {
        val queue = Volley.newRequestQueue(getApplication())

        val request = JsonObjectRequest("http://foravalon.com/getcode.php", null, {
            val qrList = it.getJSONArray("qr")
            val qr = qrList.get(0) as JSONObject
            val qrModel = QRModel(
                    code = qr.getString("code"),
                    date = qr.getString("date"),
            )

            _dataStateQR.postValue(DataState.Success(qrModel))
        }, {
            _dataStateQR.postValue(DataState.Error(it))
        })

        queue.add(request)
    }
}