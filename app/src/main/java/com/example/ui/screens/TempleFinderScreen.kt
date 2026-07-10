package com.example.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.components.SacredCard
import com.example.ui.viewmodel.SadhakViewModel
import org.json.JSONArray
import org.json.JSONObject

data class HinduTemple(
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val desc: String,
    val distanceKm: Double = 0.0
)

data class PilgrimageCity(
    val nameEn: String,
    val nameHi: String,
    val lat: Double,
    val lon: Double
)

val holyCities = listOf(
    PilgrimageCity("Varanasi", "वाराणसी", 25.3176, 82.9739),
    PilgrimageCity("Vrindavan", "वृंदावन", 27.5650, 77.7008),
    PilgrimageCity("Ayodhya", "अयोध्या", 26.7956, 82.1944),
    PilgrimageCity("Haridwar", "हरिद्वार", 29.9457, 78.1642),
    PilgrimageCity("Rameshwaram", "रामेश्वरम", 9.2881, 79.3129),
    PilgrimageCity("Kedarnath", "केदारनाथ", 30.7352, 79.0669)
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "MissingPermission")
@Composable
fun TempleFinderView(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val context = LocalContext.current

    var hasPermission by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }
    var currentLatitude by remember { mutableStateOf(25.3176) } // Default: Varanasi
    var currentLongitude by remember { mutableStateOf(82.9739) }
    var isLocating by remember { mutableStateOf(false) }

    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    val templesList = remember { mutableStateListOf<HinduTemple>() }
    var searchQuery by remember { mutableStateOf("") }
    var radiusKm by remember { mutableStateOf(10f) } // Radius in Km (1 to 50)
    var selectedCityName by remember { mutableStateOf("") }

    // Geolocation permission request launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        hasPermission = granted
        if (!granted) {
            locationError = if (isEng) "Location permission denied. Showing Varanasi." else "स्थान अनुमति अस्वीकार कर दी गई। वाराणसी दिखा रहा है।"
        }
    }

    // Function to search temples from Overpass API
    fun triggerOverpassSearch(lat: Double, lon: Double, radKm: Float) {
        val radMeters = (radKm * 1000).toInt()
        webViewInstance?.post {
            webViewInstance?.loadUrl("javascript:searchTemples($lat, $lon, $radMeters)")
        }
    }

    // Function to get current device location using LocationManager
    val requestLocationUpdate = {
        isLocating = true
        locationError = null
        val attributionContext = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context.createAttributionContext("temple_finder")
        } else {
            context
        }
        val locationManager = attributionContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                locationError = if (isEng) "GPS and Location services are disabled. Tap a Pilgrimage city below." else "जीपीएस सेवाएं बंद हैं। नीचे दिए गए पवित्र तीर्थ स्थल चुनें।"
                isLocating = false
            } else {
                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        currentLatitude = location.latitude
                        currentLongitude = location.longitude
                        isLocating = false
                        selectedCityName = ""
                        
                        webViewInstance?.post {
                            webViewInstance?.loadUrl("javascript:setLocation(${location.latitude}, ${location.longitude}, 'Current Location')")
                        }
                        triggerOverpassSearch(location.latitude, location.longitude, radiusKm)
                        locationManager.removeUpdates(this)
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, listener)
                } else if (isGpsEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, listener)
                }
            }
        } catch (e: SecurityException) {
            locationError = e.message
            isLocating = false
        }
    }

    // Request permissions on first load
    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // Trigger update if permission is granted
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            requestLocationUpdate()
        }
    }

    // Handle radius changes
    LaunchedEffect(radiusKm) {
        if (webViewInstance != null) {
            triggerOverpassSearch(currentLatitude, currentLongitude, radiusKm)
        }
    }

    val filteredTemples = templesList.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.desc.contains(searchQuery, ignoreCase = true)
    }.map { temple ->
        // Calculate dynamic distance in Km
        val results = FloatArray(1)
        Location.distanceBetween(currentLatitude, currentLongitude, temple.lat, temple.lon, results)
        temple.copy(distanceKm = (results[0] / 1000.0))
    }.sortedBy { it.distanceKm }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("temple_finder_container")
    ) {
        // Controls Header
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Loc", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isEng) "Find Nearby Mandirs" else "निकटतम हिंदू मंदिर खोजें",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    
                    if (isLocating) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        IconButton(
                            onClick = {
                                if (hasPermission) {
                                    requestLocationUpdate()
                                } else {
                                    permissionLauncher.launch(
                                        arrayOf(
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        )
                                    )
                                }
                            }
                        ) {
                            Icon(Icons.Default.MyLocation, contentDescription = "My Loc", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                locationError?.let { err ->
                    Text(text = err, color = MaterialTheme.colorScheme.error, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }

                // Radius Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEng) "Search Radius: ${radiusKm.toInt()} km" else "खोज दायरा: ${radiusKm.toInt()} किमी",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(130.dp)
                    )
                    Slider(
                        value = radiusKm,
                        onValueChange = { radiusKm = it },
                        valueRange = 1f..50f,
                        steps = 9,
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                // Pilgrimage shortcuts
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEng) "Or explore Pilgrimage hubs:" else "या पवित्र तीर्थ स्थलों को देखें:",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    holyCities.take(4).forEach { city ->
                        val isSelected = selectedCityName == city.nameEn
                        SuggestionChip(
                            onClick = {
                                currentLatitude = city.lat
                                currentLongitude = city.lon
                                selectedCityName = city.nameEn
                                webViewInstance?.post {
                                    webViewInstance?.loadUrl("javascript:setLocation(${city.lat}, ${city.lon}, '${city.nameEn}')")
                                }
                                triggerOverpassSearch(city.lat, city.lon, radiusKm)
                            },
                            label = { Text(if (isEng) city.nameEn else city.nameHi, fontSize = 10.sp) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Leaflet Interactive Map WebView
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        webChromeClient = object : WebChromeClient() {
                            override fun onGeolocationPermissionsShowPrompt(
                                origin: String,
                                callback: GeolocationPermissions.Callback
                            ) {
                                callback.invoke(origin, true, false)
                            }
                        }
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.setGeolocationEnabled(true)

                        // Bridge to send results back from HTML javascript to Compose
                        addJavascriptInterface(object {
                            @JavascriptInterface
                            fun onTemplesFound(json: String) {
                                post {
                                    try {
                                        val arr = JSONArray(json)
                                        templesList.clear()
                                        for (i in 0 until arr.length()) {
                                            val obj = arr.getJSONObject(i)
                                            templesList.add(
                                                HinduTemple(
                                                    id = obj.optLong("id", i.toLong()),
                                                    name = obj.optString("name", "Hindu Temple"),
                                                    lat = obj.optDouble("lat", 0.0),
                                                    lon = obj.optDouble("lon", 0.0),
                                                    desc = obj.optString("desc", "Temple of Peace")
                                                )
                                            )
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }

                            @JavascriptInterface
                            fun onError(msg: String) {
                                post {
                                    locationError = if (isEng) "Overpass query rate limit reached. Retrying shortly." else "सर्च गति सीमा पार हो गई है। कृपया कुछ क्षण प्रतीक्षा करें।"
                                }
                            }
                        }, "AndroidBridge")

                        val mapHtml = """
                            <!DOCTYPE html>
                            <html>
                            <head>
                                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
                                <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
                                <style>
                                    html, body, #map {
                                        width: 100%;
                                        height: 100%;
                                        margin: 0;
                                        padding: 0;
                                        background: #fdf5e6;
                                    }
                                </style>
                                <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
                            </head>
                            <body>
                                <div id="map"></div>
                                <script>
                                    var map = L.map('map', { zoomControl: false }).setView([$currentLatitude, $currentLongitude], 13);
                                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                                        attribution: '&copy; OpenStreetMap contributors'
                                    }).addTo(map);

                                    var userMarker = null;
                                    var markers = [];

                                    function setLocation(lat, lon, label) {
                                        map.setView([lat, lon], 14);
                                        if (userMarker) {
                                            userMarker.setLatLng([lat, lon]);
                                        } else {
                                            userMarker = L.marker([lat, lon], {
                                                icon: L.icon({
                                                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
                                                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                                                    iconSize: [25, 41],
                                                    iconAnchor: [12, 41],
                                                    popupAnchor: [1, -34],
                                                    shadowSize: [41, 41]
                                                })
                                            }).addTo(map).bindPopup(label || "You are here").openPopup();
                                        }
                                    }

                                    function clearMarkers() {
                                        markers.forEach(m => map.removeLayer(m));
                                        markers = [];
                                    }

                                    function addTempleMarker(lat, lon, name, desc) {
                                        var marker = L.marker([lat, lon], {
                                            icon: L.icon({
                                                iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-orange.png',
                                                shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                                                iconSize: [25, 41],
                                                iconAnchor: [12, 41],
                                                popupAnchor: [1, -34],
                                                shadowSize: [41, 41]
                                            })
                                        }).addTo(map).bindPopup("<b>" + name + "</b><br>" + (desc || "Hindu Temple"));
                                        markers.push(marker);
                                    }

                                    function centerOn(lat, lon) {
                                        map.setView([lat, lon], 16);
                                    }

                                    function searchTemples(lat, lon, radius) {
                                        var overpassUrl = "https://overpass-api.de/api/interpreter?data=[out:json][timeout:15];node[\"religion\"=\"hindu\"](around:" + radius + "," + lat + "," + lon + ");out body;";
                                        
                                        fetch(overpassUrl)
                                            .then(response => response.json())
                                            .then(data => {
                                                clearMarkers();
                                                var results = [];
                                                if (data.elements) {
                                                    data.elements.forEach(el => {
                                                        var name = el.tags.name || "Hindu Temple";
                                                        var desc = el.tags.description || el.tags.amenity || "Temple of Peace";
                                                        addTempleMarker(el.lat, el.lon, name, desc);
                                                        results.push({
                                                            id: el.id,
                                                            name: name,
                                                            lat: el.lat,
                                                            lon: el.lon,
                                                            desc: desc
                                                        });
                                                    });
                                                }
                                                if (window.AndroidBridge) {
                                                    window.AndroidBridge.onTemplesFound(JSON.stringify(results));
                                                }
                                            })
                                            .catch(err => {
                                                console.error(err);
                                                if (window.AndroidBridge) {
                                                    window.AndroidBridge.onError(err.message);
                                                }
                                            });
                                    }

                                    // Init location
                                    setLocation($currentLatitude, $currentLongitude, 'Varanasi (Default)');
                                    searchTemples($currentLatitude, $currentLongitude, 10000);
                                </script>
                            </body>
                            </html>
                        """.trimIndent()

                        loadDataWithBaseURL("https://openstreetmap.org", mapHtml, "text/html", "UTF-8", null)
                        webViewInstance = this
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Search Field and Results List
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(if (isEng) "Filter mandirs by deity/name..." else "मंदिर या देवता के नाम से खोजें...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isEng) "Discovered Temples (${filteredTemples.size})" else "खोजे गए मंदिर (${filteredTemples.size})",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (filteredTemples.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isEng) "No temples found within radius. Try increasing search range." else "इस क्षेत्र में कोई मंदिर नहीं मिला। कृपया सीमा बढाएं।",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                items(filteredTemples) { temple ->
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Place, contentDescription = "Pin", tint = MaterialTheme.colorScheme.primary)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = temple.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = temple.desc,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "📍 " + String.format("%.2f km away", temple.distanceKm),
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                // Focus on map button
                                IconButton(
                                    onClick = {
                                        webViewInstance?.post {
                                            webViewInstance?.loadUrl("javascript:centerOn(${temple.lat}, ${temple.lon})")
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.ZoomIn, contentDescription = "Focus", tint = MaterialTheme.colorScheme.secondary)
                                }

                                // Get directions button
                                IconButton(
                                    onClick = {
                                        val gmmIntentUri = Uri.parse("geo:0,0?q=${temple.lat},${temple.lon}(${Uri.encode(temple.name)})")
                                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                        mapIntent.setPackage("com.google.android.apps.maps")
                                        try {
                                            context.startActivity(mapIntent)
                                        } catch (e: Exception) {
                                            // Fallback to browser navigation
                                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${temple.lat},${temple.lon}"))
                                            context.startActivity(browserIntent)
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Navigation, contentDescription = "Directions", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
