package com.example.avitofilm.presentation.listFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avitofilm.R
import com.example.avitofilm.data.model.FilmsData
import com.example.avitofilm.databinding.FilmItemBinding
import com.example.avitofilm.utils.Constants.NO_INFO
import com.example.avitofilm.utils.loadFilm

class FilmsAdapter(private val onClick: (item: FilmsData) -> Unit) :
    PagingDataAdapter<FilmsData, FilmsAdapter.ViewHolder>(DiffUtilCallBack){
    override fun onBindViewHolder(holder: FilmsAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.film_item, parent, false
        )
        val binding = FilmItemBinding.bind(view)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmsData) {

            fun loadFilm() {
                binding.apply {
                    filmView.loadFilm(
                        url = item.poster.previewUrl,
                        id = item.id,
                        progressBar = progressBar,
                        buttonError = buttonError
                    )
                    nameFilmTextView.text = item.name ?: NO_INFO
                    yearFilmTextView.text = item.year.toString()
                    val countries = item.countries.joinToString { it.name }
                    val genres = item.genres.joinToString { it.name }
                    countryFilmTextView.text = countries
                    genreFilmTextView.text = genres

                }
            }

            loadFilm()

            binding.cardView.setOnClickListener {
                onClick.invoke(item)
            }
            binding.buttonError.setOnClickListener {
                loadFilm()
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<FilmsData>() {
        override fun areItemsTheSame(oldItem: FilmsData, newItem: FilmsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmsData, newItem: FilmsData): Boolean {
            return oldItem == newItem
        }
    }

}