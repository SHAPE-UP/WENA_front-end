package com.example.veggie_table

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.veggie_table.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "회원가입 완료" 버튼 클릭
        binding.registerBtn.setOnClickListener {
            val newNikname = binding.registerName.text
            val newEmail = binding.registerEmail.text
            val newPassword = binding.registerPassword.text
            val newResidence = binding.registerResidence.text

            // 회원 정보 넘기기
        }
    }
}