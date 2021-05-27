package com.example.mymoviddb.category

/*
@OptIn(ExperimentalPagingApi::class)
class CategoryMediator(
    private val query: String,
    private val database: TmdbDatabase,
    private val networkService: NetworkService
) : RemoteMediator<Int, ShowResult>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ShowResult>
    ): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.

                    lastItem.id
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = networkService.searchMoviesAsync(
                title = query, page = loadKey?.toInt() ?: 1
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.deleteByQuery(query)
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                userDao.insertAll(response.users)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.nextKey == null
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}*/
