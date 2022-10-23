package com.anna.homeworkandroidinterview

import android.os.Bundle
import android.util.Log
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
    private val queryTextListener = OnQueryTextListener()
    private val menuItemClick = OnMenuItemClick()
    private var imageViewDataList: List<SearchImageResponseData.Info?> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTopAppBar()
        apiResponseObservers()

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
            setRecycleViewLayout(lists.dataList)
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
     * 初始化設定RecycleView顯示方式
     * Params - dataList是API成功後回傳的資料
     *        - type是Layout呈現類型（列表、網格）
     */
    private fun setRecycleViewLayout(dataList: List<SearchImageResponseData.Info?>) {
        binding.recyclerView.adapter = ImageRecycleViewAdapter(dataList, CardsType.GRID)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
    }

    private fun switchViewLayout(
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
     * OnQueryTextListener - 搜尋文字監聽的接口
     * OnMenuItemClickListener - menu單項單擊事件的接口
     */
    private inner class OnQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            // 搜尋打API
            query?.let { mViewModel.callApiResponseData(it) }
            Log.d("測試", "onQueryTextSubmit - $query")
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            Log.d("測試", "onQueryTextChange - $newText")
            return false
        }
    }

    private inner class OnMenuItemClick :
        androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(menu: MenuItem?): Boolean {
            return when (menu?.itemId) {
                R.id.menu_search -> {
                    val searchView = menu.actionView as SearchView
                    searchView.setOnQueryTextListener(queryTextListener)
                    true  // 有處理流程
                }
                R.id.menu_switch -> {
                    when (menu.title) {
                        getString(R.string.menu_item_switch_grid) -> {
                            menu.title = getString(R.string.menu_item_switch_list)
                            menu.setIcon(R.drawable.ic_baseline_list_view)
                            switchViewLayout(imageViewDataList, CardsType.GRID)
                        }
                        getString(R.string.menu_item_switch_list) -> {
                            menu.title = getString(R.string.menu_item_switch_grid)
                            menu.setIcon(R.drawable.ic_baseline_grid_view)
                            switchViewLayout(imageViewDataList, CardsType.VERTICAL)
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
