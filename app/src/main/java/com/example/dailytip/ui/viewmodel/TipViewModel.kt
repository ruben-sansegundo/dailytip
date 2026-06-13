package com.example.dailytip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dailytip.data.local.TipEntity
import com.example.dailytip.data.repository.TipRepository
import com.example.dailytip.domain.DailyTipManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TipViewModel(
    private val repository: TipRepository,
    private val dailyTipManager: DailyTipManager
) : ViewModel() {

    val allTips: StateFlow<List<TipEntity>> = repository.allTips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _todayTip = MutableStateFlow<TipEntity?>(null)
    val todayTip: StateFlow<TipEntity?> = _todayTip.asStateFlow()

    private val _showAddSheet = MutableStateFlow(false)
    val showAddSheet: StateFlow<Boolean> = _showAddSheet.asStateFlow()

    init {
        loadTodayTip()
    }

    fun loadTodayTip() {
        viewModelScope.launch {
            _todayTip.value = dailyTipManager.getTodayTip()
        }
    }

    fun addTip(text: String, source: String?) {
        viewModelScope.launch {
            repository.insertTip(text, source)
            _todayTip.value = dailyTipManager.getTodayTip()
        }
    }

    fun deleteTip(tip: TipEntity) {
        viewModelScope.launch {
            repository.deleteTip(tip)
        }
    }

    fun setShowAddSheet(show: Boolean) {
        _showAddSheet.value = show
    }

    companion object {
        fun factory(repository: TipRepository, dailyTipManager: DailyTipManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    TipViewModel(repository, dailyTipManager) as T
            }
    }
}
