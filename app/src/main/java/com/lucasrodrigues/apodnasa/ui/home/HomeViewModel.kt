package com.lucasrodrigues.apodnasa.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lucasrodrigues.apodnasa.domain.interactor.ListenToPreviousDaysApods
import com.lucasrodrigues.apodnasa.domain.interactor.ListenToTodayApod
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    listenToPreviousDaysApods: ListenToPreviousDaysApods,
    listenToTodayApod: ListenToTodayApod,
) : ViewModel() {

    val todayApod = listenToTodayApod(Unit).asLiveData()

    val previousApods = listenToPreviousDaysApods(Unit)
        .cachedIn(viewModelScope)
}