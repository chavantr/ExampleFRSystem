package com.mywings.foodrecommended

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.mywings.foodrecommended.process.OnUpdateProfileListener
import com.mywings.foodrecommended.process.ProgressDialogUtil
import com.mywings.foodrecommended.process.UpdateProfileAsync
import com.mywings.foodrecommended.process.UserInfoHolder
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity(), OnUpdateProfileListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    private lateinit var array: Array<String>

    private lateinit var arrayE: Array<String>

    private lateinit var arrayES: Array<String>

    private val user = UserInfoHolder.getInstance().user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        txtName.setText(user.name)
        txtUserName.setText(user.username)
        txtPassword.setText(user.password)
        txtAge.setText(user.age)
        txtHistory.setText(user.predesea)
        txtAllergy.setText(user.allergy)
        txtMedicines.setText(user.medicine)
        txtHeight.setText(user.height)
        txtWeight.setText(user.weight)
        txtEconomic.setText(user.income)

        array = resources.getStringArray(R.array.states)

        arrayE = resources.getStringArray(R.array.exercise)

        arrayES = resources.getStringArray(R.array.season)


        when (user.gender) {
            "Select" -> {
                spnGender.setSelection(0)
            }
            "Male" -> {
                spnGender.setSelection(1)
            }
            "Female" -> {
                spnGender.setSelection(2)
            }
        }

        var ii = 0
        for (i in 0..array.size) {
            if (array[i].equals(user.state, true)) {
                ii = i
                break
            }
        }

        spnState.setSelection(ii)

        var ij = 0

        for (i in 0..arrayE.size) {
            if (arrayE[i].equals(user.exercise, true)) {
                ij = i
                break
            }
        }

        spnExercise.setSelection(ij)


        var ijj = 0

        for (i in 0..arrayES.size) {
            if (arrayES[i].equals(user.season, true)) {
                ijj = i
                break
            }
        }
        spnSeason.setSelection(ijj)
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
        param.put("Id", user.id)
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
