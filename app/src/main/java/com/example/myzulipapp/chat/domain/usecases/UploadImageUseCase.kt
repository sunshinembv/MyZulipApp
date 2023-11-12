package com.example.myzulipapp.chat.domain.usecases

import android.net.Uri
import com.example.myzulipapp.chat.domain.repository.MessageRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: MessageRepository) {

    suspend fun execute(fileUri: Uri): String {
        return repository.uploadImage(fileUri)
    }
}
