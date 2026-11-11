package gr.edu.samosaed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import gr.edu.samosaed.data.AedPoint
import gr.edu.samosaed.debug.logLifecycle
import gr.edu.samosaed.ui.MainScreen
import gr.edu.samosaed.ui.SamosAedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logLifecycle("onCreate")
        enableEdgeToEdge()
        setContent {
            SamosAedTheme {
                MainScreen(
                    mapConfigured = BuildConfig.MAPS_CONFIGURED,
                    onShowDetails = ::openDetails
                )
            }
        }
    }

    private fun openDetails(point: AedPoint) {
        Log.i("AED_ACTION", "Open details: ${point.id}")
        // Μεταφέρουμε μόνο τον κωδικό και όχι ολόκληρο το αντικείμενο.
        val intent = Intent(this, AedDetailsActivity::class.java)
        intent.putExtra(AedDetailsActivity.EXTRA_AED_ID, point.id)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        logLifecycle("onStart")
    }

    override fun onResume() {
        super.onResume()
        logLifecycle("onResume")
    }

    override fun onPause() {
        logLifecycle("onPause")
        super.onPause()
    }

    override fun onStop() {
        logLifecycle("onStop")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        logLifecycle("onRestart")
    }

    override fun onDestroy() {
        logLifecycle("onDestroy")
        super.onDestroy()
    }
}
