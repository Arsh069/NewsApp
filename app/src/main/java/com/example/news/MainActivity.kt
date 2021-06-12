package com.example.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.news.Fragment.AboutFragment
import com.example.news.Fragment.LicenseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth //we have to call the firebase api

    var databaseReference :  DatabaseReference? = null /*now we creating 2 variables for real time data base refrence and
                                                                database refrence*/
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile") //thats table name
        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_about -> {
                val a = supportFragmentManager.beginTransaction()
                supportFragmentManager.popBackStack()
                val frag = AboutFragment()
                a.replace(R.id.container_view, frag).addToBackStack("backStack")
                a.commit()
//                Toast.makeText(this, "about selected", Toast.LENGTH_SHORT).show()
                return true


            }
            R.id.id_License -> {
                // Toast.makeText(this, "license selected", Toast.LENGTH_SHORT).show()
                val a = supportFragmentManager.beginTransaction()
                supportFragmentManager.popBackStack()
                val frag = LicenseFragment()
                a.replace(R.id.container_view, frag).addToBackStack("backStack")
                a.commit()
                return true

            }

            R.id.id_Logout -> {
                auth.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                return true
            }

            else ->
                return super.onOptionsItemSelected(item)
        }


    }

}