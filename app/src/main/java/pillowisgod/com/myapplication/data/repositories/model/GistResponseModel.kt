package pillowisgod.com.myapplication.data.repositories.model

import com.google.gson.annotations.SerializedName

data class GistResponseModel (
    @SerializedName("files")
    val files : FilesRemoteModel,
    @SerializedName("description")
    val description : String
        )