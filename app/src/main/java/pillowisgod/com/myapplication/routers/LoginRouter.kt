package pillowisgod.com.myapplication.routers

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants.MODEL_KEY

interface LoginRoutingLogic {
    fun routeToProfile(successfulResponseModel: SuccessfulResponseModel)
}


class LoginRouter(private val fragment : Fragment) : LoginRoutingLogic  {

    override fun routeToProfile(successfulResponseModel: SuccessfulResponseModel) {
        val bundle = bundleOf(MODEL_KEY to successfulResponseModel)

        fragment
            .findNavController()
            .navigate(R.id.action_loginFragment_toProfile, bundle)
    }


}