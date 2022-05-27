package binar.and.challengechaptertujuh.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.UserManager
import binar.and.challengechaptertujuh.data.FavoriteDataBase
import binar.and.challengechaptertujuh.data.FavoriteDataClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mumtaz.binar.challangechapterenam.adapter.AdapterFavorite

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    lateinit var userManager: UserManager
    lateinit var adapterfav : AdapterFavorite
    var database : FavoriteDataBase? = null
    var film : FavoriteDataClass? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favorite, container, false)
        database = FavoriteDataBase.getInstance(requireContext())
        GlobalScope.async {
            film = database?.getFavorite()?.getFilmID(id.toInt())!!
            database?.getFavorite()?.getAllFav()
        }
        userManager = UserManager(requireContext())
        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.favorite_username.text = "Hi $it"
        }
        view.favoritehome.setOnClickListener {
            view.findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
        view.favoriteprofile.setOnClickListener {
            view.findNavController().navigate(R.id.action_favoriteFragment_to_profileFragment)
        }

        view.rv_favorite_film.layoutManager = LinearLayoutManager(requireContext())
        GlobalScope.launch {
            val listdata = database?.getFavorite()?.getAllFav()
            requireActivity().runOnUiThread {
                listdata.let {
                    if (listdata?.size == 0) {
                        checkdatafavorite.text = "Tambah film favorite mu"
                    }
                    adapterfav = AdapterFavorite(){
                        val bund = Bundle()
                        bund.putParcelable("detailfilmfavorite", it)
                        view.findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment,bund)
                    }
                    view.rv_favorite_film.adapter = adapterfav
                    adapterfav.setDataFav(it!!)
                }
            }
        }
        return view
    }


}