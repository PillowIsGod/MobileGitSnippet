package pillowisgod.com.myapplication.viewmodels.models

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.room.Room
import pillowisgod.com.myapplication.db.PasswordDao
import pillowisgod.com.myapplication.db.PasswordDatabase
import pillowisgod.com.myapplication.db.PasswordEntity

class MasterPassViewModel() : ViewModel() {




    suspend fun checkIfPasswordCorrect(roomDao: PasswordDao, passToCheck : String) : Boolean {
        val dbPass = roomDao.getAll()
        if (passToCheck.equals(dbPass[0].password)) {
            return true
        }
        return false
    }

    suspend fun insertPass(roomDao: PasswordDao, passwordEntity : PasswordEntity) {
        checkIfEmpty(roomDao = roomDao)
        roomDao.insertAll(passwordEntity)
    }



    suspend fun checkIfEmpty(roomDao: PasswordDao) {
        val data = roomDao.getAll()
        if(data.size > 0) {
            roomDao.deletePassword(data[0])
        }
    }
}