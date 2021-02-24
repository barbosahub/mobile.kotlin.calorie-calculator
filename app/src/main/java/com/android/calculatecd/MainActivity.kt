package com.android.calculatecd

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region Components
        val rg: RadioGroup = findViewById(R.id.rg)
        val edtHeight: EditText = findViewById(R.id.edtheight)
        val edtWeight: EditText = findViewById(R.id.edtweight)
        val edtAge: EditText = findViewById(R.id.edtAge)
        val button: Button = findViewById(R.id.btnResult)
        val spinner: Spinner = findViewById(R.id.spinner)
        val txtMW: TextView = findViewById(R.id.txtcalories)
        val txtSeek: TextView = findViewById(R.id.txtSeek)
        val seekBarSmooth: SeekBar = findViewById(R.id.seekBarSmooth)
        var spn: String = ""
        var bwp: String = ""
        //endregion

        //region Spinner
        var activity_level = arrayOf(
            "No physical activity (little or no exercise)",
            "Moderate physical activity (exercise 3-4 times/week)",
            "Intense physical activity (very intense exercise daily, or physical job)" )

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,activity_level)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                spn = activity_level[position]
            }
        }
        //endregion

        //region SeekBar
        seekBarSmooth.run {
            spinner.adapter = arrayAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                spn = activity_level[position]
            }
        }

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                min = -2
                max = 2
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                bwp = p0?.progress.toString()
                txtSeek.text = ("Body Weight Planner: ${p0?.progress} kg")
            }
        })
        }
        //endregion

        //region Button + RadioGroup
        // Get radio group selected item using on checked change listener
        button.setOnClickListener {
            // Get the checked radio button id from radio group
            var id: Int = rg.checkedRadioButtonId
            if (id != -1) { // If any radio button checked from radio group

                if (edtAge.text.isEmpty() or edtHeight.text.isEmpty() or edtWeight.text.isEmpty()){
                    Toast.makeText(
                            applicationContext, "IsEmpty",
                            Toast.LENGTH_SHORT
                    ).show()
                }else{
                    // Get the instance of radio button using id
                    //region variable
                    val radio: RadioButton = findViewById(id)
                    var height: Float = edtHeight.text.toString().toFloat()
                    var weight: Float = edtWeight.text.toString().toFloat()
                    var age: Float = edtAge.text.toString().toFloat()
                    var level: Double
                    var planner: Int
                    //endregion

                    when (spn) {
                        "No physical activity (little or no exercise)" -> level = 1.2
                        "Moderate physical activity (exercise 3-4 times/week)" -> level = 1.55
                        else -> level = 1.9
                    }

                    when (bwp) {
                        "-2" -> planner = 2000
                        "-1" -> planner = 1000
                        "0" -> planner = 0
                        "1" -> planner = -500
                        else -> planner = -1000
                    }


                    if (radio.text == "Male"){
                        val a = (13.75 * weight) + (5 * height) - (6.76 * age) + 66.5
                        val calc = (a * level) - planner

                        txtMW.text = "%.2f".format(calc)

                    }else{
                        val a = (9.56 * weight) + (1.85 * height) - (4.68 * age) + 665
                        val calc = (a * level) - planner

                        txtMW.text = "%.2f".format(calc)
                    }
                }
            } else {
                // If no radio button checked in this radio group
                Toast.makeText(
                    applicationContext, "Gender : nothing selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        //endregion
    }


}

