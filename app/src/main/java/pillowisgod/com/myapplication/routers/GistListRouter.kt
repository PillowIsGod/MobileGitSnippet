package pillowisgod.com.myapplication.routers

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.helpers.Constants.SINGLE_GIST_MODEL_KEY

interface GistListRoutingLogic {
    fun routeToGistFragm(gistResponseModelGist: GistResponseModel)
    fun routeToEmptyGistFragm()
}


class GistListRouter(private val fragment : Fragment) : GistListRoutingLogic {

    override fun routeToGistFragm(gistResponseModelGist: GistResponseModel) {
        val bundle = bundleOf(SINGLE_GIST_MODEL_KEY to gistResponseModelGist)
        fragment
            .findNavController()
            .navigate(R.id.action_listFragment_toGist, bundle)
    }

    override fun routeToEmptyGistFragm() {
        fragment
            .findNavController()
            .navigate(R.id.action_listFragm_toAdd)
    }
}