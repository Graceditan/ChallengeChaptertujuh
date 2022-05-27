package mumtaz.binar.challangechapterenam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.data.FavoriteDataClass
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_favorite.view.*


class AdapterFavorite (var onclick : (FavoriteDataClass)-> Unit) : RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    var datafavorite : List<FavoriteDataClass>? = null

    fun setDataFav(favorite  : List<FavoriteDataClass>){
        this.datafavorite = favorite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(datafavorite!![position].image).into(holder.itemView.favoriteimage)

        holder.itemView.favorite_tv1.text = datafavorite!![position].title
        holder.itemView.favorite_tv2.text = datafavorite!![position].director
        holder.itemView.favorite_tv3.text = datafavorite!![position].releaseDate

        holder.itemView.cardfilmfavorite.setOnClickListener {
            onclick(datafavorite!![position])
        }
    }

    override fun getItemCount(): Int {
        if (datafavorite == null){
            return 0
        }else{
            return datafavorite!!.size
        }
    }
}