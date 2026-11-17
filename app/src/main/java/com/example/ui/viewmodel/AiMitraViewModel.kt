package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import com.example.data.AiMitraRepository
import com.example.data.AppPreferences
import com.example.data.ChatMessage

class AiMitraViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AiMitraRepository(application)
    
    val chatMessages: StateFlow<List<ChatMessage>> = repository.getChatMessages().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading

    // Guest limits could be shared or managed here. For now, keep them internal if used only here.
    // However, userRole is global. We should probably get it from AppPreferences or User Session.
    // For now, I'll pass userRole as parameter if needed, or get it from AppPreferences if I added it there.
    // Wait, the guest limit logic is better placed in SadhakViewModel if it triggers login UI.
    // I will expose a callback or state for showing the login wall.
    private val _showLoginWall = MutableStateFlow(false)
    val showLoginWall: StateFlow<Boolean> = _showLoginWall

    private val _guestQuestionCount = MutableStateFlow(0)
    val guestQuestionCount: StateFlow<Int> = _guestQuestionCount

    fun dismissLoginWall() {
        _showLoginWall.value = false
    }

    fun sendMessage(prompt: String, userRole: String) {
        viewModelScope.launch {
            if (userRole == "Guest") {
                if (_guestQuestionCount.value >= 10) {
                    _showLoginWall.value = true
                    return@launch
                }
                _guestQuestionCount.value += 1
            }
            
            _isChatLoading.value = true
            val userMsg = ChatMessage(sender = "user", content = prompt)
            repository.addChatMessage(userMsg)
            
            val apiKey = com.example.BuildConfig.GEMINI_API_KEY
            val reply = if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY" || apiKey == "null") {
                if (AppPreferences.isEnglish.value) "🙏 AI Mitra isn't connected in this version of the app, so I can't answer right now. Please update the app and try again."
                else "🙏 इस संस्करण में एआई मित्र जुड़ा नहीं है, इसलिए अभी उत्तर नहीं दे सकता। कृपया ऐप अपडेट करके पुनः प्रयास करें।"
            } else {
                com.example.data.GeminiService.generateResponse(chatMessages.value, prompt, AppPreferences.isEnglish.value)
            }
            
            val modelMsg = ChatMessage(sender = "gemini", content = reply)
            repository.addChatMessage(modelMsg)
            _isChatLoading.value = false
        }
    }

    fun resetChat() {
        viewModelScope.launch {
            repository.clearChat()
        }
    }

    private fun getMockChatResponse(prompt: String, isEnglish: Boolean): String {
        val p = prompt.lowercase()
        if (isEnglish) {
            return when {
                p.contains("panchang") -> {
                    "Today's Panchang highlights an auspicious Tithi and Nakshatra, creating a favorable energy for starting new spiritual practices. The planetary alignments suggest dedicating time to meditation and inner reflection.\n\nTry this today: Light a diya during Sandhya time and chant the Gayatri Mantra.\n[CHIP:View Daily Panchang:Calendar]\n[FOLLOWUP:How do I start a daily puja?]\n[FOLLOWUP:Tell me about Mahadev]"
                }
                p.contains("karma") -> {
                    "Karma is the universal principle of cause and effect, where your actions, thoughts, and intentions shape your spiritual journey. It is not a system of punishment, but a path of learning and balancing. Nishkama Karma, performing your duties without attachment to the results, is considered the highest form of action.\n\nTry this today: Perform one act of kindness without expecting anything in return.\n[CHIP:Explore Karma & Dharma:Knowledge]\n[FOLLOWUP:Explain Dharma simply]\n[FOLLOWUP:What is Nishkama Karma?]"
                }
                p.contains("mahadev") || p.contains("shiva") -> {
                    "Mahadev, the Supreme Being in Shaivism, represents the pure consciousness that transcends space and time. He is the destroyer of ignorance and ego, clearing the path for new creation and spiritual enlightenment. His ascetic form reminds us to find peace within, detached from material desires.\n\nTry this today: Offer a simple prayer of gratitude to Mahadev for the breath of life.\n[CHIP:Read about Mahadev:Knowledge]\n[CHIP:Shiva Aarti:Bhakti]\n[FOLLOWUP:What does the Trishul symbolize?]\n[FOLLOWUP:Tell me about Parvati Mata]"
                }
                p.contains("gita") || p.contains("shloka") -> {
                    "As shared in the Bhagavad Gita, Chapter 2: 'You have a right to perform your prescribed duty, but you are not entitled to the fruits of action.' This teaching, known as Karma Yoga, encourages us to act with dedication while surrendering the outcome to the Divine.\n\nTry this today: When facing a task, focus entirely on doing it well, without worrying about success or failure.\n[CHIP:Read Bhagavad Gita:Knowledge]\n[FOLLOWUP:Explain Karma Yoga]\n[FOLLOWUP:What does Chapter 3 say?]"
                }
                p.contains("puja") || p.contains("meditation") -> {
                    "Starting a daily puja is a beautiful way to center yourself. Begin by setting aside a small, clean space. Light a diya or incense, offer fresh flowers or water, and recite a simple mantra or prayer. Consistency and devotion are more important than complex rituals.\n\nTry this today: Spend 5 minutes in your quiet space simply breathing and feeling the Divine presence.\n[CHIP:Daily Rituals Guide:Knowledge]\n[CHIP:Explore Mantras:Bhakti]\n[FOLLOWUP:What is the best time for Puja?]\n[FOLLOWUP:How to use a Mala?]"
                }
                p.contains("sindari") || p.contains("balaji") -> {
                    "Sindari ke Balaji is a revered form of Lord Hanuman, known for his immense strength, unwavering devotion to Lord Rama, and his power to dispel negative energies and grant boons to his devotees. Temples dedicated to Him often draw large crowds seeking blessings for courage and protection.\n\nTry this today: Chant 'Om Shri Hanumate Namah' to invoke His strength when you feel anxious.\n[CHIP:Read about Hanumanji:Knowledge]\n[CHIP:Hanuman Chalisa:Bhakti]\n[FOLLOWUP:Tell me a story about Hanuman]\n[FOLLOWUP:How to overcome fear?]"
                }
                else -> {
                    "I appreciate your question. As Sadhak Mitra, my purpose is to guide you on your spiritual path through the wisdom of Sanatan Dharma. While I may not answer this directly, I can share teachings on finding inner peace, devotion, or understanding life's challenges from the perspective of Dharma. How can I assist your spiritual journey today?\n[FOLLOWUP:Explain Karma simply]\n[FOLLOWUP:Tell me about Mahadev]"
                }
            }
        } else {
            return when {
                p.contains("panchang") || p.contains("पंचांग") -> {
                    "आज का पंचांग एक शुभ तिथि और नक्षत्र को उजागर करता है, जो नई आध्यात्मिक प्रथाओं को शुरू करने के लिए एक अनुकूल ऊर्जा पैदा करता है। ग्रहों की स्थिति ध्यान और आंतरिक चिंतन के लिए समय समर्पित करने का सुझाव देती है।\n\nआज ही इसे आजमाएं: संध्या के समय एक दीया जलाएं और गायत्री मंत्र का जाप करें।\n[CHIP:दैनिक पंचांग देखें:Calendar]\n[FOLLOWUP:दैनिक पूजा कैसे शुरू करें?]\n[FOLLOWUP:मुझे महादेव के बारे में बताएं]"
                }
                p.contains("karma") || p.contains("कर्म") -> {
                    "कर्म कारण और प्रभाव का सार्वभौमिक सिद्धांत है, जहां आपके कार्य, विचार और इरादे आपकी आध्यात्मिक यात्रा को आकार देते हैं। यह सजा की प्रणाली नहीं है, बल्कि सीखने और संतुलन का मार्ग है। निष्काम कर्म, बिना किसी फल की आसक्ति के अपने कर्तव्यों का पालन करना, कर्म का सर्वोच्च रूप माना जाता है।\n\nआज ही इसे आजमाएं: बिना किसी बदले की उम्मीद के किसी के लिए दयालुता का कार्य करें।\n[CHIP:कर्म और धर्म जानें:Knowledge]\n[FOLLOWUP:धर्म को सरलता से समझाएं]\n[FOLLOWUP:निष्काम कर्म क्या है?]"
                }
                p.contains("mahadev") || p.contains("shiva") || p.contains("महादेव") || p.contains("शिव") -> {
                    "महादेव, शैव मत में सर्वोच्च सत्ता हैं, जो शुद्ध चेतना का प्रतिनिधित्व करते हैं जो स्थान और समय से परे है। वह अज्ञानता और अहंकार के विनाशक हैं, जो नए निर्माण और आध्यात्मिक ज्ञान के मार्ग को साफ करते हैं। उनका तपस्वी रूप हमें भौतिक इच्छाओं से विरक्त होकर भीतर शांति खोजने की याद दिलाता है।\n\nआज ही इसे आजमाएं: जीवन की श्वास के लिए महादेव को कृतज्ञता की एक सरल प्रार्थना अर्पित करें।\n[CHIP:महादेव के बारे में पढ़ें:Knowledge]\n[CHIP:शिव आरती:Bhakti]\n[FOLLOWUP:त्रिशूल किसका प्रतीक है?]\n[FOLLOWUP:पार्वती माता के बारे में बताएं]"
                }
                p.contains("gita") || p.contains("shloka") || p.contains("गीता") || p.contains("श्लोक") -> {
                    "जैसा कि भगवद गीता के अध्याय 2 में साझा किया गया है: 'आपको अपने निर्धारित कर्तव्य का पालन करने का अधिकार है, लेकिन आप कर्म के फल के अधिकारी नहीं हैं।' यह सीख, जिसे कर्म योग के रूप में जाना जाता है, हमें परिणाम को ईश्वर को समर्पित करते हुए समर्पण के साथ कार्य करने के लिए प्रोत्साहित करती है।\n\nआज ही इसे आजमाएं: जब किसी कार्य का सामना हो, तो सफलता या विफलता की चिंता किए बिना उस पर पूरा ध्यान केंद्रित करें।\n[CHIP:भगवद गीता पढ़ें:Knowledge]\n[FOLLOWUP:कर्म योग समझाएं]\n[FOLLOWUP:अध्याय 3 क्या कहता है?]"
                }
                p.contains("puja") || p.contains("meditation") || p.contains("पूजा") || p.contains("ध्यान") -> {
                    "दैनिक पूजा शुरू करना खुद को केंद्रित करने का एक सुंदर तरीका है। एक छोटा, स्वच्छ स्थान अलग रखकर शुरुआत करें। एक दीया या अगरबत्ती जलाएं, ताजे फूल या जल अर्पित करें और एक सरल मंत्र या प्रार्थना का जाप करें। जटिल अनुष्ठानों की तुलना में निरंतरता और भक्ति अधिक महत्वपूर्ण हैं।\n\nआज ही इसे आजमाएं: अपने शांत स्थान पर केवल 5 मिनट सांस लेने और दिव्य उपस्थिति को महसूस करने में बिताएं।\n[CHIP:दैनिक अनुष्ठान मार्गदर्शिका:Knowledge]\n[CHIP:मंत्रों का अन्वेषण करें:Bhakti]\n[FOLLOWUP:पूजा के लिए सबसे अच्छा समय क्या है?]\n[FOLLOWUP:माला का उपयोग कैसे करें?]"
                }
                p.contains("sindari") || p.contains("balaji") || p.contains("सिंदरी") || p.contains("बालाजी") -> {
                    "सिंदरी के बालाजी भगवान हनुमान के एक अत्यंत पूजनीय रूप हैं, जो अपनी असीम शक्ति, भगवान राम के प्रति अटूट भक्ति और नकारात्मक ऊर्जा को दूर करने और अपने भक्तों को वरदान देने की शक्ति के लिए जाने जाते हैं। उन्हें समर्पित मंदिरों में अक्सर साहस और सुरक्षा के लिए आशीर्वाद लेने वालों की भारी भीड़ उमड़ती है।\n\nआज ही इसे आजमाएं: जब आप चिंतित महसूस करें तो उनकी शक्ति का आह्वान करने के लिए 'ओम श्री हनुमते नमः' का जाप करें।\n[CHIP:हनुमानजी के बारे में पढ़ें:Knowledge]\n[CHIP:हनुमान चालीसा:Bhakti]\n[FOLLOWUP:हनुमानजी की एक कहानी सुनाएं]\n[FOLLOWUP:भय पर विजय कैसे पाएं?]"
                }
                else -> {
                    "मैं आपके प्रश्न की सराहना करता हूँ। साधक मित्र के रूप में मेरा उद्देश्य सनातन धर्म के ज्ञान के माध्यम से आपकी आध्यात्मिक यात्रा में आपका मार्गदर्शन करना है। यद्यपि मैं सीधे इसका उत्तर नहीं दे सकता, लेकिन मैं आंतरिक शांति, भक्ति, या धर्म के दृष्टिकोण से जीवन की चुनौतियों को समझने पर सीख साझा कर सकता हूँ। आज मैं आपकी आध्यात्मिक यात्रा में किस प्रकार सहायता कर सकता हूँ?\n[FOLLOWUP:कर्म को सरलता से समझाएं]\n[FOLLOWUP:मुझे महादेव के बारे में बताएं]"
                }
            }
        }
    }
}
