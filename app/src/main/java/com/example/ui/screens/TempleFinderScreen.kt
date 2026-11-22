package com.example.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.SacredCard
import com.example.ui.viewmodel.SadhakViewModel
import kotlinx.coroutines.launch

data class HinduTemple(
    val id: Long,
    val nameEn: String,
    val nameHi: String,
    val deityEn: String,
    val deityHi: String,
    val lat: Double,
    val lon: Double,
    val descEn: String,
    val descHi: String,
    val significanceEn: String,
    val significanceHi: String,
    val openingHoursEn: String,
    val openingHoursHi: String,
    val dressCodeEn: String,
    val dressCodeHi: String,
    val majorFestivalsEn: String,
    val majorFestivalsHi: String,
    val cityEn: String,
    val cityHi: String,
    val imageUrl: String? = null,
    val distanceKm: Double = 0.0,
    val website: String? = null
)

data class PilgrimageCity(
    val nameEn: String,
    val nameHi: String,
    val lat: Double,
    val lon: Double
)

val holyCities = listOf(
    PilgrimageCity("Varanasi", "वाराणसी", 25.3176, 82.9739),
    PilgrimageCity("Vrindavan", "वृंदावन", 27.5650, 77.7008),
    PilgrimageCity("Ayodhya", "अयोध्या", 26.7956, 82.1944),
    PilgrimageCity("Haridwar", "हरिद्वार", 29.9457, 78.1642),
    PilgrimageCity("Rameshwaram", "रामेश्वरम", 9.2881, 79.3129),
    PilgrimageCity("Kedarnath", "केदारनाथ", 30.7352, 79.0669),
    PilgrimageCity("Tirupati", "तिरुपति", 13.6284, 79.4192),
    PilgrimageCity("Puri", "पुरी", 19.8135, 85.8312),
    PilgrimageCity("Somnath", "सोमनाथ", 20.8880, 70.4012),
    PilgrimageCity("Dwarka", "द्वारका", 22.2442, 68.9685),
    PilgrimageCity("Badrinath", "बद्रीनाथ", 30.7433, 79.4938)
)

