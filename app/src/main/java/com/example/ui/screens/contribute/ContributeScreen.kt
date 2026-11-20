package com.example.ui.screens.contribute

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FirebaseService.Contribution
import com.example.data.FirebaseService.RoleEntry
import com.example.data.FirebaseService.RoleRequest
import com.example.ui.viewmodel.SadhakViewModel
import kotlinx.coroutines.launch

private val CATEGORIES = listOf(
    "Spiritual Concepts", "Pooja & Vidhi", "Mantras & Chanting", "Sadhana & Yoga", "Festivals & Fasting",
    "Temples & Sacred Sites", "Saints & Acharyas", "Itihasa", "Puranas", "Stotra & Sacred Texts"
)

/**
 * Contributor workspace. Nothing written here is public until a Moderator or admin approves it.
 * Admins also manage who holds which role; roles are never self-assigned.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContributeScreen(viewModel: SadhakViewModel, onBack: () -> Unit) {
    val fb = viewModel.firebaseService
    val scope = rememberCoroutineScope()
    val isAdmin by viewModel.isAdmin.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val email by viewModel.userEmail.collectAsState()
    var role by remember { mutableStateOf<String?>(null) }
    var mine by remember { mutableStateOf<List<Contribution>>(emptyList()) }
    var pending by remember { mutableStateOf<List<Contribution>>(emptyList()) }
    var roles by remember { mutableStateOf<List<RoleEntry>>(emptyList()) }
    var requests by remember { mutableStateOf<List<RoleRequest>>(emptyList()) }
    var message by remember { mutableStateOf("") }
    var refresh by remember { mutableIntStateOf(0) }

    val canReview = isAdmin || role == "Moderator"
    val canWrite = canReview || role == "Contributor"

    LaunchedEffect(refresh, email) {
        role = fb.myContributorRole()
        mine = fb.fetchMyContributions()
        if (isAdmin || role == "Moderator") pending = fb.fetchPendingContributions()
        if (isAdmin) { roles = fb.fetchRoleEntries(); requests = fb.fetchRoleRequests() }
    }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
            Text("Contribute & Review", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text(
                    when {
                        isAdmin -> "You are an administrator."
                        role == "Moderator" -> "You are a Moderator: you review what contributors submit."
                        role == "Contributor" -> "You are a Contributor: what you submit goes live only after a Moderator approves it."
                        role == null -> "Checking your access…"
                        else -> "Contributor access is granted by an administrator. Ask for it below and you will be able to submit articles for review."
                    },
                    fontSize = 14.sp
                )
                if (message.isNotBlank()) Text(message, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
            }

            if (role != null && !canWrite) item {
                var note by remember { mutableStateOf("") }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Request contributor access", fontWeight = FontWeight.SemiBold)
                        OutlinedTextField(note, { note = it.take(500) }, label = { Text("Why would you like to contribute? (optional)") }, modifier = Modifier.fillMaxWidth())
                        Button(onClick = {
                            scope.launch {
                                message = if (fb.requestContributorAccess(userName, note)) "Request sent. An administrator will review it." else "Could not send the request. Please sign in with your account and try again."
                            }
                        }) { Text("Send request") }
                    }
                }
            }

            if (canWrite) item {
                var title by remember { mutableStateOf("") }
                var body by remember { mutableStateOf("") }
                var url by remember { mutableStateOf("") }
                var cat by remember { mutableStateOf(CATEGORIES.first()) }
                var open by remember { mutableStateOf(false) }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Submit an article for review", fontWeight = FontWeight.SemiBold)
                        OutlinedTextField(title, { title = it.take(150) }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
                        ExposedDropdownMenuBox(expanded = open, onExpandedChange = { open = it }) {
                            OutlinedTextField(cat, {}, readOnly = true, label = { Text("Category") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(open) },
                                modifier = Modifier.menuAnchor().fillMaxWidth())
                            ExposedDropdownMenu(open, { open = false }) {
                                CATEGORIES.forEach { c -> DropdownMenuItem(text = { Text(c) }, onClick = { cat = c; open = false }) }
                            }
                        }
                        OutlinedTextField(body, { body = it.take(8000) }, label = { Text("Article (at least 50 characters)") }, minLines = 6, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(url, { url = it.take(300) }, label = { Text("Source link (recommended)") }, modifier = Modifier.fillMaxWidth())
                        Button(enabled = title.trim().length >= 3 && body.trim().length >= 50, onClick = {
                            scope.launch {
                                if (fb.submitContribution(title, cat, body, url, userName)) {
                                    message = "Submitted. It stays private until it is approved."
                                    title = ""; body = ""; url = ""; refresh++
                                } else message = "Could not submit. Check your connection and role."
                            }
                        }) { Text("Submit for approval") }
                    }
                }
            }

            if (mine.isNotEmpty()) {
                item { Text("My submissions", fontWeight = FontWeight.SemiBold) }
                items(mine, key = { "m" + it.id }) { c ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(c.title, fontWeight = FontWeight.Medium)
                            Text(
                                when (c.status) { "approved" -> "Approved and live"; "rejected" -> "Not approved" + if (c.reviewNote.isNotBlank()) ": ${c.reviewNote}" else ""; else -> "Waiting for review" },
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            if (canReview) {
                item { Text("Waiting for review (${pending.size})", fontWeight = FontWeight.SemiBold) }
                items(pending, key = { "p" + it.id }) { c ->
                    var note by remember { mutableStateOf("") }
                    val own = c.authorEmail.equals(email, ignoreCase = true) && !isAdmin
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(c.title, fontWeight = FontWeight.Medium)
                            Text("${c.category} · by ${c.authorName.ifBlank { c.authorEmail }}", fontSize = 12.sp)
                            Text(c.body.take(600) + if (c.body.length > 600) "…" else "", fontSize = 13.sp)
                            if (c.sourceUrl.isNotBlank()) Text("Source: ${c.sourceUrl}", fontSize = 12.sp)
                            OutlinedTextField(note, { note = it }, label = { Text("Note to author (optional)") }, modifier = Modifier.fillMaxWidth())
                            if (own) Text("You cannot approve your own submission.", fontSize = 12.sp)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(enabled = !own, onClick = { scope.launch { fb.reviewContribution(c.id, true, note); refresh++ } }) { Text("Approve") }
                                OutlinedButton(enabled = !own, onClick = { scope.launch { fb.reviewContribution(c.id, false, note); refresh++ } }) { Text("Reject") }
                            }
                        }
                    }
                }
            }

            if (isAdmin) {
                item { Text("Access requests (${requests.size})", fontWeight = FontWeight.SemiBold) }
                items(requests, key = { "r" + it.email }) { r ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(r.email, fontWeight = FontWeight.Medium)
                            if (r.note.isNotBlank()) Text(r.note, fontSize = 13.sp)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { scope.launch { fb.setRole(r.email, "Contributor"); refresh++ } }) { Text("Contributor") }
                                Button(onClick = { scope.launch { fb.setRole(r.email, "Moderator"); refresh++ } }) { Text("Moderator") }
                                OutlinedButton(onClick = { scope.launch { fb.declineRoleRequest(r.email); refresh++ } }) { Text("Decline") }
                            }
                        }
                    }
                }
                item {
                    var newEmail by remember { mutableStateOf("") }
                    var newRole by remember { mutableStateOf("Contributor") }
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Give a role by email", fontWeight = FontWeight.SemiBold)
                            OutlinedTextField(newEmail, { newEmail = it }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("Contributor", "Moderator").forEach { r ->
                                    FilterChip(selected = newRole == r, onClick = { newRole = r }, label = { Text(r) })
                                }
                            }
                            Button(enabled = newEmail.contains("@"), onClick = {
                                scope.launch {
                                    message = if (fb.setRole(newEmail, newRole)) "${newEmail.trim()} is now a $newRole." else "Could not save the role."
                                    newEmail = ""; refresh++
                                }
                            }) { Text("Save role") }
                        }
                    }
                }
                item { Text("People with a role (${roles.size})", fontWeight = FontWeight.SemiBold) }
                items(roles, key = { "e" + it.email }) { r ->
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("${r.email} · ${r.role}", fontSize = 13.sp, modifier = Modifier.weight(1f))
                        TextButton(onClick = { scope.launch { fb.setRole(r.email, ""); refresh++ } }) { Text("Remove") }
                    }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}
