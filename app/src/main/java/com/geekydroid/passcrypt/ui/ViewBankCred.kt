package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import com.geekydroid.passcrypt.viewmodels.ViewBankCredViewModel
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

    private lateinit var tvTitle: TextView
    private lateinit var tvBankName: TextView
    private lateinit var tvAccountNumber: TextView
    private lateinit var tvIFSCCode: TextView
    private lateinit var tvCustomerId: TextView
    private lateinit var tvUpdatedOn: TextView
    private lateinit var tvComments: TextView
    private lateinit var tvBankNameLabel: TextView
    private lateinit var tvAccountNumberLabel: TextView
    private lateinit var tvIFSCCodeLabel: TextView
    private lateinit var tvCustomerIdLabel: TextView
    private lateinit var tvCommentsLabel: TextView

    private lateinit var tvCardNumber1: TextView
    private lateinit var tvCardNumber2: TextView
    private lateinit var tvCardNumber3: TextView
    private lateinit var tvCardNumber4: TextView

    private lateinit var tvExpiryDate: TextView
    private lateinit var tvPinNumber: TextView
    private lateinit var tvCvvNumber: TextView
    private lateinit var tvCardBankName: TextView

    private lateinit var ivCopyBankName: ImageView
    private lateinit var ivCopyAccountNumber: ImageView
    private lateinit var ivCopyIFSCCode: ImageView
    private lateinit var ivCopyCustomerId: ImageView
    private lateinit var ivShowPin: ImageView
    private lateinit var ivShowCVV: ImageView

    private var pinShown = false
    private var cvvShown = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        credId = args.credId
        setUI()

        ViewModel.getBankCredWithCard(credId).observe(viewLifecycleOwner) { response ->

            if (response?.first != null) {
                bankCred = response.first
                setBankData(bankCred)
            }
            if (response?.second != null) {
                cardCred = response.second
                setCardData(cardCred)
            }
        }

        ivShowPin.setOnClickListener {
            pinShown = !pinShown
            displayPIN(pinShown)
        }


    }

    private fun displayPIN(pinShown: Boolean) {
        if (pinShown) {
            tvPinNumber.text = "*".repeat(4)
        } else {
            tvPinNumber.text = cardCred[0].cardPinNumber
        }
    }

    private fun displayCVV(cvvShown: Boolean) {
        if (cvvShown) {
            tvCvvNumber.text = "*".repeat(3)
        } else {
            tvCvvNumber.text = cardCred[0].cvv
        }
    }

    private fun setCardData(Card: List<Card>) {
        val currentCard = Card[0]
        val cardNumberSplit = currentCard.cardNumber.split(":")
        tvCardNumber1.text = cardNumberSplit[0]
        tvCardNumber2.text = cardNumberSplit[1]
        tvCardNumber3.text = cardNumberSplit[2]
        tvCardNumber4.text = cardNumberSplit[3]

        tvPinNumber.text = currentCard.cardPinNumber
        tvCvvNumber.text = currentCard.cvv
        tvExpiryDate.text = currentCard.cardExpiryDate


    }

    private fun setBankData(bankData: BankCred) {
        tvBankName.text = bankData.bankName
        tvAccountNumber.text = bankData.accountNumber
        tvIFSCCode.text = bankData.ifscCode
        tvCustomerId.text = bankData.customerId
        tvComments.text = bankData.comments
        tvUpdatedOn.text = bankData.updatedOnFormatted
        tvCardBankName.text = bankData.bankName.uppercase(Locale.getDefault())
    }

    private fun setUI() {


        tvBankNameLabel = fragmentView.findViewById(R.id.tv_bank_name_label)
        tvBankName = fragmentView.findViewById(R.id.tv_bank_name)
        tvAccountNumberLabel = fragmentView.findViewById(R.id.tv_account_number_label)
        tvAccountNumber = fragmentView.findViewById(R.id.tv_account_number)
        tvIFSCCodeLabel = fragmentView.findViewById(R.id.tv_ifsc_code_label)
        tvIFSCCode = fragmentView.findViewById(R.id.tv_ifsc_code)
        tvCustomerIdLabel = fragmentView.findViewById(R.id.tv_customer_id_label)
        tvCustomerId = fragmentView.findViewById(R.id.tv_customer_id)
        tvCommentsLabel = fragmentView.findViewById(R.id.tv_comments_label)
        tvComments = fragmentView.findViewById(R.id.tv_comments)
        tvUpdatedOn = fragmentView.findViewById(R.id.tv_last_updated)

        ivCopyBankName = fragmentView.findViewById(R.id.iv_copy_bank_name)
        ivCopyAccountNumber = fragmentView.findViewById(R.id.iv_copy_account_number)
        ivCopyIFSCCode = fragmentView.findViewById(R.id.iv_copy_ifsc_code)
        ivCopyCustomerId = fragmentView.findViewById(R.id.iv_copy_customer_id)

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
    }


}