package com.avalon.vflapp.ui.hub

import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avalon.vflapp.databinding.FragmentQRBinding
import com.avalon.vflapp.util.DataState
import com.google.zxing.WriterException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.min

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class QRFragment : Fragment() {

    private var _binding: FragmentQRBinding? = null

    private val binding get() = _binding

    private val viewModel: HubViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQRBinding.inflate(inflater, container, false)
        subscribeObservers()
        viewModel.getQR()
        binding?.qrSwipeRefresh?.setOnRefreshListener {
          viewModel.getQR()
        }
        return binding?.root
    }

    private fun subscribeObservers() {
        viewModel.dataStateQR.observe(viewLifecycleOwner) { dataState ->
            when(dataState) {
                is DataState.Success -> {
                    binding?.qrDateTv?.text = dataState.data.date
                    binding?.qrSwipeRefresh?.isRefreshing = false
                    createQR(dataState.data.code)
                }

                is DataState.Error -> {
                    binding?.qrSwipeRefresh?.isRefreshing = false
                    toast("İstek başarısız")
                }
                is DataState.Cancel -> {
                    binding?.qrSwipeRefresh?.isRefreshing = false
                    toast("İstek iptal edildi")
                }
                else ->  binding?.qrSwipeRefresh?.isRefreshing = true
            }
        }
    }

    private fun createQR(code: String) {
        val point = Point()

        val display = requireActivity().display
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
        }

    }

    private fun toast(mes: String) = Toast.makeText(requireActivity(), mes, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}