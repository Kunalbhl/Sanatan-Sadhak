package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.data.DailyDharmaRepository
import com.example.data.DailyMantra
import com.example.data.DailyMantraItem
import com.example.data.DailyShlokaItem
import com.example.data.Shloka
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _dailyShlokaItem = MutableStateFlow<DailyShlokaItem>(DailyDharmaRepository.SHLOKAS_LIBRARY.first())
    val dailyShlokaItem: StateFlow<DailyShlokaItem> = _dailyShlokaItem.asStateFlow()

    private val _dailyMantraItem = MutableStateFlow<DailyMantraItem>(DailyDharmaRepository.MANTRAS_LIBRARY.first())
    val dailyMantraItem: StateFlow<DailyMantraItem> = _dailyMantraItem.asStateFlow()

    private val _dailyMantra = MutableStateFlow(
        DailyMantra(
            "Om Namah Shivaya",
            "I bow to Lord Shiva. A powerful mantra for inner peace and destruction of negative energies.",
            "ॐ नमः शिवाय", ""
        )
    )
    val dailyMantra: StateFlow<DailyMantra> = _dailyMantra

    private val _shlokaOfTheDay = MutableStateFlow(
        Shloka(
            "Karmanye Vadhikaraste, Ma phaleshou kada chana",
            "Bhagavad Gita 2.47",
            "You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions."
        )
    )
    val shlokaOfTheDay: StateFlow<Shloka> = _shlokaOfTheDay

    private val _multipleShlokas = MutableStateFlow<List<Shloka>>(emptyList())
    val multipleShlokas: StateFlow<List<Shloka>> = _multipleShlokas

    private val _multipleThoughts = MutableStateFlow<List<DailyMantra>>(emptyList())
    val multipleThoughts: StateFlow<List<DailyMantra>> = _multipleThoughts

    private val _multipleInspirations = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val multipleInspirations: StateFlow<List<Pair<String, String>>> = _multipleInspirations

    init {
        loadDeterministicDailyContent()
    }

    fun loadDeterministicDailyContent(dateStr: String = getCurrentDateKey()) {
        val shloka = DailyDharmaRepository.getDailyShloka(dateStr)
        val mantra = DailyDharmaRepository.getDailyMantra(dateStr)

        _dailyShlokaItem.value = shloka
        _dailyMantraItem.value = mantra

        _shlokaOfTheDay.value = Shloka(
            verse = shloka.verseSanskrit,
            meaning = "${shloka.meaningHi} / ${shloka.meaningEn}",
            source = "${shloka.sourceScriptureEn} ${shloka.chapterVerse}"
        )

        _dailyMantra.value = DailyMantra(
            title = mantra.nameEn,
            content = mantra.sanskrit,
            meaning = "${mantra.meaningHi} / ${mantra.meaningEn}",
            audioUrl = ""
        )

        _multipleShlokas.value = DailyDharmaRepository.SHLOKAS_LIBRARY.take(3).map {
            Shloka(it.verseSanskrit, "${it.meaningHi} / ${it.meaningEn}", "${it.sourceScriptureEn} ${it.chapterVerse}")
        }

        _multipleThoughts.value = DailyDharmaRepository.MANTRAS_LIBRARY.take(3).map {
            DailyMantra(it.nameEn, it.sanskrit, "${it.meaningHi} / ${it.meaningEn}", "")
        }

        _multipleInspirations.value = DailyDharmaRepository.SHLOKAS_LIBRARY.take(3).map {
            Pair(it.practicalWisdomEn, it.practicalWisdomHi)
        }
    }

    fun shuffleDailyContent() {
        loadDeterministicDailyContent()
    }

    private fun getCurrentDateKey(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(Date())
    }
}