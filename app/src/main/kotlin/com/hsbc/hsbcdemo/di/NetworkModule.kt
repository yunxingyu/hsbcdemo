
package com.hsbc.hsbcdemo.di

import com.hsbc.hsbcdemo.http.HsbcClient
import com.hsbc.hsbcdemo.http.HsbcService
import com.hsbc.hsbcdemo.http.HttpRequestInterceptor
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(HttpRequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://r.suconghou.cn/video/api/v3/")
      .addConverterFactory(GsonConverterFactory.create())
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideHSBCService(retrofit: Retrofit): HsbcService {
    return retrofit.create(HsbcService::class.java)
  }

  @Provides
  @Singleton
  fun provideHSBCClient(hsbcService: HsbcService): HsbcClient {
    return HsbcClient(hsbcService)
  }
}
