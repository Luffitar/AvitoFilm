package com.example.avitofilm.presentation.filterFilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.avitofilm.databinding.FragmentFiltersBinding

class FilterFragment : Fragment(){
    private var binding: FragmentFiltersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            confirmButton.setOnClickListener {
                applyFilters()
            }
        }
    }

    private fun applyFilters() {
        val selectedYear = binding?.yearEditText?.text.toString()
        val selectedCountries = binding?.countriesEditText?.text.toString()
        val selectedAgeRating = binding?.ageRatingEditText?.text.toString()

        val result = Bundle().apply {
            putString("selectedYear", selectedYear)
            putString("selectedCountries", selectedCountries)
            putString("selectedAgeRating", selectedAgeRating)
        }
        parentFragmentManager.setFragmentResult("filterKey", result)
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}