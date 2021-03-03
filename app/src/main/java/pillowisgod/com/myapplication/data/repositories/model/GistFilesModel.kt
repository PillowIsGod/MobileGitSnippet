package pillowisgod.com.myapplication.data.repositories.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GistFilesModel (
    @SerializedName("description")
    var description : String,
    @SerializedName("created_at")
    val date : String,
    @SerializedName("files")
    val files : FilesRemoteModel
        ) : Parcelable