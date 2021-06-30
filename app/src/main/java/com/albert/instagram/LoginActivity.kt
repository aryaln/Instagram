package com.albert.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.albert.instagram.models.User

class LoginActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private var storage= Storage()
    private var found: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val intent = intent.extras
        if (intent != null) {
            val err = intent.getString("msg")
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
        }

        binding()

        btnLogin.setOnClickListener{
            if(validateLogin()){
//                println(found)

                storage.setLoggedIn(found)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show()
            }
        }
        btnRegister.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun validateLogin(): Boolean {
        var res= true
        found = storage.hasUsername(etUsername.text.toString())
        when {
            TextUtils.isEmpty(etUsername.text) -> {
                etUsername.error = "This field should not be empty"
                etUsername.requestFocus()
                res = false
            }
            found == null -> {
                etUsername.error = "Username not found"
                etUsername.requestFocus()
                res = false
            }
            TextUtils.isEmpty(etPassword.text) -> {
                etPassword.error = "This field should not be empty"
                etPassword.requestFocus()
                res = false
            }
            found!!.password != etPassword.text.toString() -> {
                etPassword.error = "Password didnot match. Please try again"
                etPassword.requestFocus()
                res = false
            }
        }
        return res
    }

    private fun binding() {
        etPassword = findViewById(R.id.etPassword)
        etUsername = findViewById(R.id.etUsername)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)

    }
}