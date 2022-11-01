package com.example.veggie_table

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.example.veggie_table.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle // 토글 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.app_name, R.string.app_name)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false) // title 제거
        toggle.syncState() // 토글 동기화

        // logout alert 이벤트
        val eventHandler = DialogInterface.OnClickListener { p0, p1 ->
            if(p1 == DialogInterface.BUTTON_POSITIVE){
                //  로그아웃 api
            }
        }

        // 드로어 레이아웃 이벤트
        binding.mainDrawerView.setNavigationItemSelectedListener {
            //Log.d("mobileApp", "Navigation selected...${it.title}")
            when(it!!.title){
                "로그아웃" ->{
                    AlertDialog.Builder(this).run {
                        setTitle("알림창 테스트")
                        setIcon(android.R.drawable.ic_dialog_info)
                        setMessage("로그아웃을 하시겠습니까?")
                        setPositiveButton("YES", eventHandler) // 이벤트 설정
                        setNegativeButton("NO", null)
                        show()
                    }

                }
            }

            true
        }



       // 검색창
        binding.searchBtn.setOnClickListener {
            val inputSearch = binding.mainSearch.text

            // list page에 검색어 전달
            val intent = Intent(this, ListMemberActivity::class.java)
            intent.putExtra("search", inputSearch)
            startActivity(intent)
        }

        // 이동
        binding.gotoMemberList.setOnClickListener {
            val intent = Intent(this, ListMemberActivity::class.java)
            startActivity(intent)
        }

        binding.gotoMemberStatistics.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        binding.gotoWebpageList.setOnClickListener {
            val intent = Intent(this, LinkListActivity::class.java)
            startActivity(intent)
        }

        binding.gotoContact.setOnClickListener {
            val intent = Intent(this, ContactSelectActivity::class.java)
            startActivity(intent)
        }

    }

    // 툴바 메뉴 버튼이 클릭 됐을 때 실행하는 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawerLayout = binding.drawer

        when(item!!.itemId){
            android.R.id.home->{
                // 햄버거 버튼 클릭시 네비게이션 드로어 열기
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}