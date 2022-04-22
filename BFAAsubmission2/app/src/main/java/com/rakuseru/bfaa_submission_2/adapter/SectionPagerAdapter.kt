package com.rakuseru.bfaa_submission_2.adapter

import android.content.ContentValues.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rakuseru.bfaa_submission_2.datasource.UserResponse
import com.rakuseru.bfaa_submission_2.ui.follows.FollowsFragment


class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var model: UserResponse? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return FollowsFragment.newInstance(position + 1, model)
    }

}