package com.lucasrodrigues.apodnasa.ui.apod_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lucasrodrigues.apodnasa.domain.interactor.ListenToApod
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApodDetailsViewModel @Inject constructor(
    listenToApod: ListenToApod,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val apod = listenToApod(
        params = ListenToApod.Params(
            timestamp = savedStateHandle.get<Long>("apodTimestamp")!!
        )
    ).asLiveData()
}