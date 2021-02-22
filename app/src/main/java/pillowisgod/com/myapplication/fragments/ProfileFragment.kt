package pillowisgod.com.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.helpers.Constants.MODEL_KEY


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments()
            .get(MODEL_KEY)?.let {
                Log.e("TAG", "Profile Fragment data -> $it")
            }

    }
}