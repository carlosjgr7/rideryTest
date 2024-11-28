package com.carlosjgr7.riderytest.login.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carlosjgr7.riderytest.R
import com.carlosjgr7.riderytest.core.Resources
import com.carlosjgr7.riderytest.databinding.FragmentLoginBinding
import com.carlosjgr7.riderytest.login.presentation.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var job: Job? = null

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkInfo()

       job = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.checkLogin.collect {
                if (it){
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
            }
        }


        changeButtonName()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), viewModel.configureGoogleSignIn())

        binding.btnLoginGoogle.setOnClickListener {
            signIn()
        }

        lifecycleScope.launch {
            viewModel.loginState.collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        if (resource.data)
                            Log.i("STATUS", "Loading: CARGANDO ")

                    }

                    is Resources.Success -> {
                        Log.i("STATUS", "SUCCESS: ")
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    }

                    is Resources.Failure -> {
                        Log.i("STATUS", "FAIL: ERROR ")
                    }
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        if (completedTask.isSuccessful) {
            val account = completedTask.result

            viewModel.loginWithGoogle(account)
        } else {
            Log.e("handleSignInResult", "Error en el inicio de sesión", completedTask.exception)
        }
    }

    private fun changeButtonName() {
        for (i in 0 until binding.btnLoginGoogle.childCount) {
            val v = binding.btnLoginGoogle.getChildAt(i)
            if (v is TextView) {
                v.text = "Iniciar sesión"
                break
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        _binding = null
    }
}