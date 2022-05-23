package com.danish.kisah25nabi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.danish.kisah25nabi.data.KisahResponse
import com.danish.kisah25nabi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var _viewModel: MainViewModel? = null
    private val viewModel get() = _viewModel as MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getKisahNabi()
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.isError.observe(this) { showError(it) }
        viewModel.KisahResponse.observe(this) {
            showData(it)
        }

    }

    private fun showError(isError: Throwable?) {
        Log.e("MainActivity", "ShowError: $isError")
    }

    private fun showData(data: List<KisahResponse>?) {
        binding.recyclerMain.apply {
            val mAdapter = KisahAdapter()
            mAdapter.setData(data)
            adapter = mAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(item: KisahResponse) {
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra(DetailActivity.EXTRA_DATA, item)
                    )
                }
            })
        }

    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == true) {
            binding.progressMain.visibility = View.VISIBLE
        } else {
            binding.progressMain.visibility = View.INVISIBLE
        }
    }
}




