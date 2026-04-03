# UVA Campus Navigator (Android)

A Jetpack Compose application that provides an interactive map of the University of Virginia. This app syncs real-time placemark data from a UVA-hosted JSON API, stores it locally for offline access, and allows users to filter locations by category.

---

### 1. API Key Setup

This project uses the Google Maps SDK for Android. For security reasons, the API key is not included in this repository. To run the app:

1. Open your `local.properties` file in the project root.

2. Add the following line:

```properties
MAPS_API_KEY=YOUR_ACTUAL_API_KEY_HERE
