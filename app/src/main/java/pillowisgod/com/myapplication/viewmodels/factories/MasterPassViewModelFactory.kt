package pillowisgod.com.myapplication.viewmodels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pillowisgod.com.myapplication.viewmodels.models.LoginViewModel
import pillowisgod.com.myapplication.viewmodels.models.MasterPassViewModel

class MasterPassViewModelFactory(val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MasterPassViewModel(context = context) as T
        }
    }
