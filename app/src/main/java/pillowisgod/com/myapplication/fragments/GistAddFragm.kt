package pillowisgod.com.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_gist.etGistContent
import kotlinx.android.synthetic.main.fragment_gist.etGistDesc
import kotlinx.android.synthetic.main.fragment_gist.etGistName
import kotlinx.android.synthetic.main.fragment_gist.fbConfirmAdding
import kotlinx.android.synthetic.main.fragment_gist.fbDeclineAdding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.FilesInnerModel
import pillowisgod.com.myapplication.data.repositories.model.FilesObj
import pillowisgod.com.myapplication.data.repositories.model.FilesPostModel
import pillowisgod.com.myapplication.routers.GistFragmRouter
import pillowisgod.com.myapplication.viewmodels.factories.GistViewModelFactory
import pillowisgod.com.myapplication.viewmodels.models.GistViewModel


class GistAddFragm : Fragment(R.layout.fragment_gist_add) {

    private lateinit var viewModel: GistViewModel
    private lateinit var router: GistFragmRouter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gistViewModelFactory = GistViewModelFactory()
        viewModel = ViewModelProvider(this, gistViewModelFactory).get(GistViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = GistFragmRouter(fragment = this)
        setEditingForm()
        fbDeclineAdding.setOnClickListener {
            GlobalScope.launch {
                val bundle = viewModel.getGistsList()
                router.routeToListFromAdd(bundle)
            }
        }
        fbConfirmAdding.setOnClickListener {
            postGist()
        }
    }

    private fun postGist() {
        if (etGistContent.text.isNotEmpty() && etGistDesc.text.isNotEmpty() && etGistName.text.isNotEmpty()) {
           val model = FilesPostModel(
                description = etGistDesc.text.toString(),
                files = FilesObj(
                    FilesInnerModel(
                        content = etGistContent.text.toString(),
                        filename = etGistName.text.toString()
                    )
                )
            )
            GlobalScope.launch {
                val bool = viewModel.postGist(files = model!!)
                withContext(Dispatchers.Main) {
                    if (bool) {
                        Toast.makeText(context, R.string.success_add, Toast.LENGTH_SHORT).show()
                        val bundle = viewModel.getGistsList()
                        router.routeToListFromAdd(bundle)
                    }
                }
            }

        } else {
            Toast.makeText(context, R.string.not_enough_params, Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun setEditingForm() {
        etGistName.isEnabled = true
        etGistDesc.isEnabled = true
        etGistContent.isEnabled = true
    }
}