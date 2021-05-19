package com.android.calculatecd

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private var context = this@MainActivity
    private lateinit var rg: RadioGroup
    private lateinit var edtHeight: EditText
    private lateinit var edtWeight: EditText
    private lateinit var edtAge: EditText
    private lateinit var button: Button
    private lateinit var spinner: Spinner
    private lateinit var txtMW: TextView
    private lateinit var txtSeek: TextView
    private lateinit var seekBarSmooth: SeekBar
    private lateinit var spn: String
    private lateinit var bwp: String
    private var age: Float = 0.0f
    lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()

        var level = arrayOf(
            "Sedentário (Nenhuma atividade física)",
            "Atividade física moderada (Exercício 3-4  vezes/semana)",
            "Atividade física intensa (Treino intenso 4+ vezes/semana)"
        )

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, level)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                validate()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spn = level[position]
            }
        }

        seekBarSmooth.run {
            spinner.adapter = arrayAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    validate()
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spn = level[position]
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
                    txtSeek.text = ("Objetivo de peso: ${p0?.progress} kg")
                }
            })
        }

        button.setOnClickListener {
            var id: Int = rg.checkedRadioButtonId
            if (id != -1) {
                if (validate()) {
                    val radio: RadioButton = findViewById(id)
                    var height: Float = edtHeight.text.toString().toFloat()
                    var weight: Float = edtWeight.text.toString().toFloat()
                    age = edtAge.text.toString().toFloat()

                    var level = when (spn) {
                        "Sedentário (Nenhuma atividade física)" -> 1.2
                        "Atividade física moderada (Exercício 3-4  vezes/semana)" -> 1.55
                        else -> 1.9
                    }

                    var planner: Int = when (bwp) {
                        "-2" -> 2000
                        "-1" -> 1000
                        "0" -> 0
                        "1" -> -500
                        else -> -1000
                    }
                    mViewModel.calculation(weight, height, age, planner, radio.text, level)
                }
            } else validate()
        }
    }


    private fun validate(): Boolean {
        if (edtAge.text.isEmpty() or edtHeight.text.isEmpty() or edtWeight.text.isEmpty()) {
            showToast("Preencher todos campos!")
            return false
        }
        return true
    }

    private fun initComponents() {
        mViewModel = ViewModelProvider(context).get(MainViewModel::class.java)
        rg = findViewById(R.id.rg)
        edtHeight = findViewById(R.id.edtheight)
        edtWeight = findViewById(R.id.edtweight)
        edtAge = findViewById(R.id.edtAge)
        button = findViewById(R.id.btnResult)
        spinner = findViewById(R.id.spinner)
        txtMW = findViewById(R.id.txtcalories)
        txtSeek = findViewById(R.id.txtSeek)
        seekBarSmooth = findViewById(R.id.seekBarSmooth)
        mViewModel.mCalculation.observe(context, Observer { value ->
            txtMW.text = value
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

