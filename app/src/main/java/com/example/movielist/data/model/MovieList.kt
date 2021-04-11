package com.example.movielist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Content(
    @SerializedName("name") var name: String? = null,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var id: Int? = null,
    @SerializedName("poster-image") var posterImage: String? = null
)

data class ContentItems(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var id: Int? = null,
    @SerializedName("content")
    var content: List<Content>?
)

data class MovieList(
    @SerializedName("page")
    var page: Page?

)


data class Page(
    @SerializedName("title")
    @Expose
    var title: String?,

    @SerializedName("total-content-items")
    @Expose
    var totalContentItems: String?,

    @SerializedName("page-num")
    @Expose
    var pageNum: String?,

    @SerializedName("page-size")
    @Expose
    var pageSize: String?,

    @SerializedName("content-items")
    @Expose
    var contentItems: ContentItems?

)