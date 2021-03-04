package pillowisgod.com.myapplication.data.repositories.retrofits

import com.google.gson.GsonBuilder
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pillowisgod.com.myapplication.data.repositories.LoginCall
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGitRetrofit {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    val api : LoginCall by lazy {
        retrofit.create(LoginCall::class.java)
    }
}