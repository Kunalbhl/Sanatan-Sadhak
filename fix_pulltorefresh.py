import re
with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
    text = f.read()

# HomeScreen
home_target = '''    Box(modifier = Modifier.fillMaxSize()) {
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val buttonAlpha = if (isHovered) 0.85f else 0.5f

        LazyColumn(
            state = homeListState,'''
home_replacement = '''    val isRefreshing by viewModel.isRefreshing.collectAsState()
    @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
    androidx.compose.material3.pulltorefresh.PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refreshHome() },
        modifier = Modifier.fillMaxSize()
    ) {
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val buttonAlpha = if (isHovered) 0.85f else 0.5f

        LazyColumn(
            state = homeListState,'''
text = text.replace(home_target, home_replacement)

# KnowledgeScreen
know_target = '''        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = knowledgeListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .testTag("knowledge_screen_column"),
                verticalArrangement = Arrangement.spacedBy(12.dp),'''
know_replacement = '''        val isRefreshing by viewModel.isRefreshing.collectAsState()
        @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
        androidx.compose.material3.pulltorefresh.PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refreshGeneric() },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = knowledgeListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .testTag("knowledge_screen_column"),
                verticalArrangement = Arrangement.spacedBy(12.dp),'''
text = text.replace(know_target, know_replacement)

# CommunityScreen
comm_target = '''                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(
                            state = communityListState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {'''
comm_replacement = '''                    val isRefreshing by viewModel.isRefreshing.collectAsState()
                    @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
                    androidx.compose.material3.pulltorefresh.PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.refreshGeneric() },
                        modifier = Modifier.weight(1f)
                    ) {
                        LazyColumn(
                            state = communityListState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {'''
text = text.replace(comm_target, comm_replacement)

# VideoHubScreen
vid_target = '''            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    state = videoListState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {'''
vid_replacement = '''            val isRefreshing by viewModel.isRefreshing.collectAsState()
            @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
            androidx.compose.material3.pulltorefresh.PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshGeneric() },
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    state = videoListState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {'''
text = text.replace(vid_target, vid_replacement)

with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'w') as f:
    f.write(text)
