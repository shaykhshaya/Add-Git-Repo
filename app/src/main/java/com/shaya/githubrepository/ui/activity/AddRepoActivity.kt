package com.shaya.githubrepository.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shaya.githubrepository.databinding.ActivityAddRepoBinding
import com.shaya.githubrepository.ui.viewmodel.RepoListViewModel

class AddRepoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddRepoBinding
    private lateinit var roomViewModel: RepoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRepoBinding.inflate(layoutInflater)
        roomViewModel = ViewModelProvider(this)[RepoListViewModel::class.java]
        binding.apply {
            viewModel = roomViewModel
            lifecycleOwner = this@AddRepoActivity
        }
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() {


        binding.btnAdd.setOnClickListener {
            val updatedRepo = binding.editTextRepository.text?.trim().toString()
            val updatedOwner = binding.editTextOwnerName.text?.trim().toString()
            roomViewModel.getGitHubRepo(
                owner = updatedOwner,
                repo = updatedRepo
            )
        }


        roomViewModel.status?.observe(this){
            when(it){
                RepoListViewModel.ItemApiStatus.ERROR -> {
                    Toast.makeText(this, "Couldn't find repo", Toast.LENGTH_SHORT).show()
                }
                RepoListViewModel.ItemApiStatus.DONE -> {
                    finish()
                }
                else -> {}
            }
        }

    }

}