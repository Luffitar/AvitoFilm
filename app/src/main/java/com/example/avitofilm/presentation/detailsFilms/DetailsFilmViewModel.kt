package com.example.avitofilm.presentation.detailsFilms

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.avitofilm.data.FilmsRepository
import com.example.avitofilm.data.model.FilmPersonsData
import com.example.avitofilm.data.model.FilmPosterData
import com.example.avitofilm.data.model.FilmReviewData
import com.example.avitofilm.data.model.FilmSeasonsData
import com.example.avitofilm.data.model.FilmsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFilmViewModel @Inject constructor(private val repository: FilmsRepository,
                                               state: SavedStateHandle) : ViewModel() {
    val detailsFilm: LiveData<FilmsData> = state.getLiveData<FilmsData>("film")

    val filmsPosterListData: Flow<PagingData<FilmPosterData>> = repository
        .getPosterFilmsFlow()
        .cachedIn(viewModelScope)

    val filmPersonsListData: Flow<PagingData<FilmPersonsData>> = repository
        .getPersonsFilmsFlow()
        .cachedIn(viewModelScope)

    val filmReviewsListData: Flow<PagingData<FilmReviewData>> = repository
        .getReviewsFilmsFlow()
        .cachedIn(viewModelScope)

    val filmSeasonsListData: Flow<PagingData<FilmSeasonsData>> = repository
        .getSeasonsFilmsFlow()
        .cachedIn(viewModelScope)

    init {
        collectPoster()
        collectPersons()
        collectReviews()
        collectSeasons()
    }
    private fun collectPoster(){
        viewModelScope.launch {
            detailsFilm.value?.id?.let { id ->
                val idsArray = arrayOf(id)
                repository.searchPosterFilms(idsArray)}
        }
    }

    private fun collectPersons(){
        viewModelScope.launch {
            detailsFilm.value?.id?.let { id ->
                val idsArray = arrayOf(id)
                repository.searchPersonsFilms(idsArray)}
        }
    }

    private fun collectReviews(){
        viewModelScope.launch {
            detailsFilm.value?.id?.let { id ->
                val idsArray = arrayOf(id)
                repository.searchReviewsFilms(idsArray)}
        }
    }

    private fun collectSeasons(){
        viewModelScope.launch {
            detailsFilm.value?.id?.let { id ->
                val idsArray = arrayOf(id)
                repository.searchSeasonsFilms(idsArray)}
        }
    }
}