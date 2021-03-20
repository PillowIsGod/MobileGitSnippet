package pillowisgod.com.myapplication.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pillowisgod.com.myapplication.db.PasswordDao
import pillowisgod.com.myapplication.db.PasswordDatabase
import pillowisgod.com.myapplication.di.AppScope
import javax.inject.Singleton


@Module
class RoomModule() {


    @AppScope
    @Provides
    fun provideDatabase(context: Context) : PasswordDatabase {
        return Room.databaseBuilder(
            context,
            PasswordDatabase::class.java,
            "masterpass"
        ).build()
    }

    @AppScope
    @Provides
    fun providePasswordDao(passwordDatabase: PasswordDatabase) : PasswordDao {
        return passwordDatabase.passwordDao()
    }
}