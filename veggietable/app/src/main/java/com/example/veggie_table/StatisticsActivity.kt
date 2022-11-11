package com.example.veggie_table

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.veggie_table.databinding.ActivityMainBinding
import com.example.veggie_table.databinding.ActivityStatisticsBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_statistics.*
import kotlinx.android.synthetic.main.activity_statistics.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.awaitResponse
import java.net.SocketTimeoutException
import java.util.*

class StatisticsActivity : AppCompatActivity() {
    private lateinit var call: Call<responseInfo>
    lateinit var binding : ActivityStatisticsBinding

    // 정당별 필터링 array
    var partyDatas0 = mutableListOf<myRow>() // 더불어민주당
    var partyDatas1 = mutableListOf<myRow>() // 국민의힘
    var partyDatas2 = mutableListOf<myRow>() // 정의당
    var partyDatas3 = mutableListOf<myRow>() // 기본소득당
    var partyDatas4 = mutableListOf<myRow>() // 시대전환당
    var partyDatas5 = mutableListOf<myRow>() // 미래한국당
    var partyDatas6 = mutableListOf<myRow>() // 더불어시민당
    var partyDatas7 = mutableListOf<myRow>() // 국민의당
    var partyDatas8 = mutableListOf<myRow>() // 열린민주당
    var partyDatas9 = mutableListOf<myRow>() // 무소속
    var partyDataset = mutableListOf(partyDatas0, partyDatas1, partyDatas2, partyDatas3, partyDatas4, partyDatas5, partyDatas6, partyDatas7, partyDatas8, partyDatas9)

    // 성별 필터링 array
    val maleDatas =  mutableListOf<myRow>() // 남성
    val femaleDatas = mutableListOf<myRow>() // 여성
    val sexDataset = mutableListOf(maleDatas, femaleDatas)
    val sexLabel = arrayListOf("남성", "여성")

//    var a : Float = 38f
//    var b : Float = 14f
//    var c : Float = 14f
//    var d : Float = 34f

    // 전체 데이터
    private var totalItem = mutableListOf<myRow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("mobileApp", "실행!")

        CoroutineScope(Dispatchers.Main).launch {
            callData()

            binding.radiobuttonParty.setOnClickListener{
                //여기에 a= b= c= d=  이런 식으로 쓰면 될듯
                setUpSelectionPieChart(partyDataset)
            }
            binding.radiobuttonSex.setOnClickListener{
                //여기에 a= b= c= d=  이런 식으로 쓰면 될듯
                setUpSelectionPieChart(sexDataset)

            }
//            binding.radiobuttonCmit.setOnClickListener{
//                //여기에 a= b= c= d=  이런 식으로 쓰면 될듯
//                //setUpSelectionPieChart()
//
//            }

            // default : 정당
            setUpSelectionPieChart(partyDataset)
            binding.radiobuttonParty.isSelected
        }


    }

    // 정당별, 성별, 위원회별로 나누기
    //데이터를 불러오는 함수
    private suspend fun callData(){
        // 데이터를 가져온다.
        call = MyApplication.networkServiceAssemblyData.getList(
            "907a382b30f5412c84614096181ce479","xml", 1, 200, "", "", "", ""
        )

        //서버로부터 전달받은 내용 처리
        try{
            val response = call.awaitResponse()
            if(!response.isSuccessful){
                Log.d("mobileApp", "데이터 연결 실패!")
            }
            Log.d("mobileApp", "response: $response")
            if(response.body() == null){

            } else{
                if(response.isSuccessful){
                    totalItem = response.body()!!.row

                    // 정당 나누기
                    for(i in 0 until totalItem.size){
                        // 정당
                        when (totalItem[i].POLY_NM) {
                            "더불어민주당" -> partyDatas0.add(totalItem[i])
                            "국민의힘" -> partyDatas1.add(totalItem[i])
                            "정의당" -> partyDatas2.add(totalItem[i])
                            "기본소득당" -> partyDatas3.add(totalItem[i])
                            "시대전환당" -> partyDatas4.add(totalItem[i])
                            "미래한국당" -> partyDatas5.add(totalItem[i])
                            "더불어시민당" -> partyDatas6.add(totalItem[i])
                            "국민의당" -> partyDatas7.add(totalItem[i])
                            "열린민주당" -> partyDatas8.add(totalItem[i])
                            "무소속" -> partyDatas9.add(totalItem[i])
                        }

                        // 성별
                        when(totalItem[i].SEX_GBN_NM){
                            "남" -> maleDatas.add(totalItem[i])
                            "여" -> maleDatas.add(totalItem[i])
                        }

                        // 위원회
                    }

                }

            }

        } catch (e: SocketTimeoutException){
            Log.d("mobileApp", "Exception: ${e.toString()}")
        }
    }

   private fun setUpSelectionPieChart(item: MutableList<MutableList<myRow>>) {

        val pieChart = binding.pieChart

        //Create a dataset
       val dataArray = ArrayList<PieEntry>()
       val partyName = resources.getStringArray(R.array.parties)

       // 성별 데이터 삽입
       if (item.size == 2){
           for (i in 0 until item.size){
               // add
               dataArray.add(PieEntry(item[i].size.toFloat(), sexLabel[i]))
           }
       } else{ // 정당 데이터 삽입
           for (i in 0 until item.size){
               // add
                   if(item[i].size != 0) dataArray.add(PieEntry(item[i].size.toFloat(), partyName[i].toString()))
           }
       }



        val dataSet = PieDataSet(dataArray, "")
        dataSet.valueTextSize=20f
        dataSet.valueTextColor= Color.WHITE

       // make a color array 100 ~ 200
       val random = Random()

       //Color set for the chart
       val colorSet = java.util.ArrayList<Int>()
       for (i in 0 until item.size){
           val num1 = random.nextInt(100)+ 100
           val num2 = random.nextInt(100)+ 100
           val num3 = random.nextInt(100)+ 100
           colorSet.add(Color.rgb(num1,num2,num3))
       }
        dataSet.setColors(colorSet)

        val data = PieData(dataSet)

        //chart description
        pieChart.description.text = "출처: 국회 데이터"
        pieChart.description.textSize = 15f

        //Chart data and other styling
        pieChart.data = data
        pieChart.centerTextRadiusPercent = 0f
        pieChart.isDrawHoleEnabled = true
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = true
    }
}