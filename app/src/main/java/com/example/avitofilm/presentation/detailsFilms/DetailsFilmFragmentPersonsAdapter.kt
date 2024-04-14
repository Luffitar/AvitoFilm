package com.example.avitofilm.presentation.detailsFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avitofilm.R
import com.example.avitofilm.data.model.FilmPersonsData
import com.example.avitofilm.databinding.PersonItemBinding
import com.example.avitofilm.utils.Constants.NO_INFO
import com.example.avitofilm.utils.loadFilm

class DetailsFilmFragmentPersonsAdapter : PagingDataAdapter<FilmPersonsData,
        DetailsFilmFragmentPersonsAdapter.ViewHolder>(DiffUtilCallBack) {
    override fun onBindViewHolder(
        holder: DetailsFilmFragmentPersonsAdapter.ViewHolder,
        position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.person_item, parent, false
        )
        val binding = PersonItemBinding.bind(view)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmPersonsData) {

            fun loadPoster() {
                binding.apply {
                    personView.loadFilm(
                        url = item.photo,
                        id = item.id,
                        progressBar = progressBar,
                        buttonError = buttonError
                    )
                    namePersonTextView.text = item.name ?: item.enName ?: NO_INFO
                    agePersonFilmTextView.text = "Возраст: ${item.age?.toString() ?: NO_INFO}"
                    sexPersonFilmTextView.text = "Пол: ${item.sex ?: NO_INFO}"
                }
            }

            loadPoster()

            binding.buttonError.setOnClickListener {
                loadPoster()
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<FilmPersonsData>() {
        override fun areItemsTheSame(oldItem: FilmPersonsData, newItem: FilmPersonsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmPersonsData, newItem: FilmPersonsData): Boolean {
            return oldItem == newItem
        }
    }
}