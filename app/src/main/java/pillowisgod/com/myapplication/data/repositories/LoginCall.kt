package pillowisgod.com.myapplication.data.repositories

import pillowisgod.com.myapplication.data.repositories.model.*
import retrofit2.Response
import retrofit2.http.*


interface LoginCall {

//    @Headers("Authorization: Bearer 3e881a613c709a45cd244e090b5aae56075f3e81")
    @GET("user")
    suspend fun getLoginCall(@Header("Authorization") token: String) : Response<SuccessfulResponseModel>

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ) : Response<AccessToken>


    @GET("gists")
    suspend fun getGistsList(@Header("Authorization") token : String) : Response<List<GistResponseModel>>

    @GET("gists/{gist_id}")
    suspend fun getGist(@Path("gist_id")gistID : String) : Response<GistFilesModel>

    @PATCH("gists/{gist_id}")
    @FormUrlEncoded
    suspend fun editGist(@Header("Authorization") token: String,
                         @Path("gist_id")gistID : String,
                         @Field("description") desc : String,
    @Field("files") files : FilesRemoteModel) : Response<Boolean>

    @DELETE("gists/{gist_id}")
    suspend fun deleteGist(@Header("Authorization") token: String,
                           @Path("gist_id")gistID : String) : Response<Boolean>
}