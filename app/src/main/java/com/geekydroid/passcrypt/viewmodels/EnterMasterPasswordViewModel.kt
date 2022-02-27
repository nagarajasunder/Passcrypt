package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekydroid.passcrypt.Utils.HashingUtils
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.repository.EnterMasterPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterMasterPasswordViewModel @Inject constructor(private val repo: EnterMasterPasswordRepository) : ViewModel() {

    val userAuthFlag = MutableLiveData<Boolean>()

    fun authenticateUser(password: String) {
        CoroutineScope(IO).launch {
            val user = repo.getUser()
            if (user != null) {
//                println("EnterMasterPasswordViewModel: $user")
                verifyHash(user, password)
            }
        }
    }

    private fun verifyHash(user: User, password: String) {
        val auth = HashingUtils.verifyPassword(password, user.masterPassHash)
        println("EnterMasterPasswordViewModel: auth $auth")
        userAuthFlag.postValue(auth)
    }
}