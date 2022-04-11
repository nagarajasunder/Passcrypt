package com.geekydroid.passcrypt.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.Utils.HashingUtils
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.MasterPasswordChangeEvent
import com.geekydroid.passcrypt.enums.Result
import com.geekydroid.passcrypt.repository.SettingsFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(private val repository: SettingsFragmentRepository) :
    ViewModel() {


    init {
        Log.d("SETTINGS_PAGE", ": queried once")
    }

    private val userSettings: LiveData<User> = repository.getUserSettings()
    val exportSuccess: MutableLiveData<Result?> = MutableLiveData(null)
    var userAuthenticated: MutableLiveData<MasterPasswordChangeEvent?> = MutableLiveData(null)

    fun getUserSetting() = userSettings

    fun updateUserSettings(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun exportData(application: PasscryptApp, uri: Uri?, userEnteredPass: String) {
        viewModelScope.launch {
            val accountData = repository.getCompleteAccountData()
            val bankData = repository.getCompleteBankData()
            val workbook = createWorkBook(accountData, bankData)
            createExcel(application, workbook, uri, userEnteredPass)

        }

    }

    private fun createExcel(
        application: PasscryptApp,
        workbook: HSSFWorkbook,
        uri: Uri?,
        userEnteredPass: String
    ) {
        try {
            val fos = application.contentResolver.openOutputStream(uri!!)
            Biff8EncryptionKey.setCurrentUserPassword(userEnteredPass)
            workbook.writeProtectWorkbook(Biff8EncryptionKey.getCurrentUserPassword(), "")
            workbook.write(fos)
            fos!!.close()
            exportSuccess.postValue(Result.SUCCESS)
        } catch (e: Exception) {
//            Log.d("SETTINGS_PAGE", "createExcel: ${e.message}")
            exportSuccess.postValue(Result.ERROR)
        }
    }

    private fun createWorkBook(
        accountData: List<AccountCred>,
        bankData: List<BankCred>
    ): HSSFWorkbook {

        val workBook = HSSFWorkbook()
        val accountSheet = workBook.createSheet("Account Details")
        val bankSheet = workBook.createSheet("Bank Details")
        createSheetHeader(
            accountSheet,
            listOf("Title", "Site Name / URL", "User Name", "Password", "Comments", "Created On")
        )
        createSheetHeader(
            bankSheet,
            listOf(
                "Bank Name",
                "Account Number",
                "IFSC Code",
                "Customer ID",
                "Comments",
                "Created On"
            )
        )
        for ((index, value) in accountData.withIndex()) {
            addAccountData(index + 1, accountSheet, value)
        }
        for ((index, value) in bankData.withIndex()) {
            addBankData(index + 1, bankSheet, value)
        }

        return workBook

    }

    private fun addAccountData(rowIndex: Int, sheet: Sheet, data: AccountCred) {
        val row = sheet.createRow(rowIndex)
        var columnIndex = 0
        createCell(row, columnIndex++, data.title)
        createCell(row, columnIndex++, data.siteName)
        createCell(row, columnIndex++, data.userName)
        createCell(row, columnIndex++, data.password)
        createCell(row, columnIndex++, data.comments)
        createCell(row, columnIndex, data.createdOnFormatted)
    }

    private fun addBankData(rowIndex: Int, sheet: Sheet, data: BankCred) {
        val row = sheet.createRow(rowIndex)
        var columnIndex = 0
        createCell(row, columnIndex++, data.bankName)
        createCell(row, columnIndex++, data.accountNumber)
        createCell(row, columnIndex++, data.ifscCode)
        createCell(row, columnIndex++, data.customerId)
        createCell(row, columnIndex++, data.comments)
        createCell(row, columnIndex, data.createdOnFormated)
    }

    private fun createSheetHeader(sheet: Sheet, headerList: List<String>) {
        val row = sheet.createRow(0)
        for ((index, value) in headerList.withIndex()) {
            val columnWidth = 15 * 500
            sheet.setColumnWidth(index, columnWidth)
            val cell = row.createCell(index)
            cell?.setCellValue(value)
        }
    }

    private fun createCell(rowIndex: Row, columnIndex: Int, value: String) {
        val cell = rowIndex.createCell(columnIndex)
        cell?.setCellValue(value)
    }

    fun verifyPassword(userEnteredPass: String, masterPassHash: String) {
        val result = HashingUtils.verifyPassword(userEnteredPass, masterPassHash)
        if (result) {
            userAuthenticated.value = MasterPasswordChangeEvent.USER_AUTHENTICATED
        } else {
            userAuthenticated.value = MasterPasswordChangeEvent.USER_AUTH_ERROR
        }
    }
}