package pillowisgod.com.myapplication.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.transition.FragmentTransitionSupport
import kotlinx.android.synthetic.main.fragment_gist.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.GistResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.routers.GistFragmRouter
import pillowisgod.com.myapplication.routers.ProfileRouter
import pillowisgod.com.myapplication.viewmodels.factories.GistViewModelFactory
import pillowisgod.com.myapplication.viewmodels.models.GistViewModel


class GistFragment : Fragment(R.layout.fragment_gist) {

    private lateinit var gistModelGist : GistResponseModel
    private lateinit var viewModel : GistViewModel
    private lateinit var gistModel : GistFilesModel
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
        fbConfirmEditing.setOnClickListener {

            hideEditingForm()
        }
        fbDeclineEditing.setOnClickListener {
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
            withContext(Dispatchers.Main) {
                if (bool) {
                    Toast.makeText(context, "Current gist was successfully deleted", Toast.LENGTH_SHORT).show()
                    val bundle = viewModel.getGistsList()
                    router.routeToList(bundle)
                }
            }

        }
    }

    private fun editGist(modelGist: GistFilesModel) {
        GlobalScope.launch {
            if(etGistContent.text.isNotEmpty()) {
            modelGist.files.filename.content = etGistContent.text.toString()
            }
            if(etGistDesc.text.isNotEmpty()) {
                modelGist.description = etGistDesc.text.toString()
            }
            if(etGistName.text.isNotEmpty()) {
                modelGist.files.filename.gistName = etGistName.text.toString()
            }
            val bool = viewModel.editGist(url = gistModelGist.url,
            description = modelGist.description,
            files = modelGist.files)
            withContext(Dispatchers.Main){
                if(bool) {
                    Toast.makeText(context, "You've successfully edited gist", Toast.LENGTH_SHORT).show()
                    val bundle = viewModel.getGistsList()
                    router.routeToList(bundle)
                }
            }

        }
    }
    private fun setEditingForm() {
        etGistName.isEnabled = true
        etGistDesc.isEnabled = true
        etGistContent.isEnabled = true
        fbEditGist.visibility = View.GONE
        fbDeleteGist.visibility = View.GONE
        fbDeclineEditing.visibility = View.VISIBLE
        fbConfirmEditing.visibility = View.VISIBLE
    }

    private fun hideEditingForm() {
        etGistName.isEnabled = false
        etGistDesc.isEnabled = false
        etGistContent.isEnabled = false
        fbDeclineEditing.visibility = View.GONE
        fbConfirmEditing.visibility = View.GONE
        fbEditGist.visibility = View.VISIBLE
        fbDeleteGist.visibility = View.VISIBLE
    }
    private fun alertDialogDelete() {
        val alert = AlertDialog.Builder(context)
            .setMessage(R.string.delete_confirmation)
            .setPositiveButton("OK", DialogInterface.OnClickListener {dialog, which -> deleteGist()})
            .setNegativeButton("No", DialogInterface.OnClickListener{dialog, which -> return@OnClickListener})
        alert.show()

    }

}