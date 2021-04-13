package pillowisgod.com.myapplication.viewmodels.models

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import pillowisgod.com.myapplication.data.repositories.GistCalls
import pillowisgod.com.myapplication.data.repositories.model.FilesPostModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.db.PasswordDao
import pillowisgod.com.myapplication.helpers.Constants
import javax.inject.Inject

@HiltViewModel
class GistViewModel @Inject constructor(
    private val gistCalls: GistCalls,
    private val passwordDao: PasswordDao) : ViewModel() {

    private var gistResponse = MutableLiveData<GistResponseModel>()
    private var gistModelWithFiles = MutableLiveData<GistFilesModel>()
    val gistModel: LiveData<GistResponseModel> = gistResponse
    val gistFilesModel: LiveData<GistFilesModel> = gistModelWithFiles


    fun postGistValue(gistModel: GistResponseModel) {
        gistResponse.value = gistModel
        getGistData()
    }


    private fun getGistData() {
        viewModelScope.launch {
            val code = gistResponse.value?.url?.substringAfterLast("/")
            val response = gistCalls.getGist(code!!)
            Log.e("TAG", "This is the response -> $response")
            if (response.isSuccessful) {
                gistModelWithFiles.postValue(response.body())

            }
        }
    }

    suspend fun deleteGist(): Boolean {
        var bool = false
        val code = gistResponse.value?.url?.substringAfterLast("/")
        val response = gistCalls.deleteGist(
            token = "Bearer ${Constants.accessToken?.accessToken}",
            code!!
        )
        Log.e("TAG", "This is the response -> $response")
        if (response.isSuccessful) {
            bool = true
        }
        return bool
    }

    suspend fun getGistsList(): List<GistResponseModel> {
        var body: List<GistResponseModel>? = null
        val response =
            gistCalls.getGistsList(token = "Bearer ${Constants.accessToken?.accessToken}")
        if (response.isSuccessful) {
            body = response.body()
        }
        return body!!
    }

    suspend fun editGist(filesPostModel: FilesPostModel): Boolean {
        var bool = false
        val code = gistResponse.value?.url?.substringAfterLast("/")
        if (gistCalls.editGist(
                token = "Bearer ${Constants.accessToken?.accessToken}",
                gistID = code!!, files = filesPostModel
            ).isSuccessful
        ) {
            bool = true
            getGistData()
        }

        return bool
    }


    suspend fun postGist(filesPostModel: FilesPostModel): Boolean {
        var bool = false
        val response = gistCalls.postGist(
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

    suspend fun checkIfPasswordIsSet(): Boolean {
        var bool = false
        val data = passwordDao.getAll()
        if (data.size > 0) {
            bool = true
        }
        return bool
    }

}