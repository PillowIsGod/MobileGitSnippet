package pillowisgod.com.myapplication.fragments

import android.content.Intent
import android.content.Intent.*
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.internal.wait
import okhttp3.logging.HttpLoggingInterceptor
import pillowisgod.com.myapplication.MainActivity
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.LoginCall
import pillowisgod.com.myapplication.data.repositories.model.AccessToken
import pillowisgod.com.myapplication.data.repositories.retrofits.LoginRetrofitInstance
import pillowisgod.com.myapplication.helpers.Constants.BASE_URL
import pillowisgod.com.myapplication.helpers.Constants.CALLBACK_URL
import pillowisgod.com.myapplication.helpers.Constants.CLIENT_ID
import pillowisgod.com.myapplication.helpers.Constants.CLIENT_SECRET
import pillowisgod.com.myapplication.helpers.Constants.GIT_LOGIN_CALL
import pillowisgod.com.myapplication.helpers.Constants.LOGIN_URL
import pillowisgod.com.myapplication.routers.LoginRouter
import pillowisgod.com.myapplication.viewmodels.factories.LoginViewModelFactory
import pillowisgod.com.myapplication.viewmodels.models.LoginViewModel
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel
    private lateinit var router: LoginRouter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = LoginRouter(fragment = this)


        wvLogin.settings.javaScriptEnabled = true
        wvLogin.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
            }
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                wvLogin.visibility = View.GONE
            }


            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                GlobalScope.launch {
                    val uri = Uri.parse(url)
                    if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
                        val response = viewModel.logToAcc(uri)
                        if (response.profileUrl.isNotEmpty()) {
                            router.routeToProfile(response)
                        }
                    }
                }
            }
        }
        wvLogin.loadUrl(LOGIN_URL)
    }





}