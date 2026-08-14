package com.example.data

import android.content.Context
import com.example.ui.screens.HinduTemple
import com.example.ui.screens.famousTemples

class TempleRepository(context: Context) {
    fun getFamousTemples(): List<HinduTemple> = famousTemples
}
