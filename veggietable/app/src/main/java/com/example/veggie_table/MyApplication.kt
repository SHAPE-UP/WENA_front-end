package com.example.veggie_table

import android.app.Application
import android.util.Log
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class MyApplication: Application() {
    companion object {
        // data request
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        var networkServiceAssemblyData: NetworkServiceAssembly
        val retrofitAssemblyData: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("https://open.assembly.go.kr/portal/openapi/")
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .client(client)
                .build()

        // OkHttpClient
        var client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor())
             .build()


        init {
            networkServiceAssemblyData = retrofitAssemblyData.create(NetworkServiceAssembly::class.java)
        }

        private fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
            val interceptor = HttpLoggingInterceptor { message ->
                Log.e(
                    "MyGitHubData :",
                    message + ""
                )
            }
            return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    }


}