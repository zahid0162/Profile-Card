package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.R

data class BadgeItem(
    val id: String,
    val title: String,
    val iconName: String,
    val color: Color
)

data class ContactLink(
    val title: String,
    val value: String,
    val type: String
)

data class SkillItem(
    val name: String,
    val level: Float, // 0.0 to 1.0
    val category: String
)

data class ProfileData(
    val id: String,
    val name: String,
    val handle: String,
    val title: String,
    val company: String,
    val bio: String,
    @DrawableRes val avatarRes: Int,
    val location: String,
    val status: String,
    val statusColor: Color,
    val stats: Map<String, String>,
    val skills: List<SkillItem>,
    val badges: List<BadgeItem>,
    val contactLinks: List<ContactLink>,
    val memberId: String,
    val verificationHash: String
)

object SampleProfiles {
    val elenaVance = ProfileData(
        id = "elena_vance",
        name = "Dr. Elena Vance",
        handle = "@elena.vance",
        title = "Quantum AI Architect",
        company = "NeuralSphere Labs",
        bio = "Designing fault-tolerant neural manifolds & synthetic cognition architectures. Exploring the boundary between bio-intelligence and spatial computing.",
        avatarRes = R.drawable.avatar_elena,
        location = "Neo-Zurich / Orbital 7",
        status = "SYNCHRONIZED",
        statusColor = Color(0xFF00F5D4),
        stats = mapOf(
            "Patents" to "14",
            "Citations" to "9.4k",
            "Index" to "99.4%",
            "Rank" to "#01"
        ),
        skills = listOf(
            SkillItem("Neural Core Architecture", 0.96f, "Engineering"),
            SkillItem("Quantum Lattice Design", 0.88f, "Physics"),
            SkillItem("Holographic UX & Spatial", 0.92f, "Design"),
            SkillItem("Synthetic Cognition", 0.85f, "AI Research")
        ),
        badges = listOf(
            BadgeItem("v1", "Verified Pioneer", "verified", Color(0xFF00F5D4)),
            BadgeItem("v2", "Top 0.1% Creator", "star", Color(0xFFFFD166)),
            BadgeItem("v3", "Quantum Fellow", "shield", Color(0xFFB388FF))
        ),
        contactLinks = listOf(
            ContactLink("Matrix Comms", "elena@neuralsphere.ai", "email"),
            ContactLink("Quantum Git", "github.com/elenavance", "code"),
            ContactLink("Spatial Feed", "matrix.org/@elena", "network")
        ),
        memberId = "ID-9082-QAI",
        verificationHash = "0x7F9A2B4C91E"
    )

    val kaiThorne = ProfileData(
        id = "kai_thorne",
        name = "Kai Thorne",
        handle = "@kai.frequencies",
        title = "Spatial Sound Director",
        company = "Aura Acoustics Studio",
        bio = "Sculpting immersive psychoacoustic environments and reactive audio algorithms for next-gen spatial media and interactive installations.",
        avatarRes = R.drawable.avatar_kai,
        location = "Berlin / Spatial Hub",
        status = "TRANSMITTING",
        statusColor = Color(0xFFFF5E62),
        stats = mapOf(
            "Albums" to "12",
            "Listeners" to "4.8M",
            "Score" to "98.2%",
            "Accredited" to "VIP"
        ),
        skills = listOf(
            SkillItem("Spatial Audio Synthesis", 0.98f, "Audio"),
            SkillItem("Binaural Wave Engines", 0.91f, "DSP"),
            SkillItem("Haptic Audio Mapping", 0.87f, "Hardware"),
            SkillItem("Algorithmic Rhythm", 0.94f, "Music")
        ),
        badges = listOf(
            BadgeItem("k1", "Grammy Sound Fellow", "trophy", Color(0xFFFF5E62)),
            BadgeItem("k2", "Acoustic Innovator", "waveform", Color(0xFFFFD166)),
            BadgeItem("k3", "Studio Master", "sparkle", Color(0xFF00F5D4))
        ),
        contactLinks = listOf(
            ContactLink("Direct Audio", "kai@aurasound.io", "email"),
            ContactLink("Bandcamp VIP", "kaithorne.studio", "music"),
            ContactLink("Spatial Feed", "@kai.frequencies", "network")
        ),
        memberId = "ID-4421-SND",
        verificationHash = "0x8E31AA04DF9"
    )

    val ariaSterling = ProfileData(
        id = "aria_sterling",
        name = "Aria Sterling",
        handle = "@aria.bioforge",
        title = "Bio-Robotics Innovator",
        company = "Lumina NanoBio",
        bio = "Pioneering organic-synthetic cybernetics, smart biomaterials, and prosthetic neural interfaces. Building the symbiosis of biology and cybernetics.",
        avatarRes = R.drawable.avatar_aria,
        location = "San Francisco / Helix Bay",
        status = "ONLINE & ACTIVE",
        statusColor = Color(0xFF06D6A0),
        stats = mapOf(
            "Trials" to "38",
            "Deployments" to "120k",
            "Precision" to "99.8%",
            "Tier" to "Apex"
        ),
        skills = listOf(
            SkillItem("Bionic Neural Interfaces", 0.97f, "Biotech"),
            SkillItem("Self-healing Polymers", 0.90f, "Materials"),
            SkillItem("Robotic Kinematics", 0.93f, "Robotics"),
            SkillItem("Bio-Telemetry", 0.89f, "Data")
        ),
        badges = listOf(
            BadgeItem("a1", "Bio-Tech Laureate", "medal", Color(0xFF06D6A0)),
            BadgeItem("a2", "Apex Pioneer", "star", Color(0xFFB388FF)),
            BadgeItem("a3", "Global Impact", "globe", Color(0xFF00F5D4))
        ),
        contactLinks = listOf(
            ContactLink("Neural Ping", "aria@luminabio.tech", "email"),
            ContactLink("BioLab Portal", "luminabio.tech/aria", "network"),
            ContactLink("OpenBio Research", "github.com/ariasterling", "code")
        ),
        memberId = "ID-1107-BIO",
        verificationHash = "0x3D11F9C8E02"
    )

    val all = listOf(elenaVance, kaiThorne, ariaSterling)
}
