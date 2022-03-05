package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.entities.CredWrapper
import com.geekydroid.passcrypt.repository.HomeFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewmodel @Inject constructor(private val repository:HomeFragmentRepository) : ViewModel() {

    val accountSearchText: MutableLiveData<String> = MutableLiveData()

    val accountCred:LiveData<List<CredWrapper>> = repository.getAccountCreds()




}