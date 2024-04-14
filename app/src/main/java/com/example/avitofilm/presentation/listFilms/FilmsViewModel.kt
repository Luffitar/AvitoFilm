package com.example.avitofilm.presentation.listFilms

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.avitofilm.data.FilmsRepository
import com.example.avitofilm.data.model.FilmsData
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
class FilmsViewModel @Inject constructor(private val repository: FilmsRepository) : ViewModel() {

    private val querySearch = MutableSharedFlow<String>()
    private var query = ""
    private var year = arrayOf<Int>()
    private var countries = arrayOf<String>()
    private var ageRating = arrayOf<Int>()

    val filmsListData: Flow<PagingData<FilmsData>> = repository
        .getFilmsFlow()
        .cachedIn(viewModelScope)

    init {

        collectQuerySearch()

    }

    fun setFilters(bundle: Bundle){
        bundle.getString("selectedYear", "").toIntOrNull()?.let {
            year = arrayOf(it)
        } ?: run { year = arrayOf() }
        bundle.getString("selectedCountries", "").takeIf { it.isNotBlank() }?.let {
            countries = arrayOf(it)
        } ?: run { countries = arrayOf() }
        bundle.getString("selectedAgeRating", "").toIntOrNull()?.let {
            ageRating = arrayOf(it)
        } ?: run { ageRating = arrayOf() }
        repository.searchFilms(year = year,countries = countries, ageRating = ageRating ,
            query = query)
    }

    fun updateQuerySearch(query: String){
        viewModelScope.launch {
            querySearch.emit(query)
            this@FilmsViewModel.query = query
        }
    }

    private fun collectQuerySearch(){
        viewModelScope.launch {
            querySearch
                .debounce(1000)
                .distinctUntilChanged()
                .collect{ repository.searchFilms(it, year = year.also { Log.e("ERROR", it.firstOrNull().toString()) }, countries = countries,
                    ageRating = ageRating) }
        }
    }

}