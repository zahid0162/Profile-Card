package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppColorTheme
import com.example.model.ProfileData

@Composable
fun ProfileBackContent(
    profile: ProfileData,
    theme: AppColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onFlipClick: () -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    val textColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A)
    val textSecondaryColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
    val cardBackground = if (isDark) Color(0x351E293B) else Color(0x35F8FAFC)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header: Digital Pass ID & Flip Back
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = null,
                    tint = theme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "NEURAL ID SPEC",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.primary,
                    letterSpacing = 1.sp
                )
            }

            Surface(
                shape = CircleShape,
                color = theme.secondary.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, theme.secondary.copy(alpha = 0.35f)),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onFlipClick() }
                    .testTag("flip_back_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = "Return Front",
                        tint = theme.secondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "FRONT",
                        color = theme.secondary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Center Identity QR Code Matrix & Security Stamp
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(cardBackground)
                .border(
                    1.dp,
                    if (isDark) Color(0x25FFFFFF) else Color(0x15000000),
                    RoundedCornerShape(14.dp)
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Simulated Futuristic QR Pattern Matrix
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDark) Color(0xFF080C14) else Color(0xFFE2E8F0))
                    .padding(6.dp)
            ) {
                Canvas(modifier = Modifier.size(74.dp)) {
                    val primary = theme.primary
                    val dotSize = size.width / 9f
                    // Draw simulated QR matrix points
                    val matrix = listOf(
                        listOf(1,1,1,0,1,0,1,1,1),
                        listOf(1,0,1,0,0,1,1,0,1),
                        listOf(1,1,1,0,1,0,1,1,1),
                        listOf(0,0,0,1,0,1,0,0,0),
                        listOf(1,0,1,0,1,0,1,0,1),
                        listOf(0,1,0,1,1,1,0,1,0),
                        listOf(1,1,1,0,0,1,1,1,1),
                        listOf(1,0,1,1,0,0,1,0,1),
                        listOf(1,1,1,0,1,1,1,1,1)
                    )
                    for (r in matrix.indices) {
                        for (c in matrix[r].indices) {
                            if (matrix[r][c] == 1) {
                                drawRect(
                                    color = primary,
                                    topLeft = Offset(c * dotSize, r * dotSize),
                                    size = Size(dotSize * 0.88f, dotSize * 0.88f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.memberId,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = textColor,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "AUTH: ${profile.verificationHash}",
                    fontSize = 10.sp,
                    color = theme.accent,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = profile.statusColor,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Encrypted Ledger Validated",
                        fontSize = 10.sp,
                        color = profile.statusColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Full Capabilities Matrix
        Text(
            text = "VERIFIED CAPABILITIES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textSecondaryColor,
            letterSpacing = 1.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            profile.skills.forEach { skill ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = skill.name,
                        fontSize = 11.sp,
                        color = textColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (isDark) Color(0x33FFFFFF) else Color(0x22000000))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(skill.level)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(theme.primary, theme.secondary)
                                    )
                                )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${(skill.level * 100).toInt()}%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.primary,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.width(28.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Digital Badges Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            profile.badges.forEach { badge ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = badge.color.copy(alpha = 0.14f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, badge.color.copy(alpha = 0.35f)),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = badge.color,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = badge.title,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = badge.color,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Direct Channels / Links
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            profile.contactLinks.forEach { link ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = cardBackground,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isDark) Color(0x22FFFFFF) else Color(0x15000000)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onActionClick("Opening ${link.title}: ${link.value}") }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val icon = when (link.type) {
                            "email" -> Icons.Default.AlternateEmail
                            "code" -> Icons.Default.Code
                            else -> Icons.Default.Language
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = theme.primary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = link.title,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = link.value,
                            fontSize = 10.sp,
                            color = textSecondaryColor,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Bottom Action Button
        Button(
            onClick = { onActionClick("Exported Cryptographic ID Pass") },
            colors = ButtonDefaults.buttonColors(
                containerColor = theme.secondary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("action_export_pass_button")
        ) {
            Icon(
                imageVector = Icons.Default.QrCode2,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Export Cryptographic ID",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}
