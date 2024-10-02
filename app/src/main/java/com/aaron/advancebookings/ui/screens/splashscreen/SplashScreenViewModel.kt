package com.aaron.advancebookings.ui.screens.splashscreen

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
class SplashScreenViewModel @Inject constructor(
    private val appResources: AppResources,
    dataRepository: DataRepository
): BaseViewModel(dataRepository){
    private val _screenState = MutableStateFlow(getUiInitialState())
     val screenState = _screenState.asStateFlow()

    private fun getUiInitialState(): SplashScreenUIStates {
        return SplashScreenUIStates(
            appLogo = appResources.getString(R.string.app_logo),
            appHeaderText = appResources.getString(R.string.app_header_text)
        )
    }
}