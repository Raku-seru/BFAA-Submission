package com.rakuseru.bfaa_submission_2.ui.userdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.rakuseru.bfaa_submission_2.R
import com.rakuseru.bfaa_submission_2.adapter.SectionPagerAdapter
import com.rakuseru.bfaa_submission_2.databinding.ActivityDetailUserBinding
import com.rakuseru.bfaa_submission_2.datasource.UserResponse
import com.rakuseru.bfaa_submission_2.service.NetworkConnection

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailDataLayout.visibility = View.GONE

        val user = intent.getParcelableExtra<UserResponse>(KEY_USER)
        if (user != null) {
            user.login?.let {
                checkInternetConnection(it)
            }
        }
    }

    // Check Internet Connection
    private fun checkInternetConnection(username: String) {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                showNoInternet(false)
                showFailedLoadData(false)

                val detailViewModel: DetailViewModel by viewModels {
                    DetailViewModelFactory(username)
                }
                detailViewModel.isLoading.observe(this) {
                    showProgressBar(it)
                }
                detailViewModel.isNoInternet.observe(this) {
                    showNoInternet(it)
                }
                detailViewModel.isDataFailed.observe(this) {
                    showNoInternet(it)
                }
                detailViewModel.detailUser.observe(this@DetailUserActivity) { userResponse ->
                    if (userResponse != null) {
                        setData(userResponse)
                        setTabLayoutAdapter(userResponse)
                    }
                }
            } else {
                binding.detailDataLayout.visibility = View.GONE
                showFailedLoadData(true)
                showNoInternet(false)
            }
        }
    }

    private fun setTabLayoutAdapter(user: UserResponse) {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.model = user
        binding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun setData(userResponse: UserResponse?) {
        if (userResponse != null) {
            with(binding) {
                detailDataLayout.visibility = View.VISIBLE
                imgDetailPhoto.visibility = View.VISIBLE

                // Avatar
                Glide.with(root)
                    .load(userResponse.avatarUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .circleCrop()
                    .into(binding.imgDetailPhoto)

                tvDetailName.visibility = View.VISIBLE
                tvDetailUsername.visibility = View.VISIBLE
                tvDetailName.text = userResponse.name
                tvDetailUsername.text = userResponse.login

                if (userResponse.bio != null) {
                    tvDetailBio.visibility = View.VISIBLE
                    tvDetailBio.text = userResponse.bio
                } else {
                    tvDetailBio.visibility = View.GONE
                }

                if (userResponse.company != null) {
                    tvDetailCompany.visibility = View.VISIBLE
                    tvDetailCompany.text = userResponse.company
                } else {
                    tvDetailCompany.visibility = View.GONE
                }

                if (userResponse.location != null) {
                    tvDetailLocation.visibility = View.VISIBLE
                    tvDetailLocation.text = userResponse.location
                } else {
                    tvDetailLocation.visibility = View.GONE
                }

                if (userResponse.blog != null) {
                    tvDetailBlog.visibility = View.VISIBLE
                    tvDetailBlog.text = userResponse.blog
                } else {
                    tvDetailBlog.visibility = View.GONE
                }

                if (userResponse.followers != null) {
                    tvFollowersValue.visibility = View.VISIBLE
                    tvFollowersValue.text = userResponse.followers
                } else {
                    tvFollowersValue.visibility = View.GONE
                }
                if (userResponse.followers != null) {
                    tvUserFollowers.visibility = View.VISIBLE
                } else {
                    tvUserFollowers.visibility = View.GONE
                }

                if (userResponse.following != null) {
                    tvFollowingValue.visibility = View.VISIBLE
                    tvFollowingValue.text = userResponse.following
                } else {
                    tvFollowingValue.visibility = View.GONE
                }
                if (userResponse.following != null) {
                    tvFollowing.visibility = View.VISIBLE
                } else {
                    tvFollowing.visibility = View.GONE
                }

                if (userResponse.publicRepo != null) {
                    tvRepositoryValue.visibility = View.VISIBLE
                    tvRepositoryValue.text = userResponse.publicRepo
                } else {
                    tvRepositoryValue.visibility = View.GONE
                }
                if (userResponse.publicRepo != null) {
                    tvRepository.visibility = View.VISIBLE
                } else {
                    tvRepository.visibility = View.GONE
                }
            }
        } else {
            Log.i(TAG, "Function setData is error")
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.detailLoadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoInternet(isNoInternet: Boolean) {
        binding.tvFailedInternet.visibility = if (isNoInternet) View.VISIBLE else View.GONE
    }

    private fun showFailedLoadData(isFailed: Boolean) {
        binding.tvDetailFailed.visibility = if (isFailed) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY_USER = "user"
        private const val TAG = "DetailUserActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}