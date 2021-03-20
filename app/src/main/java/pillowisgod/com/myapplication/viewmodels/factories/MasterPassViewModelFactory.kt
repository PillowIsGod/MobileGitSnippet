package pillowisgod.com.myapplication.viewmodels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pillowisgod.com.myapplication.viewmodels.models.LoginViewModel
import pillowisgod.com.myapplication.viewmodels.models.MasterPassViewModel

class MasterPassViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MasterPassViewModel() as T
        }
    }
