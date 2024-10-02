package com.aaron.advancebookings.framework.baseviewmodel

import androidx.lifecycle.ViewModel
import com.aaron.advancebookings.data.DataRepository
import com.aaron.advancebookings.utilities.helper.StoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel(){
    val dataStoreHelper by lazy {
        StoreHelper(dataRepository.getAppDataStore())
    }
}