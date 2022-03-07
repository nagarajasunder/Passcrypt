package com.geekydroid.passcrypt.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekydroid.passcrypt.Utils.HashingUtils
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.NavigationMode
import com.geekydroid.passcrypt.repository.SetMasterPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetMasterPasswordViewmodel @Inject constructor(private val repository: SetMasterPasswordRepository) :
    ViewModel() {

    val createUserProcessFlag = MutableLiveData<Boolean>()

    fun createUser(password: String, MODE: NavigationMode) {
        CoroutineScope(IO).launch {
            val passwordHash = HashingUtils.getStrongPasswordHash(password)
            var result = -1L
            if (MODE == NavigationMode.NORMAL_MODE) {
                val user = User(masterPassHash = passwordHash, isMasterPassSet = true)
                result = repository.insertUser(user)
            } else {
                repository.resetUser(passwordHash)
                result = 11234
            }

            if (result != -1L) {
                createUserProcessFlag.postValue(true)
            } else {
                createUserProcessFlag.postValue(false)
            }

        }
    }

    fun truncateDatabase() {
        repository.truncateDatabase()
    }
}