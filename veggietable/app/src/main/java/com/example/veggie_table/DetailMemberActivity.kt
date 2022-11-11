package com.example.veggie_table

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.veggie_table.databinding.ActivityDetailMemberBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.awaitResponse
import java.net.SocketTimeoutException

class DetailMemberActivity : AppCompatActivity() {

    private lateinit var call: Call<responseInfo>
    lateinit var binding: ActivityDetailMemberBinding
    lateinit var resItem: MutableList<myRow>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // intent: member code
        val memberCode = intent.getStringExtra("code")
        //Log.d("mobileApp", "$memberCode")

        CoroutineScope(Dispatchers.Main).launch {
            callData(memberCode!!)
        }
    }

    //데이터를 불러오는 함수
    private suspend fun callData(memberCode: String){
        // 데이터를 가져온다.
        call = MyApplication.networkServiceAssemblyData.getList(
            "907a382b30f5412c84614096181ce479","xml", 1, 20, "", "", "", memberCode
        )

        //서버로부터 전달받은 내용 처리
        try{
            val response = call.awaitResponse()
            if(!response.isSuccessful){
                Log.d("mobileApp", "데이터 연결 실패!")
            }

            if(response.body() == null){
                Log.d("mobileApp", "검색결과가 없습니다.")
            } else{
                if(response.isSuccessful){
                    resItem = response.body()!!.row
                    Log.d("mobileApp", "$resItem")
                    binding.detailName.text=resItem[0].HG_NM
                    binding.detailPoly.text=resItem[0].POLY_NM
                    binding.detailOrig.text=resItem[0].ORIG_NM
                    binding.detailTitle.text=resItem[0].MEM_TITLE
                    binding.detailCmits.text=resItem[0].CMITS
                    binding.detailTel.text=resItem[0].TEL_NO
                    binding.detailEmail.text=resItem[0].E_MAIL
                }

            }

        } catch (e: SocketTimeoutException){
            Log.d("mobileApp", "Exception: ${e.toString()}")
        }
    }
}