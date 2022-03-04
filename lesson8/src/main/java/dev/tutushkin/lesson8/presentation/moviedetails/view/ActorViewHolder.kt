package dev.tutushkin.lesson8.presentation.moviedetails.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.data.movies.local.ActorEntity

class ActorViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val photo: ImageView = view.findViewById(R.id.view_holder_actor_photo_image)
    private val name: TextView = view.findViewById(R.id.view_holder_actor_name_text)

    fun onBind(actor: ActorEntity) {
        name.text = actor.name
        Glide.with(view.context)
            .load(actor.photo)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners((4 * view.context.resources.displayMetrics.density).toInt())
                )
            )
            .into(photo)
    }
}
