package pillowisgod.com.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(PasswordEntity::class), version = 1)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao() : PasswordDao
}