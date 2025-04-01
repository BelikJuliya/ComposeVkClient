package com.example.composeapp.domain

import com.example.composeapp.R

data class FeedPost(
    val postId: Int = 0,
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
)
