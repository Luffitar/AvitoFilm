package com.example.avitofilm.presentation.detailsFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avitofilm.R
import com.example.avitofilm.data.model.FilmPosterData
import com.example.avitofilm.databinding.PosterItemBinding
import com.example.avitofilm.utils.loadFilm

class DetailsFilmFragmentPosterAdapter : PagingDataAdapter<FilmPosterData,
        DetailsFilmFragmentPosterAdapter.ViewHolder>(DiffUtilCallBack) {
    override fun onBindViewHolder(
        holder: DetailsFilmFragmentPosterAdapter.ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.poster_item, parent, false
        )
        val binding = PosterItemBinding.bind(view)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: PosterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmPosterData) {

            fun loadPoster() {
                binding.apply {
                    filmPosterView.loadFilm(
                        url = item.url,
                        id = item.id,
                        progressBar = progressBar,
                        buttonError = buttonError
                    )
                }
            }

            loadPoster()

            binding.buttonError.setOnClickListener {
                loadPoster()
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<FilmPosterData>() {
        override fun areItemsTheSame(oldItem: FilmPosterData, newItem: FilmPosterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmPosterData, newItem: FilmPosterData): Boolean {
            return oldItem == newItem
        }
    }

}