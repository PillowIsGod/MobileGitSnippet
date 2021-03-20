package pillowisgod.com.myapplication.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pillowisgod.com.myapplication.data.repositories.GistCalls
import pillowisgod.com.myapplication.data.repositories.LoginCall
import pillowisgod.com.myapplication.di.AppScope
import pillowisgod.com.myapplication.helpers.Constants.BASE_API_URL
import pillowisgod.com.myapplication.helpers.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitLoginModule {
    @AppScope
    @Provides
    fun provideClient() : OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    @AppScope
    @Provides
    @Named("loginRetrofit")
    fun provideLoginRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @AppScope
    @Provides
    fun provideLoginApi(@Named("loginRetrofit")retrofit: Retrofit) : LoginCall {
        return retrofit.create(LoginCall::class.java)
    }
    @AppScope
    @Provides
    @Named("apiRetrofit")
    fun provideApiRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @AppScope
    @Provides
    fun provideGistApi(@Named("apiRetrofit") retrofit : Retrofit) : GistCalls {
        return retrofit.create(GistCalls::class.java)
    }
}