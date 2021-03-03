package pillowisgod.com.myapplication.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.CookieManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants.MODEL_KEY
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
            moveToList()
        }
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun moveToList() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val gists = viewModel.getGistsList()
                router.routeToList(gists)
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