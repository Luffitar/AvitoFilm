package com.example.avitofilm.utils

import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import coil.load
import com.example.avitofilm.R
import com.example.avitofilm.utils.Constants.DEFAULT_IMAGE

fun ImageView.loadFilm(
    url: String?,
    id: String?,
    progressBar: ProgressBar?,
    buttonError: Button?
) {

    load(url) {
        crossfade(true)
        error(R.drawable.ic_launcher_background)
        listener(
            onStart = {
                progressBar?.isVisible = true
                buttonError?.isVisible = false
            },
            onSuccess = { _, _ ->
                progressBar?.isVisible = false
                buttonError?.isVisible = false
            },
            onError = { _, _ ->
                if(id == null){
                    progressBar?.isVisible = false
                    buttonError?.isVisible = true
                }
            }
        )
    }
}