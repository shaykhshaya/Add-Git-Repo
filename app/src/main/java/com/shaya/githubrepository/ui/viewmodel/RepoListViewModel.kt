package com.shaya.githubrepository.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.*
import com.shaya.githubrepository.BaseApplication
import com.shaya.githubrepository.data.RoomItem
import com.shaya.githubrepository.data.RoomItemDao
import com.shaya.githubrepository.network.ItemApi
import com.shaya.githubrepository.utils.NetworkAvailability
import kotlinx.coroutines.launch

class RepoListViewModel: ViewModel() {

    enum class ItemApiStatus { LOADING, ERROR, DONE }

    private val _status : MutableLiveData<ItemApiStatus>? = MutableLiveData<ItemApiStatus>()
    val status: LiveData<ItemApiStatus>? = _status

    private val roomItemDao: RoomItemDao = BaseApplication.database.roomItemDao()

    val allItems: LiveData<List<RoomItem>> = roomItemDao.getItems().asLiveData()


    private fun insertItem(productItem: RoomItem) {
        viewModelScope.launch {
            roomItemDao.insert(productItem)
        }
    }

    fun getGitHubRepo(owner: String, repo: String){
        viewModelScope.launch {
            _status?.value = ItemApiStatus.LOADING
            try {
                    val response = ItemApi.retrofitService.getGithubRepository(
                        owner = owner,
                        repo = repo
                    )
                    val repoRoomItem = RoomItem(
                        name = response.name.orEmpty(),
                        owner = response.owner?.login.orEmpty(),
                        description = response.description.orEmpty(),
                        url = response.html_url.orEmpty()
                    )
                    insertItem(repoRoomItem)
                    _status?.value = ItemApiStatus.DONE

            } catch (e: Exception) {
                e.printStackTrace()
                _status?.value = ItemApiStatus.ERROR
            }
        }
    }
}