package com.mywings.foodrecommended

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.mywings.foodrecommended.process.OnUpdateProfileListener
import com.mywings.foodrecommended.process.UpdateProfileAsync
import kotlinx.android.synthetic.main.activity_profile.*

import org.json.JSONObject

class ProfileActivity : AppCompatActivity(), OnUpdateProfileListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btnCancel.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            if (validate()) {
                var item = Snackbar.make(btnRegister, "Profile updated.", Snackbar.LENGTH_INDEFINITE)
                item.setAction("Ok") {
                    finish()
                }
                item.show()

            } else {
                var item = Snackbar.make(btnRegister, "All fields required.", Snackbar.LENGTH_LONG)
                item.show()
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

    private fun initUpdateProfile() {
        var request = JSONObject()
        val updateProfileAsync = UpdateProfileAsync()
        updateProfileAsync.setOnUpdateProfileListener(this, request)
    }

    override fun onUpdateProfileSuccess(result: String) {

    }
}
