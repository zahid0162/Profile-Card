# AuraProfileCard for Android (Jetpack Compose)

[![](https://jitpack.io/v/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME].svg)](https://jitpack.io/#[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME])
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0%2B-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Ready-4285F4.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

An ultra-creative, production-ready, and highly customizable **Profile & Info Card** library for Android built with **Jetpack Compose**. It features 5 cinematic visual archetypes, smooth 3D physics, theatrical curtain reveals, parallax tilt, dynamic particle systems, and haptic feedback responses.

---

## 📖 Table of Contents
- [About the Library](#-about-the-library)
- [Card Archetypes](#-card-archetypes)
- [Installation via JitPack](#-installation-via-jitpack)
  - [Step 1: Add JitPack Repository](#step-1-add-jitpack-repository)
  - [Step 2: Add Dependency](#step-2-add-dependency)
- [How to Publish Your Own Release on JitPack](#-how-to-publish-your-own-release-on-jitpack)
- [Quick Start Example](#-quick-start-example)
- [Advanced Usage & Full Customization](#-advanced-usage--full-customization)
- [Complete Parameter Reference](#-complete-parameter-reference)
  - [1. Identity & Profile Information](#1-identity--profile-information-auraprofileinfo)
  - [2. Visual Style & Presentation](#2-visual-style--presentation-auracardstyle)
  - [3. Color Palettes & Theming](#3-color-palettes--theming-auracardcolors)
  - [4. Metric Stats](#4-metric-stats-aurastat)
  - [5. Capabilities & Skills](#5-capabilities--skills-auraskill)
  - [6. Digital Badges](#6-digital-badges-aurabadge)
  - [7. External Links & Contact Channels](#7-external-links--contact-channels-auralink)
  - [8. Interactive Action Callbacks](#8-interactive-action-callbacks-auracardactions)
  - [9. Custom Composable Slots](#9-custom-composable-slots)
- [Requirements](#-requirements)
- [License](#-license)

---

## 🌟 About the Library

**AuraProfileCard** eliminates the need to build repetitive or flat profile views. Whether you are building an ID badge, digital business card, speaker dossier, gaming card, social bio, or developer portfolio, AuraProfileCard gives you:

- **5 Distinct Visual Styles**: Choose between frosted glass, 3D flip, theater curtain, parallax depth, or titanium metal.
- **Dynamic Theming**: Seamless dark and light mode support with ready-to-use color schemes (Cyber Neon, Solar Flare, Emerald Jade, Amethyst Bloom, Titanium Luxe) or fully custom colors.
- **Fluid Gestures & Physics**: Drag to tilt in 3D, swipe to flip, or pull curtains open with spring animations.
- **Micro-Interactions & Haptics**: Integrated tactile feedback on clicks, flips, and drags.
- **Fully Modular**: Every single element (text, badges, stats, avatars, buttons, colors, and layouts) is exposed as parameters.

---

## 🎨 Card Archetypes

| Archetype | Description |
| :--- | :--- |
| **`AuraCardStyle.GLASSMORPHISM`** | Frosted glass refraction, iridescent specular border glow, and a real-time glistening light sweep. |
| **`AuraCardStyle.CARD_FLIP_3D`** | Interactive 3D flip with realistic perspective camera distance. Features identity on front and encrypted QR matrix & capabilities telemetry on reverse. |
| **`AuraCardStyle.CURTAIN_REVEAL`** | Theatrical pleated silk drapes with automatic reveal on load, and a curtain-flip animation that closes the drapes to switch between profile and bio. |
| **`AuraCardStyle.PARALLAX_NEBULA`** | Multi-depth 3D stereoscopic layering that tilts in response to touch drag and ambient gyroscopic oscillation. |
| **`AuraCardStyle.TITANIUM_LUXE`** | Dark brushed titanium alloy finish with laser-etched gold borders and an animated audio telemetry waveform equalizer. |

---

## 📦 Installation via JitPack

### Step 1: Add JitPack Repository

In your project's `settings.gradle.kts` (or root `build.gradle.kts`):

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // <-- Add this line
    }
}
```

> **Note for Groovy DSL (`settings.gradle`):**
> ```groovy
> dependencyResolutionManagement {
>     repositories {
>         google()
>         mavenCentral()
>         maven { url 'https://jitpack.io' }
>     }
> }
> ```

---

### Step 2: Add Dependency

In your app module's `build.gradle.kts` (`app/build.gradle.kts`):

```kotlin
dependencies {
    // Replace [YOUR_GITHUB_USERNAME] and [YOUR_REPOSITORY_NAME] with your GitHub details
    // Replace [VERSION_TAG] with the release tag (e.g. 1.0.0 or v1.0.0)
    implementation("com.github.[YOUR_GITHUB_USERNAME]:[YOUR_REPOSITORY_NAME]:[VERSION_TAG]")
}
```

**Example:**
```kotlin
implementation("com.github.johndoe:auraprofile-card:1.0.0")
```

---

## 🚀 How to Publish Your Own Release on JitPack

Follow these simple steps to make your repository installable by anyone in the world:

1. **Push your code to GitHub**:
   ```bash
   git init
   git add .
   git commit -m "Initial commit of AuraProfileCard library"
   git remote add origin https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME].git
   git branch -M main
   git push -u origin main
   ```

2. **Create a GitHub Release**:
   - Go to your repository on GitHub: `https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME]`
   - On the right sidebar, click **Releases** -> **Draft a new release**.
   - In **Choose a tag**, enter `1.0.0` (or `v1.0.0`) and click **Create new tag**.
   - Add a title (e.g. `Release 1.0.0`) and click **Publish release**.

3. **Build on JitPack**:
   - Open **[jitpack.io](https://jitpack.io/)** in your browser.
   - In the text box, paste your GitHub repository URL:
     ```
     https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME]
     ```
   - Click **Get it**.
   - JitPack will automatically fetch your tag, build the `:auraprofile` AAR library artifact, and turn green (`Report: OK`).
   - Done! Your library is now live and can be imported anywhere.

---

## ⚡ Quick Start Example

Here is how to create a profile card in just a few lines of Jetpack Compose:

```kotlin
import androidx.compose.runtime.Composable
import com.example.auraprofile.AuraProfileCard
import com.example.auraprofile.model.AuraCardStyle
import com.example.auraprofile.model.AuraStat
import com.example.auraprofile.model.AuraSkill

@Composable
fun SimpleProfileScreen() {
    AuraProfileCard(
        name = "Dr. Elena Vance",
        handle = "@elena.vance",
        title = "Quantum AI Architect",
        company = "NeuralSphere Labs",
        location = "Neo-Zurich",
        bio = "Designing fault-tolerant neural manifolds & synthetic cognition architectures.",
        avatarRes = R.drawable.avatar_elena,
        style = AuraCardStyle.GLASSMORPHISM, // Choose any of the 5 archetypes
        stats = listOf(
            AuraStat(label = "Patents", value = "14"),
            AuraStat(label = "Citations", value = "9.4k"),
            AuraStat(label = "Rank", value = "#01")
        ),
        skills = listOf(
            AuraSkill(name = "Neural Core Architecture", level = 0.96f),
            AuraSkill(name = "Quantum UX Design", level = 0.92f)
        ),
        onConnectClick = { println("Connected!") },
        onMessageClick = { println("Message opened!") },
        onShareClick = { println("Shared profile!") }
    )
}
```

---

## 🛠 Advanced Usage & Full Customization

For complete control over colors, telemetry, custom headers, and badges, use the object builder signature:

```kotlin
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.auraprofile.AuraProfileCard
import com.example.auraprofile.model.AuraBadge
import com.example.auraprofile.model.AuraCardActions
import com.example.auraprofile.model.AuraCardColors
import com.example.auraprofile.model.AuraCardStyle
import com.example.auraprofile.model.AuraLink
import com.example.auraprofile.model.AuraLinkType
import com.example.auraprofile.model.AuraProfileInfo
import com.example.auraprofile.model.AuraSkill
import com.example.auraprofile.model.AuraStat

@Composable
fun AdvancedProfileScreen() {
    val isDark = isSystemInDarkTheme()

    AuraProfileCard(
        info = AuraProfileInfo(
            name = "Kai Thorne",
            handle = "@kai.frequencies",
            title = "Spatial Sound Director",
            company = "Aura Acoustics Studio",
            location = "Berlin / Spatial Hub",
            bio = "Sculpting immersive psychoacoustic environments and reactive audio algorithms.",
            avatarRes = R.drawable.avatar_kai,
            statusText = "TRANSMITTING",
            statusColor = Color(0xFFFF5E62),
            memberId = "ID-4421-SND",
            verificationHash = "0x8E31AA04DF9"
        ),
        style = AuraCardStyle.CARD_FLIP_3D, // 3D Flip with QR pass on reverse
        colors = AuraCardColors.solarFlare(isDark = isDark),
        isDark = isDark,
        stats = listOf(
            AuraStat(label = "Albums", value = "12", onClick = { /* open albums */ }),
            AuraStat(label = "Listeners", value = "4.8M"),
            AuraStat(label = "Score", value = "98.2%"),
            AuraStat(label = "Tier", value = "Apex")
        ),
        skills = listOf(
            AuraSkill(name = "Spatial Audio Synthesis", level = 0.98f, category = "Audio"),
            AuraSkill(name = "Binaural Wave Engines", level = 0.91f, category = "DSP"),
            AuraSkill(name = "Haptic Sound Mapping", level = 0.87f, category = "Hardware")
        ),
        badges = listOf(
            AuraBadge(title = "Grammy Fellow", color = Color(0xFFFF5E62)),
            AuraBadge(title = "Acoustic Innovator", color = Color(0xFFFFD166)),
            AuraBadge(title = "Studio Master", color = Color(0xFF00F5D4))
        ),
        links = listOf(
            AuraLink(title = "Studio Feed", value = "aurasound.io/kai", type = AuraLinkType.WEB),
            AuraLink(title = "Direct Comms", value = "kai@aurasound.io", type = AuraLinkType.EMAIL),
            AuraLink(title = "Audio Engine Repo", value = "github.com/kaithorne", type = AuraLinkType.CODE)
        ),
        actions = AuraCardActions(
            connectLabel = "Collaborate",
            onConnectClick = { /* Handle Collaborate */ },
            messageLabel = "Direct Audio Ping",
            onMessageClick = { /* Open Audio Ping */ },
            onShareClick = { /* Share link */ },
            onCardClick = { /* Tapped entire card */ },
            onFlipClick = { /* Card was flipped */ }
        )
    )
}
```

---

## 📋 Complete Parameter Reference

### 1. Identity & Profile Information (`AuraProfileInfo`)

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `name` | `String` | *(Required)* | Full display name (e.g., `"Dr. Elena Vance"`). |
| `handle` | `String` | *(Required)* | Username handle with `@` prefix (e.g., `"@elena.vance"`). |
| `title` | `String` | *(Required)* | Professional headline or role (e.g., `"Quantum AI Architect"`). |
| `company` | `String?` | `null` | Organization or studio name. Rendered next to the role. |
| `location` | `String?` | `null` | City / region / planetary hub with pin icon. |
| `bio` | `String?` | `null` | Short biography, mission statement, or summary text. |
| `avatarRes` | `Int?` | `null` | Drawable resource ID for profile picture (e.g., `R.drawable.my_avatar`). |
| `avatarPainter` | `Painter?` | `null` | Optional Compose `Painter` for loading images (e.g., Coil `rememberAsyncImagePainter`). |
| `statusText` | `String` | `"ACTIVE"` | Pill status text in top badge (e.g., `"ONLINE"`, `"SYNCHRONIZED"`). |
| `statusColor` | `Color?` | `null` | Custom tint for the pulsing status dot and pill outline. |
| `memberId` | `String` | `"ID-2049"` | Monospace digital ID printed on reverse side and header. |
| `verificationHash` | `String` | `"0x7F9A2B4C"` | Cryptographic authorization hash displayed next to verified QR pass. |

---

### 2. Visual Style & Presentation (`AuraCardStyle`)

Choose how the card looks and behaves through the `style` parameter:

| Value | Behavior |
| :--- | :--- |
| `AuraCardStyle.GLASSMORPHISM` | Frosted glass translucent surface, specular refraction border, animated light sheen sweep. |
| `AuraCardStyle.CARD_FLIP_3D` | 3D gyroscopic flip (touch drag or button) revealing digital ID telemetry & Canvas QR pattern on back. |
| `AuraCardStyle.CURTAIN_REVEAL` | Silk pleated theatrical drapes with auto-open on display and curtain-flip transition to bio dossier. |
| `AuraCardStyle.PARALLAX_NEBULA` | Multi-depth 3D stereoscopic layering tracking finger drag and ambient oscillation with floating badges. |
| `AuraCardStyle.TITANIUM_LUXE` | Dark brushed metallic alloy texture, gold laser accents, and real-time audio waveform equalizer. |

---

### 3. Color Palettes & Theming (`AuraCardColors`)

You can pass ready-to-use color presets or construct your own palette:

#### Preset Palettes:
```kotlin
AuraCardColors.cyberNeon(isDark = true)     // Cyan, Electric Violet, Hot Pink
AuraCardColors.solarFlare(isDark = true)    // Sunset Coral, Amber, Gold
AuraCardColors.emeraldJade(isDark = true)   // Mint Jade, Matrix Teal, Emerald
AuraCardColors.amethystBloom(isDark = true) // Royal Violet, Neon Fuchsia, Lavender
AuraCardColors.titaniumLuxe(isDark = true)  // Gold Laser, Titanium Silver, Onyx
```

#### Custom Colors Specification:
| Property | Type | Description |
| :--- | :--- | :--- |
| `primary` | `Color` | Dominant accent color used for primary buttons, highlights, and main borders. |
| `secondary` | `Color` | Secondary accent used for gradient sweeps and complementary badges. |
| `accent` | `Color` | Highlight accent used for gradient endpoints and skill percentage values. |
| `glowColor` | `Color` | Ambient radial glow color layered behind the card surface. |
| `cardBackground` | `Color` | Base surface fill color (supports translucent alpha for glassmorphism). |
| `textColor` | `Color` | Primary text color for name, titles, and headers. |
| `textSecondaryColor` | `Color` | Subdued text color for handles, locations, bios, and metric labels. |
| `statusColor` | `Color` | Tint for online indicator dots and badges. |

---

### 4. Metric Stats (`AuraStat`)

Pass a list of up to 4 key numbers displayed in clean rounded stat tiles:

```kotlin
data class AuraStat(
    val label: String,             // Title under number (e.g. "Followers", "Projects")
    val value: String,             // Bold highlighted metric (e.g. "14.2k", "99.8%")
    val onClick: (() -> Unit)? = null // Optional click handler when user taps the tile
)
```

---

### 5. Capabilities & Skills (`AuraSkill`)

Render animated capability progress bars with gradient fills:

```kotlin
data class AuraSkill(
    val name: String,              // Skill title (e.g. "Neural Architecture")
    val level: Float,              // Float between 0.0f and 1.0f (e.g. 0.95f = 95%)
    val category: String? = null   // Optional domain tag (e.g. "Engineering")
)
```

---

### 6. Digital Badges (`AuraBadge`)

Showcase verified awards, ranks, and accreditation chips:

```kotlin
data class AuraBadge(
    val title: String,             // Label (e.g. "Verified Pioneer", "Top Creator")
    val icon: ImageVector? = null, // Optional Material Icon (defaults to Icons.Default.Star)
    val color: Color? = null,      // Accent color for badge background & icon
    val onClick: (() -> Unit)? = null // Optional tap callback
)
```

---

### 7. External Links & Contact Channels (`AuraLink`)

Render clickable direct communication and portfolio channels:

```kotlin
data class AuraLink(
    val title: String,             // Channel label (e.g. "Portfolio", "Email", "GitHub")
    val value: String,             // Display value or link (e.g. "matrix.org/@user")
    val type: AuraLinkType = AuraLinkType.WEB, // EMAIL, CODE, WEB, SOCIAL, OTHER
    val onClick: (() -> Unit)? = null // Invoked when the row is tapped
)
```

---

### 8. Interactive Action Callbacks (`AuraCardActions`)

| Property | Default Value | Description |
| :--- | :--- | :--- |
| `onConnectClick` | `null` | Invoked when the primary "Connect" button is pressed. |
| `connectLabel` | `"Connect"` | Custom label for primary button (e.g., `"Follow"`, `"Hire"`, `"Add"`). |
| `onMessageClick` | `null` | Invoked when the outlined "Message" button is pressed. |
| `messageLabel` | `"Message"` | Custom label for messaging action (e.g., `"Ping"`, `"Chat"`). |
| `onShareClick` | `null` | Invoked when the square share icon button is tapped. |
| `onFlipClick` | `null` | Invoked whenever the 3D or curtain card flips between front and back. |
| `onCardClick` | `null` | Invoked when the user taps anywhere on the card body. |

---

### 9. Custom Composable Slots

Need custom UI without modifying library internals? Use the slot APIs:

```kotlin
AuraProfileCard(
    info = info,
    // Replace default top status bar with your custom composable:
    customHeaderSlot = {
        MyCustomHeaderWithCompanyLogo()
    },
    // Replace default Connect/Message action buttons with your own layout:
    customActionsSlot = {
        Button(onClick = { /* custom CTA */ }) {
            Text("Book Consultation")
        }
    },
    // Insert additional composables at the bottom of the card:
    customFooterSlot = {
        Text("Verified Member since 2024", fontSize = 10.sp)
    }
)
```

---

## 💻 Requirements

- **Minimum Android SDK**: API 24 (Android 7.0 Nougat)+
- **Target Android SDK**: API 36+
- **Kotlin**: 2.0.0+
- **Jetpack Compose**: Compose BOM 2024.09.00 or higher
- **Material 3**: `androidx.compose.material3:material3`

---

## 📄 License

```
Copyright 2026 Aura Profile Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
