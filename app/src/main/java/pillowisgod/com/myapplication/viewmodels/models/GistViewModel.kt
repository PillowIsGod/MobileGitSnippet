package pillowisgod.com.myapplication.viewmodels.models

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import pillowisgod.com.myapplication.data.repositories.GistCalls
import pillowisgod.com.myapplication.data.repositories.model.FilesPostModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.db.PasswordDao
import pillowisgod.com.myapplication.helpers.Constants


class GistViewModel : ViewModel() {

    private var gistResponse = MutableLiveData<GistResponseModel>()
    private var gistModelWithFiles = MutableLiveData<GistFilesModel>()
    val gistModel: LiveData<GistResponseModel> = gistResponse
    val gistFilesModel: LiveData<GistFilesModel> = gistModelWithFiles


    fun postGistValue(api : GistCalls, gistModel: GistResponseModel) {
        gistResponse.value = gistModel
        getGistData(api)
    }


    private fun getGistData(api : GistCalls) {
        viewModelScope.launch {
            val code = gistResponse.value?.url?.substringAfterLast("/")
            val response = api.getGist(code!!)
            Log.e("TAG", "This is the response -> $response")
            if (response.isSuccessful) {
                gistModelWithFiles.postValue(response.body())

            }
        }
    }

    suspend fun deleteGist(api : GistCalls): Boolean {
        var bool = false
        val code = gistResponse.value?.url?.substringAfterLast("/")
        val response = api.deleteGist(
            token = "Bearer ${Constants.accessToken?.accessToken}",
            code!!
        )
        Log.e("TAG", "This is the response -> $response")
        if (response.isSuccessful) {
            bool = true
        }
        return bool
    }

    suspend fun getGistsList(api : GistCalls): List<GistResponseModel> {
        var body: List<GistResponseModel>? = null
        val response =
            api.getGistsList(token = "Bearer ${Constants.accessToken?.accessToken}")
        if (response.isSuccessful) {
            body = response.body()
        }
        return body!!
    }

    suspend fun editGist(api : GistCalls, filesPostModel: FilesPostModel): Boolean {
        var bool = false
        val code = gistResponse.value?.url?.substringAfterLast("/")
        if (api.editGist(
                token = "Bearer ${Constants.accessToken?.accessToken}",
                gistID = code!!, files = filesPostModel
            ).isSuccessful
        ) {
            bool = true
            getGistData(api)
        }

        return bool
    }


    suspend fun postGist(api : GistCalls, filesPostModel: FilesPostModel): Boolean {
        var bool = false
        val response = api.postGist(
            token = "Bearer ${Constants.accessToken?.accessToken}",
            files = filesPostModel
        )
        Log.e("TAG", "This is post request -> $response")
        if (response.isSuccessful) {
            bool = true
        }
        return bool
    }

    fun isAppAvailable(context: Context, appName: String?): Boolean {
        val pm: PackageManager = context.packageManager
        return try {
            pm.getPackageInfo(appName!!, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    suspend fun checkIfPasswordIsSet(passwordDao: PasswordDao): Boolean {
        var bool = false
        val data = passwordDao.getAll()
        if (data.size > 0) {
            bool = true
        }
        return bool
    }

}