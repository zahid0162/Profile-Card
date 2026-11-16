# AuraProfileCard — Maven & Maven Central Publishing Guide

**AuraProfileCard** is an ultra-creative, animated Jetpack Compose Android library featuring 5 visual card archetypes:
1. **Glass Prism (Glassmorphism)** — Frosted translucent glass, refractive borders, and real-time specular highlights.
2. **3D Hologram Flip** — Full 3D gyroscopic flip with specular reflections and biometric verification QR matrix.
3. **Curtain Reveal** — Theatrical silk pleated curtains with auto-open, curtain-flip to bio page, and haptic feedback.
4. **Cosmic Parallax** — Multi-depth 3D stereoscopic layering tracking finger drag and ambient tilt.
5. **Titanium Luxe** — Brushed dark alloy with gold laser borders and an animated audio telemetry waveform equalizer.

---

## 1. Quickstart for Consumers

Once published, any Android developer can integrate this library in their Compose project:

### Step 1: Add Dependency
In the consumer's `app/build.gradle.kts`:
```kotlin
dependencies {
    // For Maven Central:
    implementation("io.github.auraprofile:auraprofile-card:1.0.0")

    // Or if published via JitPack:
    // implementation("com.github.YOUR_USERNAME:auraprofile-card:1.0.0")
}
```

### Step 2: Simple Usage in Jetpack Compose
```kotlin
import com.example.auraprofile.AuraProfileCard
import com.example.auraprofile.model.AuraCardStyle
import com.example.auraprofile.model.AuraStat
import com.example.auraprofile.model.AuraSkill

@Composable
fun MyScreen() {
    AuraProfileCard(
        name = "Dr. Elena Vance",
        handle = "@elena.vance",
        title = "Quantum AI Architect",
        company = "NeuralSphere Labs",
        location = "Neo-Zurich",
        bio = "Designing fault-tolerant neural manifolds & synthetic cognition architectures.",
        style = AuraCardStyle.GLASSMORPHISM, // or CARD_FLIP_3D, CURTAIN_REVEAL, PARALLAX_NEBULA, TITANIUM_LUXE
        stats = listOf(
            AuraStat("Patents", "14"),
            AuraStat("Citations", "9.4k"),
            AuraStat("Rank", "#01")
        ),
        skills = listOf(
            AuraSkill("Neural Core Architecture", 0.96f),
            AuraSkill("Quantum UX Design", 0.92f)
        ),
        onConnectClick = { /* Handle connection */ },
        onMessageClick = { /* Handle message */ },
        onShareClick = { /* Handle share */ }
    )
}
```

---

## 2. Full Customized Composable API Reference

The library provides the master `AuraProfileCard` Composable with complete customization across info, styles, colors, telemetry, and action callbacks.

```kotlin
@Composable
fun AuraProfileCard(
    info: AuraProfileInfo,
    modifier: Modifier = Modifier,
    style: AuraCardStyle = AuraCardStyle.GLASSMORPHISM,
    colors: AuraCardColors = AuraCardColors.default(isSystemInDarkTheme()),
    isDark: Boolean = isSystemInDarkTheme(),
    stats: List<AuraStat> = emptyList(),
    skills: List<AuraSkill> = emptyList(),
    badges: List<AuraBadge> = emptyList(),
    links: List<AuraLink> = emptyList(),
    actions: AuraCardActions = AuraCardActions(),
    customHeaderSlot: (@Composable () -> Unit)? = null,
    customActionsSlot: (@Composable () -> Unit)? = null,
    customFooterSlot: (@Composable () -> Unit)? = null
)
```

### Custom Dynamic Colors & Themes
You can create fully custom color schemes or use pre-configured palettes:
```kotlin
// Option 1: Preset Palettes
val colors = AuraCardColors.cyberNeon(isDark = true)
val colors = AuraCardColors.solarFlare(isDark = true)
val colors = AuraCardColors.emeraldJade(isDark = true)
val colors = AuraCardColors.amethystBloom(isDark = true)
val colors = AuraCardColors.titaniumLuxe(isDark = true)

// Option 2: Completely Custom Colors
val customColors = AuraCardColors(
    primary = Color(0xFF00E5FF),
    secondary = Color(0xFF9D00FF),
    accent = Color(0xFFFF0055),
    glowColor = Color(0x6600E5FF),
    cardBackground = Color(0xF20B101E),
    textColor = Color(0xFFFFFFFF),
    textSecondaryColor = Color(0xFF94A3B8),
    statusColor = Color(0xFF00E5FF)
)
```

