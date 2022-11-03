package com.example.veggie_table

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.veggie_table.databinding.ActivityListMemberBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.net.SocketTimeoutException


class ListMemberActivity : AppCompatActivity() {

    private lateinit var call: Call<responseInfo>
    private lateinit var binding: ActivityListMemberBinding
    private var resItem = mutableListOf<myRow>()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
        }

        // 스피너 : 정당 필터링
        val partySpinner = ArrayAdapter.createFromResource(this, R.array.parties, android.R.layout.simple_spinner_dropdown_item)
        binding.partySpinner.adapter = partySpinner


        val partyArray = resources.getStringArray(R.array.parties) // party 배열

        binding.partySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resItem.clear() // data clear

                when(partyArray[position]){
                    "더불어민주당" -> {
                        if(partyDatas0.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas0.size) resItem.add(partyDatas0[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "국민의힘" -> {
                        if(partyDatas1.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas1.size) resItem.add(partyDatas1[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "정의당" -> {
                        if(partyDatas2.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas2.size) resItem.add(partyDatas2[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "기본소득당" -> {
                        if(partyDatas3.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas3.size) resItem.add(partyDatas3[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "시대전환당" -> {
                        if(partyDatas4.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas4.size) resItem.add(partyDatas4[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "미래한국당" -> {
                        if(partyDatas5.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas5.size) resItem.add(partyDatas5[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "더불어시민당" -> {
                        if(partyDatas6.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas6.size) resItem.add(partyDatas6[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "국민의당" -> {
                        if(partyDatas7.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas7.size) resItem.add(partyDatas7[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "열린민주당" -> {
                        if(partyDatas8.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas8.size) resItem.add(partyDatas8[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                    "무소속" -> {
                        if(partyDatas9.size != 0 ) {
                            binding.noSearch.visibility = View.GONE
                            for (i in 0 until partyDatas9.size) resItem.add(partyDatas9[i])
                        }
                        else binding.noSearch.visibility = View.VISIBLE
                    }
                }


                // 리사이클러 뷰
                displayList(resItem)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
            // 스피너 : 검색 필터링
        val listSpinner = ArrayAdapter.createFromResource(this, R.array.catrgories, android.R.layout.simple_spinner_dropdown_item)
        binding.searchSpinner.adapter = listSpinner

        // search
        binding.menuSearch.isSubmitButtonEnabled = true
        binding.menuSearch.queryHint="검색창에 검색어를 입력하세요."
        binding.menuSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                val type = binding.searchSpinner.selectedItem
                Log.d("mobileApp", "$type")

                // list page에 검색어 전달
                val intent = Intent(baseContext, ListMemberActivity::class.java)
                intent.putExtra("search", query.toString())
                intent.putExtra("type", type.toString())
                startActivity(intent)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })

        // list
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("mobileApp", "callDataCrt")

            // 인텐트 받기
            val search = intent.getStringExtra("search")
            val type = intent.getStringExtra("type")
            //Log.d("mobileApp", "type: $type")
            Log.d("mobileApp", "search: $search")

            // 검색의 경우
            if(type !== null){
                when (type) {
                    "의원명" -> { // 의원명
                        callData(search!!, "", "")
                    }
                    "정당명" -> { // 정당
                        callData("", search!!, "")
                    }
                    "선거구" -> { // 선거구
                        callData("", "", search!!)
                    }
                }
            } else { // 검색이 아닌 경우 (전체 디스플레이)
                callData("", "", "")
            }
        }
    }

    private fun displayList(data: MutableList<myRow>){
        val searchAdapter =  SearchAdapter(this, data)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = searchAdapter // data array

        // 리사이클러뷰 이벤트 처리
        searchAdapter.setItemClickListener(object: SearchAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // 페이지 이동, 상세 정보 페이지에 data 전송
                val intent = Intent(baseContext, DetailMemberActivity::class.java)
                intent.putExtra("code", data[position].MONA_CD) // 의원 번호를 intent
                startActivity(intent)
            }
        })
    }

    //데이터를 불러오는 함수
    private suspend fun callData(name: String, party: String, election: String){
        // 데이터를 가져온다.
        call = MyApplication.networkServiceAssemblyData.getList(
            "907a382b30f5412c84614096181ce479","xml", 1, 20, name, party, election, ""
        )

        //서버로부터 전달받은 내용 처리
            try{
                val response = call.awaitResponse()
                if(!response.isSuccessful){
                    Log.d("mobileApp", "데이터 연결 실패!")
                }
                Log.d("mobileApp", "response: $response")
                if(response.body() == null){
                    binding.noSearch.visibility = View.VISIBLE
                    binding.listMemberLayout.visibility = View.GONE
                } else{
                    if(response.isSuccessful){
                        binding.noSearch.visibility = View.GONE
                        binding.listMemberLayout.visibility = View.VISIBLE

                        resItem = response.body()!!.row

                        // 정당 나누기
                        for(i in 0 until resItem.size){
                            when (resItem[i].POLY_NM) {
                                "더불어민주당" -> partyDatas0.add(resItem[i])
                                "국민의힘" -> partyDatas1.add(resItem[i])
                                "정의당" -> partyDatas2.add(resItem[i])
                                "기본소득당" -> partyDatas3.add(resItem[i])
                                "시대전환당" -> partyDatas4.add(resItem[i])
                                "미래한국당" -> partyDatas5.add(resItem[i])
                                "더불어시민당" -> partyDatas6.add(resItem[i])
                                "국민의당" -> partyDatas7.add(resItem[i])
                                "열린민주당" -> partyDatas8.add(resItem[i])
                                "무소속" -> partyDatas9.add(resItem[i])

                            }
                        }

                        // display list
                        displayList(resItem)
                    }

                }

            } catch (e: SocketTimeoutException){
                Log.d("mobileApp", "Exception: ${e.toString()}")
            }
    }
}