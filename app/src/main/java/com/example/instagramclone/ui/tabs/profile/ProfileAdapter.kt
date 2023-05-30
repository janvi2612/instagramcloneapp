package com.example.instagramclone.ui.tabs.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.databinding.HighlitStoryBinding
import com.example.instagramclone.databinding.PostListBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Profile
import com.example.instagramclone.model.User
import com.example.instagramclone.ui.tabs.home.PostAdapter
import com.example.instagramclone.utils.DiffUtilExt

class ProfileAdapter() : RecyclerView.Adapter<ProfileAdapter.MyViewHolder>() {

    private var calllist = emptyList<Profile>()
    class MyViewHolder(private val binding: HighlitStoryBinding): RecyclerView.ViewHolder(binding.root)
    {

        @SuppressLint("StringFormatInvalid")
        fun bind(currentItem :Profile){
            binding.variable = currentItem


            binding.textitem.text = currentItem.userstory.toString()
            binding.image.load(currentItem.image){
                crossfade(true)
                transformations(CircleCropTransformation())
                error(R.drawable.baseline_error_24)
            }




        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HighlitStoryBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = calllist.getOrNull(position)

        currentItem?.let {
            holder.bind(it)
        }
    }



    override fun getItemCount(): Int {
        return  calllist.size
    }

    fun setData(Result:List<Profile>){

//        this.callList= user
//        notifyDataSetChanged()

        val userDiffUtil = DiffUtilExt(calllist, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        calllist = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }
}