package binar.and.challengechaptertujuh.view.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import binar.and.challengechaptertujuh.R

class SplashFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_splashk, container, false)

        Handler().postDelayed({
            Navigation.findNavController(v)
                .navigate(R.id.action_splashkFragment_to_loginFragment)
        }, 3000)

        return v
    }
}