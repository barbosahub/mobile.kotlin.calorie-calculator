package com.android.calculatecd

import android.os.Bundle
import android.view.View
import android.widget.*
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
        var spn: String = ""
        //endregion

        //region Spinner
        var activity_level = arrayOf(
            "No physical activity",
            "Moderate physical activity",
            "Intense physical activity" )

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

        //region Button + RadioGroup
        // Get radio group selected item using on checked change listener
        button.setOnClickListener {
            // Get the checked radio button id from radio group
            var id: Int = rg.checkedRadioButtonId
            if (id != -1) { // If any radio button checked from radio group
                // Get the instance of radio button using id
                //region variable
                val radio: RadioButton = findViewById(id)
                var height: Float = edtHeight.text.toString().toFloat()
                var weight: Float = edtWeight.text.toString().toFloat()
                var age: Float = edtAge.text.toString().toFloat()
                var level: Double
                //endregion

                when (spn) {
                    "No physical activity" -> level = 1.2
                    "Moderate physical activity" -> level = 1.55
                    else -> level = 1.9
                }

                if (radio.text == "Male"){
                    val a = (13.75 * weight) + (5 * height) - (6.76 * age) + 66.5
                    val calc = (a * level) - 1000

                    txtMW.text = "%.2f".format(calc)
                }else{
                    val a = (9.56 * weight) + (1.85 * height) - (4.68 * age) + 665
                    val calc = (a * level) - 1000

                    txtMW.text = "%.2f".format(calc)
                }
            } else {
                // If no radio button checked in this radio group
                Toast.makeText(
                    applicationContext, "On button click : nothing selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        //endregion
    }


}

