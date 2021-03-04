package pillowisgod.com.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.routers.GistListRouter
import pillowisgod.com.myapplication.viewmodels.adapters.GistListAdapter


class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var gistsData : List<GistResponseModel>
    private val gistListAdapter = GistListAdapter(fragment = this)
    private lateinit var router : GistListRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gistsData = requireArguments().get(Constants.GIST_LIST_MODEL_KEY) as List<GistResponseModel>
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = GistListRouter(fragment = this)
        initAdapter(gistsData)
        fbAddGist.setOnClickListener {
            router.routeToEmptyGistFragm()
        }
    }

    private fun initAdapter(gistResponseModelGist: List<GistResponseModel>) {
        gistListAdapter.setData(gistResponseModelGist)
        rvGistsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rvGistsList.adapter = gistListAdapter
    }

}