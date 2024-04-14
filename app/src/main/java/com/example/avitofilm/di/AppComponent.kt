package com.example.avitofilm.di

import com.example.avitofilm.presentation.detailsFilms.DetailsFilmFragment
import com.example.avitofilm.presentation.listFilms.FilmsFragment
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {
    fun inject(filmsFragment: FilmsFragment)
    fun inject(detailsFilmFragment: DetailsFilmFragment)
}