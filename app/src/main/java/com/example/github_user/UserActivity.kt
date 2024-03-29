package com.example.github_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.coroutines.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val ide:String?=intent.getStringExtra("ID")

        GlobalScope.launch(Dispatchers.Main) {
            val response= withContext(Dispatchers.IO){Client.api.getUserById(ide)}
            if(response.isSuccessful){
                response.body()?.let {
                    textView.text=it.name
                    textView2.text=it.login
                    Picasso.get().load(it.avatarUrl).into(imageView)
                }
            }
        }
    }
}