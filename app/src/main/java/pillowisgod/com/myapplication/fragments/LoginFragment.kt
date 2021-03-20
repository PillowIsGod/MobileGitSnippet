package pillowisgod.com.myapplication.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.GistCalls
import pillowisgod.com.myapplication.data.repositories.LoginCall
import pillowisgod.com.myapplication.helpers.Constants.CALLBACK_URL
import pillowisgod.com.myapplication.helpers.Constants.LOGIN_URL
import pillowisgod.com.myapplication.routers.LoginRouter
import pillowisgod.com.myapplication.viewmodels.factories.LoginViewModelFactory
import pillowisgod.com.myapplication.viewmodels.models.LoginViewModel
import javax.inject.Inject


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel
    private lateinit var router: LoginRouter
    @Inject
    lateinit var loginCall : LoginCall
    @Inject
    lateinit var gistCalls : GistCalls


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
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
                lifecycleScope.launch {
                    val uri = Uri.parse(url)
                    if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
                        val response = viewModel.logToAcc(loginCall, apiGist = gistCalls, uri = uri)
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