package pillowisgod.com.myapplication.db

import androidx.room.*


@Dao
interface PasswordDao {
    @Query("SELECT * FROM masterpass")
    fun getAll() : List<PasswordEntity>
    @Delete
    fun deletePassword(passwordEntity: PasswordEntity)
    @Insert
    fun insertAll(vararg passwordEntity: PasswordEntity)
    @Update
    fun updatePassword(passwordEntity: PasswordEntity)
}