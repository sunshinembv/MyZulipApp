package com.example.myzulipapp.contacts.domain.usecases

import com.example.myzulipapp.contacts.domain.model.User
import com.example.myzulipapp.contacts.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun execute(): List<User> {
        return repository.getAllUsers()
    }
}
