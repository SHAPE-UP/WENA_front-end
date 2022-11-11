package com.example.veggie_table

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.veggie_table.databinding.ActivityMainBinding
import com.example.veggie_table.databinding.ActivityStatisticsBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_statistics.*
import retrofit2.Call
import java.util.*

class StatisticsActivity : AppCompatActivity() {
    private lateinit var call: Call<responseInfo>
    lateinit var binding : ActivityStatisticsBinding

    var a : Float =38f
    var b : Float =14f
    var c : Float =14f
    var d : Float =34f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)

        binding.radiobuttonParty.setOnClickListener{
            //여기에 a= b= c= d=  이런 식으로 쓰면 될듯

            setUpSelectionPieChart()
        }
        binding.radiobuttonSex.setOnClickListener{
            //여기에 a= b= c= d=  이런 식으로 쓰면 될듯
            setUpSelectionPieChart()

        }
        binding.radiobuttonCmit.setOnClickListener{
            //여기에 a= b= c= d=  이런 식으로 쓰면 될듯
            setUpSelectionPieChart()

        }

        setUpSelectionPieChart()
    }

    private fun setUpSelectionPieChart() {

        //Create a dataset
        val dataArray = ArrayList<PieEntry>()
        dataArray.add(PieEntry(a))
        dataArray.add(PieEntry(b))
        dataArray.add(PieEntry(c))
        dataArray.add(PieEntry(d))
        val dataSet = PieDataSet(dataArray, "")
        dataSet.valueTextSize=20f
        dataSet.valueTextColor= Color.WHITE

        //Color set for the chart
        val colorSet = java.util.ArrayList<Int>()
        colorSet.add(Color.rgb(255,107,107))  //red
        colorSet.add(Color.rgb(173,232,244))  // blue
        colorSet.add(Color.rgb(216,243,220))  // green
        colorSet.add(Color.rgb(255,230,109))  // Yellow
        dataSet.setColors(colorSet)

        val data = PieData(dataSet)

        //chart description
        pieChart.description.text = "Pie chart"
        pieChart.description.textSize = 20f

        //Chart data and other styling
        pieChart.data = data
        pieChart.centerTextRadiusPercent = 0f
        pieChart.isDrawHoleEnabled = true
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = true
    }
}