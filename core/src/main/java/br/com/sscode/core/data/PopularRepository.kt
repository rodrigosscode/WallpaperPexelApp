package br.com.sscode.core.data

import androidx.paging.PagingSource
import br.com.sscode.core.model.PhotoDomain

interface PopularRepository {

    fun fetchPopular(pages: Int): PagingSource<Int, PhotoDomain>
}