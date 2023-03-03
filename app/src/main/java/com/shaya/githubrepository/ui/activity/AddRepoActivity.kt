package com.shaya.githubrepository.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.shaya.githubrepository.BaseApplication
import com.shaya.githubrepository.databinding.ActivityAddRepoBinding
import com.shaya.githubrepository.ui.viewmodel.RepoListViewModel
import com.shaya.githubrepository.utils.NetworkAvailability

class AddRepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRepoBinding
    private lateinit var roomViewModel: RepoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        roomViewModel = ViewModelProvider(this)[RepoListViewModel::class.java]
        initViews()
        keyListener()

        binding.apply {
            viewModel = roomViewModel
            lifecycleOwner = this@AddRepoActivity
        }
    }

    private fun initViews() {

        binding.btnAdd.setOnClickListener {
            val updatedRepo = binding.editTextRepository.text?.trim().toString()
            val updatedOwner = binding.editTextOwnerName.text?.trim().toString()

            if (updatedOwner.isEmpty()) {
                binding.editTextOwnerNameLayout.error = "Empty Owner Name"
                return@setOnClickListener
            }

            if (updatedRepo.isEmpty()) {
                binding.editTextRepositoryLayout.error = "Empty Repository Name"
                return@setOnClickListener
            }

            if (!NetworkAvailability.isNetworkAvailable(BaseApplication.instance)) {
                Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            roomViewModel.getGitHubRepo(
                owner = updatedOwner,
                repo = updatedRepo
            )
            it.hideKeyboard()
        }
        roomViewModel.status?.observe(this) {
            when (it) {
                RepoListViewModel.ItemApiStatus.ERROR -> {
                    Toast.makeText(this, "Repository, Not Found", Toast.LENGTH_SHORT).show()
                }
                RepoListViewModel.ItemApiStatus.DONE -> {
                    finish()
                }
                else -> {}
            }
        }
    }

    private fun keyListener(){
        binding.editTextOwnerName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p3 > 0) {
                    binding.editTextOwnerNameLayout.error = ""
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.editTextRepository.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p3 > 0) {
                    binding.editTextRepositoryLayout.error = ""
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}