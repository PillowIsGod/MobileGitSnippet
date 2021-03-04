package pillowisgod.com.myapplication.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_gist.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.FilesInnerModel
import pillowisgod.com.myapplication.data.repositories.model.FilesObj
import pillowisgod.com.myapplication.data.repositories.model.FilesPostModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.routers.GistFragmRouter
import pillowisgod.com.myapplication.viewmodels.factories.GistViewModelFactory
import pillowisgod.com.myapplication.viewmodels.models.GistViewModel


class GistFragment : Fragment(R.layout.fragment_gist) {

    private lateinit var gistModelGist: GistResponseModel
    private lateinit var viewModel: GistViewModel
    private lateinit var gistModel: GistFilesModel
    private lateinit var router: GistFragmRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gistModelGist = requireArguments().get(Constants.SINGLE_GIST_MODEL_KEY) as GistResponseModel
        val gistViewModelFactory = GistViewModelFactory()
        viewModel = ViewModelProvider(this, gistViewModelFactory).get(GistViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = GistFragmRouter(fragment = this)
        GlobalScope.launch {
            gistModel = viewModel.getGistData(gistModelGist.url)
            Log.e("TAG", "This is success -> $gistModel")
            withContext(Dispatchers.Main) {
                initViews(gistModel)
            }
        }
        fbDeleteGist.setOnClickListener {
            alertDialogDelete()
        }
        fbEditGist.setOnClickListener {
            setEditingForm()
        }
        fbConfirmAdding.setOnClickListener {
            editGist(gistModel)
            hideEditingForm()
        }
        fbDeclineAdding.setOnClickListener {
            hideEditingForm()
        }

    }

    private fun initViews(modelGist: GistFilesModel) {
        etGistName.setText(modelGist.files.filename.gistName, TextView.BufferType.EDITABLE)
        etGistContent.setText(modelGist.files.filename.content, TextView.BufferType.EDITABLE)
        etGistDesc.setText(modelGist.description, TextView.BufferType.EDITABLE)
    }

    private fun deleteGist() {
        GlobalScope.launch {
            val bool = viewModel.deleteGist(gistModelGist.url)
            successfulRouteFun(bool, R.string.success_delete)

        }
    }

    private fun editGist(modelGist: GistFilesModel) {

        if (etGistContent.text.isNotEmpty() && etGistName.text.isNotEmpty() && etGistDesc.text.isNotEmpty()) {
            GlobalScope.launch {
                val filesModel = FilesPostModel(
                    description = etGistDesc.text.toString(),
                    files = FilesObj(
                        filerandomname = FilesInnerModel(
                            content = etGistContent.text.toString(),
                            filename = etGistName.text.toString()
                        )
                    )
                )
                val bool = viewModel.editGist(
                    url = gistModelGist.url,
                    files = filesModel
                )
                successfulRouteFun(bool, R.string.successful_edit)
            }
        } else {
            Toast.makeText(context, R.string.not_enough_params, Toast.LENGTH_SHORT).show()
            return
        }


    }

    private fun successfulRouteFun(checkBoolean: Boolean, message: Int) {
        if (checkBoolean)
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    val bundle = viewModel.getGistsList()
                    router.routeToList(bundle)
                }
            }
        else {
            return
        }
    }

    private fun setEditingForm() {
        etGistName.isEnabled = true
        etGistDesc.isEnabled = true
        etGistContent.isEnabled = true
        fbEditGist.visibility = View.GONE
        fbDeleteGist.visibility = View.GONE
        fbDeclineAdding.visibility = View.VISIBLE
        fbConfirmAdding.visibility = View.VISIBLE
    }

    private fun hideEditingForm() {
        etGistName.isEnabled = false
        etGistDesc.isEnabled = false
        etGistContent.isEnabled = false
        fbDeclineAdding.visibility = View.GONE
        fbConfirmAdding.visibility = View.GONE
        fbEditGist.visibility = View.VISIBLE
        fbDeleteGist.visibility = View.VISIBLE
    }

    private fun alertDialogDelete() {
        val alert = AlertDialog.Builder(context)
            .setMessage(R.string.delete_confirmation)
            .setPositiveButton(
                "OK",
                DialogInterface.OnClickListener { dialog, which -> deleteGist() })
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, which -> return@OnClickListener })
        alert.show()

    }

}