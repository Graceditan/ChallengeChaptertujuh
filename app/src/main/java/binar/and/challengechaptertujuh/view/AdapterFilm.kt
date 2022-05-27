package binar.and.challengechaptertujuh.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.model.GetAllFilmItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_film_adapter.view.*


class AdapterFilm (private var onClick : (GetAllFilmItem)->Unit) : RecyclerView.Adapter<AdapterFilm.ViewHolder>() {

    private var dataFilm : List<GetAllFilmItem>? = null

    fun setDataFilm(film : List<GetAllFilmItem>){
        this.dataFilm = film

    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFilm.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_film_adapter, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AdapterFilm.ViewHolder, position: Int) {
        holder.itemView.title_film.text = dataFilm!![position].name
        holder.itemView.date_film.text = dataFilm!![position].date
        holder.itemView.author_film.text = dataFilm!![position].director
        Glide.with(holder.itemView.context).load(dataFilm!![position].image).apply(
            RequestOptions().override(120, 120)).into(holder.itemView.image_film)

        holder.itemView.cardFilm.setOnClickListener{
            onClick(dataFilm!![position])
        }

    }

    override fun getItemCount(): Int {
        if (dataFilm == null){
            return 0
        }else{
            return dataFilm!!.size

        }
    }
}