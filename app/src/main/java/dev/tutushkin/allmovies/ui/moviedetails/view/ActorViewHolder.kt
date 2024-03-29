package dev.tutushkin.allmovies.ui.moviedetails.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.tutushkin.allmovies.R
import dev.tutushkin.allmovies.databinding.ViewHolderActorBinding
import dev.tutushkin.allmovies.domain.movies.models.Actor

class ActorViewHolder(
    private val binding: ViewHolderActorBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(actor: Actor) {
        binding.apply {
            viewHolderActorNameText.text = actor.name
            Glide.with(root.context)
                .load(actor.photo)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners((4 * root.context.resources.displayMetrics.density).toInt())
                    )
                )
                .into(viewHolderActorPhotoImage)
        }
    }
}
