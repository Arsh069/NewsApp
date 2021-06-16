package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.news.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registeration.*

class RegisterationActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    var databaseReference :  DatabaseReference? = null /*now we creating 2 variables for real time data base refrence and
                                                                database refrence*/
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile") //thats table name
        auth = FirebaseAuth.getInstance()

        register()
    }
    private fun register()
    {
        registerButton.setOnClickListener {
            if(TextUtils.isEmpty(firstnameInput.text.toString())) {
                firstnameInput.setError("Please enter first name ")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(lastnameInput.text.toString())){
                lastnameInput.setError("Please enter last name")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(usernameInput.text.toString())) {
                usernameInput.setError("Please enter Email id ")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(passwordInput.text.toString())) {
                passwordInput.setError("Please enter password (min. 6 digits) ")
                return@setOnClickListener
            }
           /*else if(TextUtils.isEmpty(passcnfrmInput.text.toString())) {
                passwordInput.setError("Password mismatched")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(ageInput.text.toString())) {
                passwordInput.setError("Please enter the correct age ")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(numberInput.text.toString())) {
                passwordInput.setError("Please enter your 10 digit phone number ")
                return@setOnClickListener
            }*/
            auth.createUserWithEmailAndPassword(usernameInput.text.toString(),passwordInput.text.toString())

                .addOnCompleteListener{    //we will add a listener and inside it we will check if login suceesful
                    if (it.isSuccessful){
                        val currentUser= auth.currentUser

                        //we are saving first name and last name in the real time database
                        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))
                        currentUSerDb?.child("firstname")?.setValue(firstnameInput.text.toString())
                        currentUSerDb?.child("lastname")?.setValue(lastnameInput.text.toString())

                        Toast.makeText(this, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()
                    }

                    //if not suceesful then toast msg
                    else {
                        Toast.makeText(this, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}