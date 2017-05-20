package com.leogorman.githubuserclient

/**
 * Created by leogorman on 20/05/2017.
 */


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Github : RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int = 0

    @SerializedName("avatar_url")
    @Expose
    open var avatarUrl: String? = null

    @SerializedName("name")
    @Expose
    open var name: String? = null

    @SerializedName("login")
    @Expose
    open var login: String? = null

    @SerializedName("location")
    @Expose
    open var location: String? = null

    @SerializedName("followers")
    @Expose
    open var followers: String? = null

    @SerializedName("following")
    @Expose
    open var following: String? = null

    @SerializedName("created_at")
    @Expose
    open var created_at: String? = null

    @SerializedName("public_repos")
    @Expose
    open var publicRepos: Int? = null

}

