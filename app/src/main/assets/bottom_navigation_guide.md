# Bottom Navigation UI Guide

## 🎨 Design Overview

The WaterTrack app features a modern bottom navigation bar with a curved center cutout for a floating action button, following Material Design 3 principles.

## 🏗️ Components

### 1. Bottom Navigation Background
- **File:** `bottom_navigation_background.xml`
- **Features:** Curved center cutout, blue-white gradient, subtle borders
- **Dimensions:** 360dp width × 80dp height
- **Colors:** #E3F2FD to #FFFFFF gradient

### 2. Floating Action Button
- **File:** `floating_action_button.xml`
- **Features:** Water glass icon, radial gradient, elevation
- **Size:** 64dp × 64dp
- **Colors:** #2196F3 to #1976D2 gradient

### 3. Navigation Icons
- **Home:** `ic_nav_home.xml` - House with water droplet accent
- **Settings:** `ic_nav_settings.xml` - Gear with water droplet center
- **Add Glass:** `ic_add_glass.xml` - Plus with water glass icon

### 4. Layout Structure
- **File:** `bottom_navigation_layout.xml`
- **Components:** ConstraintLayout with FAB and navigation items
- **Features:** Proper spacing, elevation, glow effects

## 🎯 Material Design 3 Compliance

### Colors
- **Primary:** #2196F3 (Material Blue)
- **Secondary:** #03DAC6 (Water Cyan)
- **Surface:** #E3F2FD (Light Blue)
- **Background:** #FFFFFF (White)

### Elevation
- **Navigation Bar:** 8dp
- **FAB:** 12dp
- **FAB Pressed:** 16dp

### Typography
- **Labels:** 10sp, Roboto
- **Color:** #2196F3 (Primary)

## 🔧 Implementation

### Usage in Activity
```kotlin
// Include in main activity layout
<include layout="@layout/bottom_navigation_layout" />
```

### Click Handlers
```kotlin
// Set up navigation click listeners
nav_home_container.setOnClickListener { /* Navigate to Home */ }
nav_settings_container.setOnClickListener { /* Navigate to Settings */ }
fab_add_glass.setOnClickListener { /* Open Add Glass Dialog */ }
```

### State Management
```kotlin
// Update selected state
fun updateSelectedTab(tab: NavigationTab) {
    when(tab) {
        NavigationTab.HOME -> {
            nav_home_icon.setColorFilter(ContextCompat.getColor(this, R.color.navigation_icon_selected))
        }
        NavigationTab.SETTINGS -> {
            nav_settings_icon.setColorFilter(ContextCompat.getColor(this, R.color.navigation_icon_selected))
        }
    }
}
```

## 🎨 Visual Features

### Curved Cutout
- Smooth arc for FAB integration
- Maintains visual balance
- Follows Material Design guidelines

### Glow Effect
- Subtle light behind FAB
- Radial gradient (#E3F2FD)
- 20% opacity for subtlety

### Water Theme
- Consistent blue color palette
- Water droplet accents
- Hydration-focused iconography

## 📱 Responsive Design

### Screen Sizes
- **Phone:** 360dp width (standard)
- **Tablet:** Scales proportionally
- **Landscape:** Maintains aspect ratio

### Accessibility
- Content descriptions for all icons
- Proper touch targets (48dp minimum)
- High contrast colors
- Screen reader support

## 🚀 Future Enhancements

- [ ] Animation transitions between tabs
- [ ] Badge notifications on icons
- [ ] Haptic feedback on interactions
- [ ] Dark theme support
- [ ] Custom tab indicators
