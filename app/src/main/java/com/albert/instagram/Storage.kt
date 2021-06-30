package com.albert.instagram

import com.albert.instagram.models.Post
import com.albert.instagram.models.User

var users = mutableMapOf<Int, User>( 0 to User(0, "Admin", "Admin", "admin", "admin", "", ""))
var posts = mutableMapOf<Int, Post>()
var loggedIn: User? = null
class Storage() {
    public fun appendUsers(user: User){
        user.id?.let { users.put(it, user) }
    }
    public fun getUsers(): MutableMap<Int, User> {
        return users
    }

    public fun appendPosts(post: Post){
        val id = posts.size + 1
        post.id = id.toInt()
        post.id?.let { posts.put(it, post) }
    }

    public fun appendLikes(post: Post, user: User): Boolean {
        val _post = posts[post.id]
        val _user = post.likes?.get(user?.id)
//        post.likes?.set(post.id, user)
//        println(_post)
        var user_id = 0

        print(user)
        println(_user)
        if(user.id != null){
            user_id = user.id
        }
        println(user_id)
        if(_post== null && user == null){
            return false
        }else{
            if(user!=null && _user == null){
                posts[post.id]?.likes?.set(user_id, user)
                println(posts)
            }
            return true
        }
    }
    public fun removeLikes(post: Post, user: User?): Boolean{
        val _post = posts[post.id]
        val _user = post.likes?.get(user?.id)
        var user_id = 0

        if(user?.id != null){
            user_id = user.id
        }

        println(user)
        if(_post== null && user == null){
            return false
        }else{
            if(user!=null && _user != null) {
                posts[post.id]?.likes?.remove(user_id)
                println(posts)
            }
            return true
        }
    }
    public fun getPosts(): MutableMap<Int, Post> {
        return posts
    }

    public fun setLoggedIn(id: User?){
        println(id)
        loggedIn = id
    }

    public fun getLoggedIn(): User? {
        return loggedIn
    }

    public fun getUser(id: Int): User? {
        return users[id]
    }

    public fun getPost(id: Int?): Post? {
        return posts[id]
    }

    public fun hasUsername(u: String?): User? {
        var found: User? = null
        for(i in users){
            if(u == i.value.username) {
                found = i.value
                break
            }
        }
        return found
    }

    public fun getLikes(post_id: Int?): MutableMap<Int, User>? {
        val post = posts[post_id]
        if (post != null){
            return post.likes
        }
        return null
    }

    public fun hasLikes(post_id: Int? = null, user_id: Int? = null): Boolean {
        val post = posts[post_id]
        if (post != null){
            val user = post.likes?.get(user_id)
            if(user != null){
                return true
            }
        }
        return false
    }


}
