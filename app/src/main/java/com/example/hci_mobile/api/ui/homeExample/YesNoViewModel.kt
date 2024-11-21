package com.example.hci_mobile.api.ui.homeExample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobile.api.data.model.YesNoAnswer
import com.example.hci_mobile.api.data.network.YesNoApi
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface YesNoUiState {
    data class Success(val answer: YesNoAnswer): YesNoUiState
    data object Error: YesNoUiState
    data object Loading: YesNoUiState
}

class YesNoViewModel: ViewModel() {
    var yesNoUiState: YesNoUiState by mutableStateOf(YesNoUiState.Loading) //puedo ver el estado, arranca con el loading
        private set

    init {
        getAnswer()
    }

    fun getAnswer() {
        viewModelScope.launch {  //llamada a la api, hago que se ejecute en una corutina xq si no da error
            yesNoUiState = YesNoUiState.Loading
            yesNoUiState = try {
                val result = YesNoApi.retrofitService.getAnswer()
                YesNoUiState.Success(result) //actualizo el estado, como esta monitoreado por una funcion Comp se recarga todo
            } catch (e: IOException) {
                YesNoUiState.Error
            }
            catch (e: HttpException) {
                YesNoUiState.Error
            }
        }
    }
}