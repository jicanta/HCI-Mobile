package com.example.hci_mobile.api.data.network

import com.example.hci_mobile.SessionManager
import com.example.hci_mobile.api.data.network.api.UserApiService
import com.example.hci_mobile.api.data.network.model.NetworkCode
import com.example.hci_mobile.api.data.network.model.NetworkCredentials
import com.example.hci_mobile.api.data.network.model.NetworkMovement
import com.example.hci_mobile.api.data.network.model.NetworkMovementUser
import com.example.hci_mobile.api.data.network.model.NetworkToken
import com.example.hci_mobile.api.data.network.model.NetworkUser

class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val userApiService: UserApiService
) : RemoteDataSource() {


    suspend fun register(firstName: String, lastName: String, birthDate: String , email: String, password: String): NetworkUser{
        return handleApiResponse { userApiService.register(NetworkUser(
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
            email = email,
            password = password,
            id = null
        )) }
    }

    suspend fun verify(token: String): NetworkUser{
        return handleApiResponse { userApiService.verify(NetworkCode(token)) }
    }

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            userApiService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse { userApiService.logout() }
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser(): NetworkMovementUser {
        return handleApiResponse { userApiService.getCurrentUser() }
    }
}