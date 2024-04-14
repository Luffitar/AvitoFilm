package com.example.avitofilm.presentation.detailsFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avitofilm.R
import com.example.avitofilm.data.model.FilmSeasonsData
import com.example.avitofilm.databinding.SeasonsItemBinding
import com.example.avitofilm.utils.Constants

class DetailsFilmFragmentSeasonsAdapter : PagingDataAdapter<FilmSeasonsData,
        DetailsFilmFragmentSeasonsAdapter.ViewHolder>(DiffUtilCallBack) {
    override fun onBindViewHolder(
        holder: DetailsFilmFragmentSeasonsAdapter.ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsFilmFragmentSeasonsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.seasons_item, parent, false
        )
        val binding = SeasonsItemBinding.bind(view)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: SeasonsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmSeasonsData) {

            fun loadSeasons() {
                binding.apply {
                    nameSeasonsTextView.text = item.name ?: item.enName ?: Constants.NO_INFO
                    episodesCountSeasonsTextView.text =
                        "Всего серий: ${item.episodesCount ?: Constants.NO_INFO}"
                }
            }

            loadSeasons()

            binding.buttonError.setOnClickListener {
                loadSeasons()
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<FilmSeasonsData>() {
        override fun areItemsTheSame(oldItem: FilmSeasonsData, newItem: FilmSeasonsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmSeasonsData, newItem: FilmSeasonsData): Boolean {
            return oldItem == newItem
        }
    }
}