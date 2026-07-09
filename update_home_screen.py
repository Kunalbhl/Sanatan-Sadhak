import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Fix Dhyana Sadhana in Hindi
content = content.replace('"ध्यान टाइमर"', '"ध्यान साधना"')

# Add more options below Dhyana Sadhana
# Let's find the end of the Dhyana Timer block in HomeScreen.
# It ends with:
#                     contentAlignment = Alignment.Center
#                 ) {
#                     Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = MaterialTheme.colorScheme.onTertiary, modifier = Modifier.size(16.dp))
#                 }
#             }
#         }
#         Spacer(modifier = Modifier.height(24.dp))
#     }
# }

# We will inject additional quick links.

additional_links = """
        // Quick Entry: Karma Tracker
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .clickable {
                        viewModel.navigateToBhakti("KarmaLog")
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = "Karma Icon",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isEng) "Karma Tracker" else "कर्म चक्र",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isEng) "Log a good deed" else "आज का सत्कर्म दर्ज करें",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Go", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }

        // Quick Entry: Gratitude
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        viewModel.navigateToBhakti("Gratitude")
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Gratitude Icon",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isEng) "Gratitude Journal" else "कृतज्ञता डायरी",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isEng) "Express your thanks" else "अपना आभार व्यक्त करें",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Go", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
"""

# Let's use regex to insert this before `Spacer(modifier = Modifier.height(24.dp))` which is at the end of HomeScreen.
# But wait, does HomeScreen have `Spacer(modifier = Modifier.height(24.dp))`?
