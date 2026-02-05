package com.example.composeapp.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class FeedPost(
    val id: Long = 1,
    val communityId: Long = 2,
    val communityName: String = "/dev/null/",
    val publicationDate: String = "11.05.2024",
    val communityImageUrl: String = "",
    val contentText: String = "Мальдивы – тропическое государство в Индийском океане, расположенное на 26 кольцевидных атоллах, которые состоят из более чем тысячи коралловых островов. Оно славится своими пляжами, голубыми лагунами и огромными рифами.",
    val contentImageUrl: String? = "",
    val statistics: List<StatisticItem> = emptyList<StatisticItem>(),
    val isLiked: Boolean = true
) : Parcelable {

    companion object {

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {

            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
