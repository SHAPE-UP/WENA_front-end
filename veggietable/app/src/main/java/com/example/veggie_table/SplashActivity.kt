package com.example.veggie_table

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.veggie_table.databinding.ActivitySplashBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 스플래시 동작
        val backGroundExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        val mainExecutor: Executor = ContextCompat.getMainExecutor(this)
        backGroundExecutor.schedule({
            mainExecutor.execute{
                // 로그인 여부 확인
                if(SharedPreference.getUserEmail(this)?.length != 0){
                    // 메인페이지 이동
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } else{
                    // 시작 페이지 이동
                    val intent = Intent(baseContext, StartActivity::class.java)
                    startActivity(intent)
                }
            }
        }, 2, TimeUnit.SECONDS)  // 2초 후에 실행
    }
}