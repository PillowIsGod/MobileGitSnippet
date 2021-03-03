package pillowisgod.com.myapplication.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pillowisgod.com.myapplication.viewmodels.models.GistViewModel
import pillowisgod.com.myapplication.viewmodels.models.LoginViewModel
import pillowisgod.com.myapplication.viewmodels.models.ProfileViewModel

class GistViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GistViewModel() as T
    }
}