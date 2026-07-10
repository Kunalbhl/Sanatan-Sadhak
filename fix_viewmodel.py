import sys

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    lines = f.readlines()

out = []
in_send_message = False
in_mock_func = False

for line in lines:
    if "fun sendMessage(prompt: String)" in line:
        in_send_message = True
        continue
    if in_send_message:
        if "private fun getMockChatResponse" in line:
            in_mock_func = True
        if line.strip() == "}":
            if in_mock_func:
                in_mock_func = False
            else:
                in_send_message = False
        continue

    # We skip all lines that were inside the bad replacement.
    out.append(line)

new_func = """    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            if (userRole.value == "Guest") {
                if (_guestQuestionCount.value >= 10) {
                    _showLoginWall.value = true
                    return@launch
                }
                _guestQuestionCount.value += 1
            }
            _isChatLoading.value = true
            val userMsg = com.example.data.ChatMessage(sender = "user", content = prompt)
            repository.addChatMessage(userMsg)
            
            val reply = getMockChatResponse(prompt)
            
            val modelMsg = com.example.data.ChatMessage(sender = "gemini", content = reply)
            repository.addChatMessage(modelMsg)
            _isChatLoading.value = false
        }
    }

    private fun getMockChatResponse(prompt: String): String {
        val p = prompt.lowercase()
        return when {
            p.contains("panchang") -> {
                "Today's Panchang highlights an auspicious Tithi and Nakshatra, creating a favorable energy for starting new spiritual practices. The planetary alignments suggest dedicating time to meditation and inner reflection.\\n\\nTry this today: Light a diya during Sandhya time and chant the Gayatri Mantra.\\n[CHIP:View Daily Panchang:Home]\\n[FOLLOWUP:How do I start a daily puja?]\\n[FOLLOWUP:Tell me about Mahadev]"
            }
            p.contains("karma") -> {
                "Karma is the universal principle of cause and effect, where your actions, thoughts, and intentions shape your spiritual journey. It is not a system of punishment, but a path of learning and balancing. Nishkama Karma, performing your duties without attachment to the results, is considered the highest form of action.\\n\\nTry this today: Perform one act of kindness without expecting anything in return.\\n[CHIP:Explore Karma & Dharma:Knowledge]\\n[FOLLOWUP:Explain Dharma simply]\\n[FOLLOWUP:What is Nishkama Karma?]"
            }
            p.contains("mahadev") || p.contains("shiva") -> {
                "Mahadev, the Supreme Being in Shaivism, represents the pure consciousness that transcends space and time. He is the destroyer of ignorance and ego, clearing the path for new creation and spiritual enlightenment. His ascetic form reminds us to find peace within, detached from material desires.\\n\\nTry this today: Offer a simple prayer of gratitude to Mahadev for the breath of life.\\n[CHIP:Read about Mahadev:Knowledge]\\n[CHIP:Shiva Aarti:Bhakti]\\n[FOLLOWUP:What does the Trishul symbolize?]\\n[FOLLOWUP:Tell me about Parvati Mata]"
            }
            p.contains("gita") || p.contains("shloka") -> {
                "As shared in the Bhagavad Gita, Chapter 2: 'You have a right to perform your prescribed duty, but you are not entitled to the fruits of action.' This teaching, known as Karma Yoga, encourages us to act with dedication while surrendering the outcome to the Divine.\\n\\nTry this today: When facing a task, focus entirely on doing it well, without worrying about success or failure.\\n[CHIP:Read Bhagavad Gita:Knowledge]\\n[FOLLOWUP:Explain Karma Yoga]\\n[FOLLOWUP:What does Chapter 3 say?]"
            }
            p.contains("puja") -> {
                "Starting a daily puja is a beautiful way to center yourself. Begin by setting aside a small, clean space. Light a diya or incense, offer fresh flowers or water, and recite a simple mantra or prayer. Consistency and devotion are more important than complex rituals.\\n\\nTry this today: Spend 5 minutes in your quiet space simply breathing and feeling the Divine presence.\\n[CHIP:Daily Rituals Guide:Knowledge]\\n[CHIP:Explore Mantras:Bhakti]\\n[FOLLOWUP:What is the best time for Puja?]\\n[FOLLOWUP:How to use a Mala?]"
            }
            p.contains("sindari") || p.contains("balaji") -> {
                "Sindari ke Balaji is a revered form of Lord Hanuman, known for his immense strength, unwavering devotion to Lord Rama, and his power to dispel negative energies and grant boons to his devotees. Temples dedicated to Him often draw large crowds seeking blessings for courage and protection.\\n\\nTry this today: Chant 'Om Shri Hanumate Namah' to invoke His strength when you feel anxious.\\n[CHIP:Read about Hanumanji:Knowledge]\\n[CHIP:Hanuman Chalisa:Bhakti]\\n[FOLLOWUP:Tell me a story about Hanuman]\\n[FOLLOWUP:How to overcome fear?]"
            }
            else -> {
                "I appreciate your question. My purpose as Sadhak Mitra is to guide you on your spiritual path through the wisdom of Sanatan Dharma. While I cannot answer that directly, I can share teachings on inner peace, devotion, or understanding life's challenges through a dharmic lens. How can I assist your spiritual journey today?\\n[FOLLOWUP:Explain Karma simply]\\n[FOLLOWUP:Tell me about Mahadev]"
            }
        }
    }
}
"""

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    for l in out[:-1]: # skip the last '}' which belongs to class
        f.write(l)
    f.write(new_func)

