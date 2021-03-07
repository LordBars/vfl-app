package com.avalon.vflapp.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.avalon.vflapp.R
import com.avalon.vflapp.databinding.FragmentAddUserBinding
import com.avalon.vflapp.domain.User
import com.avalon.vflapp.util.DataState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddUserFragment : Fragment() {

    private val viewModel: AdminViewModel by viewModels()

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private var role: String = "student"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)

        binding.addUserBtn.setOnClickListener { 
            val name = binding.addUserNameEdt.text.toString()
            val password = binding.addUserPasswordEdt.text.toString()
            var number: String = binding.addUserNumberEdt.text.toString()
            number = if (number.isBlank()) "0" else number
            val email = "validebag.$name@vflmail.com"
            val user = User(name, email , password, role, number.toInt())
            viewModel.addUser(user)
        }

        binding.addUserRadioContainer.setOnCheckedChangeListener { _, checkedId ->
            role = when(checkedId) {
                R.id.add_user_radio_admin ->  "admin"
                R.id.add_user_radio_parent ->  "parent"
                R.id.add_user_radio_teacher -> "teacher"
                R.id.add_user_radio_student -> "student"
                else -> "student"
            }
        }

        subscribeObservers()
        return binding.root
    }
    
    

    private fun subscribeObservers() {
        viewModel.dataStateAddRes.observe(viewLifecycleOwner) { dataState ->
            when(dataState) {
                is DataState.Success -> {
                    binding.addUserPb.visibility = View.GONE
                    Snackbar.make(binding.fragmentAddUserRoot, "Kullanıcı eklendi", 4000)
                        .show()
                }
                is DataState.Error -> {
                    binding.addUserPb.visibility = View.GONE
                    Snackbar.make(
                        binding.fragmentAddUserRoot,
                        "Hata oluştu.",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Tamam") {

                        }
                        .show()
                }
                is DataState.Cancel -> {
                    binding.addUserPb.visibility = View.GONE
                    binding.addUserPb.visibility = View.GONE
                    Snackbar.make(binding.fragmentAddUserRoot, "İşlem iptal edildi", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Tamam") {
                            
                        }
                        .show()
                }
                is DataState.Loading -> binding.addUserPb.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}