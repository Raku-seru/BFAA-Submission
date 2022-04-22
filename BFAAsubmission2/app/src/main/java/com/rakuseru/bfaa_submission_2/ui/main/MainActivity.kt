package com.rakuseru.bfaa_submission_2.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.rakuseru.bfaa_submission_2.R
import com.rakuseru.bfaa_submission_2.adapter.OnItemClickCallback
import com.rakuseru.bfaa_submission_2.adapter.UserAdapter
import com.rakuseru.bfaa_submission_2.databinding.ActivityMainBinding
import com.rakuseru.bfaa_submission_2.datasource.UserResponse
import com.rakuseru.bfaa_submission_2.service.NetworkConnection
import com.rakuseru.bfaa_submission_2.ui.userdetail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter: UserAdapter by lazy {
        UserAdapter()
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting up application
        setupSearchView()
        observeAnimationAndProgressBar()
        checkInternetConnection()
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showFailedLoadData(false)
                    showProgressBar(true)

                    mainViewModel.getUserBySearch(query)
                    mainViewModel.searchUser.observe(this@MainActivity) { searchUserResponse ->
                        if (searchUserResponse != null) {
                            adapter.addDataToList(searchUserResponse)
                            setUserData()
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun observeAnimationAndProgressBar() {
        mainViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }
        mainViewModel.isDataFailed.observe(this) {
            showFailedLoadData(it)
        }
    }

    private fun checkInternetConnection() {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                showFailedLoadData(false)
                mainViewModel.user.observe(this) { userResponse ->
                    if (userResponse != null) {
                        adapter.addDataToList(userResponse)
                        setUserData()
                    }
                }
                mainViewModel.searchUser.observe(this@MainActivity) { searchUserResponse ->
                    if (searchUserResponse != null) {
                        adapter.addDataToList(searchUserResponse)
                        binding.rvUsers.visibility = View.VISIBLE
                    }
                }
            } else {
                mainViewModel.user.observe(this) { userResponse ->
                    if (userResponse != null) {
                        adapter.addDataToList(userResponse)
                        setUserData()
                    }
                }
                mainViewModel.searchUser.observe(this@MainActivity) { searchUserResponse ->
                    if (searchUserResponse != null) {
                        adapter.addDataToList(searchUserResponse)
                        binding.rvUsers.visibility = View.VISIBLE
                    }
                }
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.no_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // to Hide Recycler view showing list
    private fun hideUserList() {
        binding.rvUsers.layoutManager = null
        binding.rvUsers.adapter = null
    }

    // Loading bar controller
    private fun showProgressBar(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Failed text controller
    private fun showFailedLoadData(isFailed: Boolean) {
        binding.tvFailed.visibility = if (isFailed) View.VISIBLE else View.GONE
    }

    private fun setUserData() {
        val layoutManager =
            GridLayoutManager(this@MainActivity, 1, GridLayoutManager.VERTICAL, false)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = adapter
//        binding.rvUsers.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(user: UserResponse) {
                hideUserList()
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.KEY_USER, user)
                startActivity(intent)
            }
        })
    }
}