package com.anna.searchImage

import android.database.Cursor

interface OnHistoricalDeleteListener {

    fun itemClick(id: String?, query: String?, cursor: Cursor)
}