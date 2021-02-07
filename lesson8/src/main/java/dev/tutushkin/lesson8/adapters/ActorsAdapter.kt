package dev.tutushkin.lesson8.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.data.ActorEntity

class ActorsAdapter(
    private val actors: List<ActorEntity>
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

        fun onBind(actor: ActorEntity) {
            name.text = actor.name
            Glide.with(view.context)
                .load(actor.picture)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners((4 * view.context.resources.displayMetrics.density).toInt())))
                .into(photo)
        }
    }

}
