package com.example.mymoviddb.sharedData

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response


class FakeRemoteServer : NetworkService {

    override fun getRequestTokenAsync(apiKey: String): Deferred<Response<RequestTokenModel?>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    RequestTokenModel(
                        "2016-08-26 17:04:39 UTC", "ff5c7eeb5a8870efe3cd7fc5c282cffd26800ecd", true
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun loginAsGuestAsync(apiKey: String): Deferred<Response<GuestSessionModel?>> {

        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    GuestSessionModel(
                        "2016-08-27 16:26:40 UT", "1ce82ec1223641636ad4a60b07de3581", true
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun loginAsync(
        username: String,
        password: String,
        requestToken: String,
        apiKey: String
    ): Deferred<Response<LoginTokenModel?>> {
        val fakeUsername = "rachmanabdau"
        val fakePassword = "123456"
        val fakeRequestToken = "ff5c7eeb5a8870efe3cd7fc5c282cffd26800ecd"
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""

        return if (apiKey == realApiKey && username == fakeUsername &&
            password == fakePassword && requestToken == fakeRequestToken
        ) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    LoginTokenModel(
                        true, "", ""
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun createSessionAsync(
        requestToken: String,
        apiKey: String
    ): Deferred<Response<NewSessionModel>> {

        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    NewSessionModel(
                        "1ce82ec1223641636ad4a60b07de3581", true
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getPopularMoviesAsync(page: Int, apiKey: String): Deferred<Response<MovieModel>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(MovieModel::class.java)
        val responseSuccess = jsonConverter.fromJson(popularMovieResponse) as MovieModel

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getNowPlayingMoviesAsync(
        page: Int,
        apiKey: String
    ): Deferred<Response<MovieModel>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(MovieModel::class.java)
        val responseSuccess = jsonConverter.fromJson(nowPlayingMoviesResponse) as MovieModel

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getPopularTvShowAsync(page: Int, apiKey: String): Deferred<Response<TVShowModel>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(TVShowModel::class.java)
        val responseSuccess = jsonConverter.fromJson(popularTvResponse) as TVShowModel

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getOnAirTvShowAsync(page: Int, apiKey: String): Deferred<Response<TVShowModel>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(TVShowModel::class.java)
        val responseSuccess = jsonConverter.fromJson(onAirTVResponse) as TVShowModel

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun searchMoviesAsync(
        title: String,
        page: Int,
        apiKey: String
    ): Deferred<Response<MovieModel>> {
        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(MovieModel::class.java)
        val responseSuccess = jsonConverter.fromJson(popularMovieResponse) as MovieModel

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && title.isNotBlank() -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && title.isBlank() -> {
                val emptyMovies = MovieModel(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401,
                        error401Response
                            .toResponseBody("application/json;charset=utf-8".toMediaType())
                    )
                )
            }
        }
    }

    override fun searchTvShowsAsync(
        title: String,
        page: Int,
        apiKey: String
    ): Deferred<Response<TVShowModel>> {
        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(TVShowModel::class.java)
        val responseSuccess = jsonConverter.fromJson(popularTvResponse) as TVShowModel

        return when {
            apiKey == realApiKey && title.isNotBlank() -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            apiKey == realApiKey && title.isBlank() -> {
                val emptyMovies = TVShowModel(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401,
                        error401Response
                            .toResponseBody("application/json;charset=utf-8".toMediaType())
                    )
                )
            }
        }
    }

    override fun getDetailhMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<MovieDetail>> {
        TODO("Not yet implemented")
    }

    override fun getDetailTvShowsAsync(tvId: Long, apiKey: String): Deferred<Response<TVDetail>> {
        TODO("Not yet implemented")
    }

    override fun getRecommendationMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<MovieModel>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<MovieModel>> {
        TODO("Not yet implemented")
    }

    override fun getRecommendationTVShowsAsync(
        tvId: Long,
        apiKey: String
    ): Deferred<Response<TVShowModel>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarTVShowsAsync(
        tvId: Long,
        apiKey: String
    ): Deferred<Response<TVShowModel>> {
        TODO("Not yet implemented")
    }

    companion object {
        const val popularMovieResponse = """{
  "page": 1,
  "total_results": 10000,
  "total_pages": 500,
  "results": [
    {
      "popularity": 2367.963,
      "vote_count": 28,
      "video": false,
      "poster_path": "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
      "id": 724989,
      "adult": false,
      "backdrop_path": "/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
      "original_language": "en",
      "original_title": "Hard Kill",
      "genre_ids": [
        28,
        53
      ],
      "title": "Hard Kill",
      "vote_average": 4.2,
      "overview": "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 1815.523,
      "vote_count": 201,
      "video": false,
      "poster_path": "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
      "id": 528085,
      "adult": false,
      "backdrop_path": "/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg",
      "original_language": "en",
      "original_title": "2067",
      "genre_ids": [
        18,
        878,
        53
      ],
      "title": "2067",
      "vote_average": 5.4,
      "overview": "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
      "release_date": "2020-10-01"
    },
    {
      "popularity": 1591.383,
      "vote_count": 305,
      "video": false,
      "poster_path": "/betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
      "id": 531219,
      "adult": false,
      "backdrop_path": "/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
      "original_language": "en",
      "original_title": "Roald Dahl's The Witches",
      "genre_ids": [
        14,
        10751,
        12,
        35,
        27
      ],
      "title": "Roald Dahl's The Witches",
      "vote_average": 7.3,
      "overview": "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
      "release_date": "2020-10-26"
    },
    {
      "popularity": 1109.5,
      "vote_count": 20,
      "video": false,
      "poster_path": "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
      "id": 635302,
      "adult": false,
      "backdrop_path": "/xoqr4dMbRJnzuhsWDF3XNHQwJ9x.jpg",
      "original_language": "ja",
      "original_title": "劇場版「鬼滅の刃」無限列車編",
      "genre_ids": [
        16,
        28,
        36,
        12,
        14,
        18
      ],
      "title": "Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train",
      "vote_average": 7.5,
      "overview": "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 1070.875,
      "vote_count": 2306,
      "video": false,
      "poster_path": "/riYInlsq2kf1AWoGm80JQW5dLKp.jpg",
      "id": 497582,
      "adult": false,
      "backdrop_path": "/kMe4TKMDNXTKptQPAdOF0oZHq3V.jpg",
      "original_language": "en",
      "original_title": "Enola Holmes",
      "genre_ids": [
        80,
        18,
        9648
      ],
      "title": "Enola Holmes",
      "vote_average": 7.6,
      "overview": "While searching for her missing mother, intrepid teen Enola Holmes uses her sleuthing skills to outsmart big brother Sherlock and help a runaway lord.",
      "release_date": "2020-09-23"
    },
    {
      "popularity": 1064.722,
      "vote_count": 128,
      "video": false,
      "poster_path": "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
      "id": 741067,
      "adult": false,
      "backdrop_path": "/aO5ILS7qnqtFIprbJ40zla0jhpu.jpg",
      "original_language": "en",
      "original_title": "Welcome to Sudden Death",
      "genre_ids": [
        28,
        12,
        18,
        53
      ],
      "title": "Welcome to Sudden Death",
      "vote_average": 6.4,
      "overview": "Jesse Freeman is a former special forces officer and explosives expert now working a regular job as a security guard in a state-of-the-art basketball arena. Trouble erupts when a tech-savvy cadre of terrorists kidnap the team's owner and Jesse's daughter during opening night. Facing a ticking clock and impossible odds, it's up to Jesse to not only save them but also a full house of fans in this highly charged action thriller.",
      "release_date": "2020-09-29"
    },
    {
      "popularity": 976.711,
      "vote_count": 2,
      "video": false,
      "poster_path": "/eHFJ15cZ1pfk60XTcTl9GYxnhf6.jpg",
      "id": 733278,
      "adult": false,
      "backdrop_path": "/bMrYAvAE5MalHKglxAKduqV5S4k.jpg",
      "original_language": "en",
      "original_title": "Picture Perfect Mysteries: Exit Stage Death",
      "genre_ids": [],
      "title": "Picture Perfect Mysteries: Exit Stage Death",
      "vote_average": 6,
      "overview": "While backstage on opening night of a new play by celebrated murder mystery author/director Neil Khan, photographer Allie Adams discovers the body of the show’s leading lady, murdered before the curtain went up. As Willow Haven PD Detective Sam Acosta launches his investigation, he invites Allie – who has been helpful in solving his first two murder cases since joining the force -- to unofficially assist him on the. case. However, Allie’s involvement makes her a target for murder herself when she gets too close to the truth.",
      "release_date": "2020-10-11"
    },
    {
      "popularity": 902.112,
      "vote_count": 205,
      "video": false,
      "poster_path": "/r4Lm1XKP0VsTgHX4LG4syAwYA2I.jpg",
      "id": 590223,
      "adult": false,
      "backdrop_path": "/lA5fOBqTOQBQ1s9lEYYPmNXoYLi.jpg",
      "original_language": "en",
      "original_title": "Love and Monsters",
      "genre_ids": [
        28,
        12,
        35,
        878
      ],
      "title": "Love and Monsters",
      "vote_average": 7.7,
      "overview": "Seven years after the Monsterpocalypse, Joel Dawson, along with the rest of humanity, has been living underground ever since giant creatures took control of the land. After reconnecting over radio with his high school girlfriend Aimee, who is now 80 miles away at a coastal colony, Joel begins to fall for her again. As Joel realizes that there’s nothing left for him underground, he decides against all logic to venture out to Aimee, despite all the dangerous monsters that stand in his way.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 835.74,
      "vote_count": 500,
      "video": false,
      "poster_path": "/6agKYU5IQFpuDyUYPu39w7UCRrJ.jpg",
      "id": 740985,
      "adult": false,
      "backdrop_path": "/hbrXbVoE0NuA1ORoSGGYNASagrl.jpg",
      "original_language": "en",
      "original_title": "Borat Subsequent Moviefilm",
      "genre_ids": [
        35
      ],
      "title": "Borat Subsequent Moviefilm",
      "vote_average": 6.8,
      "overview": "14 years after making a film about his journey across the USA, Borat risks life and limb when he returns to the United States with his young daughter, and reveals more about the culture, the COVID-19 pandemic, and the political elections.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 651.132,
      "vote_count": 326,
      "video": false,
      "poster_path": "/uOw5JD8IlD546feZ6oxbIjvN66P.jpg",
      "id": 718444,
      "adult": false,
      "backdrop_path": "/x4UkhIQuHIJyeeOTdcbZ3t3gBSa.jpg",
      "original_language": "en",
      "original_title": "Rogue",
      "genre_ids": [
        28,
        12,
        18,
        53
      ],
      "title": "Rogue",
      "vote_average": 5.8,
      "overview": "Battle-hardened O’Hara leads a lively mercenary team of soldiers on a daring mission: rescue hostages from their captors in remote Africa. But as the mission goes awry and the team is stranded, O’Hara’s squad must face a bloody, brutal encounter with a gang of rebels.",
      "release_date": "2020-08-20"
    },
    {
      "popularity": 638.944,
      "vote_count": 156,
      "video": false,
      "poster_path": "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
      "id": 694919,
      "adult": false,
      "backdrop_path": "/pq0JSpwyT2URytdFG0euztQPAyR.jpg",
      "original_language": "en",
      "original_title": "Money Plane",
      "genre_ids": [
        28
      ],
      "title": "Money Plane",
      "vote_average": 5.9,
      "overview": "A professional thief with ${'$'}40 million in debt and his family's life on the line must commit one final heist - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
      "release_date": "2020-09-29"
    },
    {
      "popularity": 631.045,
      "vote_count": 1054,
      "video": false,
      "poster_path": "/kiX7UYfOpYrMFSAGbI6j1pFkLzQ.jpg",
      "id": 613504,
      "adult": false,
      "backdrop_path": "/6hgItrYQEG33y0I7yP2SRl2ei4w.jpg",
      "original_language": "en",
      "original_title": "After We Collided",
      "genre_ids": [
        18,
        10749
      ],
      "title": "After We Collided",
      "vote_average": 7.2,
      "overview": "Tessa finds herself struggling with her complicated relationship with Hardin; she faces a dilemma that could change their lives forever.",
      "release_date": "2020-09-02"
    },
    {
      "popularity": 624.425,
      "vote_count": 545,
      "video": false,
      "poster_path": "/qzA87Wf4jo1h8JMk9GilyIYvwsA.jpg",
      "id": 539885,
      "adult": false,
      "backdrop_path": "/54yOImQgj8i85u9hxxnaIQBRUuo.jpg",
      "original_language": "en",
      "original_title": "Ava",
      "genre_ids": [
        28,
        80,
        18,
        53
      ],
      "title": "Ava",
      "vote_average": 5.8,
      "overview": "A black ops assassin is forced to fight for her own survival after a job goes dangerously wrong.",
      "release_date": "2020-07-02"
    },
    {
      "popularity": 613.467,
      "vote_count": 2672,
      "video": false,
      "poster_path": "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
      "id": 337401,
      "adult": false,
      "backdrop_path": "/zzWGRw277MNoCs3zhyG3YmYQsXv.jpg",
      "original_language": "en",
      "original_title": "Mulan",
      "genre_ids": [
        28,
        12,
        18,
        14
      ],
      "title": "Mulan",
      "vote_average": 7.3,
      "overview": "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
      "release_date": "2020-09-04"
    },
    {
      "popularity": 607.834,
      "vote_count": 676,
      "video": false,
      "poster_path": "/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg",
      "id": 581392,
      "adult": false,
      "backdrop_path": "/2nFzxaAK7JIsk6l7qZ8rFBsa3yW.jpg",
      "original_language": "ko",
      "original_title": "반도",
      "genre_ids": [
        28,
        27,
        53
      ],
      "title": "Peninsula",
      "vote_average": 7,
      "overview": "A soldier and his team battle hordes of post-apocalyptic zombies in the wastelands of the Korean Peninsula.",
      "release_date": "2020-07-15"
    },
    {
      "popularity": 586.957,
      "vote_count": 78,
      "video": false,
      "poster_path": "/bkld8Me0WiLWipLORRNfF1yIPHu.jpg",
      "id": 624963,
      "adult": false,
      "backdrop_path": "/ezLKohe4HKsHQbwQwhv0ARo83NC.jpg",
      "original_language": "en",
      "original_title": "A Babysitter's Guide to Monster Hunting",
      "genre_ids": [
        12,
        35,
        14,
        10751
      ],
      "title": "A Babysitter's Guide to Monster Hunting",
      "vote_average": 6.1,
      "overview": "Recruited by a secret society of babysitters, a high schooler battles the Boogeyman and his monsters when they nab the boy she's watching on Halloween.",
      "release_date": "2020-10-14"
    },
    {
      "popularity": 559.61,
      "vote_count": 117,
      "video": false,
      "poster_path": "/xqvX5A24dbIWaeYsMTxxKX5qOfz.jpg",
      "id": 660982,
      "adult": false,
      "backdrop_path": "/75ooojtgiKYm5LcCczbCexioZze.jpg",
      "original_language": "en",
      "original_title": "American Pie Presents: Girls' Rules",
      "genre_ids": [
        35
      ],
      "title": "American Pie Presents: Girls Rules",
      "vote_average": 6.3,
      "overview": "It's Senior year at East Great Falls. Annie, Kayla, Michelle, and Stephanie decide to harness their girl power and band together to get what they want their last year of high school.",
      "release_date": "2020-10-06"
    },
    {
      "popularity": 549.968,
      "vote_count": 225,
      "video": false,
      "poster_path": "/vJHSParlylICnI7DuuI54nfTPRR.jpg",
      "id": 438396,
      "adult": false,
      "backdrop_path": "/qGZe9qTuydxyJYQ60XDtEckzLR8.jpg",
      "original_language": "es",
      "original_title": "Orígenes secretos",
      "genre_ids": [
        18,
        53
      ],
      "title": "Unknown Origins",
      "vote_average": 6.2,
      "overview": "In Madrid, Spain, a mysterious serial killer ruthlessly murders his victims by recreating the first appearance of several comic book superheroes. Cosme, a veteran police inspector who is about to retire, works on the case along with the tormented inspector David Valentín and his own son Jorge Elías, a nerdy young man who owns a comic book store.",
      "release_date": "2020-08-28"
    },
    {
      "popularity": 548.793,
      "vote_count": 903,
      "video": false,
      "poster_path": "/tI8ocADh22GtQFV28vGHaBZVb0U.jpg",
      "id": 475430,
      "adult": false,
      "backdrop_path": "/o0F8xAt8YuEm5mEZviX5pEFC12y.jpg",
      "original_language": "en",
      "original_title": "Artemis Fowl",
      "genre_ids": [
        28,
        12,
        14,
        878,
        10751
      ],
      "title": "Artemis Fowl",
      "vote_average": 5.8,
      "overview": "Artemis Fowl is a 12-year-old genius and descendant of a long line of criminal masterminds. He soon finds himself in an epic battle against a race of powerful underground fairies who may be behind his father's disappearance.",
      "release_date": "2020-06-12"
    },
    {
      "popularity": 546.091,
      "vote_count": 17,
      "video": false,
      "poster_path": "/z0r3YjyJSLqf6Hz0rbBAnEhNXQ7.jpg",
      "id": 697064,
      "adult": false,
      "backdrop_path": "/7WKIOXJa2JjHygE8Yta3uaCv6GC.jpg",
      "original_language": "en",
      "original_title": "Beckman",
      "genre_ids": [
        28
      ],
      "title": "Beckman",
      "vote_average": 4.8,
      "overview": "A contract killer, becomes the reverend of a LA church, until a cult leader and his minions kidnap his daughter. Blinded by vengeance, he cuts a bloody path across the city. The only thing that can stop him is his newfound faith.",
      "release_date": "2020-09-10"
    }
  ]
}"""
        const val nowPlayingMoviesResponse = """{
  "results": [
    {
      "popularity": 2367.963,
      "vote_count": 28,
      "video": false,
      "poster_path": "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
      "id": 724989,
      "adult": false,
      "backdrop_path": "/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
      "original_language": "en",
      "original_title": "Hard Kill",
      "genre_ids": [
        28,
        53
      ],
      "title": "Hard Kill",
      "vote_average": 4.2,
      "overview": "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 1815.523,
      "vote_count": 201,
      "video": false,
      "poster_path": "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
      "id": 528085,
      "adult": false,
      "backdrop_path": "/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg",
      "original_language": "en",
      "original_title": "2067",
      "genre_ids": [
        18,
        878,
        53
      ],
      "title": "2067",
      "vote_average": 5.4,
      "overview": "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
      "release_date": "2020-10-01"
    },
    {
      "popularity": 1591.383,
      "vote_count": 305,
      "video": false,
      "poster_path": "/betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
      "id": 531219,
      "adult": false,
      "backdrop_path": "/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
      "original_language": "en",
      "original_title": "Roald Dahl's The Witches",
      "genre_ids": [
        14,
        10751,
        12,
        35,
        27
      ],
      "title": "Roald Dahl's The Witches",
      "vote_average": 7.3,
      "overview": "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
      "release_date": "2020-10-26"
    },
    {
      "popularity": 1109.5,
      "vote_count": 20,
      "video": false,
      "poster_path": "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
      "id": 635302,
      "adult": false,
      "backdrop_path": "/xoqr4dMbRJnzuhsWDF3XNHQwJ9x.jpg",
      "original_language": "ja",
      "original_title": "劇場版「鬼滅の刃」無限列車編",
      "genre_ids": [
        16,
        28,
        36,
        12,
        14,
        18
      ],
      "title": "Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train",
      "vote_average": 7.5,
      "overview": "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 902.112,
      "vote_count": 205,
      "video": false,
      "poster_path": "/r4Lm1XKP0VsTgHX4LG4syAwYA2I.jpg",
      "id": 590223,
      "adult": false,
      "backdrop_path": "/lA5fOBqTOQBQ1s9lEYYPmNXoYLi.jpg",
      "original_language": "en",
      "original_title": "Love and Monsters",
      "genre_ids": [
        28,
        12,
        35,
        878
      ],
      "title": "Love and Monsters",
      "vote_average": 7.7,
      "overview": "Seven years after the Monsterpocalypse, Joel Dawson, along with the rest of humanity, has been living underground ever since giant creatures took control of the land. After reconnecting over radio with his high school girlfriend Aimee, who is now 80 miles away at a coastal colony, Joel begins to fall for her again. As Joel realizes that there’s nothing left for him underground, he decides against all logic to venture out to Aimee, despite all the dangerous monsters that stand in his way.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 651.132,
      "vote_count": 326,
      "video": false,
      "poster_path": "/uOw5JD8IlD546feZ6oxbIjvN66P.jpg",
      "id": 718444,
      "adult": false,
      "backdrop_path": "/x4UkhIQuHIJyeeOTdcbZ3t3gBSa.jpg",
      "original_language": "en",
      "original_title": "Rogue",
      "genre_ids": [
        28,
        12,
        18,
        53
      ],
      "title": "Rogue",
      "vote_average": 5.8,
      "overview": "Battle-hardened O’Hara leads a lively mercenary team of soldiers on a daring mission: rescue hostages from their captors in remote Africa. But as the mission goes awry and the team is stranded, O’Hara’s squad must face a bloody, brutal encounter with a gang of rebels.",
      "release_date": "2020-08-20"
    },
    {
      "popularity": 638.944,
      "vote_count": 156,
      "video": false,
      "poster_path": "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
      "id": 694919,
      "adult": false,
      "backdrop_path": "/pq0JSpwyT2URytdFG0euztQPAyR.jpg",
      "original_language": "en",
      "original_title": "Money Plane",
      "genre_ids": [
        28
      ],
      "title": "Money Plane",
      "vote_average": 5.9,
      "overview": "A professional thief with ${'$'}40 million in debt and his family's life on the line must commit one final heist - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
      "release_date": "2020-09-29"
    },
    {
      "popularity": 631.045,
      "vote_count": 1054,
      "video": false,
      "poster_path": "/kiX7UYfOpYrMFSAGbI6j1pFkLzQ.jpg",
      "id": 613504,
      "adult": false,
      "backdrop_path": "/6hgItrYQEG33y0I7yP2SRl2ei4w.jpg",
      "original_language": "en",
      "original_title": "After We Collided",
      "genre_ids": [
        18,
        10749
      ],
      "title": "After We Collided",
      "vote_average": 7.2,
      "overview": "Tessa finds herself struggling with her complicated relationship with Hardin; she faces a dilemma that could change their lives forever.",
      "release_date": "2020-09-02"
    },
    {
      "popularity": 624.425,
      "vote_count": 545,
      "video": false,
      "poster_path": "/qzA87Wf4jo1h8JMk9GilyIYvwsA.jpg",
      "id": 539885,
      "adult": false,
      "backdrop_path": "/54yOImQgj8i85u9hxxnaIQBRUuo.jpg",
      "original_language": "en",
      "original_title": "Ava",
      "genre_ids": [
        28,
        80,
        18,
        53
      ],
      "title": "Ava",
      "vote_average": 5.8,
      "overview": "A black ops assassin is forced to fight for her own survival after a job goes dangerously wrong.",
      "release_date": "2020-07-02"
    },
    {
      "popularity": 613.467,
      "vote_count": 2672,
      "video": false,
      "poster_path": "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
      "id": 337401,
      "adult": false,
      "backdrop_path": "/zzWGRw277MNoCs3zhyG3YmYQsXv.jpg",
      "original_language": "en",
      "original_title": "Mulan",
      "genre_ids": [
        28,
        12,
        18,
        14
      ],
      "title": "Mulan",
      "vote_average": 7.3,
      "overview": "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
      "release_date": "2020-09-04"
    },
    {
      "popularity": 607.834,
      "vote_count": 676,
      "video": false,
      "poster_path": "/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg",
      "id": 581392,
      "adult": false,
      "backdrop_path": "/2nFzxaAK7JIsk6l7qZ8rFBsa3yW.jpg",
      "original_language": "ko",
      "original_title": "반도",
      "genre_ids": [
        28,
        27,
        53
      ],
      "title": "Peninsula",
      "vote_average": 7,
      "overview": "A soldier and his team battle hordes of post-apocalyptic zombies in the wastelands of the Korean Peninsula.",
      "release_date": "2020-07-15"
    },
    {
      "popularity": 454.521,
      "vote_count": 56,
      "video": false,
      "poster_path": "/n9OzZmPMnVrV0cMQ7amX0DtBkQH.jpg",
      "id": 509635,
      "adult": false,
      "backdrop_path": "/obExOU9qDnMcxvWPumoD14oxup5.jpg",
      "original_language": "en",
      "original_title": "Alone",
      "genre_ids": [
        53
      ],
      "title": "Alone",
      "vote_average": 6.2,
      "overview": "A recently widowed traveler is kidnapped by a cold blooded killer, only to escape into the wilderness where she is forced to battle against the elements as her pursuer closes in on her.",
      "release_date": "2020-09-10"
    },
    {
      "popularity": 400.351,
      "vote_count": 155,
      "video": false,
      "poster_path": "/lQfdytwN7eh0tXWjIiMceFdBBvD.jpg",
      "id": 560050,
      "adult": false,
      "backdrop_path": "/htBUhLSS7FfHtydgYxUWjL3J1Q1.jpg",
      "original_language": "en",
      "original_title": "Over the Moon",
      "genre_ids": [
        12,
        16,
        14,
        10751
      ],
      "title": "Over the Moon",
      "vote_average": 7.9,
      "overview": "A girl builds a rocket to travel to the moon in hopes of meeting the legendary Moon Goddess.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 293.355,
      "vote_count": 94,
      "video": false,
      "poster_path": "/ltyARDw2EFXZ2H2ERnlEctXPioP.jpg",
      "id": 425001,
      "adult": false,
      "backdrop_path": "/4gKyQ1McHa8ZKDsYoyKQSevF01J.jpg",
      "original_language": "en",
      "original_title": "The War with Grandpa",
      "genre_ids": [
        35,
        10751,
        18
      ],
      "title": "The War with Grandpa",
      "vote_average": 6.2,
      "overview": "Sixth-grader Peter is pretty much your average kid—he likes gaming, hanging with his friends and his beloved pair of Air Jordans. But when his recently widowed grandfather Ed  moves in with Peter’s family, the boy is forced to give up his most prized possession of all, his bedroom. Unwilling to let such an injustice stand, Peter devises a series of increasingly elaborate pranks to drive out the interloper, but Grandpa Ed won’t go without a fight.",
      "release_date": "2020-08-27"
    },
    {
      "popularity": 290.758,
      "vote_count": 2,
      "video": false,
      "poster_path": "/zamW7KUYYtAdxMtTuCzukRjHTPO.jpg",
      "id": 748224,
      "adult": false,
      "backdrop_path": "/flNp7NdzBC1UGctzuubQveuVSF5.jpg",
      "original_language": "en",
      "original_title": "Reaptown",
      "genre_ids": [
        27
      ],
      "title": "Reaptown",
      "vote_average": 6,
      "overview": "In this supernatural horror film, Carrie Baldwin is freed from prison under the conditions of a work-release program in Reaptown, Nevada. As she struggles to find her missing sister while working the night shift as a security guard for the Reaptown Railway Museum, Carrie soon finds herself in the presence of evil. Could the Railway hold the clues to her sister's mysterious disappearance?",
      "release_date": "2020-09-28"
    },
    {
      "popularity": 277.491,
      "vote_count": 1240,
      "video": false,
      "poster_path": "/7W0G3YECgDAfnuiHG91r8WqgIOe.jpg",
      "id": 446893,
      "adult": false,
      "backdrop_path": "/qsxhnirlp7y4Ae9bd11oYJSX59j.jpg",
      "original_language": "en",
      "original_title": "Trolls World Tour",
      "genre_ids": [
        12,
        16,
        35,
        14,
        10402,
        10751
      ],
      "title": "Trolls World Tour",
      "vote_average": 7.6,
      "overview": "Queen Poppy and Branch make a surprising discovery — there are other Troll worlds beyond their own, and their distinct differences create big clashes between these various tribes. When a mysterious threat puts all of the Trolls across the land in danger, Poppy, Branch, and their band of friends must embark on an epic quest to create harmony among the feuding Trolls to unite them against certain doom.",
      "release_date": "2020-03-12"
    },
    {
      "popularity": 265.031,
      "vote_count": 65,
      "video": false,
      "poster_path": "/zfdhsR3Y3xw42OHrMpi0oBw0Uk8.jpg",
      "id": 741074,
      "adult": false,
      "backdrop_path": "/DA7gzvlBoxMNL0XmGgTZOyv67P.jpg",
      "original_language": "en",
      "original_title": "Once Upon a Snowman",
      "genre_ids": [
        16,
        35,
        14,
        10751
      ],
      "title": "Once Upon a Snowman",
      "vote_average": 7.4,
      "overview": "The previously untold origins of Olaf, the innocent and insightful, summer-loving snowman are revealed as we follow Olaf’s first steps as he comes to life and searches for his identity in the snowy mountains outside Arendelle.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 259.417,
      "vote_count": 87,
      "video": false,
      "poster_path": "/4BgSWFMW2MJ0dT5metLzsRWO7IJ.jpg",
      "id": 726739,
      "adult": false,
      "backdrop_path": "/t22fWbzdnThPseipsdpwgdPOPCR.jpg",
      "original_language": "en",
      "original_title": "Cats & Dogs 3: Paws Unite",
      "genre_ids": [
        28,
        35
      ],
      "title": "Cats & Dogs 3: Paws Unite",
      "vote_average": 6.5,
      "overview": "It's been ten years since the creation of the Great Truce, an elaborate joint-species surveillance system designed and monitored by cats and dogs to keep the peace when conflicts arise. But when a tech-savvy villain hacks into wireless networks to use frequencies only heard by cats and dogs, he manipulates them into conflict and the worldwide battle between cats and dogs is BACK ON. Now, a team of inexperienced and untested agents will have to use their old-school animal instincts to restore order and peace between cats and dogs everywhere.",
      "release_date": "2020-10-02"
    },
    {
      "popularity": 232.215,
      "vote_count": 233,
      "video": false,
      "poster_path": "/bSKVKcCXdKxkbgf0LL8lBTPG02e.jpg",
      "id": 505379,
      "adult": false,
      "backdrop_path": "/rVkptIl9QkhpB5xM6BnUAHOJ43b.jpg",
      "original_language": "en",
      "original_title": "Rebecca",
      "genre_ids": [
        18,
        9648,
        53,
        10749
      ],
      "title": "Rebecca",
      "vote_average": 6.3,
      "overview": "After a whirlwind romance with a wealthy widower, a naïve bride moves to his family estate but can't escape the haunting shadow of his late wife.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 213.559,
      "vote_count": 409,
      "video": false,
      "poster_path": "/hPkqY2EMqWUnFEoedukilIUieVG.jpg",
      "id": 531876,
      "adult": false,
      "backdrop_path": "/n1RohH2VoK1CdVI2fXvcP19dSlm.jpg",
      "original_language": "en",
      "original_title": "The Outpost",
      "genre_ids": [
        28,
        18,
        36,
        10752
      ],
      "title": "The Outpost",
      "vote_average": 6.7,
      "overview": "A small unit of U.S. soldiers, alone at the remote Combat Outpost Keating, located deep in the valley of three mountains in Afghanistan, battles to defend against an overwhelming force of Taliban fighters in a coordinated attack. The Battle of Kamdesh, as it was known, was the bloodiest American engagement of the Afghan War in 2009 and Bravo Troop 3-61 CAV became one of the most decorated units of the 19-year conflict.",
      "release_date": "2020-06-24"
    }
  ],
  "page": 1,
  "total_results": 1492,
  "dates": {
    "maximum": "2020-11-04",
    "minimum": "2020-09-17"
  },
  "total_pages": 75
}"""

        const val popularTvResponse = """{
  "page": 1,
  "total_results": 10000,
  "total_pages": 500,
  "results": [
    {
      "original_name": "Fear the Walking Dead",
      "genre_ids": [
        10759,
        18
      ],
      "name": "Fear the Walking Dead",
      "popularity": 742.468,
      "origin_country": [
        "US"
      ],
      "vote_count": 2411,
      "first_air_date": "2015-08-23",
      "backdrop_path": "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
      "original_language": "en",
      "id": 62286,
      "vote_average": 7.4,
      "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
      "poster_path": "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg"
    },
    {
      "original_name": "The Boys",
      "genre_ids": [
        10765,
        10759
      ],
      "name": "The Boys",
      "popularity": 725.712,
      "origin_country": [
        "US"
      ],
      "vote_count": 3302,
      "first_air_date": "2019-07-25",
      "backdrop_path": "/mGVrXeIjyecj6TKmwPVpHlscEmw.jpg",
      "original_language": "en",
      "id": 76479,
      "vote_average": 8.4,
      "overview": "A group of vigilantes known informally as “The Boys” set out to take down corrupt superheroes with no more than blue-collar grit and a willingness to fight dirty.",
      "poster_path": "/mY7SeH4HFFxW1hiI6cWuwCRKptN.jpg"
    },
    {
      "original_name": "Cobra Kai",
      "genre_ids": [
        18,
        10759
      ],
      "name": "Cobra Kai",
      "popularity": 662.769,
      "origin_country": [
        "US"
      ],
      "vote_count": 1010,
      "first_air_date": "2018-05-02",
      "backdrop_path": "/g63KmFgqkvXu6WKS23V56hqEidh.jpg",
      "original_language": "en",
      "id": 77169,
      "vote_average": 8,
      "overview": "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
      "poster_path": "/eTMMU2rKpZRbo9ERytyhwatwAjz.jpg"
    },
    {
      "original_name": "Lucifer",
      "genre_ids": [
        80,
        10765
      ],
      "name": "Lucifer",
      "popularity": 638.314,
      "origin_country": [
        "US"
      ],
      "vote_count": 6033,
      "first_air_date": "2016-01-25",
      "backdrop_path": "/ta5oblpMlEcIPIS2YGcq9XEkWK2.jpg",
      "original_language": "en",
      "id": 63174,
      "vote_average": 8.5,
      "overview": "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
      "poster_path": "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg"
    },
    {
      "original_name": "The Walking Dead: World Beyond",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "name": "The Walking Dead: World Beyond",
      "popularity": 505.253,
      "origin_country": [
        "US"
      ],
      "vote_count": 305,
      "first_air_date": "2020-10-04",
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "original_language": "en",
      "id": 94305,
      "vote_average": 7.8,
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg"
    },
    {
      "original_name": "Grey's Anatomy",
      "genre_ids": [
        18
      ],
      "name": "Grey's Anatomy",
      "popularity": 396.725,
      "origin_country": [
        "US"
      ],
      "vote_count": 3908,
      "first_air_date": "2005-03-27",
      "backdrop_path": "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
      "original_language": "en",
      "id": 1416,
      "vote_average": 8,
      "overview": "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
      "poster_path": "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg"
    },
    {
      "original_name": "The 100",
      "genre_ids": [
        18,
        10759,
        10765
      ],
      "name": "The 100",
      "popularity": 359.085,
      "origin_country": [
        "US"
      ],
      "vote_count": 4747,
      "first_air_date": "2014-03-19",
      "backdrop_path": "/hTExot1sfn7dHZjGrk0Aiwpntxt.jpg",
      "original_language": "en",
      "id": 48866,
      "vote_average": 7.8,
      "overview": "100 years in the future, when the Earth has been abandoned due to radioactivity, the last surviving humans live on an ark orbiting the planet — but the ark won't last forever. So the repressive regime picks 100 expendable juvenile delinquents to send down to Earth to see if the planet is still habitable.",
      "poster_path": "/wcaDIAG1QdXQLRaj4vC1EFdBT2.jpg"
    },
    {
      "original_name": "Riverdale",
      "genre_ids": [
        18,
        9648
      ],
      "name": "Riverdale",
      "popularity": 355.087,
      "origin_country": [
        "US"
      ],
      "vote_count": 6126,
      "first_air_date": "2017-01-26",
      "backdrop_path": "/9hvhGtcsGhQY58pukw8w55UEUbL.jpg",
      "original_language": "en",
      "id": 69050,
      "vote_average": 8.6,
      "overview": "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
      "poster_path": "/4X7o1ssOEvp4BFLim1AZmPNcYbU.jpg"
    },
    {
      "original_name": "Game of Thrones",
      "genre_ids": [
        18,
        9648,
        10759,
        10765
      ],
      "name": "Game of Thrones",
      "popularity": 332.544,
      "origin_country": [
        "US"
      ],
      "vote_count": 11148,
      "first_air_date": "2011-04-17",
      "backdrop_path": "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
      "original_language": "en",
      "id": 1399,
      "vote_average": 8.3,
      "overview": "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
      "poster_path": "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg"
    },
    {
      "original_name": "The Simpsons",
      "genre_ids": [
        16,
        35,
        18,
        10751
      ],
      "name": "The Simpsons",
      "popularity": 332.193,
      "origin_country": [
        "US"
      ],
      "vote_count": 5026,
      "first_air_date": "1989-12-16",
      "backdrop_path": "/adZ9ldSlkGfLfsHNbh37ZThCcgU.jpg",
      "original_language": "en",
      "id": 456,
      "vote_average": 7.7,
      "overview": "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
      "poster_path": "/qcr9bBY6MVeLzriKCmJOv1562uY.jpg"
    },
    {
      "original_name": "Supernatural",
      "genre_ids": [
        18,
        9648,
        10765
      ],
      "name": "Supernatural",
      "popularity": 329.016,
      "origin_country": [
        "US"
      ],
      "vote_count": 4087,
      "first_air_date": "2005-09-13",
      "backdrop_path": "/nVRyd8hlg0ZLxBn9RaI7mUMQLnz.jpg",
      "original_language": "en",
      "id": 1622,
      "vote_average": 8.1,
      "overview": "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way. ",
      "poster_path": "/KoYWXbnYuS3b0GyQPkbuexlVK9.jpg"
    },
    {
      "original_name": "The Vampire Diaries",
      "genre_ids": [
        18,
        14,
        27,
        10749
      ],
      "name": "The Vampire Diaries",
      "popularity": 328.811,
      "origin_country": [
        "US"
      ],
      "vote_count": 3925,
      "first_air_date": "2009-09-10",
      "backdrop_path": "/k7T9xRyzP41wBVNyNeLmh8Ch7gD.jpg",
      "original_language": "en",
      "id": 18165,
      "vote_average": 8.3,
      "overview": "The story of two vampire brothers obsessed with the same girl, who bears a striking resemblance to the beautiful but ruthless vampire they knew and loved in 1864.",
      "poster_path": "/b3vl6wV1W8PBezFfntKTrhrehCY.jpg"
    },
    {
      "original_name": "The Walking Dead",
      "genre_ids": [
        18,
        10759,
        10765
      ],
      "name": "The Walking Dead",
      "popularity": 325.769,
      "origin_country": [
        "US"
      ],
      "vote_count": 8336,
      "first_air_date": "2010-10-31",
      "backdrop_path": "/wXXaPMgrv96NkH8KD1TMdS2d7iq.jpg",
      "original_language": "en",
      "id": 1402,
      "vote_average": 7.9,
      "overview": "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
      "poster_path": "/qgjP2OrrX9gc6M270xdPnEmE9tC.jpg"
    },
    {
      "original_name": "The Good Doctor",
      "genre_ids": [
        18
      ],
      "name": "The Good Doctor",
      "popularity": 302.957,
      "origin_country": [
        "US"
      ],
      "vote_count": 5263,
      "first_air_date": "2017-09-25",
      "backdrop_path": "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
      "original_language": "en",
      "id": 71712,
      "vote_average": 8.6,
      "overview": "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
      "poster_path": "/53P8oHo9cfOsgb1cLxBi4pFY0ja.jpg"
    },
    {
      "original_name": "Bones",
      "genre_ids": [
        80,
        18
      ],
      "name": "Bones",
      "popularity": 280.557,
      "origin_country": [
        "US"
      ],
      "vote_count": 1452,
      "first_air_date": "2005-09-13",
      "backdrop_path": "/e9n87p3Ax67spq3eUgLB6rjIEow.jpg",
      "original_language": "en",
      "id": 1911,
      "vote_average": 8.1,
      "overview": "Dr. Temperance Brennan and her colleagues at the Jeffersonian's Medico-Legal Lab assist Special Agent Seeley Booth with murder investigations when the remains are so badly decomposed, burned or destroyed that the standard identification methods are useless.",
      "poster_path": "/1bwF1daWnsEYYjbHXiEMdS587fR.jpg"
    },
    {
      "original_name": "The Umbrella Academy",
      "genre_ids": [
        35,
        18,
        10759,
        10765
      ],
      "name": "The Umbrella Academy",
      "popularity": 278.982,
      "origin_country": [
        "US"
      ],
      "vote_count": 3232,
      "first_air_date": "2019-02-15",
      "backdrop_path": "/qJxzjUjCpTPvDHldNnlbRC4OqEh.jpg",
      "original_language": "en",
      "id": 75006,
      "vote_average": 8.7,
      "overview": "A dysfunctional family of superheroes comes together to solve the mystery of their father's death, the threat of the apocalypse and more.",
      "poster_path": "/scZlQQYnDVlnpxFTxaIv2g0BWnL.jpg"
    },
    {
      "original_name": "The Flash",
      "genre_ids": [
        18,
        10765
      ],
      "name": "The Flash",
      "popularity": 275.263,
      "origin_country": [
        "US"
      ],
      "vote_count": 5999,
      "first_air_date": "2014-10-07",
      "backdrop_path": "/z59kJfcElR9eHO9rJbWp4qWMuee.jpg",
      "original_language": "en",
      "id": 60735,
      "vote_average": 7.5,
      "overview": "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
      "poster_path": "/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg"
    },
    {
      "original_name": "Law & Order: Special Victims Unit",
      "genre_ids": [
        80,
        18
      ],
      "name": "Law & Order: Special Victims Unit",
      "popularity": 272.305,
      "origin_country": [
        "US"
      ],
      "vote_count": 1797,
      "first_air_date": "1999-09-20",
      "backdrop_path": "/cD9PxbrdWYgL7MUpD9eOYuiSe2P.jpg",
      "original_language": "en",
      "id": 2734,
      "vote_average": 7.5,
      "overview": "In the criminal justice system, sexually-based offenses are considered especially heinous. In New York City, the dedicated detectives who investigate these vicious felonies are members of an elite squad known as the Special Victims Unit. These are their stories.",
      "poster_path": "/jDCgWVlejIo8sQYxw3Yf1cVQUIL.jpg"
    },
    {
      "original_name": "I Am...",
      "genre_ids": [
        18
      ],
      "name": "I Am...",
      "popularity": 258.682,
      "origin_country": [],
      "vote_count": 2,
      "first_air_date": "2019-07-23",
      "backdrop_path": null,
      "original_language": "en",
      "id": 91605,
      "vote_average": 4,
      "overview": "Each hour-long film follows a different women as they experience “moments that are emotionally raw, thought-provoking and utterly personal”.",
      "poster_path": "/oogunE22HDTcTxFakKQbwqfw1qo.jpg"
    },
    {
      "original_name": "The Mandalorian",
      "genre_ids": [
        10765,
        10759,
        37
      ],
      "name": "The Mandalorian",
      "popularity": 253.429,
      "origin_country": [
        "US"
      ],
      "vote_count": 1714,
      "first_air_date": "2019-11-12",
      "backdrop_path": "/o7qi2v4uWQ8bZ1tW3KI0Ztn2epk.jpg",
      "original_language": "en",
      "id": 82856,
      "vote_average": 8.4,
      "overview": "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
      "poster_path": "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg"
    }
  ]
}"""

        const val onAirTVResponse = """{
  "page": 1,
  "total_results": 552,
  "total_pages": 28,
  "results": [
    {
      "original_name": "Fear the Walking Dead",
      "genre_ids": [
        10759,
        18
      ],
      "name": "Fear the Walking Dead",
      "popularity": 742.468,
      "origin_country": [
        "US"
      ],
      "vote_count": 2411,
      "first_air_date": "2015-08-23",
      "backdrop_path": "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
      "original_language": "en",
      "id": 62286,
      "vote_average": 7.4,
      "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
      "poster_path": "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg"
    },
    {
      "original_name": "The Walking Dead: World Beyond",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "name": "The Walking Dead: World Beyond",
      "popularity": 505.253,
      "origin_country": [
        "US"
      ],
      "vote_count": 305,
      "first_air_date": "2020-10-04",
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "original_language": "en",
      "id": 94305,
      "vote_average": 7.8,
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg"
    },
    {
      "original_name": "The Simpsons",
      "genre_ids": [
        16,
        35,
        18,
        10751
      ],
      "name": "The Simpsons",
      "popularity": 332.193,
      "origin_country": [
        "US"
      ],
      "vote_count": 5026,
      "first_air_date": "1989-12-16",
      "backdrop_path": "/adZ9ldSlkGfLfsHNbh37ZThCcgU.jpg",
      "original_language": "en",
      "id": 456,
      "vote_average": 7.7,
      "overview": "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
      "poster_path": "/qcr9bBY6MVeLzriKCmJOv1562uY.jpg"
    },
    {
      "original_name": "The Good Doctor",
      "genre_ids": [
        18
      ],
      "name": "The Good Doctor",
      "popularity": 302.957,
      "origin_country": [
        "US"
      ],
      "vote_count": 5263,
      "first_air_date": "2017-09-25",
      "backdrop_path": "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
      "original_language": "en",
      "id": 71712,
      "vote_average": 8.6,
      "overview": "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
      "poster_path": "/53P8oHo9cfOsgb1cLxBi4pFY0ja.jpg"
    },
    {
      "original_name": "The Mandalorian",
      "genre_ids": [
        10765,
        10759,
        37
      ],
      "name": "The Mandalorian",
      "popularity": 253.429,
      "origin_country": [
        "US"
      ],
      "vote_count": 1714,
      "first_air_date": "2019-11-12",
      "backdrop_path": "/o7qi2v4uWQ8bZ1tW3KI0Ztn2epk.jpg",
      "original_language": "en",
      "id": 82856,
      "vote_average": 8.4,
      "overview": "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
      "poster_path": "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg"
    },
    {
      "original_name": "We Are Who We Are",
      "genre_ids": [
        18
      ],
      "name": "We Are Who We Are",
      "popularity": 158.769,
      "origin_country": [
        "US"
      ],
      "vote_count": 14,
      "first_air_date": "2020-09-14",
      "backdrop_path": "/ioxGhd9jtJT8qGQHuhuodlqaGmX.jpg",
      "original_language": "en",
      "id": 88713,
      "vote_average": 7.2,
      "overview": "Two American kids who live on a U.S. military base in Italy explore friendship, first love, identity, and all the messy exhilaration and anguish of being a teenager.",
      "poster_path": "/33btSKKmjmc24hK9Vj1sRWQGfyh.jpg"
    },
    {
      "original_name": "Family Guy",
      "genre_ids": [
        16,
        35
      ],
      "name": "Family Guy",
      "popularity": 154.552,
      "origin_country": [
        "US"
      ],
      "vote_count": 2392,
      "first_air_date": "1999-01-31",
      "backdrop_path": "/4oE4vT4q0AD2cX3wcMBVzCsME8G.jpg",
      "original_language": "en",
      "id": 1434,
      "vote_average": 6.8,
      "overview": "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
      "poster_path": "/q3E71oY6qgAEiw6YZIHDlHSLwer.jpg"
    },
    {
      "original_name": "Star Trek: Discovery",
      "genre_ids": [
        10759,
        10765
      ],
      "name": "Star Trek: Discovery",
      "popularity": 122.079,
      "origin_country": [
        "US"
      ],
      "vote_count": 811,
      "first_air_date": "2017-09-24",
      "backdrop_path": "/p3McpsDNTNmpbkNBKdNxOFZJeKX.jpg",
      "original_language": "en",
      "id": 67198,
      "vote_average": 7,
      "overview": "Follow the voyages of Starfleet on their missions to discover new worlds and new life forms, and one Starfleet officer who must learn that to truly understand all things alien, you must first understand yourself.",
      "poster_path": "/98RYSYsRNKWgrAAFBn0WfploUG7.jpg"
    },
    {
      "original_name": "Patria",
      "genre_ids": [
        80,
        18
      ],
      "name": "Patria",
      "popularity": 98.61,
      "origin_country": [
        "ES"
      ],
      "vote_count": 46,
      "first_air_date": "2020-09-27",
      "backdrop_path": "/2FdeV6TxG02XL5nBF9NUvoi1BkW.jpg",
      "original_language": "es",
      "id": 85964,
      "vote_average": 6.4,
      "overview": "A look at the impact of Spain’s Basque conflict on ordinary people on both sides, such as the widow of a man killed by the ETA who returns to her home village after the 2011 ceasefire between the separatist group and the Spanish government, and her former intimate friend, the mother of a jailed terrorist.",
      "poster_path": "/6VxzHeOd237QMhnmVYALeB26bn4.jpg"
    },
    {
      "original_name": "デジモンアドベンチャー：",
      "genre_ids": [
        16,
        35,
        10759,
        10765
      ],
      "name": "Digimon Adventure:",
      "popularity": 89.008,
      "origin_country": [
        "JP"
      ],
      "vote_count": 359,
      "first_air_date": "2020-04-05",
      "backdrop_path": "/xsEMAdrDprq3Ldre56Rm0zqbfCA.jpg",
      "original_language": "ja",
      "id": 98034,
      "vote_average": 7.4,
      "overview": "The new anime will take place in 2020 and will feature an all-new story centering on Taichi Yagami when he is in his fifth year in elementary school. His partner is Agumon. The story begins in Tokyo when a large-scale network malfunction occurs. Taichi is preparing for his weekend summer camping trip when the incident happens. Taichi's mother and his younger sister Hikari get stuck on a train that won't stop moving, and Taichi heads to Shibuya in order to help them. However, on his way there, he encounters a strange phenomenon and sweeps him up into the Digital World along with the other DigiDestined.",
      "poster_path": "/7Qspx2eFX0uBSQLLlAKnYrjZgse.jpg"
    },
    {
      "original_name": "ポケットモンスター",
      "genre_ids": [
        16,
        10759,
        10765
      ],
      "name": "Pokémon",
      "popularity": 88.938,
      "origin_country": [
        "JP"
      ],
      "vote_count": 422,
      "first_air_date": "1997-04-01",
      "backdrop_path": "/tvjCdVRkaaab2ezM9BctkAOXeyW.jpg",
      "original_language": "ja",
      "id": 60572,
      "vote_average": 6.7,
      "overview": "Join Satoshi, accompanied by his partner Pikachu, as he travels through many regions, meets new friends and faces new challenges on his quest to become a Pokémon Master.",
      "poster_path": "/rOuGm07PxBhEsK9TaGPRQVJQm1X.jpg"
    },
    {
      "original_name": "런닝맨",
      "genre_ids": [
        35,
        10764
      ],
      "name": "Running Man",
      "popularity": 85.711,
      "origin_country": [
        "KR"
      ],
      "vote_count": 54,
      "first_air_date": "2010-07-11",
      "backdrop_path": "/rHuXgDmrv4vMKgQZ6pu2E2iLJnM.jpg",
      "original_language": "ko",
      "id": 33238,
      "vote_average": 8.1,
      "overview": "A landmark representing Korea, how much do you know?  Korea's representative landmark to let the running man know directly!  The best performers of Korea gathered there! Solve the missions in various places and get out of there until the next morning through game!  Through constant confrontation and urgent confrontation, we will reveal the hidden behind-the-scenes of Korean landmarks!",
      "poster_path": "/2Wmmu1MkqxJ48J7aySET9EKEjXz.jpg"
    },
    {
      "original_name": "The Graham Norton Show",
      "genre_ids": [
        35,
        10767
      ],
      "name": "The Graham Norton Show",
      "popularity": 84.657,
      "origin_country": [
        "GB"
      ],
      "vote_count": 144,
      "first_air_date": "2007-02-22",
      "backdrop_path": "/2pJYis3LUEgFC3UErTQVgmUV1hN.jpg",
      "original_language": "en",
      "id": 1220,
      "vote_average": 7,
      "overview": "Each week celebrity guests join Irish comedian Graham Norton to discuss what's being going on around the world that week. The guests poke fun and share their opinions on the main news stories. Graham is often joined by a band or artist to play the show out.",
      "poster_path": "/vrbqaBXB8AALynQzpWz6JdCPEJS.jpg"
    },
    {
      "original_name": "Young Sheldon",
      "genre_ids": [
        35
      ],
      "name": "Young Sheldon",
      "popularity": 82.501,
      "origin_country": [
        "US"
      ],
      "vote_count": 846,
      "first_air_date": "2017-09-25",
      "backdrop_path": "/ko0Gynw9pMRdcScCjwGYfhJlfWh.jpg",
      "original_language": "en",
      "id": 71728,
      "vote_average": 7.9,
      "overview": "The early life of child genius Sheldon Cooper, later seen in The Big Bang Theory.",
      "poster_path": "/h1XBglOtfAR7lZVIusPd7BLgkHu.jpg"
    },
    {
      "original_name": "Bob's Burgers",
      "genre_ids": [
        16,
        35
      ],
      "name": "Bob's Burgers",
      "popularity": 79.924,
      "origin_country": [
        "US"
      ],
      "vote_count": 391,
      "first_air_date": "2011-01-09",
      "backdrop_path": "/rUABoTPO0OLq2wlHiFWezxpV6fo.jpg",
      "original_language": "en",
      "id": 32726,
      "vote_average": 7.8,
      "overview": "Bob's Burgers follows a third-generation restaurateur, Bob, as he runs Bob's Burgers with the help of his wife and their three kids. Bob and his quirky family have big ideas about burgers, but fall short on service and sophistication. Despite the greasy counters, lousy location and a dearth of customers, Bob and his family are determined to make Bob's Burgers \"grand re-re-re-opening\" a success.",
      "poster_path": "/joPhJJblNtFDOpp6zQlx0xJWsmM.jpg"
    },
    {
      "original_name": "The Undoing",
      "genre_ids": [
        18
      ],
      "name": "The Undoing",
      "popularity": 72.729,
      "origin_country": [
        "US"
      ],
      "vote_count": 10,
      "first_air_date": "2020-10-25",
      "backdrop_path": "/tJjwEzTglpxoWJXwEbZlKLppMhr.jpg",
      "original_language": "en",
      "id": 83851,
      "vote_average": 7.7,
      "overview": "Grace Fraser is a successful therapist on the brink of publishing her first book with a devoted husband and young son who attends an elite private school in New York City. Weeks before her book is published, a chasm opens in her life: a violent death, a missing husband, and, in the place of a man Grace thought she knew, only a chain of terrible revelations.",
      "poster_path": "/3tDbJxobPN3EI2bBebL6zmusmw5.jpg"
    },
    {
      "original_name": "Suburra - La serie",
      "genre_ids": [
        80,
        18
      ],
      "name": "Suburra: Blood on Rome",
      "popularity": 72.528,
      "origin_country": [
        "IT"
      ],
      "vote_count": 44,
      "first_air_date": "2017-10-06",
      "backdrop_path": "/2MLA8lpII4CVNQV7pYGyZRRip67.jpg",
      "original_language": "it",
      "id": 73671,
      "vote_average": 7.5,
      "overview": "In 2008, a fight over land in a seaside town near Rome spirals into a deadly battle between organized crime, corrupt politicians and the Vatican.",
      "poster_path": "/ri1WF0vxDfJcUW8iNUOu8OsmVeJ.jpg"
    },
    {
      "original_name": "The Spanish Princess",
      "id": 86428,
      "name": "The Spanish Princess",
      "popularity": 60.16,
      "vote_count": 236,
      "vote_average": 7.6,
      "first_air_date": "2019-05-05",
      "poster_path": "/4Ec0jIkctpiyKuywPSRj4Pd2jEl.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/eqOAW52GkgbMDGIzpqlz8zwTIiO.jpg",
      "overview": "The beautiful Spanish princess, Catherine of Aragon, navigates the royal lineage of England with an eye on the throne.",
      "origin_country": [
        "US"
      ]
    },
    {
      "original_name": "Superstore",
      "id": 62649,
      "name": "Superstore",
      "popularity": 58.058,
      "vote_count": 162,
      "vote_average": 7,
      "first_air_date": "2015-11-30",
      "poster_path": "/maBJkaBM4UqAttn9UkLCfZEVEfk.jpg",
      "genre_ids": [
        35
      ],
      "original_language": "en",
      "backdrop_path": "/zUCGshcuj2jg8qgHAvgKWq3xan8.jpg",
      "overview": "A hilarious workplace comedy about a unique family of employees at a super-sized mega store. From the bright-eyed newbies and the seen-it-all veterans to the clueless summer hires and the in-it-for-life managers, together they hilariously tackle the day-to-day grind of rabid bargain hunters, riot-causing sales and nap-worthy training sessions.",
      "origin_country": [
        "US"
      ]
    },
    {
      "original_name": "Real Time with Bill Maher",
      "genre_ids": [
        35,
        10767
      ],
      "name": "Real Time with Bill Maher",
      "popularity": 52.428,
      "origin_country": [
        "US"
      ],
      "vote_count": 125,
      "first_air_date": "2003-02-21",
      "backdrop_path": "/cs4wxElH1XPgRLFq1FOtIFpeKqz.jpg",
      "original_language": "en",
      "id": 4419,
      "vote_average": 6.1,
      "overview": "Each week Bill Maher surrounds himself with a panel of guests which include politicians, actors, comedians, musicians and the like to discuss what's going on in the world.",
      "poster_path": "/c3EurMWJu1hXKUeJVvLIoJaN26j.jpg"
    }
  ]
}"""
    }
}