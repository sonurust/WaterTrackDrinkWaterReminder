## ✅ **TODO.md**

````markdown
# WaterTrack – Drink Water Reminder

An offline Android app that reminds users to drink water or other liquids at regular intervals, helping maintain hydration and wellness.

---

## 🧩 Core Features

- **Smart Notifications:**  
  Send reminders based on user-defined intervals (e.g., every 30 mins, 1 hr, 2 hrs).  
  Notifications open the **Add Glass** screen directly.

- **Hydration Tracker:**  
  User logs each drink type and glass amount.

- **Local Storage:**  
  All data saved locally (SQLite / SharedPreferences). Works fully offline.

- **Progress Indicator:**  
  Daily water goal progress bar on Home screen.

---

## 🏠 Tabs & Navigation

### 1. Home
- Shows daily water intake, goal, and history.  
- Simple animated UI/UX following Android best practices.  
- Add Glass floating button (FAB) at center-bottom.

### 2. Add Glass (Modal / Screen)
- Triggered from FAB or notification click.  
- Swipe left/right or tap arrows to select **Liquid Type**.  
- Below: preset glass volumes (100ml, 200ml, 300ml, 500ml).  
- “Add” button saves entry and updates total.

### 3. Settings
- Notification interval (minutes/hours)  
- Daily goal (ml)  
- Sound/vibration toggle  
- Reset data option  

---

## 💾 Data Model

```json
{
  "drinkType": "Water",
  "volume": 250,
  "timestamp": "2025-10-17T12:30:00"
}
````

---

## 🔔 Notification Flow

1. Background scheduler triggers reminder.
2. User taps notification → Opens Add Glass screen.
3. Added data updates Home dashboard instantly.

---

## 🎨 UI/UX Design Notes

* **Primary color:** Soft blue gradient (hydration theme)
* **Icons:** Outline, clean vector style
* **Typography:** Modern and readable (Roboto / Inter)
* **Layout:** Rounded cards, floating glass icon, progress ring

---

## 📅 Future Add-ons

* Cloud backup
* Weekly stats chart
* Voice input (“Add a glass”)
* Widget for quick add

---

```

---

## 🎨 **Prompts for Recraft.ai (Play Store Assets)**

### 🖼️ **App Icon Prompt (Google Play Store)**
> Create a Google Play Store compliant app icon: 512x512px high-resolution icon featuring a minimalist water glass with droplet, flat material design style, adaptive icon format with safe zone, blue gradient background (#2196F3 to #03DAC6), rounded square shape with 20% corner radius, centered composition, no text or letters, clean vector graphics, professional Android app icon quality, follows Google Material Design guidelines.  
**Negative prompt:** no text, no letters, no borders, no 3D effects, no low resolution, no people, no cluttered elements, no realistic photography, no complex backgrounds, no brand names, no promotional elements, no shadows outside safe zone.

---

### 🌊 **Feature Graphic Prompt (Google Play Store)**
> Create a Google Play Store feature graphic: 1024x500px banner showcasing WaterTrack hydration app with Android phone mockup displaying clean material design interface, water glass icon, notification bell, circular progress indicator at 75%, floating action button, refreshing blue-to-white gradient background (#E3F2FD to #FFFFFF), minimalist flat design, follows Google Play Store asset guidelines, professional presentation suitable for app store listing.  
**Negative prompt:** no text overlays, no watermarks, no real people, no dark themes, no cluttered elements, no realistic photography, no complex UI elements, no brand logos, no promotional text, no pricing information, no download buttons, no app store badges.

---

### 📱 **Screenshots Prompt (Google Play Store)**
> Create 5-8 Android phone screenshots (1080x1920px) showcasing WaterTrack app: Home screen with daily progress, Add Glass modal with liquid selection, Settings screen, notification example, weekly stats view, all rendered in material design style with blue theme (#2196F3), clean UI elements, realistic Android interface, professional app store presentation.  
**Negative prompt:** no text overlays, no watermarks, no real people, no dark themes, no cluttered elements, no promotional elements, no pricing information.

---

### 🎯 **Google Play Store Compliance Notes**
- **App Icon:** Must be 512x512px, adaptive icon format, no text/letters
- **Feature Graphic:** 1024x500px, no promotional text or pricing
- **Screenshots:** 1080x1920px minimum, show actual app functionality
- **Colors:** Use Material Design blue palette (#2196F3, #03DAC6)
- **Style:** Flat design, clean, professional, follows Android guidelines

---

### ✍️ **Title**
> WaterTrack – Drink Water Reminder  

---

### 🪣 **Short Description (≤80 chars)**
> Stay hydrated! Smart reminders to drink water or any liquid on time.

---

### 📄 **Long Description**
> WaterTrack helps you stay hydrated and healthy.  
>  
> 💧 **Smart Reminders** – Set how often you want hydration alerts.  
>  
> 🥛 **Add Your Drinks** – Log water, juices, tea, or other liquids easily.  
>  
> 📊 **Track Progress** – See your daily water intake and goal.  
>  
> ⚙️ **Custom Settings** – Choose reminder time, volume, and sound options.  
>  
> Works fully **offline**, with smooth UI and modern Android design.  
>  
> Stay refreshed every day with WaterTrack!  

---

Would you like me to include a **`assets/prompts.json`** file (for direct upload or reuse in Recraft.ai / Play Console)?
```
