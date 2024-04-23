package com.anna.searchImage.ui.adapter

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import androidx.cursoradapter.widget.CursorAdapter
import com.anna.searchImage.OnHistoricalDeleteListener
import com.anna.searchImage.databinding.ItemHistoricalSuggestionBinding

class CustomHistoricalSuggestionsAdapter(context: Context) :
    CursorAdapter(context, null, 0) {

    private lateinit var historicalDeleteListener: OnHistoricalDeleteListener
    fun addOnHistoricalDeleteListener(listener: OnHistoricalDeleteListener) {
        this.historicalDeleteListener = listener
    }

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val view =
            ItemHistoricalSuggestionBinding.inflate(LayoutInflater.from(context), parent, false)

        val viewHolder = ViewHolder(view)
        view.root.tag = viewHolder

        return view.root
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val viewHolder = view.tag as? ViewHolder
        val itemId: Int? = cursor.getIntOrNull(cursor.getColumnIndex("_id"))
        val searchStr: String? = cursor.getStringOrNull(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
        viewHolder?.searchContent?.text = searchStr
        viewHolder?.deleteButton?.setOnClickListener {
            historicalDeleteListener.itemClick(itemId.toString(), searchStr, cursor)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: ItemHistoricalSuggestionBinding) {
        val deleteButton = view.imageViewDelete as? ImageView
        val searchContent = view.tvContent as? TextView
    }

}
