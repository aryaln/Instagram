package com.albert.instagram

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.albert.instagram.models.User


class MainActivity : AppCompatActivity() {
    private lateinit var etFname: EditText
    private lateinit var etLname: EditText
    private lateinit var etId: EditText
    private lateinit var etImage: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var sBatch: Spinner
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private var batch = arrayListOf("26A", "22B", "23A", "23B", "24A", "24B", "25A", "25B", "25C")
    private var dBatch = "26A"
    private var storage = Storage()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        binding()

        adapterInit()
        btnRegister.setOnClickListener {
            if(validateInput()){
                val u = User(etId.text.toString().toInt(),
                        etFname.text.toString(),
                        etLname.text.toString(),
                        etUsername.text.toString(),
                        etPassword.text.toString(),
                        etImage.text.toString(),
                        dBatch
                )
                storage.appendUsers(u)
                val intent = Intent(
                        this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Invalid Signup", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogin.setOnClickListener {
            val intent = Intent(
                this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    private fun validateInput(): Boolean {
        var res : Boolean = true
        when {
            TextUtils.isEmpty(etId.text) -> {
                etId.error = "This field should not be empty"
                etId.requestFocus()
                res = false
            }
            storage.getUsers()[etId.text.toString().toInt()] != null -> {
                etId.error = "Id already taken"
                etId.requestFocus()
                res = false
            }
            TextUtils.isEmpty(etFname.text) -> {
                etFname.error = "This field should not be empty"
                etFname.requestFocus()
                res = false
            }
            TextUtils.isEmpty(etLname.text) -> {
                etLname.error = "This field should not be empty"
                etLname.requestFocus()
                res = false
            }
            TextUtils.isEmpty(etUsername.text) -> {
                etUsername.error = "This field should not be empty"
                etUsername.requestFocus()
                res = false
            }
            storage.hasUsername(etUsername.text.toString()) != null -> {
                etUsername.error = "Username already taken"
                etUsername.requestFocus()
                res = false
            }
            TextUtils.isEmpty(etPassword.text) -> {
                etPassword.error = "This field should not be empty"
                etPassword.requestFocus()
                res = false
            }
        }

        return res

    }


    private fun adapterInit() {
        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                batch,
        )

        sBatch.adapter = adapter
        sBatch.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val b = parent?.getItemAtPosition(position).toString()
                dBatch = b
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun binding() {
        etFname = findViewById(R.id.etFname)
        etId = findViewById(R.id.etId)
        etLname = findViewById(R.id.etLname)
        etPassword = findViewById(R.id.etPassword)
        etImage = findViewById(R.id.etImage)
        etUsername = findViewById(R.id.etUsername)
        sBatch = findViewById(R.id.sBatch)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)


    }
}