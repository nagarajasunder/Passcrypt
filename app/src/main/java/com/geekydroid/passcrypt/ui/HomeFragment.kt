package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.Utils.SortPreference
import com.geekydroid.passcrypt.adapters.AccountCredAdapter
import com.geekydroid.passcrypt.entities.CredWrapper
import com.geekydroid.passcrypt.listeners.CredOnClickListener
import com.geekydroid.passcrypt.viewmodels.HomeFragmentViewmodel
import com.geekydroid.passcrypt.viewmodels.UserFilters
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), CredOnClickListener {
    private lateinit var fragmentView: View
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabAccount: FloatingActionButton
    private lateinit var fabBank: FloatingActionButton
    private lateinit var emptyListAnim: LottieAnimationView
    private var addButtonClicked = false
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AccountCredAdapter
    private val viewmodel: HomeFragmentViewmodel by viewModels()
    private lateinit var searchView: SearchView
    private var currentSortPreference: String = SortPreference.SORT_BY_DATE_DESC.name
    private var userFilters: UserFilters = UserFilters()


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

        viewmodel.getAccountCreds().observe(viewLifecycleOwner) { credList ->
            adapter.submitList(credList)
            if (credList.isEmpty()) {
                showEmptyListAnim(true)
            } else {
                showEmptyListAnim(false)
            }
        }
        viewmodel.sortPreferences.observe(viewLifecycleOwner) {
            userFilters.sortPreference = it
            currentSortPreference = it
        }

        fabAdd.setOnClickListener {
            onAddButtonClicked()
        }

        fabAccount.setOnClickListener {
            addButtonClicked = !addButtonClicked
            val action = HomeFragmentDirections.actionHomeFragmentToAddNewPassword()
            fragmentView.findNavController().navigate(action)
        }
        fabBank.setOnClickListener {
            addButtonClicked = !addButtonClicked
            val action = HomeFragmentDirections.actionHomeFragmentToAddNewBankCred()
            fragmentView.findNavController().navigate(action)
        }
    }

    private fun showEmptyListAnim(visibility: Boolean) {

        if (visibility) {
            emptyListAnim.visibility = View.VISIBLE
        } else {
            emptyListAnim.visibility = View.GONE
        }
    }

    //Todo Add animation to add button
    private fun onAddButtonClicked() {
        setVisibility(addButtonClicked)
        setAnimation(addButtonClicked)
        addButtonClicked = !addButtonClicked


    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            fabAccount.startAnimation(fromBottom)
            fabBank.startAnimation(fromBottom)
            fabAdd.startAnimation(rotateOpen)
        } else {
            fabAccount.startAnimation(toBottom)
            fabBank.startAnimation(toBottom)
            fabAdd.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            fabAccount.visibility = View.VISIBLE
            fabBank.visibility = View.VISIBLE
        } else {
            fabAccount.visibility = View.GONE
            fabBank.visibility = View.GONE
        }
    }

    private fun setUI() {

        fabAdd = fragmentView.findViewById(R.id.fab_add)
        fabAccount = fragmentView.findViewById(R.id.fab_add_account)
        fabBank = fragmentView.findViewById(R.id.fab_add_bank)
        recyclerView = fragmentView.findViewById(R.id.recycler_view)
        emptyListAnim = fragmentView.findViewById(R.id.empty_list_anim)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = AccountCredAdapter(this)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_options_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    userFilters.searchText = ""
//                    userFilters.sortPreference = currentSortPreference
                    viewmodel.userFilters.value = userFilters
                } else {
                    userFilters.searchText = newText
                    viewmodel.userFilters.value = userFilters
                }

                return true
            }

        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings -> {
                val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                fragmentView.findNavController().navigate(action)
            }
            R.id.password_generator -> {
                val action = HomeFragmentDirections.actionHomeFragmentToPasswordGenerator()
                fragmentView.findNavController().navigate(action)
            }
            R.id.sort_by_name_asc -> {
                userFilters.sortPreference = SortPreference.SORT_BY_NAME_ASC.name
                viewmodel.userFilters.value = userFilters
                viewmodel.updateSortPreferences(SortPreference.SORT_BY_NAME_ASC)
            }
            R.id.sort_by_name_desc -> {
                userFilters.sortPreference = SortPreference.SORT_BY_NAME_DESC.name
                viewmodel.userFilters.value = userFilters
                viewmodel.updateSortPreferences(SortPreference.SORT_BY_NAME_DESC)
            }
            R.id.sort_by_date_desc -> {
                userFilters.sortPreference = SortPreference.SORT_BY_DATE_DESC.name
                viewmodel.userFilters.value = userFilters
                viewmodel.updateSortPreferences(SortPreference.SORT_BY_DATE_DESC)
            }
            R.id.sort_by_date_asc -> {
                userFilters.sortPreference = SortPreference.SORT_BY_DATE_ASC.name
                viewmodel.userFilters.value = userFilters
                viewmodel.updateSortPreferences(SortPreference.SORT_BY_DATE_ASC)
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

    override fun onDetach() {
        super.onDetach()
        if (userFilters.searchText.isNotEmpty()) {
            userFilters.searchText = ""
            viewmodel.userFilters.value = userFilters
        }
    }


}
