package pillowisgod.com.myapplication.viewmodels.models

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import pillowisgod.com.myapplication.data.repositories.GistCalls
import pillowisgod.com.myapplication.data.repositories.LoginCall
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.AccessToken
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.helpers.Constants.CALLBACK_URL
import pillowisgod.com.myapplication.helpers.Constants.CLIENT_ID
import pillowisgod.com.myapplication.helpers.Constants.GIT_LOGIN_CALL
import pillowisgod.com.myapplication.helpers.Constants.accessToken

class LoginViewModel : ViewModel() {



    fun loginIntent() : Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse("$GIT_LOGIN_CALL?client_id=$CLIENT_ID&scope=repo,gist&redirect_uri=$CALLBACK_URL"))
    }

    fun getLoginResponse(uri: Uri) : String {
        var s : String? = null
        if (uri.toString().startsWith(CALLBACK_URL))
        {
            s = uri.getQueryParameter("code")!!
        }
        return s!!
    }

    fun getCode(uri : Uri) : String {
        return uri.getQueryParameter("code")!!
    }

    suspend fun getAccessToken(api : LoginCall, code : String) : AccessToken {
        var accessToken : AccessToken? = null
            val response = api.getAccessToken(CLIENT_ID,
                Constants.CLIENT_SECRET, code)
            Log.e("TAG", "This is the coroutine response -> $response")
           if (response.isSuccessful) {
                accessToken = response.body()!!
//                Log.e("TAG", "Success response -> $accessToken")

            }
        return accessToken!!
    }
    suspend fun authorizeGit(api : GistCalls, token : String) : SuccessfulResponseModel {
        val response = api.getLoginCall(token)
        val body = response
        Log.e("TAG", "This is the response -> $response")
        return body

    }

    suspend fun logToAcc(apiLoginCall: LoginCall, apiGist: GistCalls,  uri: Uri): SuccessfulResponseModel {
        var response: SuccessfulResponseModel? = null

        val code = getCode(uri)
        Log.e("TAG", "This is received code -> $code")
        accessToken = getAccessToken(apiLoginCall, code)
        Log.e("TAG", "MVVM TOKEN QUERY -> $accessToken")
        response = authorizeGit(token = "Bearer ${accessToken?.accessToken}", api = apiGist)
        Log.e("TAG", "Response -> $response")

        return response
    }


}