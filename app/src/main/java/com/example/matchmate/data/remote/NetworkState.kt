package com.example.matchmate.data.remote

sealed class NetworkState {
    object Loading : NetworkState()
    object Success : NetworkState()
    data class Error(val message: String) : NetworkState()
}