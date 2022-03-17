package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.adapters.AccountCredAdapter
import com.geekydroid.passcrypt.entities.CredWrapper
import com.geekydroid.passcrypt.listeners.CredOnClickListener
import com.geekydroid.passcrypt.viewmodels.HomeFragmentViewmodel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment @Inject constructor() : Fragment(R.layout.fragment_home), CredOnClickListener {
    private lateinit var fragmentView: View
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabAccount: FloatingActionButton
    private lateinit var fabBank: FloatingActionButton
    private var addButtonClicked = false
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AccountCredAdapter
    private val viewmodel: HomeFragmentViewmodel by viewModels()

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.open_rotate
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.close_rotate
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        setHasOptionsMenu(true)
        setUI()

        fabAdd.setOnClickListener {
//            onAddButtonClicked()
        }

        fabAccount.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNewPassword()
            fragmentView.findNavController().navigate(action)
        }
        fabBank.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNewBankCred()
            fragmentView.findNavController().navigate(action)
        }

        viewmodel.accountCred.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(addButtonClicked)
        setAnimation(addButtonClicked)
        addButtonClicked = !addButtonClicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            Log.d(TAG, "setAnimation: if called")
            fabAccount.startAnimation(fromBottom)
            fabBank.startAnimation(fromBottom)
            fabAdd.startAnimation(rotateOpen)
        } else {
            Log.d(TAG, "setAnimation: else called")
            fabAdd.startAnimation(rotateClose)
            fabAccount.startAnimation(toBottom)
            fabBank.startAnimation(toBottom)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            Log.d(TAG, "setVisibility: if called")
            fabAccount.visibility = View.VISIBLE
            fabBank.visibility = View.VISIBLE
        } else {
            Log.d(TAG, "setVisibility: else called")
            fabAccount.visibility = View.INVISIBLE
            fabBank.visibility = View.INVISIBLE
        }
//        addButtonClicked = !addButtonClicked
    }

    private fun setUI() {

        fabAdd = fragmentView.findViewById(R.id.fab_add)
        fabAccount = fragmentView.findViewById(R.id.fab_add_account)
        fabBank = fragmentView.findViewById(R.id.fab_add_bank)
        recyclerView = fragmentView.findViewById(R.id.recycler_view)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = AccountCredAdapter(this)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings -> {
                val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                fragmentView.findNavController().navigate(action)
            }
        }

        return true
    }

    override fun onCredClick(credWrapper: CredWrapper) {
        if (credWrapper.credType == "ACCOUNT") {
            navigateToViewAccountCred(credWrapper.credId)
        } else {
            navigateToViewBankCred(credWrapper.credId)
        }
    }

    private fun navigateToViewBankCred(credId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToViewBankCred(credId)
        findNavController().navigate(action)
    }

    private fun navigateToViewAccountCred(credId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToViewAccountCred(credId)
        findNavController().navigate(action)
    }

}
