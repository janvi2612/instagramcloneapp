package com.example.instagramclone.ui.tabs.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.instagramclone.R
import com.example.instagramclone.databinding.PostGridlayoutBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Profile
import com.example.instagramclone.utils.DiffUtilExt
import timber.log.Timber

class UserPostAdapter() : RecyclerView.Adapter<UserPostAdapter.MyViewHolder>() {

    private var calllist = emptyList<Post>()
    class MyViewHolder(private val binding: PostGridlayoutBinding): RecyclerView.ViewHolder(binding.root)
    {


        fun bind(currentItem : Post){
            binding.variable = currentItem
            Timber.e(currentItem.toString())

            binding.imagepost.load(currentItem.imgpost){
                crossfade(true)
                transformations()
                error(R.drawable.baseline_error_24)
            }


        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostGridlayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserPostAdapter.MyViewHolder {
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

    fun setData(Result:List<Post>){

//        this.callList= user
//        notifyDataSetChanged()

        val userDiffUtil = DiffUtilExt(calllist, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        calllist = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }
}