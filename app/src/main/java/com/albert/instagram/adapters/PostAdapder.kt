package com.albert.instagram.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.albert.instagram.models.Post
import com.albert.instagram.R
import com.albert.instagram.Storage
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class PostAdapder(
    val lstPosts: Array<Post>,
    val context: Context
): RecyclerView.Adapter<PostAdapder.PostViewHolder>() {
    val storage = Storage()
    class PostViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgPost: ImageView
        val imgPostUser: CircleImageView
        val tvUserCap: TextView
        val tvUser: TextView
        val tvCaption: TextView
        val tvLikesCounter: TextView
        val imgHeart: ImageView

        init{
            imgPost = view.findViewById(R.id.imgPost)
            tvUserCap = view.findViewById(R.id.tvUserCap)
            tvUser = view.findViewById(R.id.tvUser)
            imgPostUser = view.findViewById(R.id.imgPostUser)
            tvCaption = view.findViewById(R.id.tvCaption)
            tvLikesCounter = view.findViewById(R.id.tvLikesCounter)
            imgHeart = view.findViewById(R.id.imgHeart)
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.posts, parent, false)
        return PostAdapder.PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = lstPosts[position]

        holder.tvUserCap.text = post.user?.username
        holder.tvUser.text = post.user?.username
        holder.tvCaption.text = post.caption
        println(post.caption)
        if(!TextUtils.isEmpty(post.user?.image_url)){
            Glide.with(context).load(post.user?.image_url).into(holder.imgPostUser)
        }
        val flag = storage.hasLikes(post.id, storage.getLoggedIn()?.id)
        if(flag){
            holder.imgHeart.setImageResource(R.drawable.redheart)
        }
        val logged_in = storage.getLoggedIn()
        Glide.with(context).load(post.image_url).into(holder.imgPost)
        holder.tvLikesCounter.text = storage.getPost(post.id)?.likes?.size.toString()
        holder.imgHeart.setOnClickListener{
            val flag = storage.hasLikes(post.id, storage.getLoggedIn()?.id)
            if(!flag){
                logged_in?.let { it1 -> storage.appendLikes(post, it1) }
                holder.imgHeart.setImageResource(R.drawable.redheart)
            }else{
                storage.removeLikes(post, logged_in)
                holder.imgHeart.setImageResource(R.drawable.heart)
            }
            holder.tvLikesCounter.text = storage.getPost(post.id)?.likes?.size.toString()
        }


    }

    override fun getItemCount(): Int {
        return lstPosts.size
    }
}