### Dynamic Actions & Callbacks
```kotlin
val actions = AuraCardActions(
    connectLabel = "Collaborate",
    onConnectClick = { viewModel.sendInvite() },
    messageLabel = "Direct Ping",
    onMessageClick = { navController.navigate("chat_screen") },
    onShareClick = { shareProfileIntent() },
    onFlipClick = { hapticFeedback() },
    onCardClick = { openExpandedProfile() }
)
```

---

## 3. How to Publish to Maven Central (Sonatype Central Portal)

Maven Central is the gold standard for public Android libraries. In 2024+, Sonatype introduced the **Sonatype Central Portal** (`central.sonatype.com`), replacing the legacy OSSRH Jira process with an automated, self-service dashboard.

### Step 1: Create an Account & Register Namespace
1. Go to [central.sonatype.com](https://central.sonatype.com/) and create an account.
2. Under **Namespaces**, click **Add Namespace**:
   * If using GitHub: enter `io.github.<your-github-username>`.
   * Sonatype will prompt you to create a temporary public repository (e.g. `https://github.com/<your-username>/<verification-code>`) to verify domain ownership.
   * Click **Verify**. Once verified, your namespace is activated!

### Step 2: Generate GPG Signing Key
Maven Central requires all `.aar` and `.pom` artifacts to be cryptographically signed with GPG:
```bash
# Generate a new GPG key pair
gpg --gen-key

# List your public keys to find the 8-character Key ID
gpg --list-keys --keyid-format SHORT

# Publish your public key to the standard keyserver
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID

# Export your ASCII-armored private key (needed by Gradle)
gpg --armor --export-secret-keys YOUR_KEY_ID > secring.gpg
```

### Step 3: Add Credentials to `gradle.properties`
In your local `~/.gradle/gradle.properties` (never commit this file to git):
```properties
# Sonatype Central Portal User Token
mavenCentralUsername=YOUR_SONATYPE_PORTAL_TOKEN_USERNAME
mavenCentralPassword=YOUR_SONATYPE_PORTAL_TOKEN_PASSWORD

# GPG Signing
signing.keyId=YOUR_KEY_ID
signing.password=YOUR_GPG_PASSPHRASE
signing.secretKeyRingFile=/path/to/secring.gpg
```

### Step 4: Run the Gradle Publish Task
In your project directory, execute:
```bash
gradle :auraprofile:publishReleasePublicationToSonatypeRepository
```
Or for local testing on your machine:
```bash
gradle :auraprofile:publishToMavenLocal
```
This builds `auraprofile-card-1.0.0.aar`, attaches the Sources JAR, signs all files with your GPG key, and securely uploads the publication bundle to Maven Central!

### Step 5: Release on Central Portal
1. Open [central.sonatype.com](https://central.sonatype.com/) -> **Deployments**.
2. Locate your uploaded deployment (`io.github.yourusername:auraprofile-card:1.0.0`).
3. Click **Publish**. Within 15–30 minutes, your library will be live across worldwide Maven Central mirrors!

---

## 4. Alternative: Instant 1-Click Publishing via JitPack

If you want an immediate zero-setup release without waiting for Sonatype namespace approval:

1. Push this project to GitHub.
2. On GitHub, go to **Releases** -> **Create a new release** -> tag `1.0.0`.
3. Go to [jitpack.io](https://jitpack.io), paste your GitHub repository URL, and click **Get it**.
4. JitPack automatically builds the library from `auraprofile/build.gradle.kts`.
5. Developers can immediately use:
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

dependencies {
    implementation("com.github.YOUR_GITHUB_USER:auraprofile-card:1.0.0")
}
```

---

## 5. Module Structure

```
├── auraprofile/               # Standalone Android Library Module (:auraprofile)
│   ├── build.gradle.kts       # Android library + maven-publish configuration
│   ├── consumer-rules.pro     # Proguard consumer rules
│   └── src/main/java/com/example/auraprofile/
│       ├── AuraProfileCard.kt # Master Composable with all 5 archetypes
│       └── model/
│           └── AuraModels.kt  # AuraCardColors, AuraCardStyle, AuraProfileInfo, etc.
│
├── app/                       # Interactive Sample & Demo App (:app)
│   ├── build.gradle.kts       # Depends on implementation(project(":auraprofile"))
│   └── src/main/java/com/example/
│       └── MainActivity.kt    # Showcase app running in the emulator
└── MAVEN_PUBLISHING_GUIDE.md  # This publishing guide
```
