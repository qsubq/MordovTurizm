package com.example.mordovturizm.presentation.screen.sign_up

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
import com.example.mordovturizm.databinding.FragmentSignUpBinding
import com.example.mordovturizm.presentation.utils.DialogError

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.isEnabled = false
        binding.tvToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        binding.btnSignUp.setOnClickListener {
            signUp(
                binding.TIETEmail.text.toString(),
                binding.TIETPassword.text.toString(),
                binding.TIETName.text.toString(),
            )
        }

        editTextValidation()
        observeViewModel()
    }

    private fun editTextValidation() {
        binding.TIETEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.TIETEmail.text.isNullOrEmpty()) {
                    binding.TILEmail.helperText = null
                    binding.btnSignUp.isEnabled = false
                } else {
                    binding.TILEmail.helperText = submitEmail()

                    binding.btnSignUp.isEnabled = binding.TILEmail.helperText.isNullOrEmpty() &&
                        binding.TILPassword.helperText.isNullOrEmpty() &&
                        !binding.TIETName.text.isNullOrEmpty() &&
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
                    binding.btnSignUp.isEnabled = false
                } else {
                    binding.TILPassword.helperText = submitPassword()

                    binding.btnSignUp.isEnabled = binding.TILEmail.helperText.isNullOrEmpty() &&
                        binding.TILPassword.helperText.isNullOrEmpty() &&
                        !binding.TIETName.text.isNullOrEmpty() &&
                        !binding.TIETPassword.text.isNullOrEmpty()
                }
            }
        })

        binding.TIETName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSignUp.isEnabled = binding.TILEmail.helperText.isNullOrEmpty() &&
                    binding.TILPassword.helperText.isNullOrEmpty() &&
                    !binding.TIETName.text.isNullOrEmpty() &&
                    !binding.TIETPassword.text.isNullOrEmpty()
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

        viewModel.signUpLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE

            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
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
            "Invalid email"
        } else {
            null
        }
    }

    private fun signUp(email: String, password: String, fullName: String) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.signUp(email, password, fullName)
    }
}
