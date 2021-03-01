package com.avalon.vflapp.ui.hub

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.avalon.vflapp.ui.model.QRModel
import com.avalon.vflapp.util.DataState
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

private const val TAG = "QRViewModel"

class QRViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val _code = MutableLiveData<DataState<QRModel>>()
    val code : LiveData<DataState<QRModel>>
    get() = _code

    fun getQR() {
        val queue = Volley.newRequestQueue(getApplication())

        val request = JsonObjectRequest("http://foravalon.com/getcode.php", null, {
            val qrList = it.getJSONArray("qr")
            val qr = qrList.get(0) as JSONObject
            val qrModel = QRModel(
                    code = qr.getString("code"),
                    date = qr.getString("date"),
            )

            _code.postValue(DataState.Success(qrModel))
            Log.d(TAG, "getQR: qrModel: $qrModel")
        }, {
            _code.postValue(DataState.Error(it))
            Log.d(TAG, "getQR: $it")
        })

        queue.add(request)
    }
}