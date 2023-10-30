package com.example.mordovturizm.presentation.screen.sign_in

import android.os.Bundle
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
        
        binding.tvToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnSignIn.setOnClickListener {
            signIn(binding.TIETEmail.text.toString(), binding.TIETPassword.text.toString())
        }

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
            bundle.putString("email", binding.TIETEmail.text.toString())
            findNavController().navigate(R.id.action_signInFragment_to_mapFragment, bundle)
        }
    }

    private fun signIn(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.signIn(email, password)
    }
}
