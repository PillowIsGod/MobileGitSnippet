package pillowisgod.com.myapplication

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import pillowisgod.com.myapplication.di.ApplicationComponent
import pillowisgod.com.myapplication.di.DaggerApplicationComponent
import pillowisgod.com.myapplication.di.modules.RoomModule
import javax.inject.Inject

class MainApp : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        val applicationComponent = DaggerApplicationComponent
            .builder()
            .application(application = this)
            .context(context = applicationContext)
            .build()
        applicationComponent.inject(this)

    }




}