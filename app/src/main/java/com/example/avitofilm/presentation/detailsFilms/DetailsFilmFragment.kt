package com.example.avitofilm.presentation.detailsFilms

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import com.example.avitofilm.appComponent
import com.example.avitofilm.databinding.FragmentDetailsFilmBinding
import com.example.avitofilm.utils.Constants.NO_INFO
import com.example.avitofilm.utils.loadFilm
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFilmFragment : Fragment() {

    private val viewModel: DetailsFilmViewModel by viewModels{
        viewModelFactory
    }

    @Inject
    lateinit var viewModelFactory: DetailsFilmViewModelFactory

    private var binding: FragmentDetailsFilmBinding? = null


    private var listPosterAdapter: DetailsFilmFragmentPosterAdapter? = null

    private var listPersonsAdapter: DetailsFilmFragmentPersonsAdapter? = null

    private var listReviewsAdapter: DetailsFilmFragmentReviewsAdapter? = null

    private var listSeasonsAdapter: DetailsFilmFragmentSeasonsAdapter? = null

    private val pagingPosterAdapter by lazy { DetailsFilmFragmentPosterAdapter() }

    private val pagingPersonsAdapter by lazy { DetailsFilmFragmentPersonsAdapter() }

    private val pagingReviewsAdapter by lazy { DetailsFilmFragmentReviewsAdapter() }

    private val pagingSeasonsAdapter by lazy { DetailsFilmFragmentSeasonsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsFilmBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        setupUI()
    }

    override fun onDestroyView() {
        binding = null
        listPosterAdapter = null
        listPersonsAdapter = null
        listReviewsAdapter = null
        listSeasonsAdapter = null

        super.onDestroyView()
    }

    private fun setupObservers() {
        viewModel.detailsFilm.observe(viewLifecycleOwner){
            binding?.apply {
                filmView.loadFilm(
                    url = it.poster.previewUrl,
                    progressBar = progressBar,
                    id = it.id,
                    buttonError = null
                )
                nameFilmTextView.text = it.name ?: NO_INFO
                descriptionFilmTextView.text = it.description ?: NO_INFO
                ratingKpFilmTextView.text = "KP: ${it.rating?.kp?.toString() ?: NO_INFO}"
                ratingImdbFilmTextView.text = "Imdb: ${it.rating?.imdb?.toString() ?: NO_INFO}"
                ratingFilmCriticsFilmTextView.text =
                    "Film critics: ${it.rating?.filmCritics?.toString() ?: NO_INFO}"
                ratingRussianFilmCriticsFilmTextView.text =
                    "Russian film critics: ${it.rating?.russianFilmCritics?.toString() ?: NO_INFO}"
            }
        }
        lifecycleScope.launch{
            viewModel.filmsPosterListData.collectLatest {
                pagingPosterAdapter.submitData(it)
            }
        }

        lifecycleScope.launch{
            viewModel.filmPersonsListData.collectLatest {
                pagingPersonsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch{
            viewModel.filmReviewsListData.collectLatest {
                pagingReviewsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch{
            viewModel.filmSeasonsListData.collectLatest {
                pagingSeasonsAdapter.submitData(it)
            }
        }

    }

    private fun setupListeners() {
        binding?.apply {

            retryButton.setOnClickListener {
                pagingPosterAdapter.retry()
                pagingPersonsAdapter.retry()
                pagingReviewsAdapter.retry()
                pagingSeasonsAdapter.retry()

            }

            pagingPosterAdapter.addLoadStateListener {
                progressBar.isVisible = it.refresh is LoadState.Loading
                retryButton.isVisible = it.refresh is LoadState.Error
                errorTextView.isVisible = it.refresh is LoadState.Error
                postersTitleFilmTextView.isVisible = pagingPosterAdapter.itemCount > 0
            }

            pagingPersonsAdapter.addLoadStateListener {
                progressBar.isVisible = it.refresh is LoadState.Loading
                retryButton.isVisible = it.refresh is LoadState.Error
                errorTextView.isVisible = it.refresh is LoadState.Error
                personsTitleFilmTextView.isVisible = pagingPersonsAdapter.itemCount > 0
            }

            pagingReviewsAdapter.addLoadStateListener {
                progressBar.isVisible = it.refresh is LoadState.Loading
                retryButton.isVisible = it.refresh is LoadState.Error
                errorTextView.isVisible = it.refresh is LoadState.Error
                reviewsTitleFilmTextView.isVisible = pagingReviewsAdapter.itemCount > 0
            }

            pagingSeasonsAdapter.addLoadStateListener {
                progressBar.isVisible = it.refresh is LoadState.Loading
                retryButton.isVisible = it.refresh is LoadState.Error
                errorTextView.isVisible = it.refresh is LoadState.Error
                seasonsTitleFilmTextView.isVisible = pagingSeasonsAdapter.itemCount > 0
            }

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

        }
    }

    private fun setupUI() {
        binding?.posterRecyclerView?.adapter = pagingPosterAdapter
        binding?.personsRecyclerView?.adapter = pagingPersonsAdapter
        binding?.reviewsRecyclerView?.adapter = pagingReviewsAdapter
        binding?.seasonsRecyclerView?.adapter = pagingSeasonsAdapter
    }
}