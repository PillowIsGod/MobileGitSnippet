package pillowisgod.com.myapplication.data.repositories.retrofits

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pillowisgod.com.myapplication.data.repositories.LoginCall
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api : LoginCall by lazy {
        retrofit.create(LoginCall::class.java)
    }
}