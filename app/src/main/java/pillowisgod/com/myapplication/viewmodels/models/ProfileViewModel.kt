package pillowisgod.com.myapplication.viewmodels.models

import android.util.Log
import androidx.lifecycle.ViewModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.retrofits.ApiGitRetrofit
import pillowisgod.com.myapplication.helpers.Constants

class ProfileViewModel : ViewModel() {
    suspend fun getGistsList() : List<GistResponseModel> {
        var body : List<GistResponseModel>? = null
        val response = ApiGitRetrofit.api.getGistsList(token = "Bearer ${Constants.accessToken?.accessToken}")
        if(response.isSuccessful) {
            body = response.body()
        }

        Log.e("TAG", "This is gist response -> $body")
        return body!!
    }

}