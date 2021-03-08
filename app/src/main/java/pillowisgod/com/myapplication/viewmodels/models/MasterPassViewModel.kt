package pillowisgod.com.myapplication.viewmodels.models

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.room.Room
import pillowisgod.com.myapplication.db.PasswordDatabase
import pillowisgod.com.myapplication.db.PasswordEntity

class MasterPassViewModel(private val context : Context) : ViewModel() {

    private val roomdb = Room.databaseBuilder(
        context,
        PasswordDatabase::class.java,
        "masterpass"
    ).build()
    private val roomDao = roomdb.passwordDao()


    suspend fun checkIfPasswordCorrect(passToCheck : String) : Boolean {
        val dbPass = roomDao.getAll()
        if (passToCheck.equals(dbPass[0].password)) {
            return true
        }
        return false
    }

    suspend fun insertPass(passwordEntity : PasswordEntity) {
        checkIfEmpty()
        roomDao.insertAll(passwordEntity)
    }



    suspend fun checkIfEmpty() {
        val data = roomDao.getAll()
        if(data.size > 0) {
            roomDao.deletePassword(data[0])
        }
    }
}