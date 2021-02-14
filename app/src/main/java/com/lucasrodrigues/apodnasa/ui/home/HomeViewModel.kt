package com.lucasrodrigues.apodnasa.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apodRepository: ApodRepository,
) : ViewModel() {

    val previousApods = apodRepository
        .getApodPaginatedList()
        .cachedIn(viewModelScope)
}