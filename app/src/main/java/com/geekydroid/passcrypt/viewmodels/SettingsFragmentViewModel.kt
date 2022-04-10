package com.geekydroid.passcrypt.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.Result
import com.geekydroid.passcrypt.repository.SettingsFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(private val repository: SettingsFragmentRepository) :
    ViewModel() {


    init {
        Log.d("SETTINGS", ": queried once")
    }

    private val userSettings: LiveData<User> = repository.getUserSettings()

    val exportSucess: MutableLiveData<Result> = MutableLiveData()


    fun getUserSetting() = userSettings

    fun updateUserSettings(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun exportData(application: PasscryptApp, uri: Uri?) {

        viewModelScope.launch {
            val accountData = repository.getCompleteAccountData()
            val bankData = repository.getCompleteBankData()
            val workbook = createWorkBook(accountData, bankData)
            createExcel(application, workbook, uri)

        }

    }

    private fun createExcel(application: PasscryptApp, workbook: Workbook, uri: Uri?) {
        try {
            val fos = application.contentResolver.openOutputStream(uri!!)
            workbook.write(fos)
            fos!!.close()
            exportSucess.postValue(Result.SUCCESS)
        } catch (e: Exception) {
            exportSucess.postValue(Result.ERROR)
        }
    }

    private fun createWorkBook(accountData: List<AccountCred>, bankData: List<BankCred>): Workbook {

        val workBook = XSSFWorkbook()
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
}