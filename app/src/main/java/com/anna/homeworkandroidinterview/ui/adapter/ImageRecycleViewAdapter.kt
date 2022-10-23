package com.anna.homeworkandroidinterview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import com.anna.homeworkandroidinterview.databinding.ItemCardViewBinding
import com.bumptech.glide.Glide

class ImageRecycleViewAdapter(
    private val imagesList: List<SearchImageResponseData.Info?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = imagesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return setCardsViewHolder(
            ItemCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardsViewHolder).bind()
    }


    private fun setCardsViewHolder(binding: ItemCardViewBinding) =
        CardsViewHolder(binding, imagesList)

    private inner class CardsViewHolder(
        private val binding: ItemCardViewBinding,
        private val imagesList: List<SearchImageResponseData.Info?>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            Glide.with(itemView.context)
                .load(imagesList[bindingAdapterPosition]?.previewURL)
                .into(binding.imageView)

            binding.tvTitle.text = imagesList[bindingAdapterPosition]?.imageTags
        }
    }
}