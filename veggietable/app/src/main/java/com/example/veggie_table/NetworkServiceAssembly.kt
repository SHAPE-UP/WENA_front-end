package com.example.veggie_table

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServiceAssembly {
    @GET("nwvrqwxyaytdsfvhu")
    fun getList(
        @Query("KEY") KEY : String?, // 인증키
        @Query("Type") Type : String?, // 타입
        @Query("pIndex") pIndex : Int?, // 페이지
        @Query("pSize") pSize : Int?, // 페이지 당 데이터 수
        @Query("HG_NM") HG_NM : String?, // 이름
        @Query("POLY_NM") POLY_NM : String?, // 정당명
        @Query("ORIG_NM") ORIG_NM : String?, // 선거구
        @Query("MONA_CD") MONA_CD: String?
    ) : Call<responseInfo>

}