val famousTemples = listOf(
    // Varanasi
    HinduTemple(
        id = 1,
        nameEn = "Kashi Vishwanath Temple",
        nameHi = "श्री काशी विश्वनाथ मंदिर",
        deityEn = "Lord Shiva (Jyotirlinga)",
        deityHi = "भगवान शिव (ज्योतिर्लिंग)",
        lat = 25.3109,
        lon = 83.0105,
        descEn = "Kashi Vishwanath Temple is one of the most famous Hindu temples dedicated to Lord Shiva. It is located in Varanasi, Uttar Pradesh, India, on the western bank of the holy river Ganges.",
        descHi = "काशी विश्वनाथ मंदिर भगवान शिव को समर्पित सबसे प्रसिद्ध हिंदू मंदिरों में से एक है। यह पवित्र नदी गंगा के पश्चिमी तट पर उत्तर प्रदेश के वाराणसी में स्थित है।",
        significanceEn = "It is one of the twelve Jyotirlingas, the holiest of Shiva temples. Varanasi is believed to be the city of Moksha (salvation) and the temple is its spiritual epicenter.",
        significanceHi = "यह बारह ज्योतिर्लिंगों में से एक है। मान्यता है कि वाराणसी मोक्ष की नगरी है और यह मंदिर इसका आध्यात्मिक केंद्र है।",
        openingHoursEn = "4:00 AM - 11:00 PM",
        openingHoursHi = "प्रातः 4:00 - रात्रि 11:00",
        dressCodeEn = "Traditional Indian attire recommended. Strict security checks apply.",
        dressCodeHi = "पारंपरिक भारतीय परिधान अनुशंसित। सख्त सुरक्षा जांच लागू।",
        majorFestivalsEn = "Maha Shivratri, Shravan Maas, Dev Deepawali",
        majorFestivalsHi = "महाशिवरात्रि, सावन मास, देव दीपावली",
        cityEn = "Varanasi",
        cityHi = "वाराणसी",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/04/Kashi_Vishwanath_Temple_Varanasi_India.jpg"
    ),
    HinduTemple(
        id = 2,
        nameEn = "Sankat Mochan Hanuman Temple",
        nameHi = "संकट मोचन हनुमान मंदिर",
        deityEn = "Lord Hanuman",
        deityHi = "भगवान हनुमान",
        lat = 25.2819,
        lon = 82.9996,
        descEn = "This historic temple was established by the great saint Goswami Tulsidas, the author of Ramcharitmanas. It is famous for relieving devotees from worries and malefic planetary effects.",
        descHi = "इस ऐतिहासिक मंदिर की स्थापना रामचरितमानस के रचयिता महान संत गोस्वामी तुलसीदास ने की थी। यह भक्तों को संकटों और ग्रह दोषों से मुक्ति दिलाने के लिए प्रसिद्ध है।",
        significanceEn = "Offering 'Sindoor' and 'Besan Laddoo' here is believed to ward off obstacles. Lord Hanuman is depicted in a unique posture facing Lord Ram.",
        significanceHi = "यहाँ सिन्दूर और बेसन के लड्डू चढ़ाने से संकट दूर होते हैं। हनुमान जी को यहाँ भगवान राम की ओर देखते हुए एक अनूठी मुद्रा में दर्शाया गया है।",
        openingHoursEn = "5:00 AM - 10:00 PM",
        openingHoursHi = "प्रातः 5:00 - रात्रि 10:00",
        dressCodeEn = "Modest and respectful attire.",
        dressCodeHi = "मर्यादित और शालीन वस्त्र।",
        majorFestivalsEn = "Hanuman Jayanti, Ram Navami",
        majorFestivalsHi = "हनुमान जयंती, राम नवमी",
        cityEn = "Varanasi",
        cityHi = "वाराणसी",
        imageUrl = "https://images.unsplash.com/photo-1544735716-392fe2489ffa?auto=format&fit=crop&q=80&w=400"
    ),
    HinduTemple(
        id = 3,
        nameEn = "Kaal Bhairav Mandir",
        nameHi = "श्री काल भैरav मंदिर",
        deityEn = "Kaal Bhairav (Shiva)",
        deityHi = "काल भैरव (शिव)",
        lat = 25.3204,
        lon = 83.0135,
        descEn = "Kaal Bhairav is the guardian deity of Varanasi. It is widely believed that no one can reside or visit Varanasi without his spiritual permission.",
        descHi = "काल भैरव वाराणसी के रक्षक देवता हैं। यह माना जाता है कि कोई भी उनकी आध्यात्मिक अनुमति के बिना वाराणसी में प्रवेश या निवास नहीं कर सकता।",
        significanceEn = "Known as the Kotwal (Police Officer) of Varanasi. Devotees receive black threads (ganda) as a protective amulet from the priests.",
        significanceHi = "इन्हें वाराणसी का 'कोतवाल' कहा जाता है। भक्त यहाँ से सुरक्षा कवच के रूप में काला धागा (गंडा) प्राप्त करते हैं।",
        openingHoursEn = "5:00 AM - 1:30 PM, 4:30 PM - 10:00 PM",
        openingHoursHi = "प्रातः 5:00 - दोपहर 1:30, अपराह्न 4:30 - रात्रि 10:00",
        dressCodeEn = "Traditional wear preferred.",
        dressCodeHi = "पारंपरिक वस्त्रों को प्राथमिकता।",
        majorFestivalsEn = "Bhairav Ashtami",
        majorFestivalsHi = "भैरव अष्टमी",
        cityEn = "Varanasi",
        cityHi = "वाराणसी",
        imageUrl = "https://images.unsplash.com/photo-1609137144813-812328828bbf?auto=format&fit=crop&q=80&w=400"
    ),

    // Vrindavan
    HinduTemple(
        id = 4,
        nameEn = "Banke Bihari Temple",
        nameHi = "श्री बांके बिहारी मंदिर",
        deityEn = "Lord Krishna (Bihari Ji)",
        deityHi = "भगवान कृष्ण (बिहारी जी)",
        lat = 27.5651,
        lon = 77.6978,
        descEn = "Established by the legendary saint Swami Haridas, this temple houses the combined black form of Radha-Krishna. The deity is worshipped with deep intimacy and affection.",
        descHi = "महान संगीतज्ञ संत स्वामी हरिदास द्वारा स्थापित, यह मंदिर राधा-कृष्ण के संयुक्त श्याम स्वरूप को समेटे हुए है। यहाँ भगवान की पूजा अत्यंत लाड-प्यार से की जाती है।",
        significanceEn = "The idol is not kept under open display; curtains are drawn every few minutes to prevent Bihari Ji's intense eyes from overwhelming devotees.",
        significanceHi = "यहाँ प्रतिमा को लगातार खुला नहीं रखा जाता; बिहारी जी की नशीली आँखों के प्रभाव से भक्तों को बचाने के लिए हर कुछ मिनट में पर्दा डाला जाता है।",
        openingHoursEn = "7:45 AM - 12:00 PM, 5:30 PM - 9:30 PM (Varies seasonally)",
        openingHoursHi = "प्रातः 7:45 - दोपहर 12:00, शाम 5:30 - रात्रि 9:30",
        dressCodeEn = "Decent and modest clothing. No cameras allowed inside.",
        dressCodeHi = "शालीन परिधान। मंदिर के भीतर कैमरा प्रतिबंधित है।",
        majorFestivalsEn = "Holi (Phoolon ki Holi), Janmashtami, Jhulan Yatra",
        majorFestivalsHi = "होली (फूलों की होली), जन्माष्टमी, झूलन यात्रा",
        cityEn = "Vrindavan",
        cityHi = "वृंदावन",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/06/Bake_bihari.jpg"
    ),
    HinduTemple(
        id = 5,
        nameEn = "Prem Mandir",
        nameHi = "प्रेम मंदिर",
        deityEn = "Radha Krishna & Sita Ram",
        deityHi = "राधा कृष्ण और सीता राम",
        lat = 27.5583,
        lon = 77.6836,
        descEn = "A massive and spiritually magnificent temple built of pure white Italian Carrara marble. It showcases intricate carvings depicting the pastimes of Lord Krishna.",
        descHi = "इतालवी सफेद संगमरमर से निर्मित एक विशाल और भव्य मंदिर। यहाँ भगवान कृष्ण की विभिन्न लीलाओं को दर्शाती अत्यंत बारीक नक्काशी की गई है।",
        significanceEn = "The temple has spectacular colored lighting sequences at night and an outdoor musical fountain depicting the sweet pastimes of Shri Radha Krishna.",
        significanceHi = "रात्रि के समय मंदिर की रंग-बिरंगी रोशनी और श्री कृष्ण लीलाओं को दर्शाता म्यूजिकल फाउंटेन भक्तों के लिए मुख्य आकर्षण है।",
        openingHoursEn = "8:30 AM - 12:00 PM, 4:30 PM - 8:30 PM",
        openingHoursHi = "प्रातः 8:30 - दोपहर 12:00, शाम 4:30 - रात्रि 8:30",
        dressCodeEn = "Respectful casual wear.",
        dressCodeHi = "शालीन वस्त्र।",
        majorFestivalsEn = "Radhashtami, Janmashtami",
        majorFestivalsHi = "राधाष्टमी, जन्माष्टमी",
        cityEn = "Vrindavan",
        cityHi = "वृंदावन",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e0/Prem_Mandir_Vrindavan_Night.jpg"
    ),
    HinduTemple(
        id = 6,
        nameEn = "Radha Raman Temple",
        nameHi = "श्री राधा रमण मंदिर",
        deityEn = "Lord Krishna (Radha Raman)",
        deityHi = "भगवान कृष्ण (राधा रमण)",
        lat = 27.5681,
        lon = 77.7058,
        descEn = "One of the most revered ancient temples of Vrindavan, where the Deity self-manifested from a Shaligram Shila in 1542. Famous for preserving strict, pristine Vedic deity services.",
        descHi = "वृंदावन के सबसे प्रतिष्ठित प्राचीन मंदिरों में से एक, जहाँ 1542 में शालिग्राम शिला से विग्रह स्वतः प्रकट हुआ था। यह अपनी शुद्ध वैदिक पूजा परंपराओं के लिए प्रसिद्ध है।",
        significanceEn = "The fire in the temple kitchen has been burning continuously for over 480 years since the temple's inception, used for cooking divine offering (bhog).",
        significanceHi = "मंदिर की रसोई की अग्नि पिछले 480 से अधिक वर्षों से अनवरत जल रही है, जिसका उपयोग ठाकुर जी का भोग पकाने में किया जाता है।",
        openingHoursEn = "8:00 AM - 12:30 PM, 6:00 PM - 9:30 PM",
        openingHoursHi = "प्रातः 8:00 - दोपहर 12:30, शाम 6:00 - रात्रि 9:30",
        dressCodeEn = "Traditional dhotis and sarees are highly appreciated.",
        dressCodeHi = "धोती और साड़ियों जैसे पारंपरिक परिधानों को प्राथमिकता।",
        majorFestivalsEn = "Janmashtami, Gaur Purnima, Jhulan Yatra",
        majorFestivalsHi = "जन्माष्टमी, गौर पूर्णिमा, झूलन यात्रा",
        cityEn = "Vrindavan",
        cityHi = "वृंदावन",
        imageUrl = "https://images.unsplash.com/photo-1621252179027-94459d278660?auto=format&fit=crop&q=80&w=400"
    ),

    // Ayodhya
    HinduTemple(
        id = 7,
        nameEn = "Shri Ram Janmabhoomi Mandir",
        nameHi = "श्री राम जन्मभूमि मंदिर",
        deityEn = "Balak Ram (Lord Rama)",
        deityHi = "बालक राम (भगवान श्री राम)",
        lat = 26.7922,
        lon = 82.1998,
        descEn = "The historic birthplace of Bhagwan Sri Ram, rebuilt into a magnificent 3-story grand temple in traditional Nagara architectural style, capturing the hearts of millions.",
        descHi = "भगवान श्री राम की ऐतिहासिक जन्मभूमि, जिसे पारंपरिक नागर वास्तुकला शैली में एक भव्य 3-मंजिला मंदिर के रूप में पुनर्जीवित किया गया है।",
        significanceEn = "Constructed entirely without iron or steel, using only pink sandstone from Bansi Paharpur. The idol of 'Balak Ram' (5-year-old child) was consecrated in January 2024.",
        significanceHi = "इसका निर्माण लोहे या स्टील के बिना, बंशी पहाड़पुर के गुलाबी बलुआ पत्थरों से हुआ है। यहाँ 5 वर्षीय बालक राम की प्रतिमा स्थापित है।",
        openingHoursEn = "6:30 AM - 12:00 PM, 2:00 PM - 9:30 PM",
        openingHoursHi = "प्रातः 6:30 - दोपहर 12:00, दोपहर 2:00 - रात्रि 9:30",
        dressCodeEn = "Decent traditional or formal clothing recommended. Bags & smart gadgets are scanned strictly.",
        dressCodeHi = "पारंपरिक या शालीन वस्त्र आवश्यक। इलेक्ट्रॉनिक उपकरण बाहर रखने होंगे।",
        majorFestivalsEn = "Ram Navami, Diwali, Dussehra",
        majorFestivalsHi = "राम नवमी, दीपावली, दशहरा",
        cityEn = "Ayodhya",
        cityHi = "अयोध्या",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b3/Ayodhya_Ram_Mandir_In_January_2024.jpg"
    ),
    HinduTemple(
        id = 8,
        nameEn = "Hanuman Garhi",
        nameHi = "हनुमान गढ़ी",
        deityEn = "Lord Hanuman",
        deityHi = "भगवान हनुमान",
        lat = 26.7972,
        lon = 82.1989,
        descEn = "A massive 10th-century cave temple situated on a hillock. Devotees traditionally ascend 76 steps to reach the shrine of Lord Hanuman, who watches over Ayodhya.",
        descHi = "एक पहाड़ी पर स्थित 10वीं शताब्दी का विशाल मंदिर। भक्तों को हनुमान जी के दर्शन के लिए 76 सीढ़ियाँ चढ़नी पड़ती हैं, जिन्हें अयोध्या का रक्षक माना जाता है।",
        significanceEn = "Customary to visit Hanuman Garhi first to seek permission before paying respects at the Ram Janmabhoomi Temple.",
        significanceHi = "ऐसी मान्यता है कि राम जन्मभूमि जाने से पहले हनुमान जी से अनुमति लेने के लिए हनुमान गढ़ी के दर्शन करने चाहिए।",
        openingHoursEn = "5:00 AM - 10:00 PM",
        openingHoursHi = "प्रातः 5:00 - रात्रि 10:00",
        dressCodeEn = "Comfortable, decent footwear and clothing for climbing stairs.",
        dressCodeHi = "सीढ़ियाँ चढ़ने के लिए आरामदायक और मर्यादित वस्त्र।",
        majorFestivalsEn = "Hanuman Jayanti, Diwali",
        majorFestivalsHi = "हनुमान जयंती, दीपावली",
        cityEn = "Ayodhya",
        cityHi = "अयोध्या",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/77/Hanuman_Garhi_Ayodhya.jpg"
    ),
    HinduTemple(
        id = 9,
        nameEn = "Kanak Bhawan",
        nameHi = "कनक भवन",
        deityEn = "Sita Ram",
        deityHi = "सीता राम",
        lat = 26.7991,
        lon = 82.2012,
        descEn = "Kanak Bhawan is also known as the 'Golden Palace'. Gifted to Goddess Sita by Queen Kaikeyi as a wedding gift, this palace became the private residence of Sita and Ram.",
        descHi = "कनक भवन को 'सोने का महल' भी कहा जाता है। रानी कैकेयी द्वारा माता सीता को विवाह उपहार में दिया गया यह महल सीता-राम का निजी निवास बना।",
        significanceEn = "The inner sanctum features highly beautiful idols of Rama and Sita wearing exquisite gold crowns and ornaments that glitter marvelously.",
        significanceHi = "गर्भगृह में राम और सीता की सुंदर मूर्तियाँ हैं जो उत्तम सोने के मुकुट और आभूषण धारण किए हुए हैं।",
        openingHoursEn = "8:00 AM - 12:00 PM, 4:00 PM - 9:00 PM",
        openingHoursHi = "प्रातः 8:00 - दोपहर 12:00, शाम 4:00 - रात्रि 9:00",
        dressCodeEn = "Modest clothing.",
        dressCodeHi = "शालीन वस्त्र।",
        majorFestivalsEn = "Sita Vivah Panchami, Ram Navami",
        majorFestivalsHi = "विवाह पंचमी, राम नवमी",
        cityEn = "Ayodhya",
        cityHi = "अयोध्या",
        imageUrl = "https://images.unsplash.com/photo-1544735716-392fe2489ffa?auto=format&fit=crop&q=80&w=400"
    ),

    // Haridwar
    HinduTemple(
        id = 10,
        nameEn = "Mansa Devi Temple",
        nameHi = "मनसा देवी मंदिर",
        deityEn = "Goddess Mansa (Shakti)",
        deityHi = "माता मनसा देवी (शक्ति)",
        lat = 29.9564,
        lon = 78.1611,
        descEn = "Perched on Bilwa Parvat on the Sivalik Hills, this temple is dedicated to Goddess Mansa, who emerged from the mind of Sage Kashyap and fulfills all desires.",
        descHi = "शिवालिक पहाड़ियों पर बिल्व पर्वत पर स्थित यह मंदिर देवी मनसा को समर्पित है, जो महर्षि कश्यप के मन से उत्पन्न हुई थीं और सभी मनोकामनाएं पूर्ण करती हैं।",
        significanceEn = "One of the Siddh Peethas. Devotees tie sacred threads onto a holy tree here to make wishes, and return to untie them when fulfilled.",
        significanceHi = "यह एक सिद्ध पीठ है। श्रद्धालु यहाँ एक पवित्र वृक्ष पर मनोकामना पूर्ति के लिए धागा बांधते हैं और पूर्ण होने पर उसे खोलते हैं।",
        openingHoursEn = "5:00 AM - 9:00 PM",
        openingHoursHi = "प्रातः 5:00 - रात्रि 9:00",
        dressCodeEn = "Comfortable traditional or casual clothing. Ropeway or staircase available.",
        dressCodeHi = "आरामदायक पारंपरिक या आकस्मिक वस्त्र। रोपवे या सीढ़ियों का मार्ग उपलब्ध।",
        majorFestivalsEn = "Navratri, Ganga Dussehra",
        majorFestivalsHi = "नवरात्रि, गंगा दशहरा",
        cityEn = "Haridwar",
        cityHi = "हरिद्वार",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/08/Mansa_Devi_Temple_Haridwar.jpg"
    ),

    // Rameshwaram
    HinduTemple(
        id = 11,
        nameEn = "Ramanathaswamy Temple",
        nameHi = "श्री रामनाथस्वामी मंदिर",
        deityEn = "Lord Shiva (Jyotirlinga)",
        deityHi = "भगवान शिव (ज्योतिर्लिंग)",
        lat = 9.2881,
        lon = 79.3129,
        descEn = "Ramanathaswamy Temple is situated on Rameswaram island in Tamil Nadu. Lord Shiva was worshipped here by Sri Ram to wash away sins after defeating Ravana.",
        descHi = "तमिलनाडु के रामेश्वरम द्वीप पर स्थित यह मंदिर अत्यंत पवित्र है। रावण का वध करने के बाद ब्रह्महत्या दोष से मुक्ति के लिए श्री राम ने यहाँ पार्थिव शिवलिंग बनाकर शिव की आराधना की थी।",
        significanceEn = "It features the longest corridor among all Hindu temples in the world. Devotees take a sacred bath in the 22 holy wells (Theerthams) inside the temple before darshan.",
        significanceHi = "इस मंदिर का गलियारा विश्व के सभी हिंदू मंदिरों में सबसे लंबा है। दर्शन से पहले मंदिर परिसर के भीतर स्थित 22 पवित्र कुंडों (तीर्थम) में स्नान करने की प्रथा है।",
        openingHoursEn = "5:00 AM - 1:00 PM, 3:00 PM - 9:00 PM",
        openingHoursHi = "प्रातः 5:00 - दोपहर 1:00, अपराह्न 3:00 - रात्रि 9:00",
        dressCodeEn = "Strict dress code: Dhotis / Pants for men, Saree / Salwar for women. Wet clothes from holy bath are dried in designated areas.",
        dressCodeHi = "सख्त नियम: पुरुषों के लिए धोती/पैंट, महिलाओं के लिए साड़ी/सलवार।",
        majorFestivalsEn = "Maha Shivratri, Thirukalyanam",
        majorFestivalsHi = "महाशिवरात्रि, तिरुकल्याणम",
        cityEn = "Rameshwaram",
        cityHi = "रामेश्वरम",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/ad/Rameshwaram_Temple_Corridor_India.jpg"
    ),

    // Kedarnath
    HinduTemple(
        id = 12,
        nameEn = "Kedarnath Temple",
        nameHi = "श्री केदारनाथ मंदिर",
        deityEn = "Lord Shiva (Jyotirlinga)",
        deityHi = "भगवान शिव (ज्योतिर्लिंग)",
        lat = 30.7352,
        lon = 79.0669,
        descEn = "Located at 3,583 meters altitude in the snow-covered Garhwal Himalayas near Mandakini river. Originally built by the Pandavas, this holy shrine is one of the ultimate spiritual destinations.",
        descHi = "मंदाकिनी नदी के समीप बर्फ से ढके गढ़वाल हिमालय में 3,583 मीटर की ऊंचाई पर स्थित। पांडवों द्वारा स्थापित यह पवित्र मंदिर हिंदुओं का परम आध्यात्मिक गंतव्य है।",
        significanceEn = "Highest of the 12 Jyotirlingas, and a key shrine of both Char Dham and Panch Kedar. Closed for 6 winter months due to heavy snow; the deity is moved to Ukhimath.",
        significanceHi = "12 ज्योतिर्लिंगों में सबसे ऊंचाई पर स्थित और चार धाम का मुख्य अंग। भारी बर्फबारी के कारण शीतकाल में 6 महीने बंद रहता है, जब भगवान की पूजा उखीमठ में होती है।",
        openingHoursEn = "4:00 AM - 9:00 PM (Opens late April to early November)",
        openingHoursHi = "प्रातः 4:00 - रात्रि 9:00 (अप्रैल के अंत से नवंबर की शुरुआत तक क्रियाशील)",
        dressCodeEn = "Warm woolens are mandatory. Sturdy trekking footwear recommended.",
        dressCodeHi = "गर्म ऊनी कपड़े अनिवार्य। चढ़ाई के लिए मजबूत जूते अनुशंसित।",
        majorFestivalsEn = "Badri-Kedar Utsav, Samadhi Puja",
        majorFestivalsHi = "बद्री-केदार उत्सव, समाधि पूजा",
        cityEn = "Kedarnath",
        cityHi = "केदारनाथ",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1b/Kedarnath_Temple_with_Snow-clad_Mountains.jpg"
    ),
    HinduTemple(
        id = 13,
        nameEn = "Dwarkadhish Temple",
        nameHi = "श्री द्वारकाधीश मंदिर",
        deityEn = "Lord Krishna (Dwarkadhish)",
        deityHi = "भगवान कृष्ण (द्वारकाधीश)",
        lat = 22.2442,
        lon = 68.9685,
        descEn = "A majestic 5-story temple dedicated to Lord Krishna, believed to have been established by His grandson Vajranabh. It is a key part of the Char Dham pilgrimage.",
        descHi = "भगवान कृष्ण को समर्पित एक भव्य 5-मंजिला मंदिर, जिसके बारे में मान्यता है कि इसकी स्थापना उनके प्रपौत्र वज्रनाभ ने की थी। यह चार धाम का प्रमुख हिस्सा है।",
        significanceEn = "Known as Jagat Mandir. Built with 72 pillars, the temple flag (dhwaj) is changed five times daily and depicts the sun and moon.",
        significanceHi = "इसे 'जगत मंदिर' भी कहा जाता है। 72 स्तंभों पर बने इस मंदिर की ध्वजा दिन में पांच बार बदली जाती है जिस पर सूर्य और चंद्रमा का चित्र होता है।",
        openingHoursEn = "6:30 AM - 1:00 PM, 5:00 PM - 9:30 PM",
        openingHoursHi = "प्रातः 6:30 - दोपहर 1:00, शाम 5:00 - रात्रि 9:30",
        dressCodeEn = "Decent traditional or modest clothing is strictly required.",
        dressCodeHi = "मर्यादित और शालीन परिधान अनिवार्य।",
        majorFestivalsEn = "Janmashtami, Holi, Rukmini Vivah",
        majorFestivalsHi = "जन्माष्टमी, होली, रुक्मिणी विवाह",
        cityEn = "Dwarka",
        cityHi = "द्वारका",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/73/Dwarka_Dwarkadhish_Temple_by_r_k_singh.jpg"
    ),
    HinduTemple(
        id = 14,
        nameEn = "Somnath Jyotirlinga Temple",
        nameHi = "श्री सोमनाथ ज्योतिर्लिंग मंदिर",
        deityEn = "Lord Shiva (Jyotirlinga)",
        deityHi = "भगवान शिव (ज्योतिर्लिंग)",
        lat = 20.8880,
        lon = 70.4012,
        descEn = "Somnath Temple is the first among the twelve holy Jyotirlingas of Lord Shiva. Located on the coast of Prabhas Patan, Gujarat, it has been rebuilt several times after historical destructions.",
        descHi = "भगवान शिव के बारह ज्योतिर्लिंगों में सोमनाथ प्रथम स्थान पर है। गुजरात के प्रभास पाटन के समुद्र तट पर स्थित यह मंदिर इतिहास में कई बार नष्ट किए जाने के बाद पुनर्निर्मित हुआ।",
        significanceEn = "Mentioned in Rigveda. The 'Baan Stambh' (Arrow Pillar) on the shore indicates a straight, unobstructed sea route to the South Pole.",
        significanceHi = "ऋग्वेद में इसका उल्लेख है। समुद्र तट पर स्थित 'बाण स्तंभ' दर्शाता है कि यहाँ से दक्षिण ध्रुव तक बिना किसी बाधा के सीधी समुद्री दूरी है।",
        openingHoursEn = "6:00 AM - 9:30 PM",
        openingHoursHi = "प्रातः 6:00 - रात्रि 9:30",
        dressCodeEn = "Formal or traditional dress. No leather items, mobiles or cameras allowed.",
        dressCodeHi = "शालीन या पारंपरिक परिधान। चमड़े की वस्तुएं और मोबाइल प्रतिबंधित।",
        majorFestivalsEn = "Maha Shivratri, Kartik Purnima, Shravan Mahotsav",
        majorFestivalsHi = "महाशिवरात्रि, कार्तिक पूर्णिमा, सावन महोत्सव",
        cityEn = "Somnath",
        cityHi = "सोमनाथ",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/da/Somnath_temple_Gujarat.JPEG"
    ),
    HinduTemple(
        id = 15,
        nameEn = "Sri Venkateswara Swamy Temple",
        nameHi = "श्री वेंकटेश्वर स्वामी मंदिर",
        deityEn = "Lord Venkateswara (Vishnu)",
        deityHi = "भगवान वेंकटेश्वर (विष्णु)",
        lat = 13.6833,
        lon = 79.3500,
        descEn = "Located on the seventh peak (Venkatadri) of Tirumala Hills, this temple is also known as the Temple of Seven Hills. Lord Venkateswara has manifested here in Kali Yuga to save humanity.",
        descHi = "तिरुमला की पहाड़ियों की सातवीं चोटी (वेंकटाद्रि) पर स्थित यह मंदिर 'सप्तगिरि मंदिर' के नाम से भी विख्यात है। मान्यता है कि कलियुग में मानवता के उद्धार हेतु भगवान यहाँ प्रकट हुए थे।",
        significanceEn = "One of the richest temples in the world. Devotees donate hair (tonsuring) as a symbol of surrendering ego to the Lord.",
        significanceHi = "विश्व के सबसे धनी मंदिरों में से एक। अहंकार समर्पण के प्रतीक के रूप में यहाँ बाल अर्पण (मुंडन) करने की प्रथा है।",
        openingHoursEn = "3:00 AM - Midnight (Varies according to Darshan type)",
        openingHoursHi = "प्रातः 3:00 - मध्यरात्रि (दर्शन के प्रकारानुसार परिवर्तनशील)",
        dressCodeEn = "Strict dress code: Dhoti/Kurta for men, Saree/Salwar for women. Western wear is prohibited.",
        dressCodeHi = "सख्त नियम: पुरुषों के लिए धोती/कुर्ता, महिलाओं के लिए साड़ी/सलवार। पश्चिमी परिधान वर्जित।",
        majorFestivalsEn = "Srivari Brahmotsavam, Vaikuntha Ekadashi",
        majorFestivalsHi = "श्रीवारी ब्रह्मोत्सव, वैकुंठ एकादशी",
        cityEn = "Tirupati",
        cityHi = "तिरुपति",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/4e/Tirumala_Tirupati_Temple_Refurbished_Ananda_Nilayam.jpg"
    ),
    HinduTemple(
        id = 16,
        nameEn = "Jagannath Temple",
        nameHi = "श्री जगन्नाथ मंदिर",
        deityEn = "Lord Jagannath (Krishna)",
        deityHi = "भगवान जगन्नाथ (कृष्ण)",
        lat = 19.8135,
        lon = 85.8312,
        descEn = "An exceptionally sacred temple in Odisha dedicated to Lord Jagannath, his brother Balabhadra and sister Subhadra. It is a crucial Char Dham site famous for its mystical architecture.",
        descHi = "ओडिशा में स्थित एक अत्यंत पवित्र मंदिर जो भगवान जगन्नाथ, उनके भ्राता बलभद्र और भगिनी सुभद्रा को समर्पित है। यह चार धाम का प्रमुख हिस्सा है।",
        significanceEn = "World-famous Rath Yatra. The temple shadow never falls on the ground, and the temple flag always flutters opposite to the wind direction.",
        significanceHi = "विश्व प्रसिद्ध रथ यात्रा। मंदिर की परछाई कभी जमीन पर नहीं पड़ती और मंदिर का ध्वज हमेशा हवा की विपरीत दिशा में लहराता है।",
        openingHoursEn = "5:00 AM - 11:00 PM",
        openingHoursHi = "प्रातः 5:00 - रात्रि 11:00",
        dressCodeEn = "Modest traditional dress only. Only practicing Hindus are allowed inside.",
        dressCodeHi = "पारंपरिक और मर्यादित वस्त्र। केवल सनातनी हिंदुओं के लिए प्रवेश।",
        majorFestivalsEn = "Rath Yatra, Snana Yatra, Chandan Yatra",
        majorFestivalsHi = "रथ यात्रा, स्नान यात्रा, चंदन यात्रा",
        cityEn = "Puri",
        cityHi = "पुरी",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/15/Jagannatha_Temple%2C_Puri.jpg"
    ),
    HinduTemple(
        id = 17,
        nameEn = "Badrinath Temple",
        nameHi = "श्री बद्रीनाथ मंदिर",
        deityEn = "Lord Badrinarayan (Vishnu)",
        deityHi = "भगवान बद्रीनारायण (विष्णु)",
        lat = 30.7433,
        lon = 79.4938,
        descEn = "Situated on the banks of Alaknanda River in Uttarakhand, this ancient temple is dedicated to Lord Vishnu in His self-manifested meditative posture.",
        descHi = "उत्तराखंड में अलकनंदा नदी के तट पर स्थित यह प्राचीन मंदिर भगवान विष्णु के ध्यानमग्न स्वयंभू रूप को समर्पित है।",
        significanceEn = "Considered the foremost of the Char Dham pilgrimage. The idol is carved of black Shaligram stone, found in Gandaki river.",
        significanceHi = "चार धाम यात्रा का सबसे महत्वपूर्ण स्थल। विग्रह शालिग्राम शिला से निर्मित है जो गंडकी नदी में पाई जाती है।",
        openingHoursEn = "4:30 AM - 9:00 PM (Closed during winter months)",
        openingHoursHi = "प्रातः 4:30 - रात्रि 9:00 (शीतकाल में बंद रहता है)",
        dressCodeEn = "Warm traditional or decent clothes. Highly respectful attire.",
        dressCodeHi = "गर्म मर्यादित वस्त्र। शालीन वस्त्र ही स्वीकार्य।",
        majorFestivalsEn = "Badri Kedar festival, Mata Murti Mela",
        majorFestivalsHi = "बद्री केदार उत्सव, माता मूर्ति मेला",
        cityEn = "Badrinath",
        cityHi = "बद्रीनाथ",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/3/30/Badrinath_Temple_with_Snowy_Mountain.jpg"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun TempleFinderView(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val context = LocalContext.current

    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 300 }
    }
    val coroutineScope = rememberCoroutineScope()

    var hasPermission by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }
    var currentLatitude by remember { mutableStateOf(25.3176) } // Default: Varanasi
    var currentLongitude by remember { mutableStateOf(82.9739) }
    var isLocating by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCityName by remember { mutableStateOf("Varanasi") }
    var selectedDeityFilter by remember { mutableStateOf("All") }

    // Detail expanded state
    var expandedTempleId by remember { mutableStateOf<Long?>(null) }

    // Geolocation launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        hasPermission = granted
        if (!granted) {
            locationError = if (isEng) "Location permission denied. Using selected pilgrimage hub coordinates." else "स्थान अनुमति अस्वीकार। चुने हुए तीर्थ स्थल के निर्देशांक का उपयोग किया जा रहा है।"
        }
    }

    val requestLocationUpdate = {
        isLocating = true
        locationError = null
        coroutineScope.launch {
            kotlinx.coroutines.delay(5000)
            if (isLocating) {
                isLocating = false
                locationError = if (isEng) "GPS timed out. Choose a city below." else "जीपीएस समय समाप्त। नीचे दी गई तीर्थ नगरी चुनें।"
            }
        }
        val attributionContext = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context.createAttributionContext("temple_finder")
        } else {
            context
        }
        val locationManager = attributionContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                locationError = if (isEng) "GPS disabled. Choose a pilgrimage city below." else "जीपीएस बंद है। नीचे दी गई पावन तीर्थ नगरी चुनें।"
                isLocating = false
            } else {
                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        currentLatitude = location.latitude
                        currentLongitude = location.longitude
                        isLocating = false
                        selectedCityName = "Current GPS"
                        locationManager.removeUpdates(this)
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, listener)
                } else if (isGpsEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, listener)
                }
            }
        } catch (e: SecurityException) {
            locationError = e.message
            isLocating = false
        }
    }

    // Check real permission status on first load without prompting automatically
    LaunchedEffect(Unit) {
        val status = com.example.utils.PermissionManager.getLocationPermissionStatus(context)
        if (status == com.example.utils.AppPermissionStatus.GRANTED) {
            hasPermission = true
            requestLocationUpdate()
        }
    }

    // Trigger update if permission is granted
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            requestLocationUpdate()
        }
    }

    // Filter and compute distance
    // Live temples from OpenStreetMap around the active point (GPS, chosen city or searched place)
    var liveTemples by remember { mutableStateOf<List<HinduTemple>>(emptyList()) }
    var isLoadingLive by remember { mutableStateOf(false) }
    var liveFailed by remember { mutableStateOf(false) }
    LaunchedEffect(currentLatitude, currentLongitude) {
        isLoadingLive = true
        liveFailed = false
        val found = com.example.data.NearbyTemples.around(currentLatitude, currentLongitude)
        liveTemples = found
        liveFailed = found.isEmpty()
        isLoadingLive = false
    }
    // Search a temple or place anywhere in the world when typing
    var searchHits by remember { mutableStateOf<List<com.example.data.NearbyTemples.Place>>(emptyList()) }
    LaunchedEffect(searchQuery) {
        searchHits = emptyList()
        if (searchQuery.trim().length >= 3) {
            kotlinx.coroutines.delay(900)
            searchHits = com.example.data.NearbyTemples.search(searchQuery.trim())
        }
    }

    val allTemples = remember(liveTemples, searchHits) {
        val curated = famousTemples
        val fromSearch = searchHits.mapNotNull { it.temple }
        val extra = (fromSearch + liveTemples).filter { lt ->
            curated.none { c ->
                val r = FloatArray(1)
                Location.distanceBetween(c.lat, c.lon, lt.lat, lt.lon, r)
                r[0] < 400f
            }
        }
        (curated + extra).distinctBy { it.id }
    }

    val processedTemples = remember(searchQuery, selectedCityName, selectedDeityFilter, currentLatitude, currentLongitude, allTemples) {
        allTemples.filter { temple ->
            // Filter by search query
            val queryMatch = if (searchQuery.isBlank()) true else if (searchHits.any { h -> h.temple?.id == temple.id }) true else {
                temple.nameEn.contains(searchQuery, ignoreCase = true) ||
                temple.nameHi.contains(searchQuery, ignoreCase = true) ||
                temple.deityEn.contains(searchQuery, ignoreCase = true) ||
                temple.deityHi.contains(searchQuery, ignoreCase = true) ||
                temple.descEn.contains(searchQuery, ignoreCase = true) ||
                temple.descHi.contains(searchQuery, ignoreCase = true) ||
                temple.cityEn.contains(searchQuery, ignoreCase = true) ||
                temple.cityHi.contains(searchQuery, ignoreCase = true)
            }

            // Filter by deity category
            val deityMatch = when (selectedDeityFilter) {
                "All" -> true
                "Shiva" -> temple.deityEn.contains("Shiva", ignoreCase = true)
                "Krishna" -> temple.deityEn.contains("Krishna", ignoreCase = true)
                "Rama" -> temple.deityEn.contains("Rama", ignoreCase = true) || temple.nameEn.contains("Ram", ignoreCase = true)
                "Hanuman" -> temple.deityEn.contains("Hanuman", ignoreCase = true)
                "Devi" -> temple.deityEn.contains("Goddess", ignoreCase = true) || temple.deityEn.contains("Shakti", ignoreCase = true) || temple.deityEn.contains("Annapurna", ignoreCase = true) || temple.deityEn.contains("Mansa", ignoreCase = true)
                else -> true
            }

            queryMatch && deityMatch
        }.map { temple ->
            // Calculate dynamic distance in Km from current coordinates (GPS or active city)
            val results = FloatArray(1)
            Location.distanceBetween(currentLatitude, currentLongitude, temple.lat, temple.lon, results)
            temple.copy(distanceKm = (results[0] / 1000.0))
        }.sortedBy { it.distanceKm }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("temple_finder_container")
    ) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                // Search, location and filters scroll away with the list
                item {
                    Column {
                // Controls Header Card
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = "Loc", tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEng) "Sacred Mandir Directory" else "पावन हिंदू मंदिर निर्देशिका",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.weight(1f))
                    
                            if (isLocating) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            } else {
                                IconButton(
                                    onClick = {
                                        if (hasPermission) {
                                            requestLocationUpdate()
                                        } else {
                                            permissionLauncher.launch(
                                                arrayOf(
                                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                                )
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MyLocation, 
                                        contentDescription = "Use My Location", 
                                        tint = if (selectedCityName == "Current GPS") MaterialTheme.colorScheme.primary else Color.Gray
                                    )
                                }
                            }
                        }

                        locationError?.let { err ->
                            Text(text = err, color = MaterialTheme.colorScheme.error, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        // Pilgrimage Shortcuts
                        Text(
                            text = if (isEng) "Set reference city or explore:" else "तीर्थ नगरी चुनें (दूरी गणना हेतु):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        androidx.compose.foundation.lazy.LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(holyCities) { city ->
                                val isSelected = selectedCityName == city.nameEn
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        isLocating = false
                                        locationError = null
                                        currentLatitude = city.lat
                                        currentLongitude = city.lon
                                        selectedCityName = city.nameEn
                                    },
                                    label = { Text(if (isEng) city.nameEn else city.nameHi, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                        selectedLabelColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Search Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(if (isEng) "Search any temple or place in the world..." else "विश्व का कोई भी मंदिर या स्थान खोजें...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Places found by the worldwide search: tap to see temples around that place
                if (searchHits.isNotEmpty()) {
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(searchHits.take(5)) { hit ->
                            SuggestionChip(
                                onClick = {
                                    currentLatitude = hit.lat
                                    currentLongitude = hit.lon
                                    selectedCityName = hit.name
                                    searchQuery = ""
                                },
                                label = { Text((if (isEng) "Temples near " else "मंदिर: ") + hit.name, fontSize = 11.sp, maxLines = 1) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Deity Category Chips
                androidx.compose.foundation.lazy.LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val filters = listOf(
                        Pair("All", if (isEng) "All Deities" else "सभी देव"),
                        Pair("Shiva", if (isEng) "Mahadev Shiva" else "शिव शंकर"),
                        Pair("Krishna", if (isEng) "Shri Krishna" else "श्री कृष्ण"),
                        Pair("Rama", if (isEng) "Bhagwan Ram" else "श्री राम"),
                        Pair("Hanuman", if (isEng) "Hanuman Ji" else "हनुमान जी"),
                        Pair("Devi", if (isEng) "Maa Shakti" else "भगवती देवी")
                    )
                    items(filters) { item ->
                        val isSelected = selectedDeityFilter == item.first
                        SuggestionChip(
                            onClick = { selectedDeityFilter = item.first },
                            label = { Text(item.second, fontSize = 11.sp) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
                                labelColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (isEng) "Discovered Temples (${processedTemples.size})" else "पावन तीर्थ स्थल (${processedTemples.size})",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    if (isLoadingLive) {
                        androidx.compose.material3.CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp)
                    }
                }
                if (liveFailed && !isLoadingLive) {
                    Text(
                        text = if (isEng) "Live temple data is unavailable right now (check your internet). Showing featured temples." else "लाइव मंदिर डेटा अभी उपलब्ध नहीं है (इंटरनेट जाँचें)। प्रमुख मंदिर दिखाए जा रहे हैं।",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
        
                    }
                }
                if (processedTemples.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isEng) "No temples match your filters." else "चयनित फिल्टर के अनुसार कोई मंदिर नहीं मिला।",
                                fontSize = 13.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(processedTemples) { temple ->
                        val isExpanded = expandedTempleId == temple.id
                        SacredCard(
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    // Image
                                    if (!temple.imageUrl.isNullOrBlank()) {
                                        AsyncImage(
                                            model = temple.imageUrl,
                                            contentDescription = if (isEng) temple.nameEn else temple.nameHi,
                                            modifier = Modifier
                                                .size(76.dp)
                                                .clip(RoundedCornerShape(8.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(76.dp)
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(Icons.Default.Place, contentDescription = "Pin", tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = if (isEng) temple.nameEn else temple.nameHi,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                        Text(
                                            text = if (isEng) "Deity: ${temple.deityEn}" else "देवता: ${temple.deityHi}",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "📍 " + (if (isEng) temple.cityEn else temple.cityHi).let { if (it.isBlank()) "" else "$it • " } + String.format("%.1f km away", temple.distanceKm),
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        if ((if (isEng) temple.descEn else temple.descHi).isNotBlank()) Text(
                                            text = if (isEng) temple.descEn else temple.descHi,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                                            lineHeight = 15.sp
                                        )
                                    }
                                }

                                AnimatedVisibility(
                                    visible = isExpanded,
                                    enter = expandVertically() + fadeIn(),
                                    exit = shrinkVertically() + fadeOut()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp)
                                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Significance
                                        if ((if (isEng) temple.significanceEn else temple.significanceHi).isNotBlank()) Column {
                                            Text(
                                                text = if (isEng) "📜 Significance" else "📜 आध्यात्मिक महत्व",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = if (isEng) temple.significanceEn else temple.significanceHi,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        // Opening Hours
                                        if ((if (isEng) temple.openingHoursEn else temple.openingHoursHi).isNotBlank()) Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = if (isEng) "⏰ Darshan Timings" else "⏰ दर्शन का समय",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = if (isEng) temple.openingHoursEn else temple.openingHoursHi,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }

                                        // Dress Code & Festivals
                                        if ((if (isEng) temple.dressCodeEn else temple.dressCodeHi).isNotBlank()) Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = if (isEng) "👕 Dress Guidelines" else "👕 वस्त्र नियमावली",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = if (isEng) temple.dressCodeEn else temple.dressCodeHi,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }

                                        if ((if (isEng) temple.majorFestivalsEn else temple.majorFestivalsHi).isNotBlank()) Column {
                                            Text(
                                                text = if (isEng) "🎉 Major Festivals" else "🎉 प्रमुख उत्सव",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = if (isEng) temple.majorFestivalsEn else temple.majorFestivalsHi,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Expand Details Button
                                    TextButton(
                                        onClick = { expandedTempleId = if (isExpanded) null else temple.id }
                                    ) {
                                        Text(
                                            text = if (isExpanded) {
                                                if (isEng) "Hide Details ▲" else "विवरण छिपाएं ▲"
                                            } else {
                                                if (isEng) "View Details ▼" else "विवरण देखें ▼"
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        // Share Button
                                        IconButton(
                                            onClick = {
                                                val intent = Intent(Intent.ACTION_SEND).apply {
                                                    type = "text/plain"
                                                    val shareText = if (isEng) {
                                                        "Jai Shri Ram! Check out details for ${temple.nameEn} in ${temple.cityEn}.\nDeity: ${temple.deityEn}\nHours: ${temple.openingHoursEn}\nExplore more on Sanatan Sadhak app!"
                                                    } else {
                                                        "जय श्री राम! ${temple.cityHi} में स्थित ${temple.nameHi} के पावन दर्शन विवरण देखें।\nमुख्य आराध्य: ${temple.deityHi}\nदर्शन समय: ${temple.openingHoursHi}\nसनातन साधक ऐप पर अधिक विवरण देखें!"
                                                    }
                                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                                }
                                                context.startActivity(Intent.createChooser(intent, null))
                                            },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(Icons.Default.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                        }

                                        // Live darshan timings / notices live on the temple's own site: open its official-site search
                                        OutlinedButton(
                                            onClick = {
                                                val q = Uri.encode("${temple.nameEn} ${temple.cityEn} official website darshan timings")
                                                try {
                                                    val target = temple.website?.takeIf { it.startsWith("http") } ?: "https://www.google.com/search?q=$q"
                                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(target)))
                                                } catch (e: Exception) {
                                                    android.widget.Toast.makeText(context, "No browser available", android.widget.Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Text(if (isEng) "Live timings" else "लाइव समय", fontSize = 11.sp)
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))

                                        // Navigation Button
                                        Button(
                                            onClick = {
                                                val gmmIntentUri = Uri.parse("geo:0,0?q=${temple.lat},${temple.lon}(${Uri.encode(if (isEng) temple.nameEn else temple.nameHi)})")
                                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                mapIntent.setPackage("com.google.android.apps.maps")
                                                try {
                                                    context.startActivity(mapIntent)
                                                } catch (e: Exception) {
                                                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${temple.lat},${temple.lon}"))
                                                    context.startActivity(browserIntent)
                                                }
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                            modifier = Modifier.height(32.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                        ) {
                                            Icon(Icons.Default.Navigation, contentDescription = "Navigate", tint = Color.White, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(if (isEng) "Navigate" else "मार्गदर्शन", fontSize = 11.sp, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            ScrollToTopButton(
                visible = showScrollToTop,
                testTag = "scroll_to_top_temple_finder",
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
            )
        }
    }
}


