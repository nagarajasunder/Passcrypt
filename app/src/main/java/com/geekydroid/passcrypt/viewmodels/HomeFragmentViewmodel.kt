package com.geekydroid.passcrypt.viewmodels

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.*
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.entities.CredWrapper
import com.geekydroid.passcrypt.repository.HomeFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeFragment"

@HiltViewModel
class HomeFragmentViewmodel @Inject constructor(
    private val repository: HomeFragmentRepository,
    private val app: PasscryptApp
) :
    ViewModel() {


    val accountSearchText = MutableLiveData("")

    val accountCred: LiveData<List<CredWrapper>> = Transformations.switchMap(accountSearchText) {
        repository.getAccountCreds(it)
    }



}