package com.pampang.nav.screens.buyer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pampang.nav.utilities.adapters.SimpleDiffUtilAdapter
import com.pampang.nav.utilities.extension.showToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.pampang.nav.R
import com.pampang.nav.databinding.FragmentProfileBinding
import com.pampang.nav.models.ProfileMenuModel
import com.pampang.nav.screens.auth.LoginActivity
import com.pampang.nav.utilities.extension.RecyclerClick
import com.pampang.nav.viewmodels.AuthViewModel
import com.pampang.nav.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyerProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentProfileBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mAdapter: SimpleDiffUtilAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        mBinding.lifecycleOwner = this

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initConfig()
    }

    override fun onResume() {
        super.onResume()
        initRequest()
    }

    private fun initConfig() {
        initExtras()
        initEventListener()
        initAdapter()
        initLiveData()
    }

    private fun initAdapter() {
        mAdapter = SimpleDiffUtilAdapter(R.layout.list_item_profile_menu, RecyclerClick {
            it as ProfileMenuModel
            when (it.title) {
                "Personal Detail (Edit Username)" -> {
                    showEditUsernameDialog()
                }
                "Contact Us" -> {
                    showToast(it.title)
                }
                "Privacy and Security" -> {
                    showChangePasswordDialog()
                }
                "Preferences" -> {
                    showToast(it.title)
                }

                "Logout" -> {
                    showLogoutConfirmationDialog()
                }
            }
        })

        mBinding.recyclerViewProfileMenu.adapter = mAdapter

    }

    private fun initExtras() {
    }

    private fun initEventListener() {
        mBinding.apply {


        }
    }

    private fun initRequest() {
        authViewModel.loadUserData()
    }

    private fun initLiveData() {
        mainViewModel.profileMenuItems.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }
        authViewModel.currentUser.observe(viewLifecycleOwner) { firebaseUser ->
            firebaseUser?.let {
                mBinding.username = it.displayName
            }
        }
        authViewModel.changePasswordResult.observe(viewLifecycleOwner) {
            if (it?.isSuccess == true) {
                showToast("Password changed successfully")
            } else if (it?.isFailure == true) {
                showToast("Failed to change password: ${it.exceptionOrNull()?.message}")
            }
        }
    }

    private fun showLogoutConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ -> logout() }
            .setNegativeButton("No", null)
            .setCancelable(false)
            .show()
    }

    private fun showEditUsernameDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_username, null)
        val editTextUsername = dialogView.findViewById<TextInputEditText>(R.id.edit_text_username)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Username")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newUsername = editTextUsername.text.toString()
                if (newUsername.isNotEmpty()) {
                    authViewModel.updateUsername(newUsername)
                } else {
                    showToast("Username cannot be empty")
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun showChangePasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_password, null)
        val currentPassword = dialogView.findViewById<TextInputEditText>(R.id.edit_text_current_password)
        val newPassword = dialogView.findViewById<TextInputEditText>(R.id.edit_text_new_password)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Change Password")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val current = currentPassword.text.toString()
                val new = newPassword.text.toString()
                if (current.isNotEmpty() && new.isNotEmpty()) {
                    authViewModel.changePassword(current, new)
                } else {
                    showToast("All fields are required")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun logout() {
        authViewModel.logout()
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}