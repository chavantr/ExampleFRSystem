package com.mywings.foodrecommended

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.mywings.foodrecommended.binder.FoodAdapter
import com.mywings.foodrecommended.models.Food
import com.mywings.foodrecommended.process.GetFoodRecommendeAsync
import com.mywings.foodrecommended.process.OnFoodListener
import com.mywings.foodrecommended.process.ProgressDialogUtil
import com.mywings.foodrecommended.process.UserInfoHolder
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.nav_header_dashboard.view.*
import org.json.JSONArray
import org.json.JSONObject

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnFoodListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil
    private lateinit var foodAdapter: FoodAdapter
    private val user = UserInfoHolder.getInstance().user
    private var foods = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        progressDialogUtil = ProgressDialogUtil(this)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        var view = nav_view.getHeaderView(0)
        view.lblName.text = user.name
        view.textView.text = user.username


        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        lstContent.layoutManager = LinearLayoutManager(this)
        initGetFood();
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@DashboardActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_profile -> {
                drawer_layout.closeDrawer(GravityCompat.START)
                val intent = Intent(this@DashboardActivity, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                false
            }
        }

    }

    private fun initGetFood() {
        progressDialogUtil.show()
        var request = JSONObject()
        var param = JSONObject()
        val user = UserInfoHolder.getInstance().user
        param.put("Price", user.income)
        param.put("Desease", user.predesea)
        param.put("Allergy", user.allergy)
        param.put("Medicines", user.medicine)
        param.put("Gender", user.gender)
        param.put("Age", user.age)
        param.put("Age", user.age)
        param.put("State", user.state)
        request.put("request", param)
        val getFoodRecommendeAsync = GetFoodRecommendeAsync()
        getFoodRecommendeAsync.setOnFoodListener(this, request)
    }

    override fun onFoodSuccess(result: JSONArray) {
        progressDialogUtil.hide()
        if (null != result && result.length() > 0) {
            var foods = ArrayList<Food>()
            for (i in 0..(result.length() - 1)) {
                val node = result.getJSONObject(i)
                var food = Food()
                food.id = node.getInt("Id")
                food.name = node.getString("Name")
                food.season = node.getString("Season")
                food.desease = node.getString("Desease")
                food.agegroupfrom = node.getString("AgeGroupFrom")
                food.agegroupto = node.getString("AgeGroupTo")
                food.medicine = node.getString("Medicine")
                food.allergy = node.getString("Allergy")
                food.pricerangefrom = node.getString("PriceRangeFrom")
                food.pricerangeto = node.getString("PriceRangeTo")
                food.gender = node.getString("Gender")
                food.state = node.getString("State")
                food.country = node.getString("Country")
                if (selectNon(food)) {
                    foods.add(food)
                } else if (selectUserSpecific(food)) {
                    foods.add(food)
                } else if (selectUserSpecificWith(food)) {
                    var flag = false;
                    if (checkUserDesease(food)) {
                        flag = true;
                    }
                    if (checkUserAllergy(food)) {
                        flag = true;
                    }
                    if (checkUserMedicine(food)) {
                        flag = true;
                    }
                    if (flag) {
                        foods.add(food)
                    }
                } else {
                    if (food.pricerangefrom.isNullOrEmpty() && food.pricerangeto.isNullOrEmpty()) {
                        if (checkUserIncome(food)) {
                            foods.add(food)
                        }
                    }
                }
            }

            foodAdapter = FoodAdapter(foods)
            lstContent.adapter = foodAdapter
        }
    }

    private fun checkUserIncome(food: Food) =
        food.pricerangefrom.toDouble() >= user.income.toDouble() && food.pricerangeto.toDouble() <= user.income.toDouble()

    private fun checkUserMedicine(food: Food) =
        !food.medicine.isNullOrEmpty() && food.medicine.contains(food.medicine, true)

    private fun checkUserAllergy(food: Food) =
        !food.allergy.isNullOrEmpty() && food.allergy.contains(user.allergy, true)

    private fun checkUserDesease(food: Food) =
        !food.desease.isNullOrEmpty() && food.desease.contains(user.predesea, true)

    private fun selectUserSpecific(food: Food): Boolean {
        return food.season.equals(user.season, true) && food.gender.equals(
            user.gender,
            true
        ) && food.desease.isNullOrEmpty() && food.medicine.isNullOrEmpty() && food.allergy.isNullOrEmpty()
    }

    private fun selectUserSpecificWith(food: Food): Boolean {
        return food.season.equals(user.season, true) && food.gender.equals(
            user.gender,
            true
        ) && (!food.desease.isNullOrEmpty() || !food.medicine.isNullOrEmpty() || !food.allergy.isNullOrEmpty())
    }

    private fun selectNon(food: Food) =
        food.season.isNullOrEmpty() && food.desease.isNullOrEmpty() && food.medicine.isNullOrEmpty() && food.allergy.isNullOrEmpty() && food.gender.isNullOrEmpty() && food.state.isNullOrEmpty()
}
