package pillowisgod.com.myapplication.viewmodels.adapters

import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import pillowisgod.com.myapplication.data.repositories.model.FilesRemoteModel
import pillowisgod.com.myapplication.data.repositories.model.GistResponseModel
import pillowisgod.com.myapplication.data.repositories.model.GistsModel

class CustomGsonAdapter : TypeAdapter<FilesRemoteModel>() {
    override fun write(out: JsonWriter?, value: FilesRemoteModel?) {
        TODO("Not yet implemented")
    }

    override fun read(reader: JsonReader?): FilesRemoteModel {
        var gistName : String? = null
        var gistLang = null
        var rawUrl : String? = null
        var type : String? = null
        var size : Int? = null
        var trunc : Boolean? = null
        var content : String? = null
        reader?.beginObject()
        while (reader?.hasNext()!!) {
            var s = reader.nextName()
            reader.beginObject()
            while (reader.hasNext()) {
                var str = reader.nextName()
                if (str.equals("filename")) {
                    val nextstr = reader.nextString()
                    gistName = nextstr
                }
                if (str.equals("language")) {
                        val nextstr1 = reader.nextNull()
                }
                if (str.equals("raw_url")) {
                    val nextstr2 = reader.nextString()
                    rawUrl = nextstr2
                }
                if (str.equals("type")) {
                    val nextstr3 = reader.nextString()
                    type = nextstr3
                }
                if (str.equals("size")) {
                    val nextint = reader.nextInt()
                    size = nextint
                }
                if(str.equals("truncated")) {
                    val nextbool = reader.nextBoolean()
                    trunc = nextbool
                }
                if(str.equals("content"))  {
                    val nextStr4 = reader.nextString()
                    content = nextStr4
                }
            }
            reader.endObject()
            }
        reader.endObject()
        val resp = FilesRemoteModel(
            GistsModel(gistName = gistName!!, gistLang = gistLang, rawUrl = rawUrl!!, type = type!!,
        size = size!!, truncated = trunc!!, content = content!!)
        )
        return resp!!
    }
}