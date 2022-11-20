package com.tabi.nytimes.domain.dataService

import com.tabi.nytimes.data.model.toArticleResponse
import com.tabi.nytimes.domain.repository.INetworkRepository
import com.tabi.nytimes.domain.utils.Error
import com.tabi.nytimes.domain.utils.Resource
import java.net.UnknownHostException
import javax.inject.Inject


class ArticleService @Inject constructor(
    private val networkRepository: INetworkRepository,
) {
    suspend fun getArticles(page: Int): Resource {
        return try {
            val response = networkRepository.getArticles(page)
            if (response != null && response.isSuccessful) {
                val movieResponse = response.body()?.toArticleResponse()
                Resource.Success(movieResponse)
            } else {
                Resource.Error(response?.message())

            }
        } catch (unKnownHost: UnknownHostException) {
            Resource.Error(Error.NETWORK_ERROR.message)
        } catch (exception: Exception) {
            Resource.Error(Error.NO_RESULT_FOUND.message)
        }
    }
}