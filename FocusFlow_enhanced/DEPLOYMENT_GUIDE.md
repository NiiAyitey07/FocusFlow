# 📱 FOCUSFLOW - GITHUB ACTIONS DEPLOYMENT GUIDE

Complete guide to deploy FocusFlow Android app using GitHub Actions (no Flutter needed!)

---

## 🎯 OVERVIEW

**What You're Getting:**
- Native Android app (Kotlin + XML)
- Automatic APK builds via GitHub Actions
- No Flutter installation required
- Download APK directly from GitHub
- Install on any Android phone (API 24+)

**Time Required:** ~10 minutes  
**Cost:** FREE (GitHub Actions free tier)

---

## 📋 PREREQUISITES

1. **GitHub Account** (free)
   - Sign up at: https://github.com/signup

2. **Android Phone** (to test app)
   - Android 7.0 or higher
   - Enable "Unknown Sources" installation

3. **No other tools needed!**
   - No Android Studio required
   - No Flutter required
   - No command line needed (optional)

---

## 🚀 STEP-BY-STEP DEPLOYMENT

### STEP 1: Upload to GitHub (3 minutes)

#### Option A: Using GitHub Website (Easiest)

1. **Create new repository:**
   - Go to: https://github.com/new
   - Name: `FocusFlow-Android`
   - Description: "Focus app without pressure"
   - Make it **Public** or **Private**
   - Click **Create repository**

2. **Upload files:**
   - Click **uploading an existing file**
   - Select all files from `FocusFlow_GitHub` folder
   - Or drag & drop the entire folder
   - Commit message: "Initial FocusFlow Android app"
   - Click **Commit changes**

#### Option B: Using Command Line (For Developers)

```bash
# 1. Navigate to project folder
cd FocusFlow_GitHub

# 2. Initialize Git
git init
git add .
git commit -m "Initial FocusFlow Android app"

# 3. Connect to GitHub
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/FocusFlow-Android.git
git push -u origin main
```

---

### STEP 2: GitHub Builds APK Automatically (3-5 minutes)

**What happens automatically:**

1. GitHub detects the push
2. Reads `.github/workflows/android-build.yml`
3. Starts Ubuntu runner
4. Installs JDK 17
5. Runs `./gradlew assembleDebug`
6. Builds `app-debug.apk` (~20 MB)
7. Uploads APK as artifact

**To watch the build:**

1. Go to your repo on GitHub
2. Click **Actions** tab
3. See "Android CI - Build APK" workflow running
4. Wait for green checkmark ✅ (3-5 minutes)

**First build:** ~5-7 minutes (downloads dependencies)  
**Subsequent builds:** ~3-4 minutes (cached)

---

### STEP 3: Download APK (1 minute)

1. **Go to Actions tab** in your repo
2. **Click latest workflow run** (green checkmark)
3. **Scroll down to "Artifacts"** section
4. **Click "FocusFlow-debug"** to download ZIP
5. **Extract ZIP** → get `app-debug.apk`

**APK Details:**
- File name: `app-debug.apk`
- Size: ~20 MB
- Version: 1.0.0
- Min Android: 7.0 (API 24)
- Target Android: 14 (API 34)

---

### STEP 4: Install on Android Phone (2 minutes)

#### Method 1: Email/Drive Transfer (Easiest)

1. **Transfer APK to phone:**
   - Email `app-debug.apk` to yourself
   - Or upload to Google Drive
   - Or use USB cable

2. **On your phone:**
   - Open `app-debug.apk` file
   - Tap **Install**
   - If prompted: **Allow from this source**
   - Wait 10-30 seconds
   - Tap **Open**

#### Method 2: ADB Install (Developer)

```bash
# Connect phone via USB
adb devices

# Install APK
adb install app-debug.apk
```

---

## 🎉 SUCCESS! APP IS INSTALLED

**Open FocusFlow and see:**

1. ✅ Splash Screen (2 seconds)
2. ✅ Onboarding (Welcome + Privacy)
3. ✅ Goal Setup
4. ✅ Home Screen with tasks
5. ✅ Progress tracking
6. ✅ All features working!

---

## 🔧 HOW TO UPDATE THE APP

**After making code changes:**

1. Push changes to GitHub:
   ```bash
   git add .
   git commit -m "Update feature X"
   git push
   ```

2. GitHub Actions rebuilds automatically (3-4 min)

3. Download new APK from Actions tab

4. Install new APK over old one (keeps data!)

---

## 🆘 TROUBLESHOOTING

### Issue: "GitHub Actions build failed"

**Solution 1: Check Logs**
1. Go to Actions tab
2. Click failed workflow
3. Read error message
4. Common issues:
   - Syntax error in `build.gradle`
   - Missing dependency
   - Gradle version mismatch

**Solution 2: Re-run Build**
1. Click failed workflow
2. Click "Re-run all jobs"
3. Wait for completion

---

### Issue: "Can't install APK on phone"

