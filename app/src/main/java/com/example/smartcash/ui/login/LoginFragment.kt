package com.example.smartcash.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcash.R
import com.example.smartcash.databinding.FragmentLoginBinding
import com.example.smartcash.viewmodel.AuthViewModel
import com.example.smartcash.viewmodel.FinanceViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()
    private val financeViewModel: FinanceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener { performLogin() }
    }

    private fun performLogin() {
        val username = binding.etUsername.text?.trim().toString()
        val password = binding.etPassword.text?.trim().toString()

        if (authViewModel.login(username, password)) {
            financeViewModel.resetAll()
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
        } else {
            Snackbar.make(binding.root, R.string.invalid_credentials, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

