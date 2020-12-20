package dev.tutushkin.lesson5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.tutushkin.lesson5.data.Actor

class ActorsAdapter(
    private val actors: List<Actor>
) : RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    class ActorViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val photo: ImageView = view.findViewById(R.id.view_holder_actor_photo_image)
        private val name: TextView = view.findViewById(R.id.view_holder_actor_name_text)

        fun onBind(actor: Actor) {
            name.text = actor.name
            photo.setImageResource(actor.photo)
        }
    }

}
