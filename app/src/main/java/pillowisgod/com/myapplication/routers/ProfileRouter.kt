package pillowisgod.com.myapplication.routers

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.helpers.Constants.MODEL_KEY

interface ProfileRouterLogic {
    fun routeToLogin()
    fun routeToList(gistFilesModel: List<GistResponseModel>)
    fun routeToMasterPass(successfulResponseModel: SuccessfulResponseModel)
    fun routeToSettings()
}


class ProfileRouter(private val fragment : Fragment) : ProfileRouterLogic {
    override fun routeToLogin() {
        fragment
            .findNavController()
            .navigate(R.id.action_profileFragment_toLoginFragm)
    }

    override fun routeToList(gistFilesModel: List<GistResponseModel>) {
        val bundle = bundleOf(Constants.GIST_LIST_MODEL_KEY to gistFilesModel)
        fragment
            .findNavController()
            .navigate(R.id.action_profileFragment_toList, bundle)
    }

    override fun routeToMasterPass(successfulResponseModel: SuccessfulResponseModel) {
        val bundle = bundleOf(MODEL_KEY to successfulResponseModel)
        fragment
            .findNavController()
            .navigate(R.id.action_profileFragment_toMasterPass, bundle)
    }

    override fun routeToSettings() {
        TODO("Not yet implemented")
    }

}

