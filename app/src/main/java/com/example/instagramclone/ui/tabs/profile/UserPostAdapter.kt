package com.example.instagramclone.ui.tabs.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.instagramclone.R
import com.example.instagramclone.databinding.HighlitStoryBinding
import com.example.instagramclone.databinding.PostGridlayoutBinding
import com.example.instagramclone.model.Profile
import com.example.instagramclone.utils.DiffUtilExt

class UserPostAdapter() : RecyclerView.Adapter<UserPostAdapter.MyViewHolder>() {

    private var calllist = emptyList<Profile>()
    class MyViewHolder(private val binding: PostGridlayoutBinding): RecyclerView.ViewHolder(binding.root)
    {

        @SuppressLint("StringFormatInvalid")
        fun bind(currentItem : Profile){
            binding.variable = currentItem



            binding.imagepost.load(currentItem.image){
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

    fun setData(Result:List<Profile>){

//        this.callList= user
//        notifyDataSetChanged()

        val userDiffUtil = DiffUtilExt(calllist, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        calllist = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }
}