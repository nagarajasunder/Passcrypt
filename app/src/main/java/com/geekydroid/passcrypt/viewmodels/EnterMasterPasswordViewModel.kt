package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.Utils.HashingUtils
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.repository.EnterMasterPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterMasterPasswordViewModel @Inject constructor(private val repo: EnterMasterPasswordRepository) :
    ViewModel() {

    val userAuthFlag = MutableLiveData<Boolean>()
    private var user: User? = null
    private var selfDestructionEnabled = MutableLiveData(false)
    private var selfDestructionCount = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            user = repo.getUser()
            updateUserDetails()
        }
    }

    fun isSelfDestructionEnabled() = selfDestructionEnabled

    fun selfDestructionCount() = selfDestructionCount


    private fun updateUserDetails() {
        user?.let { it ->
            selfDestructionEnabled.value = it.selfDestructive
            if (selfDestructionEnabled.value == true) {
                selfDestructionCount.value = it.selfDestructiveCount
            }
        }
    }


    fun authenticateUser(password: String) {
        CoroutineScope(IO).launch {
            user?.let {
                verifyHash(it, password)
            }

        }
    }

    private fun verifyHash(user: User, password: String) {
        val auth = HashingUtils.verifyPassword(password, user.masterPassHash)
        userAuthFlag.postValue(auth)
        if (selfDestructionEnabled.value == true && !auth) {
            selfDestructionCount.postValue(selfDestructionCount.value?.minus(1))
        }
    }


    fun updateUser() {
        viewModelScope.launch {
            if (selfDestructionEnabled.value == true) {
                user?.let { it ->
                    it.selfDestructiveCount = selfDestructionCount.value!!
                    repo.updateUser(it)
                }
            }
        }
    }
}