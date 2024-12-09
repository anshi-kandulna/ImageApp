package com.example.myapplication

data class PexelsPhotoResponse(
    val total_results: Int,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val next_page: String?
)

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Long,
    val avg_color: String,
    val src: Src,
    val liked: Boolean,
    val alt: String
)

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)