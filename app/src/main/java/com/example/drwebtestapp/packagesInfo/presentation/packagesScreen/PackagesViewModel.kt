package com.example.drwebtestapp.packagesInfo.presentation.packagesScreen

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.drwebtestapp.packagesInfo.data.model.AppInfo
import com.example.drwebtestapp.packagesInfo.domain.PackagesRepository
import kotlinx.coroutines.launch

class PackagesViewModel(private val repository: PackagesRepository) : ViewModel() {
    companion object {
        fun provideFactory(
            repository: PackagesRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return PackagesViewModel(repository) as T
                }
            }
    }

    private val _appsInfo = MutableLiveData<List<AppInfo>>()
    val appsInfo: LiveData<List<AppInfo>> get() = _appsInfo

    fun getApplicationsInfo(pm: PackageManager) {
        viewModelScope.launch {
            _appsInfo.value = repository.getPackagesList(pm)
        }
    }
}
