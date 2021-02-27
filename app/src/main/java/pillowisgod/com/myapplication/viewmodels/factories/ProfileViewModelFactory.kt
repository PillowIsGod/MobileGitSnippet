package pillowisgod.com.myapplication.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pillowisgod.com.myapplication.viewmodels.models.LoginViewModel
import pillowisgod.com.myapplication.viewmodels.models.ProfileViewModel

class ProfileViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel() as T
    }
}