package pillowisgod.com.myapplication.data.repositories.model.getmodels

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import kotlinx.android.parcel.Parcelize
import pillowisgod.com.myapplication.adapters.CustomGsonAdapter

@Parcelize
@JsonAdapter(CustomGsonAdapter::class)
data class FilesRemoteModel (
    @Expose(serialize = true)
    var filename : GistsModel
        ) : Parcelable