package com.example.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth= FirebaseAuth.getInstance()

        val currentuser = auth.currentUser
        if(currentuser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        login()
    }

    private fun login() {

        loginButton.setOnClickListener {

            if (TextUtils.isEmpty(usernameInput.text.toString())) {
                usernameInput.setError("Please enter user name ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                passwordInput.setError("Please enter password ")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(
                usernameInput.text.toString(),
                passwordInput.text.toString()
            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        //if authentication is success we will send user to main activity

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()//so user doesnt comeback to login state again
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed, please try again! ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
            registerText.setOnClickListener {
                //startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
                startActivity(Intent(this@LoginActivity, RegisterationActivity::class.java))
            }

    }
}