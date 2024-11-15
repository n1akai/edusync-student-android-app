package ma.n1akai.edusync.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ma.n1akai.edusync.data.network.responses.AuthResponse
import ma.n1akai.edusync.data.repository.AuthRepository
import ma.n1akai.edusync.util.ApiException
import ma.n1akai.edusync.util.NoInternetException
import ma.n1akai.edusync.util.TokenManager
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _login = MutableLiveData<UiState<AuthResponse>>();
    val login: LiveData<UiState<AuthResponse>>
        get() = _login

    fun login(email: String, password: String) {
        viewModelScope.safeLaunch({
            _login.value = UiState.Loading
            _login.value = repository.login(email, password)
        }) {
            _login.value = UiState.Failure(it)
        }
    }

}