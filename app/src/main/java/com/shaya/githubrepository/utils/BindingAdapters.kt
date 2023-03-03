package com.shaya.githubrepository.utils


import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.shaya.githubrepository.R
import com.shaya.githubrepository.ui.viewmodel.RepoListViewModel


@BindingAdapter("productApiStatus")
fun bindStatus(
    statusImageView: ImageView,
    status: RepoListViewModel.ItemApiStatus?
) {
    when (status) {
        RepoListViewModel.ItemApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.git_icon)
        }
        RepoListViewModel.ItemApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        RepoListViewModel.ItemApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}

@BindingAdapter("productApiProgressBar")
fun bindStatus(
    progressBar: ProgressBar,
    status: RepoListViewModel.ItemApiStatus?
) {
    when (status) {
        RepoListViewModel.ItemApiStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
       else -> {
           progressBar.visibility = View.GONE
       }
    }
}