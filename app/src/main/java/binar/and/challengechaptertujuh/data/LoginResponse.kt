package binar.and.challengechaptertujuh.data

import binar.and.challengechaptertujuh.model.GetUserItem
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("responseuser")
    val responseuser: GetUserItem
)
