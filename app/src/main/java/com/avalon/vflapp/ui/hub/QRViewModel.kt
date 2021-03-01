package com.avalon.vflapp.ui.hub

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.avalon.vflapp.util.DataState

private const val TAG = "QRViewModel"

class QRViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val _code = MutableLiveData<DataState<String>>()
    val code : LiveData<DataState<String>>
    get() = _code

    fun getQR() {
        val queue = Volley.newRequestQueue(getApplication())

        val request = StringRequest(Request.Method.GET, "http://foravalon.com/getcode.php",
            { res ->
                _code.postValue(DataState.Success(res))
                Log.d(TAG, "getQR: $res")
            }, {
                _code.postValue(DataState.Error(it))
                Log.d(TAG, "getQR: $it")
            })

        queue.add(request)
    }
}