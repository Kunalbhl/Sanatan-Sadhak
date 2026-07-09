import re

with open("app/src/main/java/com/example/data/GeminiService.kt", "r") as f:
    content = f.read()

bad_panchang_setup = """            val turn = JSONObject().put("role", "user")
            val parts = JSONArray()
            val partObj = JSONObject().put("text", 
                "Provide the real, accurate Hindu Panchang for Gregorian date: $dateStr. " +
                "Also provide a highly beautiful, deep spiritual thought of the day (e.g., from Bhagavad Gita or Upanishads). " +
                "The response MUST be a valid JSON object only with these exact keys (no surrounding code block formatting or markdown): " +
                "tithi_en, tithi_hi, nakshatra_en, nakshatra_hi, yoga_en, yoga_hi, festival_en, festival_hi, thought_en, thought_hi. " +
                "Ensure that tithi contains details about Shukla/Krishna Paksha, nakshatra is correct, yoga is correct, festival contains any major Hindu festival or tithi significance. " +
                "Example JSON: {\\"tithi_en\\":\\"Shukla Paksha Ekadashi\\",\\"tithi_hi\\":\\"शुक्ल पक्ष एकादशी\\",\\"nakshatra_en\\":\\"Rohini Nakshatra\\",\\"nakshatra_hi\\":\\"रोहिणी नक्षत्र\\",\\"yoga_en\\":\\"Siddha Yoga\\",\\"yoga_hi\\":\\"सिद्ध योग\\",\\"festival_en\\":\\"Nirjala Ekadashi / Devotional Puja\\",\\"festival_hi\\":\\"निर्जला एकादशी / भक्ति पूजा\\",\\"thought_en\\":\\"Perform action without attachment to outcomes.\\",\\"thought_hi\\":\\"फल की इच्छा के बिना कर्म करो।\\"}"
            )
            parts.put(partObj)
            turn.put("parts", parts)
            contentsArray.put(turn)
            root.put("contents", contentsArray)

            val config = JSONObject()
            config.put("responseMimeType", "application/json")
            root.put("generationConfig", config)"""

good_panchang_setup = """            val turn = JSONObject().put("role", "user")
            val parts = JSONArray()
            val partObj = JSONObject().put("text", 
                "Search the web using googleSearch for the accurate Hindu Panchang for today: $dateStr. " +
                "Also provide a beautiful spiritual thought of the day. " +
                "The response MUST be a valid JSON object only with exactly these keys: " +
                "tithi_en, tithi_hi, nakshatra_en, nakshatra_hi, yoga_en, yoga_hi, festival_en, festival_hi, thought_en, thought_hi. " +
                "No markdown formatting, just raw JSON."
            )
            parts.put(partObj)
            turn.put("parts", parts)
            contentsArray.put(turn)
            root.put("contents", contentsArray)
            
            // Add Google Search Tool
            val toolsArray = JSONArray()
            val toolObj = JSONObject()
            val googleSearchObj = JSONObject()
            toolObj.put("googleSearch", googleSearchObj)
            toolsArray.put(toolObj)
            root.put("tools", toolsArray)

            val config = JSONObject()
            config.put("responseMimeType", "application/json")
            root.put("generationConfig", config)"""

content = content.replace(bad_panchang_setup, good_panchang_setup)

with open("app/src/main/java/com/example/data/GeminiService.kt", "w") as f:
    f.write(content)
