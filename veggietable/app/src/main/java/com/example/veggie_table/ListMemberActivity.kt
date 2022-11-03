package com.example.veggie_table

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
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
    lateinit var resItem : MutableList<myRow>

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

        // 스피너 : 정당 필터링
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

                        // display list
                        displayList(resItem)
                    }

                }

            } catch (e: SocketTimeoutException){
                Log.d("mobileApp", "Exception: ${e.toString()}")
            }
    }
}