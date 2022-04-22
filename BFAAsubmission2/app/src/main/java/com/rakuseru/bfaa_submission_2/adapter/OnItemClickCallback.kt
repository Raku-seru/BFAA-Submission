package com.rakuseru.bfaa_submission_2.adapter

import com.rakuseru.bfaa_submission_2.datasource.UserResponse

interface OnItemClickCallback {
    fun onItemClicked(user: UserResponse)
}