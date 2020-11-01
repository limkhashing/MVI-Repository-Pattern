package io.limkhashing.mvirepositorypattern.repository

import io.limkhashing.mvirepositorypattern.DataState
import io.limkhashing.mvirepositorypattern.models.Blog
import io.limkhashing.mvirepositorypattern.retrofit.BlogRetrofit
import io.limkhashing.mvirepositorypattern.retrofit.NetworkMapper
import io.limkhashing.mvirepositorypattern.room.BlogDao
import io.limkhashing.mvirepositorypattern.room.CacheMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}