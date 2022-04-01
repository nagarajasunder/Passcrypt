package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.Utils.HashingUtils
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.MasterPasswordChangeEvent
import com.geekydroid.passcrypt.repository.UpdateMasterPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMasterPasswordViewModel @Inject constructor(private val repo: UpdateMasterPasswordRepository) :
    ViewModel() {

    val passwordChangeResult: MutableLiveData<MasterPasswordChangeEvent> = MutableLiveData()

    val userProfile = repo.getUserProfile()

    fun authenticateUser(userEnteredPassword: String, user: User) {
        viewModelScope.launch {
            val result = verifyMasterPassword(userEnteredPassword, user.masterPassHash)
            if (result) {
                passwordChangeResult.value = MasterPasswordChangeEvent.USER_AUTHENTICATED
            } else {
                passwordChangeResult.value = MasterPasswordChangeEvent.USER_AUTH_ERROR
            }
        }
    }

    fun updateMasterPassword(userEnteredPassword: String, user: User) {
        viewModelScope.launch {
            val passwordHash = HashingUtils.getStrongPasswordHash(userEnteredPassword)
            user.masterPassHash = passwordHash
            user.updatedOn = System.currentTimeMillis()
            repo.updateUserProfile(user)
            repo.updateDBKey(newKey = userEnteredPassword)
            passwordChangeResult.value = MasterPasswordChangeEvent.MASTER_PASSWORD_UPDATED
        }
    }

    private fun verifyMasterPassword(password: String, storedPassword: String): Boolean {
        return HashingUtils.verifyPassword(password, storedPassword)
    }

}