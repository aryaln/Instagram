package com.albert.instagram.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.albert.instagram.R
import com.albert.instagram.models.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class StoryAdapter(
    val lstStories: Array<User>,
    val context: Context
    ): RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    class StoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgStory: CircleImageView
        val tvName: TextView

        init{
            imgStory = view.findViewById(R.id.imgStory)
            tvName= view.findViewById(R.id.tvName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stories, parent, false)
        return StoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val stories = lstStories[position]
        holder.tvName.text = stories.username
        if(!TextUtils.isEmpty(stories.image_url)) {
            Glide.with(context).load(stories.image_url).into(holder.imgStory)
        }
    }

    override fun getItemCount(): Int {
        return lstStories.size
    }

}