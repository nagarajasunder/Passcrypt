package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import com.geekydroid.passcrypt.viewmodels.ViewBankCredViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private const val TAG = "ViewBankCred"

@AndroidEntryPoint
class ViewBankCred : Fragment(R.layout.fragment_view_bank_cred) {

    private lateinit var fragmentView: View
    private val args: ViewBankCredArgs by navArgs()
    private val ViewModel: ViewBankCredViewModel by viewModels()
    private var credId: Int = 0
    private lateinit var bankCred: BankCred
    private var cardCred = listOf<Card>()

    private lateinit var tvBankName: TextView
    private lateinit var tvAccountNumber: TextView
    private lateinit var tvIFSCCode: TextView
    private lateinit var tvCustomerId: TextView
    private lateinit var tvUpdatedOn: TextView
    private lateinit var tvComments: TextView
    private lateinit var tvAccountNumberLabel: TextView
    private lateinit var tvIFSCCodeLabel: TextView
    private lateinit var tvCustomerIdLabel: TextView
    private lateinit var tvCommentsLabel: TextView
    private lateinit var tvCardDetailsLabel: TextView

    private lateinit var tvCardNumber1: TextView
    private lateinit var tvCardNumber2: TextView
    private lateinit var tvCardNumber3: TextView
    private lateinit var tvCardNumber4: TextView

    private lateinit var tvExpiryDate: TextView
    private lateinit var tvPinNumber: TextView
    private lateinit var tvCvvNumber: TextView
    private lateinit var tvCardBankName: TextView

    private lateinit var ivCopyAccountNumber: ImageView
    private lateinit var ivCopyCustomerId: ImageView
    private lateinit var ivCopyIFSCCode: ImageView
    private lateinit var ivShowPin: ImageView
    private lateinit var ivShowCVV: ImageView
    private lateinit var ivShowCustomerId: ImageView
    private lateinit var ivFavorite: ImageView
    private lateinit var ivEdit: ImageView

    private lateinit var cardReadOnly: RelativeLayout

    private var pinShown = false
    private var cvvShown = false
    private var customerIdShown = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        credId = args.credId
        setUI()
        setHasOptionsMenu(true)

        ViewModel.getBankCredWithCard(credId).observe(viewLifecycleOwner) { response ->

            Log.d(TAG, "onViewCreated: $response")

            if (response?.first != null) {
                bankCred = response.first
                setBankData(bankCred)
            }
            if (response?.second?.isNotEmpty() == true) {
                cardCred = response.second
                setCardData(cardCred)
                showCardUI()
            }
        }

        ivShowPin.setOnClickListener {
            pinShown = !pinShown
            displayPIN(pinShown)
        }

        ivShowCVV.setOnClickListener {
            cvvShown = !cvvShown
            displayCVV(cvvShown)
        }

        ivShowCustomerId.setOnClickListener {
            customerIdShown = !customerIdShown
            displayCustomerId(customerIdShown)
        }

        ivFavorite.setOnClickListener {
            if (bankCred.isFavorite) {
                ViewModel.removeFromFavorites(bankCred)
                ivFavorite.setImageResource(R.drawable.favourite_off)
            } else {
                ViewModel.addToFavorites(bankCred)
                ivFavorite.setImageResource(R.drawable.favorite)
            }
        }

