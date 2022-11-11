package com.example.veggie_table

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.veggie_table.databinding.ActivityContactSelectBinding

class ContactSelectActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContactSelCall.setOnClickListener {
            val intent = Intent(this, ContactCallListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
        }

        binding.btnContactSelMail.setOnClickListener {
            val intent = Intent(this, ContactMailListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
        }

    }
}