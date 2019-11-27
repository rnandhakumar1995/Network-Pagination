package com.soower.networkpagination.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.soower.networkpagination.R
import com.soower.networkpagination.model.NetworkState
import com.soower.networkpagination.model.pojo.News
import com.soower.networkpagination.view.adapter.NewsListAdapter
import com.soower.networkpagination.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        search_btn.setOnClickListener {
            val query = query_et.text.toString()
            if (query.isNotEmpty()) {
                viewModel.updaterQuery(query)
                val adapter = NewsListAdapter()
                news_rv.layoutManager = LinearLayoutManager(this)
                news_rv.adapter = adapter
                viewModel.newsList.observe(this, Observer<PagedList<News>> { adapter.submitList(it) })
                viewModel.getNetworkState()
                    .observe(this, Observer<NetworkState> { adapter.setNetworkState(it) })
            }
        }
    }
}
