package com.zzh.cutemusic.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zzh.cutemusic.R

@Composable
fun SettingsCredits(
    onNavigateUp: () -> Unit
) {
    val contributors = listOf(
        "sosauce",
        "Alec-Nesat Colak",
        "Angel",
        "Aniol",
        "Erenay",
        "Izzy",
        "ZZH life",
        "Pixel Z",
        "Kin69",
        "Leo Heitmann Ruiz",
        "Luiggi33",
        "MicGan6",
        "Mickael81",
        "minqwq",
        "Nube",
        "polarwood",
        "UnifeGi",
        "weiguangtwk",
        "WINZORT",
        "Yurt Page"
    )

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateUp) {
                    Icon(painterResource(R.drawable.back), contentDescription = null)
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.credits),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    ) { pv ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Lead Developer",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "sosauce",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.size(24.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    Spacer(Modifier.size(24.dp))
                    Text(
                        text = stringResource(R.string.contributors),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            items(contributors.filter { it != "sosauce" }) { contributor ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.artists_filled),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = contributor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add_emoji_rounded),
                        contentDescription = null,
                        tint = Color(0xFFF48FB1),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.made_with_love),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
