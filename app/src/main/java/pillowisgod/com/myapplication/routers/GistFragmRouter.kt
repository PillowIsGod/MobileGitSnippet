package pillowisgod.com.myapplication.routers

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.helpers.Constants.STRING_KEY

interface GistRouterLogic {
    fun routeToList(gistFilesModel: List<GistResponseModel>)
}


class GistFragmRouter(private val fragment : Fragment) : GistRouterLogic {

    override fun routeToList(gistFilesModel: List<GistResponseModel>) {
        val bundle = bundleOf(Constants.GIST_LIST_MODEL_KEY to gistFilesModel)
        fragment
            .findNavController()
            .navigate(R.id.action_gistFragm_toList, bundle)
    }
    fun routeToListFromAdd(gistFilesModel: List<GistResponseModel>) {
        val bundle = bundleOf(Constants.GIST_LIST_MODEL_KEY to gistFilesModel)
        fragment
            .findNavController()
            .navigate(R.id.action_gistAdd_toList, bundle)
    }
    fun routeToPassCheck(string : String) {
        val bundle = bundleOf(STRING_KEY to string)
        fragment
            .findNavController()
            .navigate(R.id.action_gistFragm_toCheck, bundle)
    }
    fun routeToPassCheckFromAdd(string : String) {
        val bundle = bundleOf(STRING_KEY to string)
        fragment
            .findNavController()
            .navigate(R.id.action_gistaddFragm_toCheck, bundle)
    }
}