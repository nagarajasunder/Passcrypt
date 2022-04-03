package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.*
import com.geekydroid.passcrypt.Utils.SortPreference
import com.geekydroid.passcrypt.Utils.UserPreferences
import com.geekydroid.passcrypt.repository.HomeFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeFragment"

@HiltViewModel
class HomeFragmentViewmodel @Inject constructor(
    repository: HomeFragmentRepository,
    private val preferences: UserPreferences
) :
    ViewModel() {

    val sortPreferences = preferences.sortPreferences.asLiveData()
    val userFilters: MutableLiveData<UserFilters> = MutableLiveData()

    init {
        getSortPreferences()
    }

    private fun getSortPreferences() {
        viewModelScope.launch {
            val userFilter = UserFilters("", sortPreference = preferences.sortPreferences.first())
            userFilters.value = userFilter
        }
    }

    var accountCreds = Transformations.switchMap(userFilters) { userFilters ->
        repository.getAccountCreds(userFilters.searchText, userFilters.sortPreference)
    }

    fun updateSortPreferences(preference: SortPreference) {
        viewModelScope.launch {
            preferences.updateSortPreferences(preference)
        }
    }

}

data class UserFilters(
    var searchText: String = "",
    var sortPreference: String = SortPreference.SORT_BY_DATE_DESC.name
)
