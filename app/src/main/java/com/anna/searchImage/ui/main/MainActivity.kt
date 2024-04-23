package com.anna.searchImage.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anna.searchImage.MyApplication
import com.anna.searchImage.OnHistoricalDeleteListener
import com.anna.searchImage.R
import com.anna.searchImage.data.element.CardsType
import com.anna.searchImage.data.model.response.SearchImageResponseData
import com.anna.searchImage.databinding.ActivityMainBinding
import com.anna.searchImage.ui.adapter.CustomHistoricalSuggestionsAdapter
import com.anna.searchImage.ui.adapter.ImageRecycleViewAdapter
import com.anna.searchImage.util.EventObserver
import com.anna.searchImage.util.HistoricalSuggestionsHelper


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mViewModel by viewModels<MainViewModel> {
        TasksViewModelFactory((application as MyApplication).imagesRepository)
    }

    private val historicalSuggestionsHelper by lazy { HistoricalSuggestionsHelper.initContext(this) }

    private var mImageViewDataList: List<SearchImageResponseData.Info?> = listOf()

    private var mSearchView: SearchView? = null
    private val mMenuItemClick = OnMenuItemClick()
    private val mQueryTextListener = OnQueryTextListener()
    private val mSuggestionListener = OnSuggestionListener()
    private lateinit var customSuggestionsAdapter: CustomHistoricalSuggestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        initTopAppBar()
        initObservers()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        searchHandleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        mSearchView = searchItem.actionView as? SearchView

        //將可搜尋配置與SearchView關聯
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView?.apply {
            // 呼叫getSearchableInfo()獲取從可搜尋配置XML檔案建立的SearchableInfo物件，
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            // 監聽輸入搜尋
            setOnQueryTextListener(mQueryTextListener)
            // 監聽歷史紀錄搜尋
            setOnSuggestionListener(mSuggestionListener)
            isSubmitButtonEnabled = true
            // 客製Adapter View
            configureSearchMenu(this)
        }

        return true
    }

    private fun configureSearchMenu(searchView: SearchView) {
        searchView.run {
            setIconifiedByDefault(true)
            customSuggestionsAdapter = CustomHistoricalSuggestionsAdapter(this@MainActivity)
            suggestionsAdapter = customSuggestionsAdapter
            customSuggestionsAdapter.addOnHistoricalDeleteListener(object : OnHistoricalDeleteListener {
                override fun itemClick(id: String?, query: String?, cursor: Cursor) {
                    if (!id.isNullOrEmpty() && !query.isNullOrEmpty()) {
                        historicalSuggestionsHelper.deleteSuggestions(id)
                    }
                }
            })
        }
    }


    private fun initTopAppBar() {
        binding.topAppBar.setOnMenuItemClickListener(mMenuItemClick)
    }

    /**
     *  註冊 LiveData 觀察者
     */
    private fun initObservers() {
        // response Success
        mViewModel.getResponseImagesList.observe(this@MainActivity) { lists ->
            mImageViewDataList = lists.dataList
            setViewLayout(binding.topAppBar.menu.findItem(R.id.menu_switch))
        }

        // response NotFound
        mViewModel.isSearchNotFound.observe(this@MainActivity) { isNotFound ->
            if (isNotFound) {
                shawDialogMessage(getString(R.string.dialog_not_found_message))
            }
        }


        // response Error 一次性
        mViewModel.responseError.observe(this@MainActivity, EventObserver { errorMessage ->
            shawDialogMessage(errorMessage)
        })

        // service Error 一次性
        mViewModel.serviceError.observe(this@MainActivity, EventObserver { errorMessage ->
            shawDialogMessage(errorMessage)
        })

        // ProgressBar 一次性
        mViewModel.isShowProgress.observe(this@MainActivity, EventObserver { isLoad ->
            if (isLoad) {
                binding.contentLoadingProgressBar.show()
            } else {
                binding.contentLoadingProgressBar.hide()
            }
        })
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
        binding.recyclerView.adapter = ImageRecycleViewAdapter(imageViewDataList)
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
                switchRecycleViewLayout(mImageViewDataList, CardsType.VERTICAL)
            }

            getString(R.string.menu_item_switch_list) -> {
                menu.title = getString(R.string.menu_item_switch_list)
                menu.setIcon(R.drawable.ic_baseline_list_view)
                switchRecycleViewLayout(mImageViewDataList, CardsType.GRID)
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
                historicalSuggestionsHelper.doSearchSave(query)
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
     */
    private inner class OnQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            // 搜尋打API
            query?.let { mViewModel.callApiResponseData(it) }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            val cursor = historicalSuggestionsHelper.getRecentSuggestions(newText)
            mSearchView?.suggestionsAdapter?.swapCursor(cursor)
            return true
        }
    }

    /**
     *  OnSuggestionListener - 點擊歷史紀錄搜尋文字監聽的接口
     */
    private inner class OnSuggestionListener : SearchView.OnSuggestionListener {
        override fun onSuggestionSelect(position: Int): Boolean {
            return false
        }

        @SuppressLint("Range")
        override fun onSuggestionClick(position: Int): Boolean {
            val cursor = mSearchView?.suggestionsAdapter?.getItem(position) as? Cursor
            val selection = cursor?.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
            mSearchView?.setQuery(selection, true)
            return true
        }
    }

    /**
     * OnMenuItemClickListener - menu單項單擊事件的接口
     */
    private inner class OnMenuItemClick :
        androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(menu: MenuItem?): Boolean {
            return when (menu?.itemId) {
                R.id.menu_switch -> {
                    when (menu.title) {
                        getString(R.string.menu_item_switch_grid) -> {
                            menu.title = getString(R.string.menu_item_switch_list)
                            menu.setIcon(R.drawable.ic_baseline_list_view)
                            switchRecycleViewLayout(mImageViewDataList, CardsType.GRID)
                        }

                        getString(R.string.menu_item_switch_list) -> {
                            menu.title = getString(R.string.menu_item_switch_grid)
                            menu.setIcon(R.drawable.ic_baseline_grid_view)
                            switchRecycleViewLayout(mImageViewDataList, CardsType.VERTICAL)
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
