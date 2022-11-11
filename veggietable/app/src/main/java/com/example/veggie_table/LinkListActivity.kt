package com.example.veggie_table

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.veggie_table.databinding.ActivityLinkListBinding
import kotlinx.android.synthetic.main.activity_link_list.*


class LinkListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLinkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.banner1.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.assembly.go.kr/portal/main/main.do"))
            startActivity(mIntent)
        }

        binding.banner2.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://likms.assembly.go.kr/bill/main.do"))
            startActivity(mIntent)
        }

        binding.banner3.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://petitions.assembly.go.kr/"))
            startActivity(mIntent)
        }

    }
}