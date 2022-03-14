package com.geekydroid.passcrypt.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.viewmodels.ViewAccountCredViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewAccountCred : Fragment(R.layout.fragment_view_account_cred) {

    private lateinit var fragmentView: View
    private lateinit var tvSiteUrl: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvPassword: TextView
    private lateinit var tvComments: TextView
    private lateinit var tvCreatedOn: TextView

    private lateinit var ivCopySiteUrl: ImageView
    private lateinit var ivCopyUserName: ImageView
    private lateinit var ivCopyPassword: ImageView

    private lateinit var accountCred: AccountCred
    private val ViewModel: ViewAccountCredViewModel by viewModels()
    private val args: ViewAccountCredArgs by navArgs()
    private var credId: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
        credId = args.credId

        setUI()
        ViewModel.getAccountCred(credId).observe(viewLifecycleOwner) { AccountCred ->
            if (AccountCred != null) {
                accountCred = AccountCred
                setData(accountCred)
            }
        }

        ivCopySiteUrl.setOnClickListener {
            setPrimaryClip(accountCred.siteName)
        }
        ivCopyUserName.setOnClickListener {
            setPrimaryClip(accountCred.userName)
        }
        ivCopyPassword.setOnClickListener {
            setPrimaryClip(accountCred.password)
        }


    }

    private fun setPrimaryClip(text: String) {
        val clipManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("cred", text)
        clipManager.setPrimaryClip(clipData)
    }

    private fun setData(accountCred: AccountCred) {
        tvSiteUrl.text = accountCred.siteName
        tvUserName.text = accountCred.userName
        tvPassword.text = accountCred.password
        tvComments.text = accountCred.comments
    }

    private fun setUI() {
        tvSiteUrl = fragmentView.findViewById(R.id.tv_site_url)
        tvUserName = fragmentView.findViewById(R.id.tv_user_name)
        tvPassword = fragmentView.findViewById(R.id.tv_password)
        tvComments = fragmentView.findViewById(R.id.tv_comments)

        ivCopySiteUrl = fragmentView.findViewById(R.id.iv_copy_site_url)
        ivCopyUserName = fragmentView.findViewById(R.id.iv_copy_user_name)
        ivCopyPassword = fragmentView.findViewById(R.id.iv_copy_password)
    }


}