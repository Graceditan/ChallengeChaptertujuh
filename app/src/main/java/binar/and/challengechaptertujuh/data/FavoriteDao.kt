package binar.and.challengechaptertujuh.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface FavoriteDao {
    @Insert
    fun addFilm (favoriteFilm : FavoriteDataClass) : Long
    @Delete()
    fun deleteFav(favoriteFilm: FavoriteDataClass ):Int
    @Query("SELECT * FROM Favorite WHERE Favorite.id = :id")
    fun getFilmID(id:Int): FavoriteDataClass
    @Query("SELECT *  FROM Favorite")
    fun getAllFav(): List<FavoriteDataClass>
}
