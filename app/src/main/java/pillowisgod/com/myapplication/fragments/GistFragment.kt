package pillowisgod.com.myapplication.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_gist.*
import kotlinx.coroutines.*
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.FilesInnerModel
import pillowisgod.com.myapplication.data.repositories.model.FilesObj
import pillowisgod.com.myapplication.data.repositories.model.FilesPostModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistFilesModel
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.helpers.Constants.Gist_String
import pillowisgod.com.myapplication.routers.GistFragmRouter
import pillowisgod.com.myapplication.viewmodels.factories.GistViewModelFactory
import pillowisgod.com.myapplication.viewmodels.models.GistViewModel
import java.lang.Exception


class GistFragment : Fragment(R.layout.fragment_gist) {

    private lateinit var viewModel: GistViewModel
    private lateinit var router: GistFragmRouter
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gistViewModelFactory = GistViewModelFactory()
        viewModel = ViewModelProvider(this, gistViewModelFactory).get(GistViewModel::class.java)
        viewModel.postGistValue(requireArguments().get(Constants.SINGLE_GIST_MODEL_KEY) as GistResponseModel)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = GistFragmRouter(fragment = this)
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                initViews()
            }

            fbDeleteGist.setOnClickListener {
//                checkIfPassIsSet()
//                    router.routeToPassCheck(Gist_String)
//
//                    flag = requireArguments().get(Constants.CHECK_FRAGM_KEY) as Boolean
//
//
//                    if (flag == true) {
                        alertDialogDelete()
//
//                    }
            }


            fbEditGist.setOnClickListener {
                setEditingForm()
            }

            fbConfirmAdding.setOnClickListener {
                editGist()
                hideEditingForm()
            }
            fbDeclineAdding.setOnClickListener {
                hideEditingForm()
            }
            fbShareGist.setOnClickListener {
//                checkIfPassIsSet()
//                    flag = requireArguments().get(Constants.CHECK_FRAGM_KEY) as Boolean
//                    if (flag == true) {
                        intentMessageTelegram(viewModel.gistFilesModel.value?.files?.filename?.content)
//                    }
            }
        }
    }


    private fun initViews() {
        viewModel.gistFilesModel.observe(viewLifecycleOwner, { gistModel ->
            etGistName.setText(gistModel.files.filename.gistName, TextView.BufferType.EDITABLE)
            etGistContent.setText(gistModel.files.filename.content, TextView.BufferType.EDITABLE)
            etGistDesc.setText(gistModel.description, TextView.BufferType.EDITABLE)
        })
    }

    private fun deleteGist() {
        GlobalScope.launch {
            val bool = viewModel.deleteGist()
            successfulRouteFun(bool, R.string.success_delete)

        }
    }

    private fun editGist() {
        if (etGistContent.text.isNotEmpty() && etGistName.text.isNotEmpty() && etGistDesc.text.isNotEmpty()) {
            val filesModel = FilesPostModel(
                description = etGistDesc.text.toString(),
                files = FilesObj(
                    filerandomname = FilesInnerModel(
                        content = etGistContent.text.toString(),
                        filename = etGistName.text.toString()
                    )
                )
            )
            lifecycleScope.launch {
                val bool = viewModel.editGist(filesModel)
                withContext(Dispatchers.Main) {
                    successfulRouteFun(bool, R.string.successful_edit)
                }
            }
        } else {
            Toast.makeText(context, R.string.not_enough_params, Toast.LENGTH_SHORT).show()
            return
        }


    }

    private fun successfulRouteFun(checkBoolean: Boolean, message: Int) {
        if (checkBoolean)
            lifecycleScope.launch {
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

    fun intentMessageTelegram(msg: String?) {
        val appName = "org.telegram.messenger"
        try {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            myIntent.setPackage(appName)
            myIntent.putExtra(Intent.EXTRA_TEXT, msg) //
            context?.startActivity(Intent.createChooser(myIntent, "Share with"))
        } catch (e: Exception) {
            Toast.makeText(context, R.string.telegram_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    fun checkIfPassIsSet() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (viewModel.checkIfPasswordIsSet(requireContext())) {
                    withContext(Dispatchers.Main) {
                        router.routeToPassCheck(Gist_String)
//                        flag = requireArguments().get(Constants.CHECK_FRAGM_KEY) as Boolean
//                        if (flag == true) {
//                            alertDialogDelete()
//                        }
//                        else {
//                            alertDialogDelete()
//                        }
                    }
                }
            }
        }
    }
}
