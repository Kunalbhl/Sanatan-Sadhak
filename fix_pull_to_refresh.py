import re
with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
    text = f.read()

# For HomeScreen
home_target = '''    Box(modifier = Modifier.fillMaxSize()) {
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val buttonAlpha = if (isHovered) 0.85f else 0.5f

        LazyColumn('''
home_replacement = '''    val isRefreshing by viewModel.isRefreshing.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val buttonAlpha = if (isHovered) 0.85f else 0.5f

        @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
        androidx.compose.material3.pulltorefresh.PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refreshHome() },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn('''

# We also need to close the PullToRefreshBox brace.
# This requires a bit of regex parsing. Let's just do a sed on the LazyColumn closing brace.
# Actually, I can use a simpler approach. Just wrap the content of `HomeScreen` in PullToRefreshBox? No, it should wrap the scrolling component. 
