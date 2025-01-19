package com.rabin2123.giphyapp.presentation.fragments.fullscreengifs

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabin2123.domain.models.GifsInfoModel
import com.rabin2123.giphyapp.R
import com.rabin2123.giphyapp.databinding.FragmentItemFullscreenGifBinding
import com.rabin2123.giphyapp.databinding.FragmentItemPictureBinding

class FullscreenRecyclerAdapter(
    private val onHidePost: ((String) -> Unit),
    private val firstIndex: Int

) : PagingDataAdapter<GifsInfoModel.GifItem, FullscreenRecyclerAdapter.MyViewHolder>(
    ItemDiffCallBack()
) {
    private var checkPos: Boolean = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FragmentItemFullscreenGifBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(
            if (checkPos) {
                checkPos = false
                firstIndex
            } else {
                position
            }
        )
        if (item != null)
            holder.bind(item)
    }

    inner class MyViewHolder(
        private val binding: FragmentItemFullscreenGifBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gifItem: GifsInfoModel.GifItem) {
            with(binding) {
                tvTitle.text = gifItem.title
                Glide.with(root.context.applicationContext)
                    .load(gifItem.fullPicUrl)
                    .placeholder(R.drawable.elementor_placeholder_image)
                    .centerInside()
                    .into(ivFullscreen)
                btnHidePost.setOnClickListener { onHidePost(gifItem.id) }
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