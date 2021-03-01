package com.avalon.vflapp.ui.hub

import android.content.Context.WINDOW_SERVICE
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avalon.vflapp.R
import com.avalon.vflapp.databinding.FragmentQRBinding
import com.avalon.vflapp.util.DataState
import com.google.zxing.WriterException
import kotlin.math.min

private const val TAG = "QRFragment"

class QRFragment : Fragment() {

    private var _binding: FragmentQRBinding? = null

    private val binding get() = _binding

    private val viewModel: QRViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQRBinding.inflate(inflater, container, false)
        val view = binding?.root
        subscribeObservers()
        viewModel.getQR()
        return view
    }

    private fun subscribeObservers() {
        viewModel.code.observe(requireActivity()) { dataState ->
            when(dataState) {
                is DataState.Success -> createQR(dataState.data)
                
                is DataState.Error -> {
                    Log.d(TAG, "subscribeObservers: ${dataState.exception}")
                    toast("İstek başarısız")
                }
            }
        }
    }

    private fun createQR(code: String) {
        val display = requireActivity().display
        val point = Point()
        val windowManager = requireActivity().getSystemService(WINDOW_SERVICE) as WindowManager
        display?.getSize(point)

        val w = point.x
        val h = point.y
        val dimen = min(w, h) * 3 / 4

        val qrgEncoder = QRGEncoder(code, null, QRGContents.Type.TEXT, dimen)
        try {
            val bitmap = qrgEncoder.encodeAsBitmap()
            binding?.qrContainer?.setImageBitmap(bitmap)
        }catch (e: WriterException) {
            toast("Karekod oluşturulamadı")
            Log.d(TAG, "createQR: $e")
        }

    }

    private fun toast(mes: String) = Toast.makeText(requireActivity(), mes, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}