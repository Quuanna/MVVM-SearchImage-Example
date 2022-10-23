package com.anna.homeworkandroidinterview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anna.homeworkandroidinterview.data.CardsType
import com.anna.homeworkandroidinterview.adapter.viewHolder.CardsViewHolder
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import com.anna.homeworkandroidinterview.databinding.ItemCardsGridBinding
import com.anna.homeworkandroidinterview.databinding.ItemCardsVerticalBinding

//
class ImageRecycleViewAdapter(
    private val imagesList: List<SearchImageResponseData.Info?>, private val type: CardsType
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = imagesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            CardsType.GRID -> {
                setCardsViewHolder(
                    ItemCardsGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            CardsType.VERTICAL -> {
                setCardsViewHolder(
                    ItemCardsVerticalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardsViewHolder).bind()
    }

    private fun setCardsViewHolder(binding: ViewBinding) =
        CardsViewHolder(binding, imagesList)

}