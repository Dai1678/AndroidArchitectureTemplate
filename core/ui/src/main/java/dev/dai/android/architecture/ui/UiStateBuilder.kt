package dev.dai.android.architecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

const val STOP_TIMEOUT_MILLIS = 5_000L

fun <T1, R> ViewModel.buildUiState(
  flow: StateFlow<T1>,
  transform: (T1) -> R,
): StateFlow<R> = flow.map(transform = transform)
  .stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
    initialValue = transform(
      flow.value,
    ),
  )
