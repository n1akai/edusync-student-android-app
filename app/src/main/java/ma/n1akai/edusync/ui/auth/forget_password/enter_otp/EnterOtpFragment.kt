package ma.n1akai.edusync.ui.auth.forget_password.enter_otp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentEnterEmailBinding
import ma.n1akai.edusync.databinding.FragmentEnterOtpBinding
import ma.n1akai.edusync.ui.auth.forget_password.enter_email.EnterEmailFragmentDirections
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.navigate
import ma.n1akai.edusync.util.popBackStack
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class EnterOtpFragment : Fragment() {

    private val viewModel: EnterOtpViewModel by viewModels()
    private var _binding: FragmentEnterOtpBinding? = null
    private val binding get() = _binding!!
    private var email: String? = null
    private var otp: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verify()
        observer()

        // Cancel
        binding.forgetPasswordButtonCancel.popBackStack()

    }

    private fun verify() {
        email = arguments?.getString("email")
        otp = binding.forgetPasswordPinViewOtp.text.toString()

        binding.forgetPasswordButtonVerify.setOnButtonClickListener {
            viewModel.verifyOtp(email!!, otp!!)
        }
    }

    private fun observer() {
        viewModel.verifyOtp.observe(viewLifecycleOwner) {
            binding.forgetPasswordButtonVerify.apply {
                when(it) {
                    is UiState.Loading -> {
                        showProgress()
                    }
                    is UiState.Success -> {
                        hideProgress()
                        findNavController()
                            .navigate(EnterOtpFragmentDirections
                                .actionEnterOtpFragmentToChangePasswordFragment())
                    }
                    is UiState.Failure -> {
                        hideProgress()
                        snackbar(it.error!!)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}