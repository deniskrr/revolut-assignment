package com.deniskrr.data.di

import android.content.Context
import androidx.room.Room
import com.deniskrr.data.BuildConfig
import com.deniskrr.data.RatesApi
import com.deniskrr.data.repo.LocalRateDataSource
import com.deniskrr.data.repo.RateRepositoryImpl
import com.deniskrr.data.repo.RemoteRateDataSource
import com.deniskrr.data.repo.RevolutDatabase
import com.deniskrr.domain.repo.RateRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NetworkModule {

  @Binds
  abstract fun bindRateRepository(rateRepositoryImpl: RateRepositoryImpl): RateRepository
}

@Module
@InstallIn(ActivityRetainedComponent::class)
class ConcreteNetworkModule {

  @Provides
  fun provideRatesApi(
    httpClient: OkHttpClient
  ): RatesApi {
    return Retrofit.Builder().baseUrl(
      "https://hiring.revolut.codes/"
    )
      .addConverterFactory(GsonConverterFactory.create())
      .client(httpClient)
      .build()
      .create(RatesApi::class.java)
  }

  @Provides
  fun provideHttpClient(): OkHttpClient {
    return OkHttpClient()
      .newBuilder()
      .addInterceptor(HttpLoggingInterceptor().apply {
        level =
          if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
      })
      .build()
  }

  @Provides
  fun provideRemoteRateDataSource(
    api: RatesApi
  ): RemoteRateDataSource {
    return RemoteRateDataSource(api)
  }

  @Provides
  fun provideDb(@ApplicationContext appContext: Context): RevolutDatabase {
    return Room.databaseBuilder(appContext, RevolutDatabase::class.java, "rates").build()
  }

  @Provides
  fun provideLocalDataSource(
    db: RevolutDatabase
  ): LocalRateDataSource {
    return LocalRateDataSource(db)
  }

  @Provides
  fun provideRateRepositoryImpl(
    remoteRateDataSource: RemoteRateDataSource,
    localRateDataSource: LocalRateDataSource
  ): RateRepositoryImpl {
    return RateRepositoryImpl(remoteRateDataSource, localRateDataSource)
  }
}