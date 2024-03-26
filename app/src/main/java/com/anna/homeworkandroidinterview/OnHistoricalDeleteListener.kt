package com.anna.homeworkandroidinterview

import android.database.Cursor

interface OnHistoricalDeleteListener {

    fun itemClick(id: String?, query: String?, cursor: Cursor)
}