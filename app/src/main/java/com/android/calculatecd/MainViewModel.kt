package com.android.calculatecd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var calculation: Double = 0.0
    private var result: Double = 0.0
    var mCalculation = MutableLiveData<String>().apply {
        value =
            calculation.toString()
    }

    private fun setmGender() {
        mCalculation.value = calculation.toString()
    }

    private fun validaCalculation(
        weight: Float,
        height: Float,
        age: Float,
        planner: Int,
        gender: CharSequence,
        level: Double
    ) {

        result =
            if (gender == "Masculino") (13.75 * weight) + (5 * height) - (6.76 * age) + 66.5
            else (9.56 * weight) + (1.85 * height) - (4.68 * age) + 665

        calculation = "%.2f".format(((result * level) - planner)).toDouble()

        setmGender()
    }

    fun calculation(
        weight: Float,
        height: Float,
        age: Float,
        planner: Int,
        gender: CharSequence,
        level: Double
    ) {
        validaCalculation(weight, height, age, planner, gender, level)
    }
}