package pillowisgod.com.myapplication.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pillowisgod.com.myapplication.db.PasswordDao
import pillowisgod.com.myapplication.db.PasswordDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule() {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : PasswordDatabase {
        return Room.databaseBuilder(
            context,
            PasswordDatabase::class.java,
            "masterpass"
        ).build()
    }

    @Provides
    @Singleton
    fun providePasswordDao(passwordDatabase: PasswordDatabase) : PasswordDao {
        return passwordDatabase.passwordDao()
    }
}