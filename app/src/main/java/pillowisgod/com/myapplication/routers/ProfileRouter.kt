package pillowisgod.com.myapplication.routers

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants

interface ProfileRouterLogic {
    fun routeToLogin()
}


class ProfileRouter(private val fragment : Fragment) : ProfileRouterLogic {
    override fun routeToLogin() {
        fragment
            .findNavController()
            .navigate(R.id.action_profileFragment_toLoginFragm)
    }

}

