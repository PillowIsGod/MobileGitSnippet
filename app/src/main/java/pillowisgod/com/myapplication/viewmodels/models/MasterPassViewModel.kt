package pillowisgod.com.myapplication.viewmodels.models


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pillowisgod.com.myapplication.db.PasswordDao
import pillowisgod.com.myapplication.db.PasswordEntity
import javax.inject.Inject

@HiltViewModel
class MasterPassViewModel @Inject constructor(private val roomDao: PasswordDao) : ViewModel() {




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