package com.mywings.foodrecommended

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.mywings.foodrecommended.process.LoginAsync
import com.mywings.foodrecommended.process.OnLoginListener
import kotlinx.android.synthetic.main.activity_main.*

import org.json.JSONObject

class MainActivity : AppCompatActivity(), OnLoginListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSignUp.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }


        btnLogin.setOnClickListener {

            if (txtUserName.text!!.isEmpty() && txtPassword.text!!.isEmpty()) {
                Snackbar.make(btnSignUp, "All fields required.", Snackbar.LENGTH_LONG).show()
            } else {

                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                startActivity(intent)

            }

        }
    }

    private fun initLogin() {

        var request = JSONObject()

        val loginAsync = LoginAsync()

        loginAsync.setOnLoginListener(this, request)
    }


    override fun onLoginSuccess(result: JSONObject) {

    }
}
