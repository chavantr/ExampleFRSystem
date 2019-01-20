package com.mywings.foodrecommended

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        btnCancel.setOnClickListener {
            finish()
        }
        btnRegister.setOnClickListener {
            if (validate()) {
                var item = Snackbar.make(btnRegister, "Registration completed.", Snackbar.LENGTH_INDEFINITE)
                item.setAction("Ok") {
                    finish()
                }
                item.show()
            } else {
                Snackbar.make(btnRegister, "All fields required.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun validate(): Boolean {
        if (txtName.text!!.isEmpty() && txtAge.text!!.isEmpty()
            && txtUserName.text!!.isEmpty()
            && txtPassword.text!!.isEmpty()
        ) {
            return false
        }
        return true
    }
}
