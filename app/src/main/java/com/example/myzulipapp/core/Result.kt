package com.example.myzulipapp.core

data class Result<C : Command>(
    val command: C?,
)
