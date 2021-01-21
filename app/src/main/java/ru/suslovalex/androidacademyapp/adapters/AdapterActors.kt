package ru.suslovalex.androidacademyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.data.Actor


class AdapterActors: ListAdapter<Actor,ActorViewHolder>(ActorsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val image: ImageView = itemView.findViewById(R.id.img_actor)
    private val name: TextView = itemView.findViewById(R.id.actor_name)

    fun onBind(actor: Actor){
        Glide.with(itemView)
            .load(actor.actorImage)
            .placeholder(R.drawable.ic_unknown_img_artist)
            .into(image)
        name.text = actor.name
    }
}

private class ActorsDiffUtil: DiffUtil.ItemCallback<Actor>(){
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }

}