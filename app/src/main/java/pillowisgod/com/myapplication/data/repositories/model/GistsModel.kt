package pillowisgod.com.myapplication.data.repositories.model

import com.google.gson.annotations.SerializedName

data class GistsModel (
    @SerializedName("filename")
    val gistName : String,
    @SerializedName("language")
    val gistLang : String?,
    @SerializedName("raw_url")
    val rawUrl : String,
    @SerializedName("type")
    val type : String,
    @SerializedName("size")
    val size : Int
        )