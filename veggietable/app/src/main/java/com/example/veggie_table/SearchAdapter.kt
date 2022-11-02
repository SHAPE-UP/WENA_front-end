package com.example.veggie_table

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.veggie_table.databinding.ItemMemberListBinding

class ViewHolderSearch(val binding: ItemMemberListBinding): RecyclerView.ViewHolder(binding.root)
class SearchAdapter(val context: Context, val datas:MutableList<myRow>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderSearch(ItemMemberListBinding.inflate(LayoutInflater.from(parent.context), parent,false ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolderSearch).binding
        val model = datas!![position]

        binding.itemListName.text = model.HG_NM
        binding.itemListParty.text = model.POLY_NM
        binding.itemListElection.text = model.ORIG_NM
        // 이미지도 넣어줘야함
        //binding.itemListImage

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        //Log.d("mobileApp", "${datas?.size}")
        return datas?.size ?: 0
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

}