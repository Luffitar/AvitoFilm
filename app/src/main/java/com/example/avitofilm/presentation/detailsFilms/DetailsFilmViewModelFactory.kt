package com.example.avitofilm.presentation.detailsFilms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.avitofilm.data.FilmsRepository
import javax.inject.Inject

class DetailsFilmViewModelFactory @Inject constructor(
    private val repository: FilmsRepository,
) : ViewModelProvider.Factory  {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        require(modelClass == DetailsFilmViewModel::class.java)
        return DetailsFilmViewModel(repository, extras.createSavedStateHandle()) as T
    }
}