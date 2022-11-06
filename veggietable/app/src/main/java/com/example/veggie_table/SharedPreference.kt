package com.example.veggie_table

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager

class SharedPreference {
    companion object{
        fun getSharedPreferences(ctx: Context): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }
        // _id
        fun setUserId(ctx: Context, _id: String){
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("_id", _id)
            editor.commit()
        }
        fun getUserId(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("_id", "") }

        // 이메일
        fun setUserEmail(ctx: Context, email: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("userEmail", email)
            editor.commit()
        }
        fun getUserEmail(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("userEmail", "") }

        // 닉네임
        fun setUserName(ctx: Context, name: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("username", name)
            editor.commit()
        }
        fun getUserName(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("username", "") }

        // 거주지
        fun setUserResidence(ctx: Context, residence: String){
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("userResidence", residence)
            editor.commit()
        }
        fun getUserResidence(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("userResidence", "") }

        // 저장된 SharedPreference 값 전체 삭제
        fun clearAll(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.clear()
            editor.commit()
        }
    }
}