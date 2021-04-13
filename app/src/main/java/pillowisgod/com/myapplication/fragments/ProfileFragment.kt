package pillowisgod.com.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.CookieManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants.MODEL_KEY
import pillowisgod.com.myapplication.routers.ProfileRouter
import pillowisgod.com.myapplication.viewmodels.models.ProfileViewModel

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel : ProfileViewModel by viewModels()
    private lateinit var router: ProfileRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setModel(requireArguments().get(MODEL_KEY) as SuccessfulResponseModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(viewModel.responseModel.value!!)
        router = ProfileRouter(fragment = this)
        fbLogOut.setOnClickListener {
           logOut()
        }
        fbListGists.setOnClickListener {
            moveToList()
        }
        fbSetMasterPass.setOnClickListener {
            router.routeToMasterPass(viewModel.responseModel.value!!)
        }
    }

    private fun moveToList() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val gists = viewModel.getGistsList()
                withContext(Dispatchers.Main) {
                    router.routeToList(gists)
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