package com.aaron.advancebookings.ui.screens.infoscreen

import com.aaron.advancebookings.R
import com.aaron.advancebookings.data.DataRepository
import com.aaron.advancebookings.di.appresources.AppResources
import com.aaron.advancebookings.framework.baseviewmodel.BaseViewModel
import com.aaron.advancebookings.ui.common.screenstate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class InfoScreenViewModel  @Inject constructor(
    private val appResources: AppResources,
    private val dataRepository: DataRepository
) : BaseViewModel(dataRepository) {
    private val _screenState = MutableStateFlow(getUiInitialState())
    val screenState = _screenState.asStateFlow()
    private val _progressState = MutableStateFlow<UIState>(UIState.Success)
    val progressState = _progressState.asStateFlow()

    private fun getUiInitialState(): InfoScreenUIStates {
        return InfoScreenUIStates(
            infoHeader = appResources.getString(R.string.info_header),
            infoDetailList = appResources.getStringList(R.array.info_detail_list).toList(),
            infoFooter = appResources.getStringList(R.array.info_footer).toList()
        )
    }
    fun onInfoScreenEvent(events: InfoScreenUIEvents) {
        when (events) {
            else -> {}
        }
    }
}