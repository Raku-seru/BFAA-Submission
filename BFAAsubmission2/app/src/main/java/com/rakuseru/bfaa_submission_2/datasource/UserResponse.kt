package com.rakuseru.bfaa_submission_2.datasource

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val login: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("followers_url")
    val followersUrl: String?,
    @field:SerializedName("following_url")
    val followingUrl: String?,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val bio: String?,
    @field:SerializedName("public_repos")
    val publicRepo: String?,
    val followers: String?,
    val following: String?,
) : Parcelable