        ivEdit.setOnClickListener {
            navigateToEditBankAccount()
        }


    }

    private fun navigateToEditBankAccount() {
        val action = ViewBankCredDirections.actionViewBankCredToEditBankAccountCred(credId)
        findNavController().navigate(action)
    }

    private fun showCardUI() {
        cardReadOnly.visibility = View.VISIBLE
        tvCardDetailsLabel.visibility = View.VISIBLE
    }

    private fun displayPIN(pinShown: Boolean) {
        if (cardCred[0].cvv.isEmpty()) {
            tvCvvNumber.text = ""
            ivShowCVV.isEnabled = false
        } else {
            ivShowCVV.isEnabled = true
            if (pinShown) {
                tvPinNumber.text = cardCred[0].cardPinNumber
                ivShowPin.setImageResource(R.drawable.visibility_off)
            } else {
                tvPinNumber.text = "*".repeat(4)
                ivShowPin.setImageResource(R.drawable.visibility)
            }
        }


    }

    private fun displayCustomerId(customerIdShown: Boolean) {
        if (customerIdShown) {
            tvCustomerId.text = bankCred.customerId
            ivShowCustomerId.setImageResource(R.drawable.visibility_off)
        } else {
            tvCustomerId.text = "*".repeat(bankCred.customerId.length)
            ivShowCustomerId.setImageResource(R.drawable.visibility)
        }
    }

    private fun displayCVV(cvvShown: Boolean) {
        if (cardCred[0].cardPinNumber.isEmpty()) {
            tvPinNumber.text = ""
            ivShowPin.isEnabled = false
        } else {
            ivShowPin.isEnabled = true
            if (cvvShown) {
                tvCvvNumber.text = cardCred[0].cvv
                ivShowCVV.setImageResource(R.drawable.visibility_off)
            } else {
                tvCvvNumber.text = "*".repeat(3)
                ivShowCVV.setImageResource(R.drawable.visibility)
            }
        }
    }

    private fun setCardData(Card: List<Card>) {
        val currentCard = Card[0]
        val cardNumberSplit = currentCard.cardNumber.split(":")
        tvCardNumber1.text = cardNumberSplit[0]
        tvCardNumber2.text = cardNumberSplit[1]
        tvCardNumber3.text = cardNumberSplit[2]
        tvCardNumber4.text = cardNumberSplit[3]
        tvExpiryDate.text = currentCard.cardExpiryDate

        displayPIN(pinShown)
        displayCVV(cvvShown)

    }


    private fun setBankData(bankData: BankCred) {

        tvBankName.text = bankData.bankName
        if (bankCred.accountNumber.isEmpty()) {
            tvAccountNumber.visibility = View.GONE
            tvAccountNumberLabel.visibility = View.GONE
            ivCopyAccountNumber.visibility = View.GONE
        } else {
            tvAccountNumber.text = bankData.accountNumber
            tvAccountNumber.visibility = View.VISIBLE
            tvAccountNumberLabel.visibility = View.VISIBLE
            ivCopyAccountNumber.visibility = View.VISIBLE
        }

        if (bankCred.ifscCode.isEmpty()) {
            tvIFSCCode.visibility = View.GONE
            tvIFSCCodeLabel.visibility = View.GONE
            ivCopyIFSCCode.visibility = View.GONE
        } else {
            tvIFSCCode.text = bankData.ifscCode
            tvIFSCCode.visibility = View.VISIBLE
            tvIFSCCodeLabel.visibility = View.VISIBLE
            ivCopyIFSCCode.visibility = View.VISIBLE

        }
        if (bankCred.customerId.isEmpty()) {
            tvCustomerId.visibility = View.GONE
            tvCustomerIdLabel.visibility = View.GONE
            ivCopyCustomerId.visibility = View.GONE
            ivShowCustomerId.visibility = View.GONE
        } else {
            tvCustomerId.visibility = View.VISIBLE
            tvCustomerIdLabel.visibility = View.VISIBLE
            ivCopyCustomerId.visibility = View.VISIBLE
            ivShowCustomerId.visibility = View.VISIBLE
            displayCustomerId(customerIdShown)
        }

        if (bankCred.comments.isEmpty()) {
            tvComments.visibility = View.GONE
            tvCommentsLabel.visibility = View.GONE
        } else {
            tvComments.text = bankData.comments
            tvComments.visibility = View.VISIBLE
            tvCommentsLabel.visibility = View.VISIBLE
        }

        tvUpdatedOn.text = bankData.updatedOnFormatted
        tvCardBankName.text = bankData.bankName.uppercase(Locale.getDefault())
    }

    private fun setUI() {


        tvBankName = fragmentView.findViewById(R.id.tv_bank_name)
        tvAccountNumberLabel = fragmentView.findViewById(R.id.tv_account_number_label)
        tvAccountNumber = fragmentView.findViewById(R.id.tv_account_number)
        tvIFSCCodeLabel = fragmentView.findViewById(R.id.tv_ifsc_code_label)
        tvIFSCCode = fragmentView.findViewById(R.id.tv_ifsc_code)
        tvCustomerIdLabel = fragmentView.findViewById(R.id.tv_customer_id_label)
        tvCustomerId = fragmentView.findViewById(R.id.tv_customer_id)
        tvCommentsLabel = fragmentView.findViewById(R.id.tv_comments_label)
        tvCardDetailsLabel = fragmentView.findViewById(R.id.tv_card_details)
        tvComments = fragmentView.findViewById(R.id.tv_comments)
        tvUpdatedOn = fragmentView.findViewById(R.id.tv_last_updated)

        ivCopyAccountNumber = fragmentView.findViewById(R.id.iv_copy_account_number)
        ivCopyCustomerId = fragmentView.findViewById(R.id.iv_copy_customer_id)
        ivCopyIFSCCode = fragmentView.findViewById(R.id.iv_copy_ifsc_code)
        ivFavorite = fragmentView.findViewById(R.id.iv_favorite)
        ivEdit = fragmentView.findViewById(R.id.iv_edit)


        tvCardNumber1 = fragmentView.findViewById(R.id.tv_card_num_1)
        tvCardNumber2 = fragmentView.findViewById(R.id.tv_card_num_2)
        tvCardNumber3 = fragmentView.findViewById(R.id.tv_card_num_3)
        tvCardNumber4 = fragmentView.findViewById(R.id.tv_card_num_4)
        tvExpiryDate = fragmentView.findViewById(R.id.tv_expiry_date)
        tvPinNumber = fragmentView.findViewById(R.id.tv_pin_number)
        tvCvvNumber = fragmentView.findViewById(R.id.tv_cvv_number)
        tvCardBankName = fragmentView.findViewById(R.id.tv_card_bank_name)

        ivShowPin = fragmentView.findViewById(R.id.iv_show_pin)
        ivShowCVV = fragmentView.findViewById(R.id.iv_show_cvv)
        ivShowCustomerId = fragmentView.findViewById(R.id.iv_show_customer_id)

        cardReadOnly = fragmentView.findViewById(R.id.card_cred)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.view_cred_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete -> showDeletionWarningDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDeletionWarningDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Credential")
            .setMessage("All data associated with this credential will be removed")
            .setPositiveButton("Yes") { dialog, _ ->
                deleteCredential()
                dialog.dismiss()

            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        dialog.show()
    }

    private fun deleteCredential() {
        ViewModel.deleteCredential(bankCred)
        showSnackBar("Bank Details deleted successfully")
        findNavController().navigateUp()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
    }


}