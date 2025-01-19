package com.rabin2123.giphyapp.presentation.fragments.listofgifs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabin2123.domain.models.GifsInfoModel
import com.rabin2123.giphyapp.R
import com.rabin2123.giphyapp.databinding.FragmentItemPictureBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class ListOfGifsRecyclerAdapter(
    private val onOpenFullScreen: (Int) -> Unit,
    private val onHidePost: ((String) -> Unit)
) : PagingDataAdapter<GifsInfoModel.GifItem, ListOfGifsRecyclerAdapter.MyViewHolder>(
    ItemDiffCallBack()
) {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FragmentItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.bind(item, onOpenFullScreen)
    }

    inner class MyViewHolder(
        private val binding: FragmentItemPictureBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gifItem: GifsInfoModel.GifItem, onItemClicked: (Int) -> Unit) {
            with(binding) {
                tvTitle.text = gifItem.title
                Glide.with(root.context.applicationContext).load(gifItem.smallPicUrl)
                    .placeholder(R.drawable.elementor_placeholder_image)
                    .into(ivThumbnail)
                scope.launch {
                    Glide.with(root.context.applicationContext).downloadOnly()
                        .load(gifItem.fullPicUrl)
                        .submit()
                }
                ivThumbnail.setOnClickListener { onItemClicked(bindingAdapterPosition) }
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