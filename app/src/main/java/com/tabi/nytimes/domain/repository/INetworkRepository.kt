package com.tabi.nytimes.domain.repository

import com.tabi.nytimes.data.model.ArticleResponseDTO
import retrofit2.Response

interface INetworkRepository {

    suspend fun getArticles(period: Int): Response<ArticleResponseDTO>?

}