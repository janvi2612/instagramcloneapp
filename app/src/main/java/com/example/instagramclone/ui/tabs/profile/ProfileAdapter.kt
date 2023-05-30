package com.example.instagramclone.ui.tabs.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.model.User
import com.example.instagramclone.ui.tabs.home.PostAdapter

class ProfileAdapter() : RecyclerView.Adapter<ProfileAdapter.MyViewHolder>() {
    private val list = emptyList<User>()

    class MyViewHolder(private val binding: FragmentProfileBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("StringFormatInvalid")
        fun bind(currentItem : User) {

            binding.textusername.text =
                itemView.context.getString(R.string.username, currentItem.username.toString())
            binding.txtusername.text =
                itemView.context.getString(R.string.Username, currentItem.name.toString())
            binding.txt.text = itemView.context.getString(R.string.text, currentItem.bio.toString())
            binding.txtpostno.text =
                itemView.context.getString(R.string.post_no, currentItem.postno.toString())
            binding.txtfollowersno.text = itemView.context.getString(
                R.string.followers_no,
                currentItem.followersno.toString()
            )
            binding.txtfollowingno.text = itemView.context.getString(
                R.string.following_no,
                currentItem.followingno.toString()
            )
            binding.imgprofile.load(currentItem.profilepic) {
                crossfade(true)
                transformations(CircleCropTransformation())
                error(R.drawable.baseline_error_24)
            }
        }
        companion object {
            fun from(parent: ViewGroup): PostAdapter.MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentProfileBinding.inflate(layoutInflater, parent, false)
                return PostAdapter.MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}