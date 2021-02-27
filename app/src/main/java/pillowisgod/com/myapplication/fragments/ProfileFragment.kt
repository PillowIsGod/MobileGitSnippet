package pillowisgod.com.myapplication.fragments

import android.content.ContentResolver
import android.os.Bundle
import android.provider.Browser
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cookie
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants.MODEL_KEY
import pillowisgod.com.myapplication.routers.LoginRouter
import pillowisgod.com.myapplication.routers.ProfileRouter

import pillowisgod.com.myapplication.viewmodels.factories.ProfileViewModelFactory

import pillowisgod.com.myapplication.viewmodels.models.ProfileViewModel


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel : ProfileViewModel
    private lateinit var model : SuccessfulResponseModel
    private lateinit var router: ProfileRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = requireArguments().get(MODEL_KEY) as SuccessfulResponseModel
        val profileViewModelFactory = ProfileViewModelFactory()
        viewModel = ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(model)
        router = ProfileRouter(fragment = this)
        fbLogOut.setOnClickListener {
           logOut()
        }
        fbListGists.setOnClickListener {
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    viewModel.getGistsList()
                }
            }
        }


    }

    private fun initViews(successfulResponseModel: SuccessfulResponseModel) {
        tvNickName.text = successfulResponseModel.login
        Picasso.get().load(successfulResponseModel.avatarUrl).into(imgAvatar)
    }
    private fun logOut() {
        if(CookieManager.getInstance().hasCookies()) {
            CookieManager.getInstance().removeAllCookie()
            router.routeToLogin()
        }
    }
}