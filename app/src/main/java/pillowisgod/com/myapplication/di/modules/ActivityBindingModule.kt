package pillowisgod.com.myapplication.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pillowisgod.com.myapplication.MainActivity


@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun mainActivity() : MainActivity
}