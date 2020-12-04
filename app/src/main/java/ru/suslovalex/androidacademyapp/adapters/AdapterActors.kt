package ru.suslovalex.androidacademyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.model.Actor

class AdapterActors: RecyclerView.Adapter<ActorViewHolder>() {

    private var actors: List<Actor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    fun setActors(newActors: List<Actor>){
        actors = newActors
    }
}

class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val image: ImageView = itemView.findViewById(R.id.img_actor)
    private val name: TextView = itemView.findViewById(R.id.actor_name)

    fun onBind(actor: Actor){
        Glide.with(itemView).load(actor.image).into(image)
        name.text = actor.name
    }
}