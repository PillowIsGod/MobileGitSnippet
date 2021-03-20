package pillowisgod.com.myapplication.helpers

import pillowisgod.com.myapplication.data.repositories.model.loginmodels.AccessToken

object Constants {
    const val GIT_LOGIN_CALL = "https://github.com/login/oauth/authorize"
    const val BASE_URL = "https://github.com/"
    const val BASE_API_URL = "https://api.github.com/"
    const val CLIENT_ID = "1fc241458a24f34b16c2"
    const val CLIENT_SECRET = "de95b8959d7fd07faf8e9a84813bef3e3efe9c59"
    const val CALLBACK_URL = "mobilegitschema://mobilegitsnippet.com"
    const val LOGIN_URL = "$GIT_LOGIN_CALL?client_id=$CLIENT_ID&scope=repo,gist&redirect_uri=$CALLBACK_URL"
    const val MODEL_KEY = "ModelKey18asd3377"
    const val GIST_LIST_MODEL_KEY = "GistListModelKey1834134123412"
    const val SINGLE_GIST_MODEL_KEY = "SingleGistModelKey1099u985818593"
    const val CHECK_FRAGM_KEY = "Checksonasd1q23129312931"
    const val STRING_KEY = "STRINGasdaskkEyasdazz111"
    const val Gist_String = "GistFragment"
    const val Gist_Add_String = "GistAddFragment"
    var accessToken: AccessToken? = null
}