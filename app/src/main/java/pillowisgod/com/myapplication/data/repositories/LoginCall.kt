package pillowisgod.com.myapplication.data.repositories

import pillowisgod.com.myapplication.data.repositories.model.*
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.AccessToken
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import retrofit2.Response
import retrofit2.http.*


interface LoginCall {



    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Response<AccessToken>




}