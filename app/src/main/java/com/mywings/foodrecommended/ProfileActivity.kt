package com.mywings.foodrecommended

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.mywings.foodrecommended.process.OnUpdateProfileListener
import com.mywings.foodrecommended.process.ProgressDialogUtil
import com.mywings.foodrecommended.process.UpdateProfileAsync
import kotlinx.android.synthetic.main.activity_profile.*

import org.json.JSONObject

class ProfileActivity : AppCompatActivity(), OnUpdateProfileListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        progressDialogUtil = ProgressDialogUtil(this)

        btnCancel.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            if (validate()) {
                initUpdateProfile()

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

        progressDialogUtil.show()

        var request = JSONObject()


        var param = JSONObject()

        param.put("Name", txtName.text)
        param.put("Username", txtUserName.text)
        param.put("Password", txtPassword.text)
        param.put("Age", txtAge.text)
        param.put("Allergy", txtAllergy.text)
        param.put("Medicine", txtMedicines.text)
        param.put("Gender", spnGender.selectedItem.toString())
        param.put("State", spnState.selectedItem.toString())
        param.put("Season", spnSeason.selectedItem.toString())
        param.put("Height", txtHeight.text)
        param.put("Weight", txtWeight.text)
        param.put("Income", txtEconomic.text)
        param.put("Exercise", spnExercise.selectedItem.toString())
        request.put("user", param)

        val updateProfileAsync = UpdateProfileAsync()
        updateProfileAsync.setOnUpdateProfileListener(this, request)
    }

    override fun onUpdateProfileSuccess(result: String) {

        progressDialogUtil.hide()

        if (result.isNotEmpty()) {
            var item = Snackbar.make(btnRegister, "Profile updated.", Snackbar.LENGTH_INDEFINITE)
            item.setAction("Ok") {
                finish()
            }
            item.show()
        } else {
            var item = Snackbar.make(btnRegister, "An error has occurred, try later", Snackbar.LENGTH_LONG)
            item.setAction("Ok") {

            }
            item.show()
        }

    }
}
