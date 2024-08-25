package dev.dai.android.architecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

fun <T1, T2, R> ViewModel.buildUiState(
  flow: StateFlow<T1>,
  flow2: StateFlow<T2>,
  transform: (T1, T2) -> R,
): StateFlow<R> = combine(
  flow = flow,
  flow2 = flow2,
  transform = transform,
).stateIn(
  scope = viewModelScope,
  started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
  initialValue = transform(
    flow.value,
    flow2.value,
  ),
)
