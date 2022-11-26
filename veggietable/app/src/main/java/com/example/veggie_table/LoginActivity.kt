package com.example.veggie_table

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.veggie_table.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "로그인" 버튼 클릭
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.inputEmail.text.toString() // 아이디 입력창
            val inputPassword = binding.inputPassword.text.toString() // 비밀번호 입력창

            // 해당 이메일과 비밀번호가 일치하는 회원 찾기
            val call: Call<LoginRes> = MyApplication.networkServiceUsers.login(
                LoginReq(inputEmail, inputPassword)
            )

            call.enqueue(object : Callback<LoginRes> {
                override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                    if(response.isSuccessful){
                        if(response.body()?.loginSuccess == "true"){ // 로그인 성공

                            // SharedPreference 저장
                            Log.d("mobileApp", "inputEmail: $inputEmail")
                            SharedPreference.setUserEmail(baseContext, inputEmail) // 이메일 저장
                            SharedPreference.setUserId(baseContext, response.body()!!.user._id) // 유저 id 저장
                            SharedPreference.setUserResidence(baseContext, response.body()!!.user.residence) // 거주지 저장
                            SharedPreference.setUserName(baseContext, response.body()!!.user.id) // 유저 닉네임 저장

                            // 메인 페이지로 이동
                            Toast.makeText(baseContext, "로그인을 성공했습니다!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(intent)

                        } else { // 로그인 실패 or null
                            Toast.makeText(baseContext, "이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                    Log.d("mobileApp", "onFailure $t")
                    Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 회원가입 이동
        binding.gotoLinkRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}