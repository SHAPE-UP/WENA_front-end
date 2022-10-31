package com.example.veggie_table

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.veggie_table.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "로그인" 버튼 클릭
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.inputEmail.text
            val inputPassword = binding.inputPassword.text

            // 해당 이메일과 비밀번호가 일치하는 회원 찾기
        }

        // 회원가입 이동
        binding.gotoLinkRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}