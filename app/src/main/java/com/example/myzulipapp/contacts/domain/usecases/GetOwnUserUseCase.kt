package com.example.myzulipapp.contacts.domain.usecases

import com.example.myzulipapp.contacts.domain.model.User
import com.example.myzulipapp.contacts.domain.repository.UserRepository
import javax.inject.Inject

class GetOwnUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun execute(): User {
        return repository.getOwnUsers()
    }
}
