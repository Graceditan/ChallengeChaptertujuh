package binar.and.challengechaptertujuh.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.UserManager
import binar.and.challengechaptertujuh.data.FavoriteDataBase
import binar.and.challengechaptertujuh.data.FavoriteDataClass
import binar.and.challengechaptertujuh.view.AdapterFilm
import binar.and.challengechaptertujuh.viewmodel.viewmodelfilm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_film_adapter.*

class HomeFragment : Fragment() {

    lateinit var usermanager : UserManager

//    var db: FavoriteDataBase? = null
//    var film : FavoriteDataClass? = null
//    lateinit var email : String
    lateinit var adapterfilm : AdapterFilm
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usermanager = UserManager(requireContext())
        usermanager.userUsername.asLiveData().observe(viewLifecycleOwner,{
            home_username.text = "Welcome, $it"
        })

        home_profile.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        view.rv_homefilm.layoutManager = LinearLayoutManager(requireContext())
        adapterfilm = AdapterFilm(){
            val bund = Bundle()

            bund.putParcelable("detailfilm", it)
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)

        }
        view.rv_homefilm.adapter = adapterfilm

        //  val username = home.getString("username", "")
        // view.welcome.text = username

        getDataFilm()

        view.home_profile.setOnClickListener {

            view.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        view.home_to_favorite.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

    }
    fun getDataFilm(){
        val filmadapter = AdapterFilm(){}

        rv_homefilm.layoutManager = LinearLayoutManager(requireContext())
        rv_homefilm.adapter = AdapterFilm(){}

        val viewmodle = ViewModelProvider(this).get(viewmodelfilm::class .java)
        viewmodle.film.observe(requireActivity(),{

            if (it != null)
                filmadapter.setDataFilm(it)
            filmadapter.notifyDataSetChanged()


        })

    }





}




