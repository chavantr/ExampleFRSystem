package com.mywings.foodrecommended

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mywings.foodrecommended.process.UserInfoHolder
import kotlinx.android.synthetic.main.activity_food_detail.*

class FoodDetailActivity : AppCompatActivity() {

    private val food = UserInfoHolder.getInstance().food

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        lblName.text = "Food Name : ${food.name}"
        lblPrice.text = "Price : ${food.agegroupfrom} - ${food.pricerangeto}"
        lblAgeGroup.text = "Age group : ${food.agegroupfrom} - ${food.agegroupto}"
        lblSeason.text = "Season : ${food.season}"
        lblState.text = "State : ${food.state}"

    }
}
