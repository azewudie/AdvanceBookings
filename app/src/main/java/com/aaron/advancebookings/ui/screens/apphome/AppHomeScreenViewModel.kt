package com.aaron.advancebookings.ui.screens.apphome

import com.aaron.advancebookings.R
import com.aaron.advancebookings.data.DataRepository
import com.aaron.advancebookings.di.appresources.AppResources
import com.aaron.advancebookings.framework.baseviewmodel.BaseViewModel
import com.aaron.advancebookings.navigation.AppScreens
import com.aaron.advancebookings.ui.common.screenstate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppHomeScreenViewModel @Inject constructor(
    private val appResources: AppResources,
    dataRepository: DataRepository
) : BaseViewModel(dataRepository) {
    private val _screenState = MutableStateFlow(getUiInitialState())
    val screenState = _screenState.asStateFlow()
    private val _progressState = MutableStateFlow<UIState>(UIState.Success)
    val progressState = _progressState.asStateFlow()

    private fun getUiInitialState(): AppHomeScreenUIStates {
        return AppHomeScreenUIStates(
            appHomeHeader = appResources.getString(R.string.app_home_header),
            appHomeDetailList = appResources.getStringList(R.array.app_home_detail_list).toList(),
            appHomeFooter = appResources.getString(R.string.app_home_footer),
            appHomeClientButton = appResources.getString(R.string.button_client_text),
            appHomeProviderButton = appResources.getString(R.string.button_provider_text),
        )
    }

    fun onAppHomeScreenEvent(events: AppHomeScreenUIEvents) {
        when (events) {
            is AppHomeScreenUIEvents.OnClickGetStartClient ->{
                events.navController.navigate(AppScreens.Client.route)
            }

            is AppHomeScreenUIEvents.OnClickGetStartProvider ->{
                events.navController.navigate(AppScreens.Provider.route)

            }
        }
    }

}