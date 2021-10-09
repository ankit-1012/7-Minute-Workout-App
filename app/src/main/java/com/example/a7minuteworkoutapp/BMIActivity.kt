package com.example.a7minuteworkoutapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import kotlinx.android.synthetic.main.activity_exercise.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"
    var currentVisibleView:String  = METRIC_UNITS_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)
        setSupportActionBar(toolbar_bmi_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if(currentVisibleView == METRIC_UNITS_VIEW) {
                if (validateMetricUnits()) {
                    val heightValue: Float = etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()
                    val bmi = weightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }else{
                if(validateUSUnits()){
                    val usHeightFeet: Float= etUSUnitHeightFeet.text.toString().toFloat()
                    val usHeightInch: Float=  etUSUnitHeightInch.text.toString().toFloat()
                    val usWeightValue: Float = etUSUnitWeight.text.toString().toFloat()
                    val heightValue = usHeightInch.toFloat() + usHeightFeet.toFloat()*12
                    val bmi = usWeightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)

                }else{
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
        }
            makeVisibleMetricUnitView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }else
            {
                makeVisibleUSUnitView()
            }
        }
    }
    private fun makeVisibleUSUnitView(){
        currentVisibleView= US_UNITS_VIEW
        tilMetricUnitWeight.visibility= View.GONE
        tilMetricUnitHeight.visibility= View.GONE
        etUSUnitHeightFeet.text!!.clear()
        etUSUnitHeightInch.text!!.clear()
        etUSUnitWeight.text!!.clear()
        tilUSUnitWeight.visibility= View.VISIBLE
        llUSUnitsHeight.visibility= View.VISIBLE
        llDiplayBMIResult.visibility= View.GONE
    }
    private fun makeVisibleMetricUnitView(){
        currentVisibleView= METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility= View.VISIBLE
        tilMetricUnitHeight.visibility= View.VISIBLE
        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()
        tilUSUnitWeight.visibility= View.GONE
        llUSUnitsHeight.visibility= View.GONE
        llDiplayBMIResult.visibility= View.GONE
    }
    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        llDiplayBMIResult.visibility=View.VISIBLE

        val bmiValue= BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        tvBMIValue.text=bmiValue
        tvBMIType.text=bmiLabel
        tvBMIDescription.text=bmiDescription
    }
    private fun validateMetricUnits():Boolean{
        var isValid = true
        if(etMetricUnitWeight.text.toString().isEmpty())
            isValid=false
        else if (etMetricUnitHeight.text.toString().isEmpty())
            isValid=false

        return isValid
    }
    private fun validateUSUnits():Boolean{
        var isValid = true
        when {
            etUSUnitWeight.text.toString().isEmpty() -> isValid=false
            etUSUnitHeightFeet.text.toString().isEmpty() -> isValid=false
            etUSUnitHeightInch.text.toString().isEmpty() -> isValid=false
        }

        return isValid
    }
}