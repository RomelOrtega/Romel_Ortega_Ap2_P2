package edu.ucne.romel_ortega_ap2_p2.di

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.Api
import edu.ucne.romel_ortega_ap2_p2.data.remote.remotedatasource.RemoteDataSource
import edu.ucne.romel_ortega_ap2_p2.data.repository.RepositoryImpl
import edu.ucne.romel_ortega_ap2_p2.domain.repository.Repository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): Api =
        Retrofit.Builder()
            .baseUrl("https://api-2026-h7eddqgydxc0fmau.eastus2-01.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(Api::class.java)

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource): Repository {
        return RepositoryImpl(remoteDataSource)
    }
}