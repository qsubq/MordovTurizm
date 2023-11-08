package com.example.mordovturizm.presentation.screen.sign_in

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mordovturizm.R
import com.example.mordovturizm.databinding.FragmentSignInBinding
import com.example.mordovturizm.presentation.utils.DialogError

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isSignedIn()

        binding.tvToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnSignIn.setOnClickListener {
            signIn(binding.TIETEmail.text.toString(), binding.TIETPassword.text.toString())
        }
        observeViewModel()
        validateEditText()
    }

    private fun validateEditText() {
        binding.TIETEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.TIETEmail.text.isNullOrEmpty()) {
                    binding.TILEmail.helperText = null
                    binding.btnSignIn.isEnabled = false
                } else {
                    binding.TILEmail.helperText = submitEmail()

                    binding.btnSignIn.isEnabled = binding.TILEmail.helperText.isNullOrEmpty() &&
                        binding.TILPassword.helperText.isNullOrEmpty() &&
                        !binding.TIETPassword.text.isNullOrEmpty()
                }
            }
        })

        binding.TIETPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.TIETPassword.text.isNullOrEmpty()) {
                    binding.TILPassword.helperText = null
                    binding.btnSignIn.isEnabled = false
                } else {
                    binding.TILPassword.helperText = submitPassword()

                    binding.btnSignIn.isEnabled = binding.TILEmail.helperText.isNullOrEmpty() &&
                        binding.TILPassword.helperText.isNullOrEmpty() &&
                        !binding.TIETPassword.text.isNullOrEmpty()
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE

            DialogError("Some server error", it).show(
                requireActivity().supportFragmentManager,
                "DialogError",
            )
        }

        viewModel.networkLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            DialogError("Out of connection", "Check your network").show(
                requireActivity().supportFragmentManager,
                "DialogError",
            )
        }

        viewModel.signInLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE

            val bundle = Bundle()
            bundle.putString("email", it)
            findNavController().navigate(R.id.action_signInFragment_to_mapFragment, bundle)
        }
    }

    private fun signIn(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.signIn(email, password)
    }

    private fun submitPassword(): String? {
        return if (binding.TIETPassword.text.toString().length < 8) {
            "Пароль должен содержать минимум 8 символов"
        } else {
            null
        }
    }

    private fun submitEmail(): String? {
        return if (!Patterns.EMAIL_ADDRESS.matcher(binding.TIETEmail.text.toString()).matches()) {
            "Неправильно указан email"
        } else {
            null
        }
    }
}
