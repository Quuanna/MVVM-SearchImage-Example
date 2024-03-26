package com.anna.homeworkandroidinterview.util

import android.app.SearchManager
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.SearchRecentSuggestions
import android.util.Log
import androidx.core.net.toUri
import com.anna.homeworkandroidinterview.ui.searchSuggest.MySuggestionProvider
import java.lang.ref.WeakReference

class HistoricalSuggestionsHelper private constructor(context: WeakReference<Context>) {

    private lateinit var mContext: Context
    private lateinit var contentResolver: ContentResolver

    companion object {
        fun initContext(context: Context) = HistoricalSuggestionsHelper(WeakReference(context))
    }

    init {
        context.get()?.let {
            mContext = it
            contentResolver = it.contentResolver
        }
    }

    fun deleteSuggestions(searchId:String): Int {
        val uriBuilder = Uri.Builder()
        uriBuilder.apply {
            scheme(ContentResolver.SCHEME_CONTENT)
            authority(MySuggestionProvider.AUTHORITY)
            appendPath(SearchManager.SUGGEST_URI_PATH_QUERY)
        }
        val uri = uriBuilder.build()
        return contentResolver.delete(uri, "_id=$searchId", null)
    }


    /**
     * 搜尋歷史紀錄
     */
    fun getRecentSuggestions(query: String?): Cursor? {
        val uriBuilder = Uri.Builder()
        uriBuilder.apply {
            scheme(ContentResolver.SCHEME_CONTENT)
            authority(MySuggestionProvider.AUTHORITY)
            appendPath(SearchManager.SUGGEST_URI_PATH_QUERY)
        }

        val selection = "?"
        val selectionArgs = arrayOf(query)
        return contentResolver.query(uriBuilder.build(), null, selection, selectionArgs, null)
    }


    /**
     * 參數1是搜尋查詢字串、參數2有啟用"最近的建議提供"模式時使用，相反則帶入null
     */
    fun doSearchSave(query: String) {
        Log.d("TEST", "doSearchSave query = $query")
        SearchRecentSuggestions(mContext, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            .saveRecentQuery(query, null)
    }

    /**
     * 刪除紀錄
     */
    fun clearSearchHistory() {
        SearchRecentSuggestions(mContext, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            .clearHistory()
    }


}