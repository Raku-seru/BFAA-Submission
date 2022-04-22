package com.rakuseru.bfaa_submission1

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailUser : AppCompatActivity() {

    companion object {
        const val DATA = "data"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val tvUsername: TextView = findViewById(R.id.tv_detail_username)
        val tvAvatar: ImageView = findViewById(R.id.img_detail_photo)
        val tvName: TextView = findViewById(R.id.tv_detail_name)
        val tvLocation: TextView = findViewById(R.id.tv_detail_location)
        val tvCompany: TextView = findViewById(R.id.tv_detail_company)
        val tvRepo: TextView = findViewById(R.id.tv_detail_repository)
        val tvFollowing: TextView = findViewById(R.id.tv_detail_following)
        val tvFollowers: TextView = findViewById(R.id.tv_detail_followers)

        val data = intent.getParcelableExtra<User>("DATA") as User
        Log.d("Detail Data", data.username.toString())

        tvAvatar.setImageResource(data.avatar)
        tvUsername.text = data.username
        tvName.text = data.realname
        tvLocation.text = data.location
        tvCompany.text = data.company
        tvRepo.text = "Repositories : " + data.repository
        tvFollowing.text = "Following : " + data.following
        tvFollowers.text = "Followers : " + data.followers
    }
}