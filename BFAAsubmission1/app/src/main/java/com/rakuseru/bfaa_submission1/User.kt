package com.rakuseru.bfaa_submission1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatar: Int,
    var username: String,
    var repository: String,
    var followers: String,
    var following: String,
    var realname: String,
    var location: String,
    var company: String,

) : Parcelable
