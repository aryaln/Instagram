package com.albert.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.albert.instagram.models.Post

class PostActivity : AppCompatActivity() {
    private lateinit var btnAdd: Button
    private lateinit var btnBack: Button
    private lateinit var etCaption: EditText
    private lateinit var etImageUrl: EditText
    private var storage=  Storage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        binding()
        btnAdd.setOnClickListener {
            if(validateInput()){
                println(storage.getLoggedIn())
                val p = Post (null, etCaption.text.toString(), storage.getLoggedIn(), etImageUrl.text.toString())
                storage.appendPosts(p)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, "Invalid Post", Toast.LENGTH_LONG).show()
            }
        }
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateInput(): Boolean {
        var res = true
        if(TextUtils.isEmpty(etCaption.text)){
            etCaption.error="This field should not be empty"
            etCaption.requestFocus()
            res = false
        }
        else if(TextUtils.isEmpty(etImageUrl.text)){
            etImageUrl.error = "This field should not be empty"
            etImageUrl.requestFocus()
            res = false
        }
        return res
    }


    private fun binding() {
        btnAdd = findViewById(R.id.btnAdd)
        btnBack = findViewById(R.id.btnBack)
        etCaption = findViewById(R.id.etCaption)
        etImageUrl = findViewById(R.id.etImageUrl)
    }
}