package com.example.composeapp.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.composeapp.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    override val id: Int = 0,
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.me,
    val contentText: String = "Кабаныч, когда узнал, что если сотрудникам не платить, то они умирают от голода",
    val contentImageResId: Int = R.drawable.homeless,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticType.LIKES, count = 27),
    )
): BaseModel, Parcelable {
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
