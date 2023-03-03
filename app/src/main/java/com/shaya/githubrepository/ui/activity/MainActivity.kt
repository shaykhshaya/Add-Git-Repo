package com.shaya.githubrepository.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shaya.githubrepository.databinding.ActivityMainBinding
import com.shaya.githubrepository.ui.adapter.RepoListAdapter
import com.shaya.githubrepository.ui.viewmodel.RepoListViewModel
import com.shaya.githubrepository.utils.openLink
import com.shaya.githubrepository.utils.shareRepo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var roomViewModel: RepoListViewModel
    private var adapter: RepoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        roomViewModel = ViewModelProvider(this)[RepoListViewModel::class.java]
        initViews()
        observeRepos()
    }

    private fun initViews() {
        binding.btnAddEmpty.setOnClickListener {
            binding.btnAdd.performClick()
        }
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddRepoActivity::class.java))
        }

        adapter = RepoListAdapter(
            onRepoClicked = {
                openLink(it.url)
            },
            callbackShareItem = {
                shareRepo(it.name, it.url)
            })

        binding.recyclerViewList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerViewList.adapter = adapter
    }

    private fun observeRepos() {
        roomViewModel.allItems.observe(this) {
            it.let {
                binding.btnContainer.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                adapter?.submitList(it)
            }
        }
    }


}