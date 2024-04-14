package com.example.avitofilm.presentation.listFilms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.avitofilm.data.FilmsRepository
import javax.inject.Inject

class FilmsViewModelFactory @Inject constructor(
    private val repository: FilmsRepository
) : ViewModelProvider.Factory  {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == FilmsViewModel::class.java)
        return FilmsViewModel(repository) as T
    }
}