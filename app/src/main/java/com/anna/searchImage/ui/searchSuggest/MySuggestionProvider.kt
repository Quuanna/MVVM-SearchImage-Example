package com.anna.searchImage.ui.searchSuggest

import android.content.SearchRecentSuggestionsProvider
import android.net.Uri
import android.os.Bundle

class MySuggestionProvider : SearchRecentSuggestionsProvider() {


    init {
        setupSuggestions(AUTHORITY, MODE) // 應用程式和資料庫模式
    }

    companion object {
        const val AUTHORITY = "com.anna.homeworkandroidinterview.ui.searchSuggest.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES or DATABASE_MODE_2LINES
    }

    override fun delete(uri: Uri, extras: Bundle?): Int {
        return super.delete(uri, extras)
    }
}