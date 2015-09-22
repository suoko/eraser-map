package com.mapzen.erasermap.model

import android.location.Location
import android.preference.PreferenceManager
import com.mapzen.erasermap.EraserMapApplication
import com.mapzen.erasermap.R
import com.mapzen.valhalla.Router
import java.io.File

public class AndroidAppSettings(val application: EraserMapApplication) : AppSettings {
    companion object {
        public val KEY_DISTANCE_UNITS: String = "list_distance_units"
        public val KEY_MOCK_LOCATION_ENABLED: String = "checkbox_mock_location"
        public val KEY_MOCK_LOCATION_VALUE: String = "edittext_mock_location"
        public val KEY_MOCK_ROUTE_ENABLED: String = "checkbox_mock_route"
        public val KEY_MOCK_ROUTE_VALUE: String = "edittext_mock_route"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(application)

    /**
     * Routing distance units setting.
     */
    override var distanceUnits: Router.DistanceUnits
        get() {
            val value = prefs.getString(KEY_DISTANCE_UNITS, null)
            when (value) {
                Router.DistanceUnits.MILES.toString() -> return Router.DistanceUnits.MILES
                Router.DistanceUnits.KILOMETERS.toString() -> return Router.DistanceUnits.KILOMETERS
            }

            return Router.DistanceUnits.MILES
        }
        set(value) {
            prefs.edit().putString(KEY_DISTANCE_UNITS, value.toString()).commit()
        }

    /**
     * Mock location checkbox setting.
     */
    override var isMockLocationEnabled: Boolean
        get() {
            return prefs.getBoolean(KEY_MOCK_LOCATION_ENABLED, false)
        }
        set(value) {
            prefs.edit().putBoolean(KEY_MOCK_LOCATION_ENABLED, value).commit()
        }

    /**
     * Mock location (lat, lng) value setting.
     */
    override var mockLocation: Location
        get() {
            val default = application.getString(R.string.edittext_mock_location_default_value)
            val values = prefs.getString(KEY_MOCK_LOCATION_VALUE, default)
            val split = values?.split(",")
            val location = Location("mock")
            location.setLatitude(split?.get(0)?.toDouble() as Double)
            location.setLongitude(split?.get(1)?.toDouble() as Double)
            return location
        }
        set(value) {
            val string = value.getLatitude().toString() + ", " + value.getLongitude().toString()
            prefs.edit().putString(KEY_MOCK_LOCATION_VALUE, string).commit()
        }

    /**
     * Mock route checkbox setting.
     */
    override var isMockRouteEnabled: Boolean
        get() {
            return prefs.getBoolean(KEY_MOCK_ROUTE_ENABLED, false)
        }
        set(value) {
            prefs.edit().putBoolean(KEY_MOCK_ROUTE_ENABLED, value).commit()
        }

    /**
     * Mock route GPX trace file setting.
     */
    override var mockRoute: File
        get() {
            val default = application.getString(R.string.edittext_mock_route_default_value)
            val value = prefs.getString(KEY_MOCK_ROUTE_VALUE, default)
            return File(application.getExternalFilesDir(null), value)
        }
        set(value) {
            prefs.edit().putString(KEY_MOCK_ROUTE_VALUE, value.getName()).commit()
        }
}
