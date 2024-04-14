package com.example.avitofilm.presentation.detailsFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avitofilm.R
import com.example.avitofilm.data.model.FilmReviewData
import com.example.avitofilm.databinding.ReviewItemBinding
import com.example.avitofilm.utils.Constants

class DetailsFilmFragmentReviewsAdapter : PagingDataAdapter<FilmReviewData,
        DetailsFilmFragmentReviewsAdapter.ViewHolder>(DiffUtilCallBack) {
    override fun onBindViewHolder(
        holder: DetailsFilmFragmentReviewsAdapter.ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsFilmFragmentReviewsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.review_item, parent, false
        )
        val binding = ReviewItemBinding.bind(view)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmReviewData) {

            fun loadReview() {
                binding.apply {
                    authorReviewTextView.text = item.author ?: Constants.NO_INFO
                    titleReviewFilmTextView.text = item.title ?: Constants.NO_INFO
                    typeReviewFilmTextView.text = "Тип: ${item.type ?: Constants.NO_INFO}"
                    reviewFilmTextView.text = item.review ?: Constants.NO_INFO
                    dateFilmTextView.text = "Дата: ${item.date ?: Constants.NO_INFO}"
                }
            }

            loadReview()

            binding.buttonError.setOnClickListener {
                loadReview()
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<FilmReviewData>() {
        override fun areItemsTheSame(oldItem: FilmReviewData, newItem: FilmReviewData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmReviewData, newItem: FilmReviewData): Boolean {
            return oldItem == newItem
        }
    }
}