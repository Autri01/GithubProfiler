package com.example.github_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    val originalList = arrayListOf<User>()
    val adapter=UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        adapter.onItemClick={
            val intent=Intent(this,UserActivity::class.java)
            intent.putExtra("ID",it)
            startActivity(intent)
        }

        userRv.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=this@MainActivity.adapter
        }
        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchUser(it) }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchUser(it) }
                return true
            }
        })
        searchView.setOnCloseListener {
            adapter.swapData(originalList)

            true
        }
        GlobalScope.launch(Dispatchers.Main){
            val response= withContext(Dispatchers.IO){Client.api.getUser()}
            if(response.isSuccessful){
                response.body()?.let {
                    originalList.addAll(it)
                    adapter.swapData(it)
                }
            }
        }
    }
    fun searchUser(query: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) { Client.api.searchUser(query) }
            if (response.isSuccessful) {
                response.body()?.let {
                    adapter.swapData(it.items as List<User>)
                }
            }
        }
    }
}
