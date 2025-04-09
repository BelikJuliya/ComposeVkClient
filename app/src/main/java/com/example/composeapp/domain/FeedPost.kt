package com.example.composeapp.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    override val id: String = "id0",
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val communityImageUrl: String = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fvk.com%2Fphoto-72495085_457334988&psig=AOvVaw0-14hqwyjjHkao69lPMrik&ust=1744223600631000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMizyLKJyYwDFQAAAAAdAAAAABAE",
    val contentText: String = "Кабаныч, когда узнал, что если сотрудникам не платить, то они умирают от голода",
    val contentImageUrl: String? = "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffikiwiki.com%2Fzhivotnye%2F1490-kartinki-krasivye-narisovannye-koshki-57-foto.html&psig=AOvVaw0zuh6FVDR6hCENUKTppxNa&ust=1744223534044000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCPCt7pKJyYwDFQAAAAAdAAAAABAR",
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticType.LIKES, count = 27),
    )
) : BaseModel, Parcelable {

    companion object {

        val navigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
