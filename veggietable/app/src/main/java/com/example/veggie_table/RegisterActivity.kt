package com.example.veggie_table

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.veggie_table.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "회원가입 완료" 버튼 클릭
        binding.registerBtn.setOnClickListener {
            val newNikname = binding.registerName.text.toString()
            val newEmail = binding.registerEmail.text.toString()
            val newPassword = binding.registerPassword.text.toString()
            val newResidence = binding.registerResidence.text.toString()

            // 회원 정보 넘기기
            val call: Call<RegisterRes> = MyApplication.networkServiceUsers.register(
                RegisterReq(newNikname, newEmail, newPassword, newResidence)
            )

            call.enqueue(object : Callback<RegisterRes> {
                override fun onResponse(call: Call<RegisterRes>, response: Response<RegisterRes>) {
                    if(response.isSuccessful){

                        // 로그인 페이지로 이동
                        Toast.makeText(baseContext, "회원가입을 완료했습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(baseContext, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                    Log.d("mobileApp", "onFailure $t")
                    Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}