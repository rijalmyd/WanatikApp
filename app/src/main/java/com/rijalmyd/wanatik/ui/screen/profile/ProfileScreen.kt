package com.rijalmyd.wanatik.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import com.rijalmyd.wanatik.data.model.History
import com.rijalmyd.wanatik.ui.components.LoadingDialog
import com.rijalmyd.wanatik.ui.navigation.ScreenHistory
import com.rijalmyd.wanatik.ui.screen.profile.composable.CardLogin
import com.rijalmyd.wanatik.ui.screen.profile.composable.ItemHistory
import com.rijalmyd.wanatik.ui.screen.profile.composable.UserProfile

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ProfileContent(
        user = state.user,
        histories = state.histories,
        onLogout = {
            viewModel.onEvent(ProfileEvent.OnLogout)
        },
        onHistoryClick = { navController.navigate(ScreenHistory(it.id)) },
        onLoginClick = {
            viewModel.onEvent(ProfileEvent.SetLoginLoadingState(true))
            viewModel.onEvent(ProfileEvent.OnSignInGoogleWithIntent(context))
        }
    )

    if (state.isLoginLoading) {
        LoadingDialog()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    user: FirebaseUser?,
    histories: List<History>,
    onLoginClick: () -> Unit,
    onLogout: () -> Unit,
    onHistoryClick: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                actions = {
                    if (user != null) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = null,
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(text = { Text(text = "Logout") }, onClick = onLogout)
                        }
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = modifier.padding(contentPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                if (user != null) {
                    UserProfile(
                        user = user,
                        modifier = Modifier
                    )
                } else {
                    CardLogin(
                        onLoginClick = onLoginClick,
                        modifier = Modifier
                    )
                }
            }
            if (histories.isNotEmpty()) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Riwayat",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
                items(items = histories, key = { it.id }) {
                    ItemHistory(
                        imageUrl = it.imageUrl,
                        motif = it.motifs.getOrNull(0),
                        date = it.date,
                        onClick = { onHistoryClick(it) },
                    )
                }
            }
        }
    }
}