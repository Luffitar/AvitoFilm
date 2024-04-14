package com.example.avitofilm.presentation.listFilms

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.avitofilm.MainActivity
import com.example.avitofilm.R
import com.example.avitofilm.appComponent
import com.example.avitofilm.databinding.FragmentFilmsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmsFragment : Fragment() , MenuProvider{
    private val viewModel: FilmsViewModel by viewModels{
        viewModelFactory
    }

    @Inject
    lateinit var viewModelFactory: FilmsViewModelFactory

    private var binding: FragmentFilmsBinding? = null
    private var listAdapter: FilmsAdapter? = null

    private val pagingAdapter by lazy {
        FilmsAdapter{
            val action = FilmsFragmentDirections.actionFilmsFragmentToDetailsFilmFragment(it)
            view?.let { view ->
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("filterKey"){ _, bundle ->
            viewModel.setFilters(bundle)}
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmsBinding.inflate(inflater, container, false)
        (requireActivity() as? MainActivity)?.setSupportActionBar( binding?.toolbar)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        setupObservers()
        setupListeners()
        setupUI()
    }

    override fun onDestroyView() {
        binding = null
        listAdapter = null

        super.onDestroyView()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.filters -> {
                Log.e("ERROR", "TAP")
                val action = FilmsFragmentDirections.actionFilmsFragmentToFilterFragment()
                view?.let { view ->
                    Navigation.findNavController(view).navigate(action)
                }
                true
            }
            else -> false
        }
    }

    private fun setupListeners() {
        binding?.apply {

            retryButton.setOnClickListener {
                pagingAdapter.retry()
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.updateQuerySearch(p0 ?: "")
                    return true
                }

            })

            pagingAdapter.addLoadStateListener {
                progressBar.isVisible = it.refresh is LoadState.Loading
                retryButton.isVisible = it.refresh is LoadState.Error
                errorTextView.isVisible = it.refresh is LoadState.Error
            }

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    searchView.clearFocus()
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    private fun setupObservers() {

        lifecycleScope.launch {
            viewModel.filmsListData.collectLatest {
                pagingAdapter.submitData(it)
            }
        }

    }

    private fun setupUI() {
        binding?.recyclerView?.adapter = pagingAdapter
    }

}