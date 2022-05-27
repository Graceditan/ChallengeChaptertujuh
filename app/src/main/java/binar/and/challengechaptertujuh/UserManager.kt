package binar.and.challengechaptertujuh

import android.content.Context

import androidx.datastore.DataStore

import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class UserManager (context: Context) {

    private val dataStore: DataStore<androidx.datastore.preferences.Preferences> =
        context.createDataStore("user")
    private val loginDS: DataStore<androidx.datastore.preferences.Preferences> =
        context.createDataStore("login")

    companion object {

        val ID = preferencesKey<String>("id_user")
        val EMAIL = preferencesKey<String>("email_user")
        val USERNAME = preferencesKey<String>("username_user")
        val NAMA = preferencesKey<String>("nama_user")
        val TGLHR = preferencesKey<String>("tglhr_user")
        val ALAMAT = preferencesKey<String>("alamat_user")
        val LOGINSTATE = preferencesKey<String>("login_user")
    }


    suspend fun saveDataUser(
        id: String,
        email: String,
        username: String,
        nama: String,
        tglhr: String,
        alamat: String
    ) {
        dataStore.edit {
            it[ID] = id
            it[USERNAME] = username
            it[EMAIL] = email
            it[NAMA] = nama
            it[TGLHR] = tglhr
            it[ALAMAT] = alamat
        }
    }


    suspend fun saveDataLogin(login: String) {
        loginDS.edit {
            it[LOGINSTATE] = login
        }
    }

    suspend fun deleteDataLogin() {
        loginDS.edit {
            it.clear()
        }
    }

    val userID: Flow<String> = dataStore.data.map {
        it[ID] ?: ""
    }

    val userUsername: Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val userEmail: Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val userNama: Flow<String> = dataStore.data.map {
        it[NAMA] ?: ""
    }

    val userTGLHR: Flow<String> = dataStore.data.map {
        it[TGLHR] ?: ""
    }

    val userAlamat: Flow<String> = dataStore.data.map {
        it[ALAMAT] ?: ""
    }

    val userLogin: Flow<String> = loginDS.data.map {
        it[LOGINSTATE] ?: "false"
    }
}
//class UserManager(context: Context) {
//    private val imagedata: DataStore<Preferences> = context.createDataStore(name = "image_prefs")
//    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_login")
//    val IMAGE  = preferencesKey<String>("USER_IMAGE")
//
//    suspend fun saveData(
//        username: String,
//        password: String,
//        nama: String,
//        umur: String,
//        image: String,
//        address: String
//    ){
//        dataStore.edit {
//            it[user] = username
//            it[pass] = password
//            it[Nama] = nama
//            it[Umur] = umur
//            it[Image] = image
//            it[Address] = address
//            it[Id] = userId.toString()
//
//        }
//    }
//
//
//    val userUsername : Flow<String> = dataStore.data.map {
//        it[user]?: ""
//    }
//    val userPassword : Flow<String> = dataStore.data.map {
//        it[pass]?: ""
//    }
//    val userNama : Flow<String> = dataStore.data.map {
//        it[Nama]?: ""
//    }
//    val userUmur : Flow<String> = dataStore.data.map {
//        it[Umur]?: ""
//    }
//    val userImage : Flow<String> = dataStore.data.map {
//        it[Image]?: ""
//    }
//    val userAddress : Flow<String> = dataStore.data.map {
//        it[Address]?: ""
//    }
//    val userId: Flow<String> = dataStore.data.map {
//        it[Id]?: ""
//    }
//    companion object {
//        val user = preferencesKey<String>("user_username")
//        val pass = preferencesKey<String>("user_pass")
//        val Nama = preferencesKey<String>("user_nama")
//        val Umur = preferencesKey<String>("user_umur")
//        val Image = preferencesKey<String>("user_image")
//        val Address = preferencesKey<String>("user_address")
//        val Id = preferencesKey<String>("user_id")
//    }
//    suspend fun saveDataImage(image: String) {
//        imagedata.edit {
//            it[IMAGE] = image
//
//        }
//    }
//}