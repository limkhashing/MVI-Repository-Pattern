package io.limkhashing.mvirepositorypattern.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.limkhashing.mvirepositorypattern.repository.MainRepository
import io.limkhashing.mvirepositorypattern.retrofit.BlogRetrofit
import io.limkhashing.mvirepositorypattern.retrofit.NetworkMapper
import io.limkhashing.mvirepositorypattern.room.BlogDao
import io.limkhashing.mvirepositorypattern.room.CacheMapper
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}
