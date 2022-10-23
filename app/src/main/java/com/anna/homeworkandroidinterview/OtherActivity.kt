package com.anna.homeworkandroidinterview

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.anna.homeworkandroidinterview.databinding.ActivityMainBinding
import com.anna.homeworkandroidinterview.databinding.ActivitySearchBinding

class OtherActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
        Log.d("search", "4 - handleIntent ")
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
                Log.d("search", "5 - doMySearch ")
            }
        }
    }

    private fun doMySearch(query: String) {

    }
}