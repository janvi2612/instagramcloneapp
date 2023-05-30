package com.example.instagramclone.ui.tabs.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.databinding.PostListBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.utils.DiffUtilExt


class PostAdapter() : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    private var calllist = emptyList<Post>()
    class MyViewHolder(private val binding: FragmentProfileBinding): RecyclerView.ViewHolder(binding.root)
    {

        @SuppressLint("StringFormatInvalid")
        fun bind(currentItem :Post){
            binding.variable = currentItem


            binding.txtUsernameCaption.text = itemView.context.getString(R.string.user_caption,currentItem.caption.toString())
            binding.imgInsta.load(currentItem.img){
                crossfade(true)
                transformations(CircleCropTransformation())
                error(R.drawable.baseline_error_24)
            }


            binding.imgUserPost.load(currentItem.imgpost){
                crossfade(true)
                transformations()
                error(R.drawable.baseline_error_24)
            }
            binding.textLike.text = itemView.context.getString(R.string.like_no,currentItem.like)
            binding.locationVideo.text = itemView.context.getString(R.string.location,currentItem.location)
            binding.userId.text = itemView.context.getString(R.string.user_id,currentItem.userid)


        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostListBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostAdapter.MyViewHolder, position: Int) {
        val currentItem = calllist.getOrNull(position)

        currentItem?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return  calllist.size
    }

    fun setData(Result:List<Post>){

//        this.callList= user
//        notifyDataSetChanged()

        val userDiffUtil = DiffUtilExt(calllist, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        calllist = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }
}