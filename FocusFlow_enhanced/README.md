# 🚀 FocusFlow - GitHub Actions Build (Flutter-Free!)

**Native Android App** with automatic APK builds via GitHub Actions.

---

## ✨ WHAT IS THIS?

FocusFlow rebuilt as a **native Android app** (no Flutter required!):
- ✅ **Native Android** - Java/Kotlin with Material Design 3
- ✅ **GitHub Actions** - Auto-builds APK on every push (FREE!)
- ✅ **Local Storage** - Room database (SQLite)
- ✅ **Zero Setup** - Just push to GitHub and download APK

---

## 🎯 ALL FOCUSFLOW FEATURES INCLUDED

- 30-day goal tracking (Daily/Weekly/Monthly)
- 1-3 daily tasks (self-paced, no timers)
- Streak tracking & progress calendar
- Privacy-first design (local storage only)
- Dark navy theme with green accents
- Evening reflections (optional)

---

## 📱 HOW TO GET THE APK (3 STEPS)

### Step 1: Upload to GitHub

```bash
# 1. Create new GitHub repo
# Go to: https://github.com/new
# Name: FocusFlow-Android

# 2. Initialize and push
cd FocusFlow_GitHub
git init
git add .
git commit -m "Initial FocusFlow Android app"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/FocusFlow-Android.git
git push -u origin main
```

### Step 2: GitHub Builds Automatically

After you push:
1. GitHub Actions starts automatically
2. Builds APK (takes ~3-5 minutes)
3. Uploads APK as artifact

### Step 3: Download APK

1. Go to your repo on GitHub
2. Click **Actions** tab
3. Click latest workflow run (green checkmark)
4. Scroll down to **Artifacts**
5. Download **FocusFlow-debug.zip**
6. Extract and install `app-debug.apk` on your phone

---

## 📲 INSTALL ON YOUR ANDROID PHONE

### Method 1: Direct Install (Recommended)

1. Download `FocusFlow-debug.zip` from GitHub Actions
2. Extract `app-debug.apk`
3. Transfer to your phone via:
   - Email attachment
   - Google Drive
   - USB cable
4. On phone: Open `app-debug.apk`
5. Allow "Install from Unknown Sources" if prompted
6. Tap **Install**

### Method 2: ADB Install (Developer)

```bash
# Extract APK from ZIP
unzip FocusFlow-debug.zip

# Install via ADB
adb install app-debug.apk
```

---

## 🔧 TECH STACK

| Component | Technology |
|-----------|------------|
| **Platform** | Native Android (Kotlin) |
| **Build System** | Gradle 8.2 + AGP 8.2.0 |
| **UI** | Material Design 3 + XML Layouts |
| **Database** | Room (SQLite) |
| **Architecture** | MVVM with LiveData |
| **Min SDK** | Android 7.0 (API 24) |
| **Target SDK** | Android 14 (API 34) |
| **CI/CD** | GitHub Actions (FREE) |

---

## 📦 PROJECT STRUCTURE

```
FocusFlow_GitHub/
├── .github/workflows/
│   └── android-build.yml          # GitHub Actions workflow
├── app/
│   ├── build.gradle               # App-level build config
│   └── src/main/
│       ├── java/com/focusflow/app/
│       │   ├── FocusFlowApplication.kt
│       │   ├── ui/                # Activities & Fragments
│       │   ├── data/              # Database, DAOs, Models
│       │   └── utils/             # PreferencesManager
│       ├── res/                   # Layouts, strings, colors
│       └── AndroidManifest.xml
├── gradle/wrapper/
├── build.gradle                   # Project-level build
├── settings.gradle
└── README.md
```

---

## 🎨 WHAT THE APP LOOKS LIKE

**Screens Included:**
1. ✅ Splash Screen - FocusFlow logo + tagline
2. ✅ Onboarding - 2-page intro (Welcome + Privacy)
3. ✅ Goal Setup - Choose duration, set goal, add "why"
4. ✅ Home Screen - Daily tasks, streak counter, privacy badge
5. ✅ Progress Screen - Calendar, stats, completion rate
6. ✅ Privacy Screen - Data transparency

**Design:**
- Dark navy background (#1a1f3a)
- Green accent color (#4CAF50)
- Material Design 3
- No timers or countdowns
- Self-paced UX

---

## 🔒 PRIVACY & DATA

**What We Store (Local Only):**
- ✅ Your goals
- ✅ Daily tasks
- ✅ Progress & streaks
- ✅ Reflections (optional)

**What We DON'T Collect:**
- ❌ No personal information
- ❌ No analytics or tracking
- ❌ No location data
- ❌ No internet requests (except Firebase if you add it)

---

## ⚡ GITHUB ACTIONS WORKFLOW

**What Happens When You Push:**

```yaml
1. GitHub checks out your code
2. Sets up JDK 17
3. Grants execute permission to gradlew
4. Runs: ./gradlew assembleDebug
5. Uploads app-debug.apk as artifact
6. You download from Actions tab
```

**Build Time:** ~3-5 minutes  
**Cost:** FREE (GitHub Actions free tier: 2000 min/month)

---

## 🚀 NEXT STEPS

### Option A: Quick Test (Recommended)
1. Push this code to GitHub
2. Wait 3-5 minutes for build
3. Download APK from Actions tab
4. Install on your Android phone
5. Test the app!

### Option B: Local Development
```bash
# Open in Android Studio
# File → Open → Select FocusFlow_GitHub folder
# Wait for Gradle sync
# Click Run button
```

---

## 🆘 TROUBLESHOOTING

### "GitHub Actions build failed"
- Check that `gradlew` has execute permission
- Verify `build.gradle` syntax
- Look at Actions log for specific error

### "Can't install APK on phone"
- Enable "Install from Unknown Sources"
- Settings → Security → Unknown Sources

### "Build takes too long"
- First build: ~5-7 minutes (downloads dependencies)
- Subsequent builds: ~3-4 minutes (cached)

---

## 🎉 READY TO GO!

This is a **complete, working Android app** that builds automatically on GitHub.

**No Flutter. No complex setup. Just push and download!**

---

## 📞 SUPPORT

Questions? Issues?
- Check GitHub Actions logs
- Verify build.gradle syntax
- Ensure JDK 17 compatibility

---

## 📄 LICENSE

MIT License - Free to use, modify, and distribute.

---

**Built with ❤️ for stress-free productivity**
