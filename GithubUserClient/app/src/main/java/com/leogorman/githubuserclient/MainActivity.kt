package com.leogorman.githubuserclient;

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.leogorman.githubuserclient.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)

        val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaringClass == RealmObject::class.java
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        }).create()

        val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/")
                .build()

        val githubService: GithubService = retrofit.create(
                GithubService::class.java)

        val realm = Realm.getDefaultInstance()

        //get user if it's already saved
        val savedUser: Github? = RealmQuery.createQuery(realm,
                Github::class.java).findFirst()
        updateViews(binding, savedUser)

        githubService.getGithubUser("leogorman")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            realm.beginTransaction()
                            realm.copyToRealmOrUpdate(user)
                            realm.commitTransaction()
                            updateViews(binding, user)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )


    }

    private fun updateViews(binding: ActivityMainBinding, savedUser: Github?) {
        Glide.with(this).load(savedUser?.avatarUrl).into(binding.userImage)
        binding.displayName.text = savedUser?.name
        binding.userName.text = "Username: " + savedUser?.login
        binding.accountCreateDate.text = "User Since: " + savedUser?.created_at
        binding.followers.text = "Followers: " + savedUser?.followers
        binding.following.text = "Following: " + savedUser?.following
        binding.location.text = "Location: " + savedUser?.location
        binding.publicRepos.text = "Public Repos: "+ savedUser?.publicRepos.toString()
    }
}



