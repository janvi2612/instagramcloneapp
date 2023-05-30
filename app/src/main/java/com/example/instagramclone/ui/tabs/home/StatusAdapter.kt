package com.example.instagramclone.ui.tabs.home

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.transform.CircleCropTransformation
import com.example.instagramclone.R
import com.example.instagramclone.databinding.StoryListItemBinding
import com.example.instagramclone.model.Status
import com.example.instagramclone.utils.DiffUtilExt
import com.google.firebase.firestore.core.View
import timber.log.Timber


class StatusAdapter() : RecyclerView.Adapter<StatusAdapter.MyViewHolder>() {
    private var requestList = emptyList<Status>()
    class MyViewHolder(private val binding : StoryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem : Status){
            binding.variable=currentItem
            Timber.e(currentItem.imagepost.toString())
            binding.imgStatus.load(currentItem.imagepost){
                crossfade(true)
                transformations(CircleCropTransformation())
                error(R.drawable.baseline_error_24)
            }
            //binding.imgStatus.setImageResource(currentItem.imagepost)


        }



        companion object{
            fun from(parent: ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StoryListItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = requestList.getOrNull(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

//    fun removeItem(currentItem: Status) {
//        val index = requestList.indexOf(currentItem)
//        if(index != -1){
//            val newList = requestList.toMutableList()
//            newList.remove(currentItem)
//            setData(newList)
//        }
//    }

    fun setData(Result : List<Status>){
        val diffUtil = DiffUtilExt(requestList,Result)
        val diffUtilResult  = DiffUtil.calculateDiff(diffUtil)
        requestList = Result
        diffUtilResult.dispatchUpdatesTo(this)
    }
}