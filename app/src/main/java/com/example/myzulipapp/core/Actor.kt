package com.example.myzulipapp.core

interface Actor<C : Command, IE : Event> {
    suspend fun execute(command: C, onEvent: (IE) -> Unit)
}
