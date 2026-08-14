import java.util.Calendar

fun main() {
    val year = 2026
    val month = 7
    val day = 10
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
    val angle = 2 * Math.PI * (dayOfYear - 80) / 365.0
    val sunriseMinutes = (6 * 60 + 15) - (45 * Math.sin(angle)).toInt()
    val sunsetMinutes = (18 * 60 + 15) + (50 * Math.sin(angle)).toInt()

    val epoch = Calendar.getInstance()
    epoch.set(2020, 0, 1)
    val msDiff = calendar.timeInMillis - epoch.timeInMillis
    val daysDiff = (msDiff / (1000 * 60 * 60 * 24)).toInt()

    val lunarAge = (daysDiff + 5.5) % 29.53
    val tithiNum = (lunarAge / 29.53 * 30).toInt() + 1
    val isShukla = tithiNum <= 15
    val localTithiNum = if (isShukla) tithiNum else tithiNum - 15

    val tithiNames = listOf("Pratipada", "Dwitiya", "Tritiya", "Chaturthi", "Panchami", "Shashthi", "Saptami", "Ashtami", "Navami", "Dashami", "Ekadashi", "Dwadashi", "Trayodashi", "Chaturdashi", "Purnima", "Amavasya")
    val tithiName = if (localTithiNum == 15) {
        if (isShukla) "Purnima" else "Amavasya"
    } else if (localTithiNum >= 1 && localTithiNum <= 14) {
        tithiNames[localTithiNum - 1]
    } else "Tritiya"
    println("tithiName = $tithiName")
}
