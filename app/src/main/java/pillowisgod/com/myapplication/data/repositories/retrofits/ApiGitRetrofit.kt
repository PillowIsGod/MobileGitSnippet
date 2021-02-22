package pillowisgod.com.myapplication.data.repositories.retrofits

import pillowisgod.com.myapplication.data.repositories.LoginCall
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGitRetrofit {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api : LoginCall by lazy {
        retrofit.create(LoginCall::class.java)
    }
}