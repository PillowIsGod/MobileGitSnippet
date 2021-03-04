package pillowisgod.com.myapplication.data.repositories.model.loginmodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuccessfulResponseModel(
    @SerializedName("login")
    val login : String,
    @SerializedName("id")
    val id : Int,
    @SerializedName("avatar_url")
    val avatarUrl : String,
    @SerializedName("url")
    val profileUrl : String,
    @SerializedName("gists_url")
    val gistsUrl : String
) : Parcelable
