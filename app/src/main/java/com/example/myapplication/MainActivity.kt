package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.recyclerView)
       // recyclerView.layoutManager = GridLayoutManager(this, 2)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofitBuilder.getImageData()

        retrofitData.enqueue(object : Callback<PexelsPhotoResponse> {
            override fun onResponse(call: Call<PexelsPhotoResponse>, response: Response<PexelsPhotoResponse>) {
                var responseBody = response.body()
                val photoList = responseBody?.photos

                myAdapter = photoList?.let { MyAdapter(this@MainActivity, it) }!!
                recyclerView.adapter = myAdapter
                //recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            }

            override fun onFailure(call: Call<PexelsPhotoResponse>, t: Throwable) {
                // Handle failure (e.g., network issue)
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        })



    }

    private fun GridLayoutManager(callback: Callback<PexelsPhotoResponse>, i: Int): GridLayoutManager {
      return GridLayoutManager(this,2)
    }
}

