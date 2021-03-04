package pillowisgod.com.myapplication.data.repositories.model

import com.google.gson.annotations.SerializedName

data class FilesPostModel (
    @SerializedName("description")
    val description : String,
    @SerializedName("files")
    val files : FilesObj
        )

data class FilesObj (
    val filerandomname : FilesInnerModel
        )

data class FilesInnerModel (
    @SerializedName("content")
    var content : String,
    @SerializedName("filename")
    var filename : String
        )
