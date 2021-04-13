package pillowisgod.com.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.android.support.AndroidSupportInjection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pass_check.*
import kotlinx.coroutines.*
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.helpers.Constants.Gist_String
import pillowisgod.com.myapplication.helpers.Constants.STRING_KEY
import pillowisgod.com.myapplication.routers.DialogRouter
import pillowisgod.com.myapplication.viewmodels.models.MasterPassViewModel

@AndroidEntryPoint
class PassCheckFragment : DialogFragment() {

    private lateinit var router: DialogRouter
    private val viewModel : MasterPassViewModel by viewModels()
    private lateinit var string : String


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        string = requireArguments().get(STRING_KEY) as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pass_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = DialogRouter(fragment = this)
        btnConfirmCheck.setOnClickListener {
            if(string.equals(Gist_String)) {
                GlobalScope.launch {
                    val bool = viewModel.checkIfPasswordCorrect(etCheckMasterPassword.text.toString())
                    withContext(Dispatchers.Main) {
                        router.routeToGistFragm(bool)
                    }

                }

            }
            else {
                GlobalScope.launch {
                    val bool = viewModel.checkIfPasswordCorrect(etCheckMasterPassword.text.toString())
                    withContext(Dispatchers.Main) {
                        router.routeToGistAdd(bool)
                    }
                }
            }
        }
        btnDeclineCheck.setOnClickListener {
            if(string.equals(Gist_String)) {
                router.routeToGistFragm(false)
            }
            else {
                router.routeToGistAdd(false)
            }
        }
    }

}