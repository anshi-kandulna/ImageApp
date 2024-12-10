package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    lateinit var searchView: SearchView
    var currentPage = 1
    val pageSize = 80
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Initialize the adapter with an empty list
        myAdapter = MyAdapter(this, mutableListOf())
        recyclerView.adapter = myAdapter

        // Initialize the SearchView
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    currentPage = 1  // Reset to the first page
                    loadImages(query, currentPage)  // Load images based on the query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        // Call the method to load random images by default when the app starts
        loadImages("", currentPage)

        // Set up the scroll listener for pagination
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Check if the user has scrolled to the bottom
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        loadImages(searchView.query.toString(), currentPage)  // Load next page based on search query
                    }
                }
            }
        })
    }

    // Method to load images from the API
    private fun loadImages(query: String, page: Int) {
        isLoading = true

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val retrofitData: Call<PexelsPhotoResponse> = if (query.isEmpty()) {
            // Fetch random images if the query is empty
            retrofitBuilder.getRandomImages(perPage = pageSize, pageCount = page)
        } else {
            // Otherwise, fetch images based on the search query
            retrofitBuilder.searchImages(query, perPage = pageSize, pageCount = page)
        }

        retrofitData.enqueue(object : Callback<PexelsPhotoResponse> {
            override fun onResponse(call: Call<PexelsPhotoResponse>, response: Response<PexelsPhotoResponse>) {
                isLoading = false  // Reset the loading state

                val responseBody = response.body()
                val photoList = responseBody?.photos

                if (photoList != null) {
                    // If it's the first page, set the adapter
                    if (currentPage == 1) {
                        myAdapter = MyAdapter(this@MainActivity, photoList.toMutableList())
                        recyclerView.adapter = myAdapter
                    } else {
                        // If it's not the first page, add more items to the adapter
                        myAdapter.addItems(photoList)
                    }
                }

                // Increment page number for the next request
                currentPage++
            }

            override fun onFailure(call: Call<PexelsPhotoResponse>, t: Throwable) {
                isLoading = false  // Reset loading state on failure
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        })
    }
}
