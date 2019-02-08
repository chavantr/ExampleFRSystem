package com.mywings.foodrecommended

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.mywings.foodrecommended.process.OnRegistrationListener
import com.mywings.foodrecommended.process.ProgressDialogUtil
import com.mywings.foodrecommended.process.RegistrationAsync
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity(), OnRegistrationListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        progressDialogUtil = ProgressDialogUtil(this)
        btnCancel.setOnClickListener {
            finish()
        }
        btnRegister.setOnClickListener {
            if (validate()) {
                initRegistration()
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

    private fun initRegistration() {

        progressDialogUtil.show()

        val registrationAsync = RegistrationAsync();

        var jrequest = JSONObject()

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
        jrequest.put("user", param)
        registrationAsync.setOnRegistrationListener(this, jrequest)
    }

    override fun onRegistrationSuccess(result: String) {
        progressDialogUtil.hide()
        if (result.isNotEmpty()) {
            val snackbar = Snackbar.make(btnRegister, "Registration completed.", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
                finish()
            }
            snackbar.show()
        } else {
            val snackbar = Snackbar.make(btnRegister, "An error has occurred.try later", Snackbar.LENGTH_LONG)
            snackbar.setAction("Ok") {

            }
            snackbar.show()
        }
    }
}
