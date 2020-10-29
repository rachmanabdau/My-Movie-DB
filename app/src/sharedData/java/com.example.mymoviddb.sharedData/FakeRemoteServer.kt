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

    override fun createSeesionAsync(
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
        val responseSuccess = jsonConverter.fromJson(successResponseMovieJson) as MovieModel

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
        val responseSuccess = jsonConverter.fromJson(successResponseMovieJson) as MovieModel

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
        val responseSuccess = jsonConverter.fromJson(successResponseTvShowJson) as TVShowModel

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
        val responseSuccess = jsonConverter.fromJson(successResponseTvShowJson) as TVShowModel

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

    companion object {
        const val successResponseMovieJson = """{
  "page": 1,
  "results": [
    {
      "poster_path": "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
      "adult": false,
      "overview": "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
      "release_date": "2016-08-03",
      "genre_ids": [
        14,
        28,
        80
      ],
      "id": 297761,
      "original_title": "Suicide Squad",
      "original_language": "en",
      "title": "Suicide Squad",
      "backdrop_path": "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
      "popularity": 48.261451,
      "vote_count": 1466,
      "video": false,
      "vote_average": 5.91
    },
    {
      "poster_path": "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
      "adult": false,
      "overview": "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
      "release_date": "2016-07-27",
      "genre_ids": [
        28,
        53
      ],
      "id": 324668,
      "original_title": "Jason Bourne",
      "original_language": "en",
      "title": "Jason Bourne",
      "backdrop_path": "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
      "popularity": 30.690177,
      "vote_count": 649,
      "video": false,
      "vote_average": 5.25
    },
    {
      "poster_path": "/hU0E130tsGdsYa4K9lc3Xrn5Wyt.jpg",
      "adult": false,
      "overview": "One year after outwitting the FBI and winning the public’s adulation with their mind-bending spectacles, the Four Horsemen resurface only to find themselves face to face with a new enemy who enlists them to pull off their most dangerous heist yet.",
      "release_date": "2016-06-02",
      "genre_ids": [
        28,
        12,
        35,
        80,
        9648,
        53
      ],
      "id": 291805,
      "original_title": "Now You See Me 2",
      "original_language": "en",
      "title": "Now You See Me 2",
      "backdrop_path": "/zrAO2OOa6s6dQMQ7zsUbDyIBrAP.jpg",
      "popularity": 29.737342,
      "vote_count": 684,
      "video": false,
      "vote_average": 6.64
    },
    {
      "poster_path": "/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg",
      "adult": false,
      "overview": "A recently cheated on married woman falls for a younger man who has moved in next door, but their torrid affair soon takes a dangerous turn.",
      "release_date": "2015-01-23",
      "genre_ids": [
        53
      ],
      "id": 241251,
      "original_title": "The Boy Next Door",
      "original_language": "en",
      "title": "The Boy Next Door",
      "backdrop_path": "/vj4IhmH4HCMZYYjTMiYBybTWR5o.jpg",
      "popularity": 22.279864,
      "vote_count": 628,
      "video": false,
      "vote_average": 4.13
    },
    {
      "poster_path": "/vOipe2myi26UDwP978hsYOrnUWC.jpg",
      "adult": false,
      "overview": "An orphan boy is raised in the Jungle with the help of a pack of wolves, a bear and a black panther.",
      "release_date": "2016-04-07",
      "genre_ids": [
        12,
        18,
        14
      ],
      "id": 278927,
      "original_title": "The Jungle Book",
      "original_language": "en",
      "title": "The Jungle Book",
      "backdrop_path": "/eIOTsGg9FCVrBc4r2nXaV61JF4F.jpg",
      "popularity": 21.104822,
      "vote_count": 1085,
      "video": false,
      "vote_average": 6.42
    },
    {
      "poster_path": "/tgfRDJs5PFW20Aoh1orEzuxW8cN.jpg",
      "adult": false,
      "overview": "Arthur Bishop thought he had put his murderous past behind him when his most formidable foe kidnaps the love of his life. Now he is forced to travel the globe to complete three impossible assassinations, and do what he does best, make them look like accidents.",
      "release_date": "2016-08-25",
      "genre_ids": [
        80,
        28,
        53
      ],
      "id": 278924,
      "original_title": "Mechanic: Resurrection",
      "original_language": "en",
      "title": "Mechanic: Resurrection",
      "backdrop_path": "/3oRHlbxMLBXHfMqUsx1emwqiuQ3.jpg",
      "popularity": 20.375179,
      "vote_count": 119,
      "video": false,
      "vote_average": 4.59
    },
    {
      "poster_path": "/cGOPbv9wA5gEejkUN892JrveARt.jpg",
      "adult": false,
      "overview": "Fearing the actions of a god-like Super Hero left unchecked, Gotham City’s own formidable, forceful vigilante takes on Metropolis’s most revered, modern-day savior, while the world wrestles with what sort of hero it really needs. And with Batman and Superman at war with one another, a new threat quickly arises, putting mankind in greater danger than it’s ever known before.",
      "release_date": "2016-03-23",
      "genre_ids": [
        28,
        12,
        14
      ],
      "id": 209112,
      "original_title": "Batman v Superman: Dawn of Justice",
      "original_language": "en",
      "title": "Batman v Superman: Dawn of Justice",
      "backdrop_path": "/vsjBeMPZtyB7yNsYY56XYxifaQZ.jpg",
      "popularity": 19.413721,
      "vote_count": 3486,
      "video": false,
      "vote_average": 5.52
    },
    {
      "poster_path": "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
      "adult": false,
      "overview": "An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.",
      "release_date": "2015-05-13",
      "genre_ids": [
        28,
        12,
        878,
        53
      ],
      "id": 76341,
      "original_title": "Mad Max: Fury Road",
      "original_language": "en",
      "title": "Mad Max: Fury Road",
      "backdrop_path": "/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg",
      "popularity": 18.797187,
      "vote_count": 5236,
      "video": false,
      "vote_average": 7.26
    },
    {
      "poster_path": "/5N20rQURev5CNDcMjHVUZhpoCNC.jpg",
      "adult": false,
      "overview": "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.",
      "release_date": "2016-04-27",
      "genre_ids": [
        28,
        53,
        878
      ],
      "id": 271110,
      "original_title": "Captain America: Civil War",
      "original_language": "en",
      "title": "Captain America: Civil War",
      "backdrop_path": "/m5O3SZvQ6EgD5XXXLPIP1wLppeW.jpg",
      "popularity": 16.733457,
      "vote_count": 2570,
      "video": false,
      "vote_average": 6.93
    },
    {
      "poster_path": "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",
      "adult": false,
      "overview": "Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.",
      "release_date": "2015-06-09",
      "genre_ids": [
        28,
        12,
        878,
        53
      ],
      "id": 135397,
      "original_title": "Jurassic World",
      "original_language": "en",
      "title": "Jurassic World",
      "backdrop_path": "/dkMD5qlogeRMiEixC4YNPUvax2T.jpg",
      "popularity": 15.930056,
      "vote_count": 4934,
      "video": false,
      "vote_average": 6.59
    },
    {
      "poster_path": "/gj282Pniaa78ZJfbaixyLXnXEDI.jpg",
      "adult": false,
      "overview": "Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol.",
      "release_date": "2014-11-18",
      "genre_ids": [
        878,
        12,
        53
      ],
      "id": 131631,
      "original_title": "The Hunger Games: Mockingjay - Part 1",
      "original_language": "en",
      "title": "The Hunger Games: Mockingjay - Part 1",
      "backdrop_path": "/83nHcz2KcnEpPXY50Ky2VldewJJ.jpg",
      "popularity": 15.774241,
      "vote_count": 3182,
      "video": false,
      "vote_average": 6.69
    },
    {
      "poster_path": "/dCgm7efXDmiABSdWDHBDBx2jwmn.jpg",
      "adult": false,
      "overview": "Deckard Shaw seeks revenge against Dominic Toretto and his family for his comatose brother.",
      "release_date": "2015-04-01",
      "genre_ids": [
        28,
        80,
        53
      ],
      "id": 168259,
      "original_title": "Furious 7",
      "original_language": "en",
      "title": "Furious 7",
      "backdrop_path": "/ypyeMfKydpyuuTMdp36rMlkGDUL.jpg",
      "popularity": 13.659073,
      "vote_count": 2718,
      "video": false,
      "vote_average": 7.39
    },
    {
      "poster_path": "/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg",
      "adult": false,
      "overview": "The year is 2029. John Connor, leader of the resistance continues the war against the machines. At the Los Angeles offensive, John's fears of the unknown future begin to emerge when TECOM spies reveal a new plot by SkyNet that will attack him from both fronts; past and future, and will ultimately change warfare forever.",
      "release_date": "2015-06-23",
      "genre_ids": [
        878,
        28,
        53,
        12
      ],
      "id": 87101,
      "original_title": "Terminator Genisys",
      "original_language": "en",
      "title": "Terminator Genisys",
      "backdrop_path": "/bIlYH4l2AyYvEysmS2AOfjO7Dn8.jpg",
      "popularity": 13.438976,
      "vote_count": 2334,
      "video": false,
      "vote_average": 5.91
    },
    {
      "poster_path": "/q0R4crx2SehcEEQEkYObktdeFy.jpg",
      "adult": false,
      "overview": "Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.",
      "release_date": "2015-06-17",
      "genre_ids": [
        10751,
        16,
        12,
        35
      ],
      "id": 211672,
      "original_title": "Minions",
      "original_language": "en",
      "title": "Minions",
      "backdrop_path": "/uX7LXnsC7bZJZjn048UCOwkPXWJ.jpg",
      "popularity": 13.001193,
      "vote_count": 2699,
      "video": false,
      "vote_average": 6.55
    },
    {
      "poster_path": "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
      "adult": false,
      "overview": "Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
      "release_date": "2014-11-05",
      "genre_ids": [
        12,
        18,
        878
      ],
      "id": 157336,
      "original_title": "Interstellar",
      "original_language": "en",
      "title": "Interstellar",
      "backdrop_path": "/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg",
      "popularity": 12.481061,
      "vote_count": 5600,
      "video": false,
      "vote_average": 8.12
    },
    {
      "poster_path": "/1ZQVHkvOegv5wVzxD2fphcxl1Ba.jpg",
      "adult": false,
      "overview": "Set after the events of Continental Drift, Scrat's epic pursuit of his elusive acorn catapults him outside of Earth, where he accidentally sets off a series of cosmic events that transform and threaten the planet. To save themselves from peril, Manny, Sid, Diego, and the rest of the herd leave their home and embark on a quest full of thrills and spills, highs and lows, laughter and adventure while traveling to exotic new lands and encountering a host of colorful new characters.",
      "release_date": "2016-06-23",
      "genre_ids": [
        12,
        16,
        35,
        10751,
        878
      ],
      "id": 278154,
      "original_title": "Ice Age: Collision Course",
      "original_language": "en",
      "title": "Ice Age: Collision Course",
      "backdrop_path": "/o29BFNqgXOUT1yHNYusnITsH7P9.jpg",
      "popularity": 12.150474,
      "vote_count": 242,
      "video": false,
      "vote_average": 5.15
    },
    {
      "poster_path": "/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",
      "adult": false,
      "overview": "Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.",
      "release_date": "2016-02-09",
      "genre_ids": [
        28,
        12,
        35,
        10749
      ],
      "id": 293660,
      "original_title": "Deadpool",
      "original_language": "en",
      "title": "Deadpool",
      "backdrop_path": "/nbIrDhOtUpdD9HKDBRy02a8VhpV.jpg",
      "popularity": 12.083976,
      "vote_count": 4834,
      "video": false,
      "vote_average": 7.16
    },
    {
      "poster_path": "/vNCeqxbKyDHL9LUza03V2Im16wB.jpg",
      "adult": false,
      "overview": "A private eye investigates the apparent suicide of a fading porn star in 1970s Los Angeles and uncovers a conspiracy.",
      "release_date": "2016-05-15",
      "genre_ids": [
        28,
        35,
        80,
        9648,
        53
      ],
      "id": 290250,
      "original_title": "The Nice Guys",
      "original_language": "en",
      "title": "The Nice Guys",
      "backdrop_path": "/8GwMVfq8Hsq1EFbw2MYJgSCAckb.jpg",
      "popularity": 11.374819,
      "vote_count": 537,
      "video": false,
      "vote_average": 6.84
    },
    {
      "poster_path": "/bWUeJHbKIyT306WtJFRHoSzX9nk.jpg",
      "adult": false,
      "overview": "A sorority moves in next door to the home of Mac and Kelly Radner who have a young child. The Radner's enlist their former nemeses from the fraternity to help battle the raucous sisters.",
      "release_date": "2016-05-05",
      "genre_ids": [
        35
      ],
      "id": 325133,
      "original_title": "Neighbors 2: Sorority Rising",
      "original_language": "en",
      "title": "Neighbors 2: Sorority Rising",
      "backdrop_path": "/8HuO1RMDI3prfWDkF7t1y8EhLVO.jpg",
      "popularity": 11.178222,
      "vote_count": 414,
      "video": false,
      "vote_average": 5.36
    },
    {
      "poster_path": "/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg",
      "adult": false,
      "overview": "Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.",
      "release_date": "2014-10-10",
      "genre_ids": [
        18,
        10402
      ],
      "id": 244786,
      "original_title": "Whiplash",
      "original_language": "en",
      "title": "Whiplash",
      "backdrop_path": "/6bbZ6XyvgfjhQwbplnUh1LSj1ky.jpg",
      "popularity": 10.776056,
      "vote_count": 2059,
      "video": false,
      "vote_average": 8.29
    }
  ],
  "total_results": 19629,
  "total_pages": 982
}"""

        const val successResponseTvShowJson = """{
  "page": 1,
  "results": [
    {
      "poster_path": "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
      "popularity": 47.432451,
      "id": 31917,
      "backdrop_path": "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
      "vote_average": 5.04,
      "overview": "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
      "first_air_date": "2010-06-08",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        9648
      ],
      "original_language": "en",
      "vote_count": 133,
      "name": "Pretty Little Liars",
      "original_name": "Pretty Little Liars"
    },
    {
      "poster_path": "/esN3gWb1P091xExLddD2nh4zmi3.jpg",
      "popularity": 37.882356,
      "id": 62560,
      "backdrop_path": "/v8Y9yurHuI7MujWQMd8iL3Gy4B5.jpg",
      "vote_average": 7.5,
      "overview": "A contemporary and culturally resonant drama about a young programmer, Elliot, who suffers from a debilitating anti-social disorder and decides that he can only connect to people by hacking them. He wields his skills as a weapon to protect the people that he cares about. Elliot will find himself in the intersection between a cybersecurity firm he works for and the underworld organizations that are recruiting him to bring down corporate America.",
      "first_air_date": "2015-05-27",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        80,
        18
      ],
      "original_language": "en",
      "vote_count": 287,
      "name": "Mr. Robot",
      "original_name": "Mr. Robot"
    },
    {
      "poster_path": "/i6Iu6pTzfL6iRWhXuYkNs8cPdJF.jpg",
      "popularity": 34.376914,
      "id": 37680,
      "backdrop_path": "/8SAQqivlp74MZ7u55ccR1xa0Nby.jpg",
      "vote_average": 6.94,
      "overview": "While running from a drug deal gone bad, Mike Ross, a brilliant young college-dropout, slips into a job interview with one of New York City's best legal closers, Harvey Specter. Tired of cookie-cutter law school grads, Harvey takes a gamble by hiring Mike on the spot after he recognizes his raw talent and photographic memory. Mike and Harvey are a winning team. Even though Mike is a genius, he still has a lot to learn about law. And while Harvey may seem like an emotionless, cold-blooded shark, Mike's sympathy and concern for their cases and clients will help remind Harvey why he went into law in the first place. Mike's other allies in the office include the firm's best paralegal Rachel and Harvey's no-nonsense assistant Donna to help him serve justice. Proving to be an irrepressible duo and invaluable to the practice, Mike and Harvey must keep their secret from everyone including managing partner Jessica and Harvey's arch nemesis Louis, who seems intent on making Mike's life as difficult as possible.",
      "first_air_date": "2011-06-23",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "vote_count": 161,
      "name": "Suits",
      "original_name": "Suits"
    },
    {
      "poster_path": "/jIhL6mlT7AblhbHJgEoiBIOUVl1.jpg",
      "popularity": 29.780826,
      "id": 1399,
      "backdrop_path": "/mUkuc2wyV9dHLG0D0Loaw5pO2s8.jpg",
      "vote_average": 7.91,
      "overview": "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
      "first_air_date": "2011-04-17",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        10765,
        10759,
        18
      ],
      "original_language": "en",
      "vote_count": 1172,
      "name": "Game of Thrones",
      "original_name": "Game of Thrones"
    },
    {
      "poster_path": "/vxuoMW6YBt6UsxvMfRNwRl9LtWS.jpg",
      "popularity": 25.172397,
      "id": 1402,
      "backdrop_path": "/zYFQM9G5j9cRsMNMuZAX64nmUMf.jpg",
      "vote_average": 7.38,
      "overview": "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
      "first_air_date": "2010-10-31",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        10759,
        18
      ],
      "original_language": "en",
      "vote_count": 599,
      "name": "The Walking Dead",
      "original_name": "The Walking Dead"
    },
    {
      "poster_path": "/wQoosZYg9FqPrmI4zeCLRdEbqAB.jpg",
      "popularity": 24.933765,
      "id": 1418,
      "backdrop_path": "/nGsNruW3W27V6r4gkyc3iiEGsKR.jpg",
      "vote_average": 7.21,
      "overview": "The Big Bang Theory is centered on five characters living in Pasadena, California: roommates Leonard Hofstadter and Sheldon Cooper; Penny, a waitress and aspiring actress who lives across the hall; and Leonard and Sheldon's equally geeky and socially awkward friends and co-workers, mechanical engineer Howard Wolowitz and astrophysicist Raj Koothrappali. The geekiness and intellect of the four guys is contrasted for comic effect with Penny's social skills and common sense.",
      "first_air_date": "2007-09-24",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        35
      ],
      "original_language": "en",
      "vote_count": 597,
      "name": "The Big Bang Theory",
      "original_name": "The Big Bang Theory"
    },
    {
      "poster_path": "/igDhbYQTvact1SbNDbzoeiFBGda.jpg",
      "popularity": 22.509632,
      "id": 57243,
      "backdrop_path": "/cVWsigSx97cTw1QfYFFsCMcR4bp.jpg",
      "vote_average": 7.16,
      "overview": "The Doctor looks and seems human. He's handsome, witty, and could be mistaken for just another man in the street. But he is a Time Lord: a 900 year old alien with 2 hearts, part of a gifted civilization who mastered time travel. The Doctor saves planets for a living – more of a hobby actually, and he's very, very good at it. He's saved us from alien menaces and evil from before time began – but just who is he?",
      "first_air_date": "2005-03-26",
      "origin_country": [
        "GB"
      ],
      "genre_ids": [
        28,
        12,
        18,
        878
      ],
      "original_language": "en",
      "vote_count": 251,
      "name": "Doctor Who",
      "original_name": "Doctor Who"
    },
    {
      "poster_path": "/cCDuZqLv6jwnf3cZZq7g3uNLaIu.jpg",
      "popularity": 21.734193,
      "id": 62286,
      "backdrop_path": "/okhLwP26UXHJ4KYGVsERQqp3129.jpg",
      "vote_average": 6.23,
      "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
      "first_air_date": "2015-08-23",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        27
      ],
      "original_language": "en",
      "vote_count": 160,
      "name": "Fear the Walking Dead",
      "original_name": "Fear the Walking Dead"
    },
    {
      "poster_path": "/1yeVJox3rjo2jBKrrihIMj7uoS9.jpg",
      "popularity": 21.173765,
      "id": 1396,
      "backdrop_path": "/eSzpy96DwBujGFj0xMbXBcGcfxX.jpg",
      "vote_average": 8.1,
      "overview": "Breaking Bad is an American crime drama television series created and produced by Vince Gilligan. Set and produced in Albuquerque, New Mexico, Breaking Bad is the story of Walter White, a struggling high school chemistry teacher who is diagnosed with inoperable lung cancer at the beginning of the series. He turns to a life of crime, producing and selling methamphetamine, in order to secure his family's financial future before he dies, teaming with his former student, Jesse Pinkman. Heavily serialized, the series is known for positioning its characters in seemingly inextricable corners and has been labeled a contemporary western by its creator.",
      "first_air_date": "2008-01-19",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "vote_count": 690,
      "name": "Breaking Bad",
      "original_name": "Breaking Bad"
    },
    {
      "poster_path": "/v9zc0cZpy5aPSfAy6Tgb6I1zWgV.jpg",
      "popularity": 19.140976,
      "id": 2190,
      "backdrop_path": "/mWsbqSspO5n5dsvfhduKcAlj4vu.jpg",
      "vote_average": 7.63,
      "overview": "Follows the misadventures of four irreverent grade-schoolers in the quiet, dysfunctional town of South Park, Colorado.",
      "first_air_date": "1997-08-13",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        35,
        16
      ],
      "original_language": "en",
      "vote_count": 153,
      "name": "South Park",
      "original_name": "South Park"
    },
    {
      "poster_path": "/i1zeXFOoHsEiNYsHii3ebS1Pnmz.jpg",
      "popularity": 18.222092,
      "id": 693,
      "backdrop_path": "/8926LtRZhlAUrpCSnwrI6MXCqDH.jpg",
      "vote_average": 6.42,
      "overview": "Desperate Housewives is an American television comedy-drama-mystery series created by Marc Cherry and produced by ABC Studios and Cherry Productions. It aired Sundays at 9 P.M. Eastern/8 P.M. Central, on ABC from October 3, 2004, until May 13, 2012. Executive producer Cherry served as showrunner. Other executive producers since the fourth season included Bob Daily, George W. Perkins, John Pardee, Joey Murphy, David Grossman, and Larry Shaw.\n\nThe main setting of the show was Wisteria Lane, a street in the fictional American town of 'Fairview' in the fictional 'Eagle State'. The show followed the lives of a group of women as seen through the eyes of a dead neighbor who committed suicide in the very first episode. The storyline covers thirteen years of the women's lives over eight seasons, set between the years 2004–2008, and later 2013–2017. They worked through domestic struggles and family life, while facing the secrets, crimes and mysteries hidden behind the doors of their — at the surface — beautiful and seemingly perfect suburban neighborhood.\n\nThe show featured an ensemble cast, headed by Teri Hatcher as Susan Mayer, Felicity Huffman as Lynette Scavo, Marcia Cross as Bree Van de Kamp, and Eva Longoria as Gabrielle Solis. Brenda Strong narrated the show as the deceased Mary Alice Young, appearing sporadically in flashbacks or dream sequences.",
      "first_air_date": "2004-10-03",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        9648,
        18,
        35
      ],
      "original_language": "en",
      "vote_count": 43,
      "name": "Desperate Housewives",
      "original_name": "Desperate Housewives"
    },
    {
      "poster_path": "/yTZQkSsxUFJZJe67IenRM0AEklc.jpg",
      "popularity": 17.908016,
      "id": 456,
      "backdrop_path": "/f5uNbUC76oowt5mt5J9QlqrIYQ6.jpg",
      "vote_average": 7.3,
      "overview": "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
      "first_air_date": "1989-12-16",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        35,
        16,
        10751
      ],
      "original_language": "en",
      "vote_count": 298,
      "name": "The Simpsons",
      "original_name": "The Simpsons"
    },
    {
      "poster_path": "/7Fwo5d29j374khrFJQ7cs5U69cv.jpg",
      "popularity": 17.133592,
      "id": 45253,
      "backdrop_path": "/r8qkc5No5PC75x88PJ5vEdwwQpX.jpg",
      "vote_average": 4.3,
      "overview": "The Super Sentai Series is the name given to the long-running Japanese superhero team genre of shows produced by Toei Co., Ltd., Toei Agency and Bandai, and aired by TV Asahi. The shows are of the tokusatsu genre, featuring live action characters and colorful special effects, and are aimed mainly at children. The Super Sentai Series is one of the most prominent tokusatsu productions in Japan, alongside the Ultra Series and the Kamen Rider Series, which it currently airs alongside in the Super Hero Time programming block on Sundays. Outside Japan, the Super Sentai Series are best known as the source material for the Power Rangers franchise.",
      "first_air_date": "1975-04-05",
      "origin_country": [
        "JP"
      ],
      "genre_ids": [
        12,
        10759,
        10765
      ],
      "original_language": "ja",
      "vote_count": 10,
      "name": "Super Sentai",
      "original_name": "スーパー戦隊シリーズ"
    },
    {
      "poster_path": "/7XFZOcYiBuFDrhqGrEoawF0T30l.jpg",
      "popularity": 16.649778,
      "id": 1411,
      "backdrop_path": "/wJ1D6uvKmc5sqqdYfyNmWMMxS22.jpg",
      "vote_average": 7.11,
      "overview": "Person of Interest follows former CIA paramilitary operative, John Reese, who is presumed dead and teams up with reclusive billionaire Finch to prevent violent crimes in New York City by initiating their own type of justice. With the special training that Reese has had in Covert Operations and Finch's genius software inventing mind, the two are a perfect match for the job that they have to complete. With the help of surveillance equipment, they work \"outside the law\" and get the right criminal behind bars. ",
      "first_air_date": "2011-09-22",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        28,
        12,
        18,
        9648,
        53
      ],
      "original_language": "en",
      "vote_count": 185,
      "name": "Person of Interest",
      "original_name": "Person of Interest"
    },
    {
      "poster_path": "/aI4bobthe7OORg4s2zjm0f0FdC1.jpg",
      "popularity": 16.155372,
      "id": 1416,
      "backdrop_path": "/rIu4XdgSV50B6nhgUuEPuufHsB2.jpg",
      "vote_average": 5.74,
      "overview": "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
      "first_air_date": "2005-03-27",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "vote_count": 119,
      "name": "Grey's Anatomy",
      "original_name": "Grey's Anatomy"
    },
    {
      "poster_path": "/3kl2oI6fhAio35wtz0EkRA3M4Of.jpg",
      "popularity": 15.951948,
      "id": 47640,
      "backdrop_path": "/5WDUW025SEZktkDkbqPA6upFWxK.jpg",
      "vote_average": 7.08,
      "overview": "The Strain is a high concept thriller that tells the story of Dr. Ephraim Goodweather, the head of the Center for Disease Control Canary Team in New York City. He and his team are called upon to investigate a mysterious viral outbreak with hallmarks of an ancient and evil strain of vampirism. As the strain spreads, Eph, his team, and an assembly of everyday New Yorkers, wage war for the fate of humanity itself.",
      "first_air_date": "2014-07-13",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        878,
        18,
        9648
      ],
      "original_language": "en",
      "vote_count": 90,
      "name": "The Strain",
      "original_name": "The Strain"
    },
    {
      "poster_path": "/u0cLcBQITrYqfHsn06fxnQwtqiE.jpg",
      "popularity": 15.71135,
      "id": 1398,
      "backdrop_path": "/8GZ91vtbYOMp05qruAGPezWC0Ja.jpg",
      "vote_average": 7.87,
      "overview": "The Sopranos is an American television drama created by David Chase. The series revolves around the New Jersey-based Italian-American mobster Tony Soprano and the difficulties he faces as he tries to balance the conflicting requirements of his home life and the criminal organization he heads. Those difficulties are often highlighted through his ongoing professional relationship with psychiatrist Jennifer Melfi. The show features Tony's family members and Mafia associates in prominent roles and story arcs, most notably his wife Carmela and his cousin and protégé Christopher Moltisanti.",
      "first_air_date": "1999-01-10",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "vote_count": 121,
      "name": "The Sopranos",
      "original_name": "The Sopranos"
    },
    {
      "poster_path": "/3iFm6Kz7iYoFaEcj4fLyZHAmTQA.jpg",
      "popularity": 15.645593,
      "id": 1622,
      "backdrop_path": "/o9OKe3M06QMLOzTl3l6GStYtnE9.jpg",
      "vote_average": 6.82,
      "overview": "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way. ",
      "first_air_date": "2005-09-13",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        9648,
        10765
      ],
      "original_language": "en",
      "vote_count": 278,
      "name": "Supernatural",
      "original_name": "Supernatural"
    },
    {
      "poster_path": "/rtvezj8Z2NVE9fu83YOU1HimwYP.jpg",
      "popularity": 15.565902,
      "id": 2458,
      "backdrop_path": "/xcIvrXzBaky8umxxHSzb1VaXUZH.jpg",
      "vote_average": 6.24,
      "overview": "CSI: NY is an American police procedural television series that ran on CBS from September 22, 2004 to February 22, 2013 for a total of nine seasons and 197 original episodes. The show follows the investigations of a team of NYPD forensic scientists and police officers identified as \"Crime Scene Investigators\".",
      "first_air_date": "2004-09-21",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        9648
      ],
      "original_language": "en",
      "vote_count": 29,
      "name": "CSI: NY",
      "original_name": "CSI: NY"
    },
    {
      "poster_path": "/2eALZgo89aHezKDRjZMveRjD5gc.jpg",
      "popularity": 15.40679,
      "id": 52,
      "backdrop_path": "/vBCZI4LTOVMGIlrBPhD1LQjDYtY.jpg",
      "vote_average": 7.13,
      "overview": "That '70s Show is an American television period sitcom that originally aired on Fox from August 23, 1998, to May 18, 2006. The series focused on the lives of a group of teenage friends living in the fictional suburban town of Point Place, Wisconsin, from May 17, 1976, to December 31, 1979.\n\nThe main teenage cast members were Topher Grace, Mila Kunis, Ashton Kutcher, Danny Masterson, Laura Prepon, and Wilmer Valderrama. The main adult cast members were Debra Jo Rupp, Kurtwood Smith, Don Stark and, during the first three seasons, Tanya Roberts.",
      "first_air_date": "1998-08-23",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        35
      ],
      "original_language": "en",
      "vote_count": 61,
      "name": "That '70s Show",
      "original_name": "That '70s Show"
    }
  ],
  "total_results": 20000,
  "total_pages": 1000
}"""
    }
}