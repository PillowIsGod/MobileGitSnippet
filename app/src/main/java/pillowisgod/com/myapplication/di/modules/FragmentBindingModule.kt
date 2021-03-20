package pillowisgod.com.myapplication.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pillowisgod.com.myapplication.fragments.GistFragment
import pillowisgod.com.myapplication.fragments.MasterPassDialogFragment
import pillowisgod.com.myapplication.fragments.PassCheckFragment

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun masterPassDialogFragment() : MasterPassDialogFragment
    @ContributesAndroidInjector
    abstract fun passCheckFragment() : PassCheckFragment
    @ContributesAndroidInjector
    abstract fun gistFragment() : GistFragment
}