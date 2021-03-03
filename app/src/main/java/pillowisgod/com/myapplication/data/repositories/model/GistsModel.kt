package pillowisgod.com.myapplication.data.repositories.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GistsModel (
    @SerializedName("filename")
    var gistName : String,
    @SerializedName("language")
    var gistLang : String?,
    @SerializedName("raw_url")
    var rawUrl : String,
    @SerializedName("type")
    var type : String,
    @SerializedName("size")
    var size : Int,
    @SerializedName("truncated")
    val truncated : Boolean,
    @SerializedName("content")
    var content : String
        ) : Parcelable