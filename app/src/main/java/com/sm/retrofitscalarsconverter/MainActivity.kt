package com.sm.retrofitscalarsconverter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sm.retrofitscalarsconverter.model.ItemsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


class MainActivity : AppCompatActivity() {
    var list: ArrayList<ItemsViewModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = ArrayList()
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://android-developers.googleblog.com/")
            .build()

        val api = retrofit.create(Api::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val result =
                api.getPageResponse("/2022/04/twitter-going-all-in-on-jetpack-compose.html")
            if (result.isSuccessful && result.body() != null) {
//                Log.d("Ravi data",result.body().toString())
                val htmldoc = Jsoup.parse(result.body())
                val element = htmldoc.select("img")

                element.forEach {
                    Log.d("Ravidata", it.attr("src"))
                    /*loop method 1st set Image in thread*/
//                    for(i in element){
//                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//                            Glide.with(this@MainActivity)
//                                .load(it.attr("src"))
//                                .into(image)
//                        },1000)
//                       SystemClock.sleep(200)
//                    }

                    /*Set Image Data in Recycler View */
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        list?.add(ItemsViewModel(it.attr("src")))
                        val adapter = CustomAdapter(list!!)
                        recyclerView.adapter=adapter
                    },2000)


                    /*loop method 2nd set Image in thread*/

//                    val handler = Handler(Looper.getMainLooper())
//                    val runnable: Runnable = object : Runnable {
//                        var i = 0
//                        override fun run() {
//                            i = 1
//                            while (i < 20) {
//                                handler.postDelayed({ // need to do tasks on the UI thread
//                                    Glide.with(this@MainActivity)
//                                        .load(it.attr("src"))
//                                        .into(image)
//                                }, 1000)
//                                //Add some downtime
//                                SystemClock.sleep(500)
//                                i++
//                            }
//                        }
//                    }
//                    Thread(runnable).start()

                }
            }
        }


    }

    interface Api {
        @GET
        suspend fun getPageResponse(@Url url: String): Response<String>
    }
}