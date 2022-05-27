package binar.and.challengechaptertujuh.network

import binar.and.challengechaptertujuh.data.RegisterResponse
import binar.and.challengechaptertujuh.model.GetAllFilmItem
import binar.and.challengechaptertujuh.model.GetUserItem
import mumtaz.binar.challangechapterenam.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @GET("apiuser.php")
    fun getAllUser(): Call<List<GetUserItem>>


    @POST("updateuser.php")
    @FormUrlEncoded
    fun updateUser(
        @Field("id") id: Int,
        @Field("username") username: String,
        @Field("complete_name") completename: String,
        @Field("address") address: String,
        @Field("dateofbirth") dateofbirth: String,
    ): Call<List<GetUserItem>>


    @GET("film")
    suspend fun getAllfilm(): List<GetAllFilmItem>
    @POST("register.php/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,

        ): Call<RegisterResponse>
    @POST("login.php")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>


}