package com.example.hci_mobile.api.data.repository

import com.example.hci_mobile.api.data.model.User
import com.example.hci_mobile.api.data.network.UserRemoteDataSource
import com.example.hci_mobile.api.data.network.model.NetworkUser
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {

    // Mutex to make writes to cached values thread-safe.
    private val currentUserMutex = Mutex()
    // Cache of the current user got from the network.
    private var currentUser: User? = null

    suspend fun register(firstName: String, lastName: String, birthDate: String , email: String, password: String): User {
        val result = remoteDataSource.register(firstName, lastName, birthDate, email, password)
        return result.asModel()
    }

    suspend fun verify(token: String): User {
        val result = remoteDataSource.verify(token)
        return result.asModel()
    }

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun getCurrentUser(refresh: Boolean) : User? {
        if (refresh || currentUser == null) {
            val result = remoteDataSource.getCurrentUser()

            currentUserMutex.withLock {
                this.currentUser = result.asModel()
            }
        }

        return currentUserMutex.withLock { this.currentUser }
    }
}