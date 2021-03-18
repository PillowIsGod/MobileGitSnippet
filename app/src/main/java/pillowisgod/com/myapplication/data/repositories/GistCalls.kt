package pillowisgod.com.myapplication.data.repositories

import pillowisgod.com.myapplication.data.repositories.model.FilesPostModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import retrofit2.Response
import retrofit2.http.*

interface GistCalls {
    @GET("gists")
    suspend fun getGistsList(@Header("Authorization") token: String): Response<List<GistResponseModel>>

    @GET("gists/{gist_id}")
    suspend fun getGist(@Path("gist_id") gistID: String): Response<GistFilesModel>

    @PATCH("gists/{gist_id}")
    suspend fun editGist(
        @Header("Authorization") token: String,
        @Path("gist_id") gistID: String,
        @Body files: FilesPostModel
    ): Response<GistFilesModel>


    @POST("gists")
    suspend fun postGist(
        @Header("Authorization") token: String,
        @Body files : FilesPostModel
    ): Response<GistFilesModel>


    @DELETE("gists/{gist_id}")
    suspend fun deleteGist(
        @Header("Authorization") token: String,
        @Path("gist_id") gistID: String
    ): Response<Boolean>
}