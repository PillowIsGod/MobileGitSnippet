package pillowisgod.com.myapplication.viewmodels.models

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pillowisgod.com.myapplication.data.repositories.model.AccessToken
import pillowisgod.com.myapplication.data.repositories.model.SuccessfulResponseModel
import pillowisgod.com.myapplication.data.repositories.retrofits.ApiGitRetrofit
import pillowisgod.com.myapplication.data.repositories.retrofits.LoginRetrofitInstance
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.helpers.Constants.CALLBACK_URL
import pillowisgod.com.myapplication.helpers.Constants.CLIENT_ID
import pillowisgod.com.myapplication.helpers.Constants.GIT_LOGIN_CALL

class LoginViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    var accessToken: AccessToken? = null

    fun loginIntent() : Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse("$GIT_LOGIN_CALL?client_id=$CLIENT_ID&scope=repo,gist&redirect_uri=$CALLBACK_URL"))
    }

    fun getLoginResponse(uri: Uri) : String {
        var s : String? = null
        if (uri != null && uri.toString().startsWith(CALLBACK_URL))
        {
            s = uri.getQueryParameter("code")!!
        }
        return s!!
    }

    fun getCode(uri : Uri) : String {
        return uri.getQueryParameter("code")!!
    }

    suspend fun getAccessToken(code : String) : AccessToken {
        var accessToken : AccessToken? = null
            val response = LoginRetrofitInstance.api.getAccessToken(CLIENT_ID,
                Constants.CLIENT_SECRET, code!!)
//            Log.e("TAG", "This is the coroutine response -> $response")
           if (response.isSuccessful) {
                accessToken = response.body()!!
//                Log.e("TAG", "Success response -> $accessToken")

            }
        return accessToken!!
    }
    suspend fun authorizeGit(token : String) : SuccessfulResponseModel {
        val response = ApiGitRetrofit.api.getLoginCall(token)
        val body = response.body()
        Log.e("TAG", "This is the response -> $response")
        return body!!

    }

    suspend fun logToAcc(uri: Uri): SuccessfulResponseModel {
        var response: SuccessfulResponseModel? = null

        val code = getCode(uri)
        accessToken = getAccessToken(code)
        Log.e("TAG", "MVVM TOKEN QUERY -> $accessToken")
        response = authorizeGit(token = "Bearer ${accessToken?.accessToken}")
        Log.e("TAG", "Response -> $response")

        return response!!
    }


}