**Error: "Install blocked"**
- Go to: Settings → Security
- Enable: **Install unknown apps**
- Allow installation from browser/email app

**Error: "App not installed"**
- Uninstall old version first
- Or increase `versionCode` in `build.gradle`

---

### Issue: "Build takes too long"

**Normal times:**
- First build: 5-7 minutes (downloads ~200 MB dependencies)
- Cached builds: 3-4 minutes

**If longer than 10 minutes:**
- Check GitHub Actions status page
- Re-run workflow
- Check for network issues

---

### Issue: "APK not found in Artifacts"

**Check these:**
1. Build completed successfully (green checkmark)?
2. Scrolled to bottom of workflow run page?
3. Artifacts section shows "FocusFlow-debug"?

**If missing:**
- Re-run workflow
- Check `android-build.yml` syntax
- Verify paths: `app/build/outputs/apk/debug/app-debug.apk`

---

## 📊 GITHUB ACTIONS FREE TIER LIMITS

**What's included (FREE):**
- 2,000 minutes/month (Linux runner)
- Unlimited public repos
- 500 MB artifact storage

**How much does FocusFlow use?**
- Each build: ~4 minutes
- Monthly builds: 500 builds FREE
- Artifact size: ~20 MB (auto-deleted after 30 days)

**You're covered!** ✅

---

## 🎨 CUSTOMIZATION OPTIONS

### Change App Name
Edit: `app/src/main/res/values/strings.xml`
```xml
<string name="app_name">MyFocusApp</string>
```

### Change Colors
Edit: `app/src/main/res/values/colors.xml`
```xml
<color name="green_primary">#4CAF50</color>
<color name="navy_background">#1a1f3a</color>
```

### Change App ID
Edit: `app/build.gradle`
```gradle
applicationId "com.yourname.focusflow"
```

**Then:** Push changes → GitHub rebuilds APK automatically!

---

## 🔐 PRIVACY & SECURITY

**What data is collected during build:**
- ❌ None! GitHub Actions runs in isolated container
- ✅ No personal data
- ✅ No tracking
- ✅ Code never leaves GitHub

**What data the app collects:**
- ❌ No personal information
- ❌ No analytics
- ❌ No location
- ✅ Only local storage (goals, tasks, progress)

---

## 📞 SUPPORT

**Build Issues:**
- Check: `.github/workflows/android-build.yml`
- Verify: `app/build.gradle` syntax
- Review: GitHub Actions logs

**App Issues:**
- Test on multiple Android devices
- Check: Android version (7.0+ required)
- Review: App logs via `adb logcat`

---

## 🎯 NEXT STEPS

### ✅ Completed:
- [x] Upload code to GitHub
- [x] GitHub Actions builds APK
- [x] Download and install APK
- [x] Test FocusFlow on phone

### 🚀 What's Next:
- [ ] Customize colors/branding
- [ ] Add app icon (see below)
- [ ] Test on multiple devices
- [ ] Publish to Google Play Store (optional)
- [ ] Add Firebase (optional)

---

## 🎨 ADDING APP ICON (BONUS)

**Quick Steps:**

1. **Generate icon:**
   - Use: https://icon.kitchen
   - Upload logo image
   - Download icon pack

2. **Replace icons:**
   - Extract downloaded pack
   - Copy to: `app/src/main/res/mipmap-*/`
   - Overwrites: `ic_launcher.png` and `ic_launcher_round.png`

3. **Push changes:**
   ```bash
   git add .
   git commit -m "Add custom app icon"
   git push
   ```

4. **Download new APK** from Actions tab

---

## 📈 PUBLISHING TO GOOGLE PLAY (ADVANCED)

**When you're ready:**

1. **Create signed APK:**
   - Generate keystore
   - Update `build.gradle` with signing config
   - Build release APK: `./gradlew assembleRelease`

2. **Create Play Store account:**
   - Cost: $25 one-time fee
   - https://play.google.com/console

3. **Submit APK:**
   - Upload AAB/APK
   - Fill store listing
   - Submit for review (2-7 days)

**Guide:** https://developer.android.com/studio/publish

---

## ✅ CHECKLIST - DEPLOYMENT COMPLETE!

- [x] GitHub repo created
- [x] Code pushed to GitHub
- [x] GitHub Actions built APK
- [x] APK downloaded from Artifacts
- [x] APK installed on Android phone
- [x] FocusFlow app tested and working

**🎉 CONGRATULATIONS! You deployed FocusFlow!**

---

## 📄 SUMMARY

**What you achieved:**
- ✅ Deployed native Android app
- ✅ Used GitHub Actions for free CI/CD
- ✅ No Flutter or Android Studio needed
- ✅ Automatic APK builds on every push
- ✅ Easy updates and distribution

**Time spent:** ~10 minutes  
**Cost:** $0 (FREE!)

---

**Ready to build your 30-day focus journey!** 🌿✨
