package binar.and.challengechaptertujuh.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [FavoriteDataClass::class],
    version = 4
)
abstract class FavoriteDataBase : RoomDatabase(){

    abstract fun getFavorite(): FavoriteDao

    companion object{
        private var getFavDB : FavoriteDataBase? = null

        fun getInstance(context: Context) : FavoriteDataBase?{
            if (getFavDB == null) {
                kotlin.synchronized(FavoriteDataBase::class) {
                    getFavDB = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDataBase::class.java,
                        "Favorite.db"
                    ).build()
                }
            }
            return getFavDB
        }

        fun destroy(){
            getFavDB = null
        }
    }
}