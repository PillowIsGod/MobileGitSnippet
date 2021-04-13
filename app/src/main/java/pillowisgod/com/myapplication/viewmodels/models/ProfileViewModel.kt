package pillowisgod.com.myapplication.viewmodels.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pillowisgod.com.myapplication.data.repositories.GistCalls
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val api : GistCalls) : ViewModel() {
    private val model = MutableLiveData<SuccessfulResponseModel>()
    val responseModel : LiveData<SuccessfulResponseModel> = model

    fun setModel(successfulResponseModel: SuccessfulResponseModel) {
        model.value = successfulResponseModel
    }
    suspend fun getGistsList() : List<GistResponseModel> {
        var body : List<GistResponseModel>? = null
        val response = api.getGistsList(token = "Bearer ${Constants.accessToken?.accessToken}")
        if(response.isSuccessful) {
            body = response.body()
        }

        Log.e("TAG", "This is gist response -> $body")
        return body!!
    }

}