package pillowisgod.com.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "masterpass")
data class PasswordEntity(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "pass") val password : String
            )