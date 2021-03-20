package pillowisgod.com.myapplication.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pillowisgod.com.myapplication.fragments.*

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun masterPassDialogFragment() : MasterPassDialogFragment
    @ContributesAndroidInjector
    abstract fun passCheckFragment() : PassCheckFragment
    @ContributesAndroidInjector
    abstract fun gistFragment() : GistFragment
    @ContributesAndroidInjector
    abstract fun loginFragment() : LoginFragment
    @ContributesAndroidInjector
    abstract fun gistAddFragment() : GistAddFragm
    @ContributesAndroidInjector
    abstract fun profileFragment() : ProfileFragment
}