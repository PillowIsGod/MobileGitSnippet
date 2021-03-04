package pillowisgod.com.myapplication.viewmodels.models

import android.util.Log
import androidx.lifecycle.ViewModel
import pillowisgod.com.myapplication.data.repositories.model.FilesPostModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.retrofits.ApiGitRetrofit
import pillowisgod.com.myapplication.helpers.Constants

class GistViewModel : ViewModel() {

    suspend fun getGistData(url : String) : GistFilesModel {
        var gist : GistFilesModel? = null
        val code = url.substringAfterLast("/")
        val response = ApiGitRetrofit.api.getGist(code)
        Log.e("TAG", "This is the response -> $response")
        if(response.isSuccessful) {
            gist = response.body()
        }
        return gist!!
    }
    suspend fun deleteGist(url:String) : Boolean {
        var bool = false
        val code = url.substringAfterLast("/")
        val response = ApiGitRetrofit.api.deleteGist(token = "Bearer ${Constants.accessToken?.accessToken}",code)
        Log.e("TAG", "This is the response -> $response")
        if(response.isSuccessful) {
            bool = true
        }
        return bool
    }
    suspend fun getGistsList() : List<GistResponseModel> {
        var body : List<GistResponseModel>? = null
        val response = ApiGitRetrofit.api.getGistsList(token = "Bearer ${Constants.accessToken?.accessToken}")
        if(response.isSuccessful) {
            body = response.body()
        }
        return body!!
    }
    suspend fun editGist(url: String, files : FilesPostModel) : Boolean {
        var bool = false
        val code = url.substringAfterLast("/")
        val response = ApiGitRetrofit.api.editGist(token = "Bearer ${Constants.accessToken?.accessToken}",
            gistID = code, files= files)
        Log.e("TAG", "This is edit -> $response")
        if(response.isSuccessful) {
            bool = true
        }
        return bool
    }

    suspend fun postGist(files : FilesPostModel) : Boolean {
        var bool = false
        val response = ApiGitRetrofit.api.postGist(token = "Bearer ${Constants.accessToken?.accessToken}",files = files)
        Log.e("TAG", "This is post request -> $response")
        if (response.isSuccessful) {
            bool = true
        }
        return bool
    }
}