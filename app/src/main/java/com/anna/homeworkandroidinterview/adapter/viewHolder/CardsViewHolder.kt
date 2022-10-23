package com.anna.homeworkandroidinterview.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import com.anna.homeworkandroidinterview.databinding.ItemCardsGridBinding
import com.anna.homeworkandroidinterview.databinding.ItemCardsVerticalBinding
import com.bumptech.glide.Glide


class CardsViewHolder(
    private val binding: ViewBinding,
    private val imagesList: List<SearchImageResponseData.Info?>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        when (binding) {
            is ItemCardsGridBinding -> { // 呈現網格的
                Glide.with(itemView.context)
                    .load(imagesList[bindingAdapterPosition]?.previewURL)
                    .into(binding.imageView)
                binding.tvTitle.text = imagesList[bindingAdapterPosition]?.imageTags
            }

            is ItemCardsVerticalBinding -> { // 呈現列表的
                Glide.with(itemView.context)
                    .load(imagesList[bindingAdapterPosition]?.largeImageURL)
                    .into(binding.imageView)

                binding.tvTitle.text = imagesList[bindingAdapterPosition]?.imageTags
            }
        }
    }
}