package com.example.pixabaytt.app.data.api.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.pixabaytt.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CategoryType(@StringRes val categoryTitle: Int): Parcelable {
    BACKGROUNDS(categoryTitle = R.string.background_title),
    FASHION(categoryTitle = R.string.fashion_title),
    NATURE(categoryTitle = R.string.nature_title),
    SCIENCE(categoryTitle = R.string.science_title),
    EDUCATION(categoryTitle = R.string.education_title),
    FEELINGS(categoryTitle = R.string.feelings_title),
    HEALTH(categoryTitle = R.string.health_title),
    PEOPLE(categoryTitle = R.string.people_title),
    RELIGION(categoryTitle = R.string.religion_title),
    PLACES(categoryTitle = R.string.places_title),
    ANIMALS(categoryTitle = R.string.animals_title),
    INDUSTRY(categoryTitle = R.string.industry_title),
    COMPUTER(categoryTitle = R.string.computer_title),
    FOOD(categoryTitle = R.string.food_title),
    SPORTS(categoryTitle = R.string.sports_title),
    TRANSPORTATION(categoryTitle = R.string.transportation_title),
    TRAVEL(categoryTitle = R.string.travel_title),
    BUILDINGS(categoryTitle = R.string.buildings_title),
    BUSINESS(categoryTitle = R.string.business_title),
    MUSIC(categoryTitle = R.string.music_title)
}