package com.shaya.githubrepository.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.shaya.githubrepository.BASE_URL
import com.shaya.githubrepository.BaseApplication
import com.shaya.githubrepository.END_POINT
import com.shaya.githubrepository.network.gitresponse.GitResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(ChuckerInterceptor(BaseApplication.instance.applicationContext))
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface ItemApiService {


    @GET(END_POINT)
    suspend fun getGithubRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): GitResponse
}

object ItemApi {
    val retrofitService: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }
}

