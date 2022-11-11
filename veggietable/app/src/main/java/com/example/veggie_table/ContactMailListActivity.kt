package com.example.veggie_table

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.veggie_table.databinding.ActivityContactMailListBinding


class ContactMailListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityContactMailListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mailitem1.setOnClickListener {
            val sendto = binding.mailaddress1.text.toString()
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf(sendto)
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "[wena] 문의")
            email.putExtra(Intent.EXTRA_TEXT, "내용")
            startActivity(email)
        }
    }
}