package binar.and.challengechaptertujuh.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.data.FavoriteDataBase
import binar.and.challengechaptertujuh.data.FavoriteDataClass
import binar.and.challengechaptertujuh.model.GetAllFilmItem
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {
    lateinit var favorite : String
    var database : FavoriteDataBase? = null
    var filmfav : FavoriteDataClass? = null

    lateinit var id : String
    lateinit var title : String
    lateinit var director : String
    lateinit var createdAt : String
    lateinit var synopsis : String
    lateinit var image: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_detail, container, false)
        val getfilm = arguments?.getParcelable<GetAllFilmItem>("detailfilm")
        val getfavfilm = arguments?.getParcelable<FavoriteDataClass>("detailfilmfavorite")
        database = FavoriteDataBase.getInstance(requireActivity())

        if (getfilm != null){
            view.detail1.text = getfilm?.name
            view.detail2.text = getfilm?.director
            view.detail3.text = getfilm?.date
            view.detail4.text = getfilm?.description
            Glide.with(requireContext()).load(getfilm?.image).into(view.setailgambar)
            id = getfilm.id
            title = getfilm.name
            director = getfilm.director
            createdAt = getfilm.director
            synopsis = getfilm.description
            image = getfilm.image
        }

        if (getfavfilm != null){
            view.detail1.text = getfavfilm?.title
            view.detail2.text = getfavfilm?.director
            view.detail3.text = getfavfilm?.createdAt
            view.detail4.text = getfavfilm?.synopsis
            Glide.with(requireContext()).load(getfavfilm?.image).into(view.setailgambar)
            id = getfavfilm.id.toString()
            title = getfavfilm.title
            director = getfavfilm.director
            createdAt = getfavfilm.createdAt
            synopsis = getfavfilm.synopsis
            image = getfavfilm.image
        }

        favorite = "false"
        GlobalScope.launch {
            filmfav = database?.getFavorite()?.getFilmID(id.toInt())

            if (filmfav?.isfav == "true"){
                btn_detailfavorite.setImageResource(R.drawable.ic_fav)
                favorite = "true"
            }

            if (filmfav?.isfav == "false"){
                btn_detailfavorite.setImageResource(R.drawable.ic_fav)
                favorite = "false"
            }
        }

        view.btn_detailfavorite.setOnClickListener {
            for (data in favorite){
                if (favorite == "true"){
                    btn_detailfavorite.setImageResource(R.drawable.ic_fav)
                    favorite = "false"
                    GlobalScope.async {
                        database?.getFavorite()?.deleteFav(
                            FavoriteDataClass(createdAt,
                                director,
                                id.toInt(),
                                image,
                                "",
                                synopsis,
                                title,
                                "false")
                        )
                    }
                    break
                }


                if (favorite == "false"){
                    btn_detailfavorite.setImageResource(R.drawable.ic_fav)
                    favorite = "true"
                    GlobalScope.launch {
                        database?.getFavorite()?.addFilm(
                            FavoriteDataClass(
                                createdAt,
                                director,
                                id.toInt(),
                                image,
                                "",
                                synopsis,
                                title,
                                "true"
                            )
                        )
                    }
                    break
                }
            }


        }
        return view
    }


}
//class DetailFragment : Fragment() {
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_detail, container, false)
//        val getfilm = arguments?.getParcelable<GetAllFilmItem>("detailfilm")
//
//        view.detail1.text = getfilm?.name
//        view.detail2.text = getfilm?.director
//        view.detail3.text = getfilm?.date
//        view.detail4.text = getfilm?.description
//
//        Glide.with(requireContext()).load(getfilm?.image).into(view.setailgambar)
//
//
//
//        return view
//    }
//
//}
