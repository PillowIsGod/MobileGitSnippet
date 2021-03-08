package pillowisgod.com.myapplication.routers

import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.helpers.Constants.CHECK_FRAGM_KEY
import pillowisgod.com.myapplication.helpers.Constants.MODEL_KEY

interface DialogRoutingLogic {
    fun routeToProfile(successfulResponseModel: SuccessfulResponseModel)
//    fun routeToGistFragm(gistResponseModelGist: GistResponseModel)
    fun routeToGistAdd(bool : Boolean)
}

class DialogRouter(private val fragment: DialogFragment) : DialogRoutingLogic {
    override fun routeToProfile(successfulResponseModel: SuccessfulResponseModel) {
        val bundle = bundleOf(MODEL_KEY to successfulResponseModel)
        fragment
            .findNavController()
            .navigate(R.id.action_dialog_to_profile, bundle)
    }

    fun routeToGistFragm(bool: Boolean) {
        val bundle = bundleOf(CHECK_FRAGM_KEY to bool)
        fragment
            .findNavController()
            .navigate(R.id.action_check_to_gist_fragment, bundle)
    }

    override fun routeToGistAdd(bool: Boolean) {
        val bundle = bundleOf(Constants.CHECK_FRAGM_KEY to bool)
        fragment
            .findNavController()
            .navigate(R.id.action_check_to_add_gist_fragm, bundle)
    }


}