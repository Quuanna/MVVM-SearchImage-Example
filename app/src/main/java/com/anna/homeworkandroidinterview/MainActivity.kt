package com.anna.homeworkandroidinterview

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anna.homeworkandroidinterview.adapter.ImageRecycleViewAdapter
import com.anna.homeworkandroidinterview.data.CardsType
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import com.anna.homeworkandroidinterview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels() // 透過委托屬性使用Kotlin，本質上是間在使用ViewModelProvider
    private var imageViewDataList: List<SearchImageResponseData.Info?> = listOf()

    private var searchView: SearchView? = null
    private val menuItemClick = OnMenuItemClick()
    private val queryTextListener = OnQueryTextListener()
    private val suggestionListener = OnSuggestionListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        initTopAppBar()
        apiResponseObservers()
        clearSearchHistory()
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        searchHandleIntent(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        searchView = searchItem.actionView as SearchView

        //將可搜尋配置與SearchView關聯
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView?.apply {
            // 呼叫getSearchableInfo()獲取從可搜尋配置XML檔案建立的SearchableInfo物件，
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            // 監聽輸入搜尋
            setOnQueryTextListener(queryTextListener)
            // 監聽歷史紀錄搜尋
            setOnSuggestionListener(suggestionListener)
        }
        return true
    }

    private fun initTopAppBar() {
        binding.topAppBar.setOnMenuItemClickListener(menuItemClick)
    }
    /**
     *  註冊 LiveData 觀察者
     */
    private fun apiResponseObservers() {
        // response Success
        mViewModel.getImagesList.observe(this@MainActivity) { lists ->
            imageViewDataList = lists.dataList
            setViewLayout(binding.topAppBar.menu.findItem(R.id.menu_switch))
        }

        // response NotFound
        mViewModel.isSearchNotFound.observe(this@MainActivity) { isNotFound ->
            if (isNotFound) {
                shawDialogMessage(getString(R.string.dialog_not_found_message))
            }
        }

        // response Error
        mViewModel.responseError.observe(this@MainActivity) { errorMessage ->
            shawDialogMessage(errorMessage)
        }

        // ProgressBar
        mViewModel.isLoadRequest.observe(this@MainActivity) { isLoad ->
            if (isLoad) {
                binding.contentLoadingProgressBar.show()
            } else {
                binding.contentLoadingProgressBar.hide()
            }
        }
    }

    /**
     * SearchRecentSuggestions
     * doSearchSave - 儲存用戶最近查詢
     * clearSearchHistory - 清除建議資料
     */
    private fun searchHandleIntent(intent: Intent) {
        //Get Intent，驗證操作並獲取查詢
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doSearchSave(query)

            }
        }
    }
    private fun doSearchSave(query: String) {
        Log.d("search", "4 - saveRecentQuery ")
        SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            .saveRecentQuery(query, null) // 參數1是搜尋查詢字串、參數2有啟用兩行模式時使用，相反則帶入null
    }
    private fun clearSearchHistory() {
        SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            .clearHistory()
    }


    /**
     * 初始化設定RecycleView顯示方式
     * Params - dataList是API成功後回傳的資料
     *        - type是Layout呈現類型（列表、網格）
     */
    private fun switchRecycleViewLayout(
        imageViewDataList: List<SearchImageResponseData.Info?>,
        type: CardsType
    ) {
        binding.recyclerView.adapter = ImageRecycleViewAdapter(imageViewDataList, CardsType.GRID)
        when (type) {
            CardsType.GRID -> {
                binding.recyclerView.layoutManager =
                    GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
            }
            CardsType.VERTICAL -> {
                binding.recyclerView.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }
        }
    }

    private fun setViewLayout(menu: MenuItem) {
        when (menu.title) {
            getString(R.string.menu_item_switch_grid) -> {
                menu.title = getString(R.string.menu_item_switch_grid)
                menu.setIcon(R.drawable.ic_baseline_grid_view)
                switchRecycleViewLayout(imageViewDataList, CardsType.VERTICAL)
            }
            getString(R.string.menu_item_switch_list) -> {
                menu.title = getString(R.string.menu_item_switch_list)
                menu.setIcon(R.drawable.ic_baseline_list_view)
                switchRecycleViewLayout(imageViewDataList, CardsType.GRID)
            }
        }
    }
    /**
     *  訊息視窗
     */
    private fun shawDialogMessage(message: String): AlertDialog {
        val builder = AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                dialog.dismiss()
            }

        return builder.show()
    }

    /**
     * inner class
     * OnQueryTextListener - 輸入搜尋文字監聽的接口
     * OnSuggestionListener - 點擊歷史紀錄搜尋文字監聽的接口
     * OnMenuItemClickListener - menu單項單擊事件的接口
     */
    private inner class OnQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            // 搜尋打API
            query?.let { mViewModel.callApiResponseData(it) }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    }
    private inner class OnSuggestionListener : SearchView.OnSuggestionListener {
        override fun onSuggestionSelect(position: Int): Boolean {
            return false
        }

        @SuppressLint("Range")
        override fun onSuggestionClick(position: Int): Boolean {
            val cursor = searchView?.suggestionsAdapter?.getItem(position) as Cursor
            val selection =
                cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
            searchView?.setQuery(selection, false)
            return true
        }
    }
    private inner class OnMenuItemClick : androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(menu: MenuItem?): Boolean {
            return when (menu?.itemId) {
                R.id.menu_switch -> {
                    when (menu.title) {
                        getString(R.string.menu_item_switch_grid) -> {
                            menu.title = getString(R.string.menu_item_switch_list)
                            menu.setIcon(R.drawable.ic_baseline_list_view)
                            switchRecycleViewLayout(imageViewDataList, CardsType.GRID)
                        }
                        getString(R.string.menu_item_switch_list) -> {
                            menu.title = getString(R.string.menu_item_switch_grid)
                            menu.setIcon(R.drawable.ic_baseline_grid_view)
                            switchRecycleViewLayout(imageViewDataList, CardsType.VERTICAL)
                        }
                    }
                    true
                }
                else -> {
                    false // 無需處理流程
                }
            }
        }
    }

}
