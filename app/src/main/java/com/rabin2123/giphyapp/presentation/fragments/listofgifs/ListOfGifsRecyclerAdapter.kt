package com.rabin2123.giphyapp.presentation.fragments.listofgifs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabin2123.domain.models.GifsInfoModel
import com.rabin2123.giphyapp.databinding.FragmentItemPictureBinding

class ListOfGifsRecyclerAdapter(
    private val onItemClicked: (GifsInfoModel.GifItem) -> Unit,
    private val onLoadNextPage: (() -> Unit)? = null
) : ListAdapter<GifsInfoModel.GifItem, ListOfGifsRecyclerAdapter.MyViewHolder>(ItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FragmentItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (currentList.size - 5 == position) {
            onLoadNextPage?.invoke()
        }
        holder.bind(currentList[position], onItemClicked)
    }

    inner class MyViewHolder(
        private val binding: FragmentItemPictureBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gifItem: GifsInfoModel.GifItem, onItemClicked: (GifsInfoModel.GifItem) -> Unit) {
            with(binding) {
                tvTitle.text = gifItem.title
                Glide.with(root.context.applicationContext).load(gifItem.smallPicUrl)
                    .into(ivThumbnail)
                ivThumbnail.setOnClickListener { onItemClicked(gifItem) }
            }
        }
    }

    class ItemDiffCallBack : DiffUtil.ItemCallback<GifsInfoModel.GifItem>() {
        override fun areItemsTheSame(
            oldItem: GifsInfoModel.GifItem, newItem: GifsInfoModel.GifItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GifsInfoModel.GifItem, newItem: GifsInfoModel.GifItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}