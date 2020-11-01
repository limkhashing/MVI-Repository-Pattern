package io.limkhashing.mvirepositorypattern.retrofit

import com.google.gson.annotations.SerializedName

class BlogNetworkEntity(

    @SerializedName("pk")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("body")
    var body: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("category")
    var category: String
)