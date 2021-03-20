package pillowisgod.com.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_master_pass_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pillowisgod.com.myapplication.MainApp
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.loginmodels.SuccessfulResponseModel
import pillowisgod.com.myapplication.db.PasswordDao
import pillowisgod.com.myapplication.db.PasswordDatabase
import pillowisgod.com.myapplication.db.PasswordEntity
import pillowisgod.com.myapplication.helpers.Constants
import pillowisgod.com.myapplication.routers.DialogRouter
import pillowisgod.com.myapplication.viewmodels.factories.MasterPassViewModelFactory
import pillowisgod.com.myapplication.viewmodels.models.MasterPassViewModel
import javax.inject.Inject


class MasterPassDialogFragment : DialogFragment() {

    private lateinit var router: DialogRouter
    private lateinit var model : SuccessfulResponseModel
    private lateinit var viewModel : MasterPassViewModel
    @Inject lateinit var passwordDao: PasswordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        model = requireArguments().get(Constants.MODEL_KEY) as SuccessfulResponseModel
        val viewModelFactory = MasterPassViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MasterPassViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_master_pass_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = DialogRouter(fragment = this)

        btnDeclineSettingMP.setOnClickListener {
            router.routeToProfile(model)
        }
        btnConfirmMasterPassword.setOnClickListener {
            insertPassword()
            router.routeToProfile(model)
        }
    }


    fun insertPassword() {
        if(etSetMasterPassword.text.isNotEmpty()) {
            GlobalScope.launch {
                val passwordEntity = PasswordEntity(id = 1, password = etSetMasterPassword.text.toString())
                viewModel.insertPass(roomDao = passwordDao, passwordEntity)
            }

        }

    }

}