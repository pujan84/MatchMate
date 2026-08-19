package com.example.matchmate.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.matchmate.MatchMateApplication
import com.example.matchmate.data.local.MatchEntity
import com.example.matchmate.data.remote.NetworkState
import com.example.matchmate.data.repository.MatchRepository
import kotlinx.coroutines.launch

class MatchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MatchRepository

    var matches: LiveData<List<MatchEntity>>

    private val _state = MutableLiveData<NetworkState>()
    val state: LiveData<NetworkState> = _state

    private var currentPage = 0
    private var isLoading = false

    init {

        val dao = (application as MatchMateApplication)
            .database
            .matchDao()

        repository = MatchRepository(dao)

        matches = repository.allMatches

        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val count = repository.getDatabaseCount()
            if (count == 0) {
                fetchPage(1)
            } else {
                // Set currentPage based on records in DB (10 per page)
                currentPage = count / 10
            }
        }
    }

    fun loadNextPage() {
        if (isLoading) return
        fetchPage(currentPage + 1)
    }

    private fun fetchPage(page: Int) {
        isLoading = true
        viewModelScope.launch {
            _state.value = NetworkState.Loading

            val result = repository.loadUsers(page)

            if (result.isSuccess) {
                _state.value = NetworkState.Success
                currentPage = page
            } else {
                val exception = result.exceptionOrNull()
                val message = if (exception is java.io.IOException || 
                    exception?.message?.contains("Unable to resolve host", ignoreCase = true) == true) {
                    "No internet connection. Please check your network."
                } else {
                    exception?.message ?: "Unknown Error"
                }
                _state.value = NetworkState.Error(message)
            }

            isLoading = false
        }
    }

    fun accept(id: String) {
        viewModelScope.launch {
            repository.accept(id)
        }
    }
    fun retry() {
        fetchPage(currentPage + 1)
    }

    fun decline(id: String) {
        viewModelScope.launch {
            repository.decline(id)
        }
    }
}