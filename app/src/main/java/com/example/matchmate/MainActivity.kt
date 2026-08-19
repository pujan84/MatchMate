package com.example.matchmate

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchmate.data.remote.NetworkState
import com.example.matchmate.databinding.ActivityMainBinding
import com.example.matchmate.ui.MatchAdapter
import com.example.matchmate.ui.MatchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MatchViewModel by viewModels()

    private lateinit var adapter: MatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MatchAdapter(
            onAccept = { viewModel.accept(it) },
            onDecline = { viewModel.decline(it) }
        )

        val spanCount = resources.getInteger(R.integer.grid_span_count)
        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        binding.recyclerView.adapter = adapter

        viewModel.matches.observe(this) {
            adapter.submitList(it)
        }

        viewModel.state.observe(this) {

            when (it) {

                is NetworkState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                }

                is NetworkState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE
                }

                is NetworkState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    
                    if (adapter.itemCount > 0) {
                        // If we already have data, show a Toast/Popup instead of full screen error
                        android.widget.Toast.makeText(this, it.message, android.widget.Toast.LENGTH_SHORT).show()
                    } else {
                        // If no data, show full screen error layout
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.txtError.text = it.message
                    }
                }
            }
        }
        binding.btnRetry.setOnClickListener {
            viewModel.retry()
        }
        binding.recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    super.onScrolled(recyclerView, dx, dy)

                    val manager =
                        recyclerView.layoutManager as LinearLayoutManager

                    if (manager.findLastVisibleItemPosition() >= adapter.itemCount - 2) {
                        viewModel.loadNextPage()
                    }
                }
            }
        )
    }
}