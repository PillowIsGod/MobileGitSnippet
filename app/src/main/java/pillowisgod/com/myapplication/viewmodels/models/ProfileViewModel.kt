package pillowisgod.com.myapplication.viewmodels.models

import android.util.Log
import androidx.lifecycle.ViewModel
import pillowisgod.com.myapplication.data.repositories.model.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.retrofits.ApiGitRetrofit
import pillowisgod.com.myapplication.helpers.Constants

class ProfileViewModel : ViewModel() {
    suspend fun getGistsList() : List<GistResponseModel> {
        var body : List<GistResponseModel>? = null
        var model : GistResponseModel? = null
        val response = ApiGitRetrofit.api.getGistsList(token = "Bearer ${Constants.accessToken?.accessToken}")
        if(response.isSuccessful) {
            body = response.body()
            if (body != null) {
                model = body[0]
            }
        }
        Log.e("TAG", "This is gist response -> ${model?.files?.filename?.gistName}")
        return body!!
    }

}