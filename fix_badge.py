import sys
content = sys.stdin.read()
target = """                                SacredBadge(text = if (panchangSourceVal == "API") "Live" else "Offline", color = if (panchangSourceVal == "API") SageGreen else Terracotta)"""
replacement = """                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (panchangSourceVal == "API") Color(0xFF4CAF50) else Color(0xFFF44336),
                                            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (panchangSourceVal == "API") "Live" else "Offline",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }"""
if target in content:
    with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'w') as f:
        f.write(content.replace(target, replacement))
    print("Fixed badge")
else:
    print("Target not found")
