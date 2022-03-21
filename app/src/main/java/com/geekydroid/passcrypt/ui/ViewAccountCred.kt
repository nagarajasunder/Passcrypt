package com.geekydroid.passcrypt.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.viewmodels.ViewAccountCredViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "ViewAccountCred"

@AndroidEntryPoint
class ViewAccountCred : Fragment(R.layout.fragment_view_account_cred) {

    private lateinit var fragmentView: View
    private lateinit var tvTitle: TextView
    private lateinit var tvSiteUrl: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvPassword: TextView
    private lateinit var tvComments: TextView
    private lateinit var tvUpdatedOn: TextView
    private lateinit var tvSiteLabel: TextView
    private lateinit var tvUserNameLabel: TextView
    private lateinit var tvPasswordLabel: TextView
    private lateinit var tvCommentsLabel: TextView


    private var passwordShown = false

    private lateinit var ivCopySiteUrl: ImageView
    private lateinit var ivCopyUserName: ImageView
    private lateinit var ivCopyPassword: ImageView
    private lateinit var ivShowPassword: ImageView
    private lateinit var ivFavorite: ImageView

    //    Todo Add the edit functionality
    private lateinit var ivEdit: ImageView

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

        ivEdit.setOnClickListener {
            navigateToEditCred()
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
        ivShowPassword.setOnClickListener {
            passwordShown = !passwordShown
            displayPassword(passwordShown)
        }
        ivFavorite.setOnClickListener {
            if (accountCred.isFavourite) {
                ViewModel.removeFromFavorites(accountCred)
                showSnackBar("Removed from favorites")
            } else {
                ViewModel.addToFavorites(accountCred)
                showSnackBar("Added to favorites")
            }
        }


    }

    private fun navigateToEditCred() {
        val action = ViewAccountCredDirections.actionViewAccountCredToEditAccountCred(credId)
        findNavController().navigate(action)
    }


    private fun setPrimaryClip(text: String) {
        val clipManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("cred", text)
        clipManager.setPrimaryClip(clipData)
        showSnackBar("The text has been copied to your clipboard")
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun displayPassword(toggle: Boolean) {
        if (toggle) {
            tvPassword.text = accountCred.password
            ivShowPassword.setImageResource(R.drawable.visibility_off)
        } else {
            tvPassword.text = "*".repeat(accountCred.password.length)
            ivShowPassword.setImageResource(R.drawable.visibility)
        }
    }


    private fun setData(accountCred: AccountCred) {
        tvTitle.text = accountCred.title
        if (accountCred.siteName.isNotEmpty()) {
            tvSiteUrl.visibility = View.VISIBLE
            ivCopySiteUrl.visibility = View.VISIBLE
            tvSiteLabel.visibility = View.VISIBLE
            tvSiteUrl.text = accountCred.siteName
        }
        if (accountCred.userName.isNotEmpty()) {
            tvUserNameLabel.visibility = View.VISIBLE
            tvUserName.visibility = View.VISIBLE
            ivCopyUserName.visibility = View.VISIBLE
            tvUserName.text = accountCred.userName

        }
        if (accountCred.password.isNotEmpty()) {
            tvPasswordLabel.visibility = View.VISIBLE
            tvPassword.visibility = View.VISIBLE
            ivCopyPassword.visibility = View.VISIBLE
            ivShowPassword.visibility = View.VISIBLE
            displayPassword(passwordShown)
        }
        if (accountCred.comments.isNotEmpty()) {
            tvCommentsLabel.visibility = View.VISIBLE
            tvComments.visibility = View.VISIBLE
            tvComments.text = accountCred.comments
        }
        tvUpdatedOn.text = getString(R.string.last_updated_on, accountCred.updatedOnFormatted)
        Log.d(TAG, "setData: accountCred.isFavorite ${accountCred.isFavourite}")
        if (accountCred.isFavourite) {
            ivFavorite.setImageResource(R.drawable.favorite)
            ivFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_200))
        } else {
            ivFavorite.setImageResource(R.drawable.favourite_off)
            ivFavorite.setColorFilter(Color.argb(1, 255, 255, 255))
        }
    }

    private fun setUI() {
        tvTitle = fragmentView.findViewById(R.id.tv_cred_title)
        tvSiteUrl = fragmentView.findViewById(R.id.tv_site_url)
        tvUserName = fragmentView.findViewById(R.id.tv_user_name)
        tvPassword = fragmentView.findViewById(R.id.tv_password)
        tvComments = fragmentView.findViewById(R.id.tv_comments)
        tvUpdatedOn = fragmentView.findViewById(R.id.tv_last_updated)

        tvSiteLabel = fragmentView.findViewById(R.id.tv_site_label)
        tvUserNameLabel = fragmentView.findViewById(R.id.tv_user_name_label)
        tvPasswordLabel = fragmentView.findViewById(R.id.tv_password_label)
        tvCommentsLabel = fragmentView.findViewById(R.id.tv_comments_label)

        ivCopySiteUrl = fragmentView.findViewById(R.id.iv_copy_site_url)
        ivCopyUserName = fragmentView.findViewById(R.id.iv_copy_user_name)
        ivCopyPassword = fragmentView.findViewById(R.id.iv_copy_password)
        ivShowPassword = fragmentView.findViewById(R.id.iv_show_password)
        ivFavorite = fragmentView.findViewById(R.id.iv_favorite)
        ivEdit = fragmentView.findViewById(R.id.iv_edit)
    }


}