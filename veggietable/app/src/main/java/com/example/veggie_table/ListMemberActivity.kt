package com.example.veggie_table

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.veggie_table.databinding.ActivityListMemberBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListMemberActivity : AppCompatActivity() {

    private lateinit var call: Call<responseInfo>
    private lateinit var binding: ActivityListMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트 받기
        val search = intent.getStringExtra("search")
        val type = intent.getStringExtra("type")

        // 검색의 경우
        if(type?.length != 0){
            when (type) {
                "member" -> { // 의원명
                    callData(search!!, "", "")
                }
                "party" -> { // 정당
                    callData("", search!!, "")
                }
                "election" -> { // 지역구
                    callData("", "", search!!)
                }
            }
        } else { // 검색이 아닌 경우 (전체 디스플레이)
            callData("", "", "")
        }
    }

    //데이터를 불러오는 함수
    private fun callData(name: String, party: String, election: String){
        // 데이터를 가져온다.
        call = MyApplication.networkServiceAssemblyData.getList(
            "907a382b30f5412c84614096181ce479","xml", 1, 20, name, party, election
        )

        //서버로부터 전달받은 내용 처리
        call?.enqueue(object: Callback<responseInfo> {
            override fun onResponse(call: Call<responseInfo>, response: Response<responseInfo>) {
                Log.d("mobileApp", "야호!")
                if(response.isSuccessful){
                    val locateItem = response.body()!!.row
                    Log.d("mobileApp","$locateItem")

                }
            }

            override fun onFailure(call: Call<responseInfo>, t: Throwable) {
                Log.d("appTest", "onFailure")
                Log.d("appTest", "$t")
            }

        })
    }
}