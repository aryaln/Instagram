package com.albert.instagram

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.albert.instagram.adapters.PostAdapder
import com.albert.instagram.adapters.StoryAdapter
import com.albert.instagram.models.Post
import com.albert.instagram.models.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
//    private var lstStories= ArrayList<User>()
    private var lstPosts= ArrayList<Post>()
    private lateinit var rvStories: RecyclerView
    private lateinit var rvPosts: RecyclerView
    private lateinit var btnAddImage: FloatingActionButton
    private var storage =  Storage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        println(storage.getLoggedIn())
        if(storage.getLoggedIn() == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("msg", "Invalid Login")
            startActivity(intent)
        }

        binding()

        loadStoryAdapter()
        loadPostAdapter()
        btnAddImage.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(storage.getLoggedIn() == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("msg", "Invalid Login")
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout?")

        builder.setMessage("Do you want to logout?")

        builder.setIcon(android.R.drawable.ic_dialog_info)

        builder.setPositiveButton("YES"){ _, _ ->
            storage.setLoggedIn(null)
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("msg", "Logged Out")
            startActivity(intent)
        }

        val alert: AlertDialog = builder.create()
        alert.setCancelable(true)
        alert.show()
    }

    private fun loadPostAdapter() {
        val arr = storage.getPosts().values.toTypedArray().reversedArray()
        val adapter = PostAdapder(arr, this)
        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = adapter
    }

    private fun loadStoryAdapter() {
        val arr = storage.getUsers().values.toTypedArray().reversedArray()
        val adapter = StoryAdapter(arr, this)
        rvStories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvStories.adapter = adapter
    }

    private fun binding() {
        rvStories = findViewById(R.id.rvStories)
        rvPosts = findViewById(R.id.rvPosts)
        btnAddImage = findViewById(R.id.btnAddImage)
    }
}