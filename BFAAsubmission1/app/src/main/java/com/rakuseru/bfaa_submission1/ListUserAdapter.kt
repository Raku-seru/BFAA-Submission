package com.rakuseru.bfaa_submission1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
        var tvUserName: TextView = itemView.findViewById(R.id.tv_user_name)
        var tvRepo: TextView = itemView.findViewById(R.id.tv_repositories)
        var tvFollowing: TextView = itemView.findViewById(R.id.tv_following)
        var tvFollowers: TextView = itemView.findViewById(R.id.tv_followers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.listuser, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatar, username, repository, followers, following) = listUser[position]
//        holder.imgPhoto.setImageResource(avatar)
        Glide.with(holder.itemView.context)
            .load(avatar) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.imgPhoto) // imageView mana yang akan diterapkan
        holder.tvUserName.text = username
        holder.tvRepo.text = "Total Repository : $repository"
        holder.tvFollowers.text = "Followers : $followers"
        holder.tvFollowing.text = "Following : $following"
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun getItemCount(): Int = listUser.size
}