package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.data.DailyMantra
import com.example.data.Shloka

class HomeViewModel(application: Application) : AndroidViewModel(application) {

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
        shuffleDailyContent()
    }

    fun shuffleDailyContent() {
        // 1. Select random Shloka of the day
        val shlokasList = listOf(
            Shloka(
                verse = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥",
                meaning = "You have a right to perform your prescribed duty, but you are not entitled to the fruits of action. / तुम्हारा अधिकार केवल कर्म करने का है, उसके फलों पर नहीं।",
                source = "Bhagavad Gita 2.47"
            ),
            Shloka(
                verse = "यदा यदा हि धर्मस्य ग्लानिर्भवति भारत।\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम्॥",
                meaning = "Whenever there is a decline in righteousness and an increase in unrighteousness, O Arjun, at that time I manifest myself on earth. / जब-जब धर्म की हानि और अधर्म की वृद्धि होती है, तब-तब मैं अवतार लेता हूँ।",
                source = "Bhagavad Gita 4.7"
            ),
            Shloka(
                verse = "परित्राणाय साधूनां विनाशाय च दुष्कृताम्।\nधर्मसंस्थापनार्थाय सम्भवामि युगे युगे॥",
                meaning = "To deliver the pious and to annihilate the miscreants, as well as to reestablish the principles of religion, I Myself appear, millennium after millennium. / साधुओं की रक्षा, दुष्टों के विनाश और धर्म की स्थापना के लिए मैं युग-युग में अवतार लेता हूँ।",
                source = "Bhagavad Gita 4.8"
            )
        )
        val shuffledShlokas = shlokasList.shuffled()
        _shlokaOfTheDay.value = shuffledShlokas.first()
        _multipleShlokas.value = shuffledShlokas.take(3)

        // 2. Select random Thought of the day
        val thoughtsList = listOf(
            DailyMantra(
                title = "Divine Love",
                content = "The easiest way to realize God is to see Him in all beings and love them as yourself.",
                meaning = "ईश्वर को प्राप्त करने का सबसे सरल मार्ग है उन्हें सभी जीवों में देखना और उन्हें स्वयं के समान प्रेम करना।",
                audioUrl = ""
            ),
            DailyMantra(
                title = "Inward Peace",
                content = "Peace is not the absence of trouble, but the presence of Divine Grace in your heart.",
                meaning = "शांति कठिनाइयों की अनुपस्थिति नहीं है, बल्कि आपके हृदय में दिव्य कृपा की उपस्थिति है।",
                audioUrl = ""
            ),
            DailyMantra(
                title = "Nishkama Karma",
                content = "Work without worrying about the results. Offer every action to the Divine.",
                meaning = "परिणामों की चिंता किए बिना कार्य करें। प्रत्येक कार्य को परमात्मा को समर्पित करें।",
                audioUrl = ""
            )
        )
        val shuffledThoughts = thoughtsList.shuffled()
        _dailyMantra.value = shuffledThoughts.first()
        _multipleThoughts.value = shuffledThoughts.take(3)
        
        val listEn = listOf(
            "Perform action without attachment to outcomes.",
            "You have the right to work, but never to the fruit of work."
        )
        val listHi = listOf(
            "परिणामों की आसक्ति के बिना कर्म करो।",
            "तुम्हारा अधिकार कर्म पर है, उसके फलों पर नहीं।"
        )
        _multipleInspirations.value = listEn.zip(listHi).shuffled().take(3)
    }
}