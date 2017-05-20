package com.leogorman.githubuserclient

/**
 * Created by leogorman on 20/05/2017.
 */

import retrofit.http.GET
import retrofit.http.Path
import rx.Observable
import java.util.*

interface GithubService {
    @GET("users/{username}")
    fun getGithubUser(@Path("username") username: String): Observable<Github>
}
