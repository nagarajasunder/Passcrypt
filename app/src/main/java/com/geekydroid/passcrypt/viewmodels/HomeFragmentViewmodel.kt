package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.*
import com.geekydroid.passcrypt.Utils.SortPreference
import com.geekydroid.passcrypt.Utils.UserPreferences
import com.geekydroid.passcrypt.entities.CredWrapper
import com.geekydroid.passcrypt.repository.HomeFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeFragmentViewmodel @Inject constructor(
    repository: HomeFragmentRepository,
    private val preferences: UserPreferences
) :
    ViewModel() {

    val sortPreferences = preferences.sortPreferences.asLiveData()
    val userFilters: MutableLiveData<UserFilters> = MutableLiveData()
    private val accountCreds: LiveData<List<CredWrapper>>

    init {
        getSortPreferences()
        accountCreds = Transformations.switchMap(userFilters) { userFilters ->
            repository.getAccountCreds(userFilters.searchText, userFilters.sortPreference)
        }
    }

    private fun getSortPreferences() {
        viewModelScope.launch {
            val userFilter = UserFilters("", sortPreference = preferences.sortPreferences.first())
            userFilters.value = userFilter
        }
    }


    fun getAccountCreds() = accountCreds


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
