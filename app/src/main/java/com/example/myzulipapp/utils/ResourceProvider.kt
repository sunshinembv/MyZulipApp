package com.example.myzulipapp.utils

import android.content.ContentResolver
import android.content.Context
import java.io.File
import javax.inject.Inject

interface ResourceProvider {
    fun getContentResolver(): ContentResolver

    fun getExternalFilesDir(dirName: String): File?
}

class ResourceProviderImpl @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun getContentResolver(): ContentResolver {
        return context.contentResolver
    }

    override fun getExternalFilesDir(dirName: String): File? {
        return context.getExternalFilesDir(dirName)
    }
}
