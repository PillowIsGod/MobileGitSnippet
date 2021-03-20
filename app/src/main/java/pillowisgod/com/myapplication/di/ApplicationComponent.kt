package pillowisgod.com.myapplication.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import pillowisgod.com.myapplication.MainActivity
import pillowisgod.com.myapplication.MainApp
import pillowisgod.com.myapplication.di.modules.ActivityBindingModule
import pillowisgod.com.myapplication.di.modules.FragmentBindingModule
import pillowisgod.com.myapplication.di.modules.RoomModule
import pillowisgod.com.myapplication.fragments.MasterPassDialogFragment
import pillowisgod.com.myapplication.fragments.PassCheckFragment


@Component(modules = [RoomModule::class,
AndroidInjectionModule::class,
ActivityBindingModule::class,
FragmentBindingModule::class])
@AppScope
interface ApplicationComponent : AndroidInjector<MainApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application : Application): ApplicationComponent.Builder
        @BindsInstance
        fun context(context: Context) : ApplicationComponent.Builder

        fun build() : ApplicationComponent
    }
}