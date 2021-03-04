package pillowisgod.com.myapplication.data.repositories.model.getmodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GistResponseModel (
    @SerializedName("url")
    val url : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("created_at")
    val date : String
        ) : Parcelable