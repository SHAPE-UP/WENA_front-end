package com.example.veggie_table


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.net.SocketTimeoutException

class ContactCallListActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    lateinit var rv_phone_book: RecyclerView
    var phoneBookListAdapter: PhoneBookListAdapter? = null
    lateinit var persons: ArrayList<Person>
    var tempPersons = ArrayList<Person>()

    private var resItem = mutableListOf<myRow>()
    private lateinit var call: Call<responseInfo>

    lateinit var search_view_phone_book: SearchView

    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_call_list)

        // 국회의원 전화번호, 성명 저장
        CoroutineScope(Dispatchers.Main).launch {
            tempPersons()
            setAdapter()
        }

        rv_phone_book = findViewById(R.id.rv_phone_book)
        search_view_phone_book = findViewById(R.id.search_view_phone_book)
        search_view_phone_book.setOnQueryTextListener(searchViewTextListener)


    }

    override fun onResume() {
        super.onResume()
        if (phoneBookListAdapter == null) {
//            phoneBookListAdapter.initFilteredPersons()
//            Persons.getPersons().sort()
            setAdapter()
        } else {
            phoneBookListAdapter!!.initFilteredPersons()
//          Persons.getPersons().sort()
            phoneBookListAdapter!!.notifyDataSetChanged()
            phoneBookListAdapter!!.filter.filter(search_view_phone_book.query)
        }
    }

    //SearchView 텍스트 입력시 이벤트
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                phoneBookListAdapter?.filter?.filter(s)
                Log.d(TAG, "SearchVies Text is changed : $s")
                return false
            }
        }

    fun setAdapter(){
        //리사이클러뷰에 리사이클러뷰 어댑터 부착
        rv_phone_book.layoutManager = LinearLayoutManager(this)
        phoneBookListAdapter = PhoneBookListAdapter(tempPersons)
        rv_phone_book.adapter = phoneBookListAdapter

        //스와이프 이벤트 부착
        itemTouchHelper = ItemTouchHelper(PhoneBookListItemHelper(this))
        itemTouchHelper.attachToRecyclerView(rv_phone_book)
    }

    private suspend fun tempPersons() {

        // 의원 데이터 불러오기
        // 해당 이메일과 비밀번호가 일치하는 회원 찾기
        call = MyApplication.networkServiceAssemblyData.getList(
            "907a382b30f5412c84614096181ce479","xml", 1, 20, "", "", "", ""
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

                    resItem = response.body()!!.row

                    // 데이터 추가
                    for(i in 0 until resItem.size){
                        tempPersons.add(Person(i+1, resItem[i].HG_NM!!, resItem[i].TEL_NO!!))
                    }
                }

            }

        } catch (e: SocketTimeoutException){
            Log.d("mobileApp", "Exception: ${e.toString()}")
        }

//        tempPersons.add(Person(1, "kim", "01011111111"))
//        tempPersons.add(Person(2, "lee", "01022222222"))
//        tempPersons.add(Person(3, "park", "01033333333"))
//        tempPersons.add(Person(4, "son", "01044444444"))
//        tempPersons.add(Person(5, "hwang", "01055555555"))
//        tempPersons.add(Person(6, "jo", "01066666666"))
//        tempPersons.add(Person(7, "gwak", "01077777777"))
//        tempPersons.add(Person(8, "sim", "01088888888"))
//        tempPersons.add(Person(9, "choi", "01099999999"))
//        tempPersons.add(Person(10, "choi", "01099999999"))

    }
}