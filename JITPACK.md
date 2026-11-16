# AuraProfileCard — JitPack Publishing & Integration Guide

This guide is dedicated specifically to publishing and consuming **AuraProfileCard** via [JitPack](https://jitpack.io).

---

## 🔗 Your Repository Configuration (Fill in your details)

Before sharing with other developers, replace the placeholders below with your GitHub username and repo name:

* **Your GitHub Repository URL**: `https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME]`
* **Your JitPack Package URL**: `https://jitpack.io/#[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME]`
* **Gradle Dependency String**: `implementation("com.github.[YOUR_GITHUB_USERNAME]:[YOUR_REPOSITORY_NAME]:[VERSION_TAG]")`

---

## 🚀 How to Publish on JitPack (Step-by-Step)

JitPack builds directly from GitHub tags/releases. There are no registration delays or manual approval steps required.

### Step 1: Push your project to GitHub
In your project terminal:
```bash
git init
git add .
git commit -m "feat: initial release of AuraProfileCard library"
git remote add origin https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME].git
git branch -M main
git push -u origin main
```

### Step 2: Create a Release Tag on GitHub
1. Open your repository on GitHub: `https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME]`
2. In the right-hand column, click **Releases** -> **Draft a new release**.
3. In **Choose a tag**, enter your version number (e.g., `1.0.0` or `v1.0.0`) and click **Create new tag: 1.0.0 on publish**.
4. Set the Release title (e.g. `AuraProfileCard 1.0.0`).
5. Click **Publish release**.

### Step 3: Trigger the JitPack Build
1. Visit **[https://jitpack.io](https://jitpack.io)**.
2. In the search box, paste:
   ```
   https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY_NAME]
   ```
3. Click **Get it**.
4. JitPack will clone your repository, build the `:auraprofile` AAR library module, and generate a green `Report: OK` status.

---

## 📦 How Consumers Add Your Library

Share these instructions with any developer who wants to use your card in their app:

### 1. In `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // <-- Add JitPack repository
    }
}
```

### 2. In `app/build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.[YOUR_GITHUB_USERNAME]:[YOUR_REPOSITORY_NAME]:1.0.0")
}
```

---

## 💡 Quick Code Example for Consumers

```kotlin
import androidx.compose.runtime.Composable
import com.example.auraprofile.AuraProfileCard
import com.example.auraprofile.model.AuraCardStyle
import com.example.auraprofile.model.AuraCardColors
import com.example.auraprofile.model.AuraStat
import com.example.auraprofile.model.AuraSkill

@Composable
fun ProfileCardDemo() {
    AuraProfileCard(
        name = "Dr. Elena Vance",
        handle = "@elena.vance",
        title = "Quantum AI Architect",
        company = "NeuralSphere Labs",
        location = "Neo-Zurich",
        bio = "Designing fault-tolerant neural manifolds & synthetic cognition architectures.",
        avatarRes = R.drawable.avatar_elena,
        style = AuraCardStyle.GLASSMORPHISM, // GLASSMORPHISM, CARD_FLIP_3D, CURTAIN_REVEAL, PARALLAX_NEBULA, TITANIUM_LUXE
        colors = AuraCardColors.cyberNeon(isDark = true),
        isDark = true,
        stats = listOf(
            AuraStat(label = "Patents", value = "14"),
            AuraStat(label = "Citations", value = "9.4k"),
            AuraStat(label = "Rank", value = "#01")
        ),
        skills = listOf(
            AuraSkill(name = "Neural Core Architecture", level = 0.96f),
            AuraSkill(name = "Quantum UX Design", level = 0.92f)
        ),
        onConnectClick = { /* Handle connect */ },
        onMessageClick = { /* Handle message */ },
        onShareClick = { /* Handle share */ }
    )
}
```

---

## 📚 Complete Parameter Reference

| Parameter | Type | Required? | Description |
| :--- | :--- | :--- | :--- |
| `name` | `String` | **Yes** | Full display name (e.g. `"Dr. Elena Vance"`). |
| `handle` | `String` | **Yes** | Profile handle (e.g. `"@elena.vance"`). |
| `title` | `String` | **Yes** | Headline role or title (e.g. `"Quantum AI Architect"`). |
| `company` | `String?` | Optional | Organization or team name. |
| `location` | `String?` | Optional | Location text displayed alongside a pin icon. |
| `bio` | `String?` | Optional | Multi-line biography or mission statement. |
| `avatarRes` | `Int?` | Optional | Local drawable resource ID for avatar. |
| `avatarPainter` | `Painter?` | Optional | Compose `Painter` for custom/remote image loading. |
| `statusText` | `String` | Optional (`"ACTIVE"`) | Top status pill text (e.g. `"SYNCHRONIZED"`, `"ONLINE"`). |
| `statusColor` | `Color?` | Optional | Color of pulsing status indicator dot. |
| `memberId` | `String` | Optional (`"ID-2049"`) | Monospace ID badge code for reverse side. |
| `verificationHash` | `String` | Optional | Cryptographic verification stamp. |
| `style` | `AuraCardStyle` | Optional (`GLASSMORPHISM`) | One of the 5 archetypes: `GLASSMORPHISM`, `CARD_FLIP_3D`, `CURTAIN_REVEAL`, `PARALLAX_NEBULA`, `TITANIUM_LUXE`. |
| `colors` | `AuraCardColors` | Optional | Palette preset (`cyberNeon`, `solarFlare`, `emeraldJade`, `amethystBloom`, `titaniumLuxe`) or custom. |
| `isDark` | `Boolean` | Optional (System dark theme) | Switches between high-contrast dark mode and clean light mode. |
| `stats` | `List<AuraStat>` | Optional | Key metrics (up to 4 rounded tiles with label & value). |
| `skills` | `List<AuraSkill>` | Optional | Capability bars with percentage levels (0.0f to 1.0f). |
| `badges` | `List<AuraBadge>` | Optional | Digital accreditation and award chips. |
| `links` | `List<AuraLink>` | Optional | Communication channels (Email, Web, Code, Social). |
| `onConnectClick` | `(() -> Unit)?` | Optional | Primary action button callback. |
| `onMessageClick` | `(() -> Unit)?` | Optional | Secondary action button callback. |
| `onShareClick` | `(() -> Unit)?` | Optional | Share button callback. |
| `onCardClick` | `(() -> Unit)?` | Optional | Callback when user taps the card body. |
| `customHeaderSlot` | `(@Composable () -> Unit)?` | Optional | Custom slot to replace the top status bar. |
| `customActionsSlot`| `(@Composable () -> Unit)?` | Optional | Custom slot to replace the action button row. |
| `customFooterSlot` | `(@Composable () -> Unit)?` | Optional | Custom slot to insert extra content at bottom. |
