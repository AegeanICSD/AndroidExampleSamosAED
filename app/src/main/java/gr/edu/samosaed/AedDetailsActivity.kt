package gr.edu.samosaed

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import gr.edu.samosaed.data.AedRepository
import gr.edu.samosaed.debug.DemoSettings
import gr.edu.samosaed.debug.logLifecycle
import gr.edu.samosaed.ui.DetailsScreen
import gr.edu.samosaed.ui.SamosAedTheme

class AedDetailsActivity : ComponentActivity() {
    companion object {
        const val EXTRA_AED_ID = "gr.edu.samosaed.AED_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logLifecycle("onCreate")
        enableEdgeToEdge()

        // Ενεργοποιείται μόνο ως επίδειξη ενός stack trace.
        if (BuildConfig.DEBUG && DemoSettings.SIMULATE_DETAIL_CRASH) {
            error("Workshop demo crash: opening AED details")
        }

        val pointId = intent.getStringExtra(EXTRA_AED_ID)
        val point = AedRepository.findById(pointId) // BREAKPOINT: έλεγχος του κωδικού.
        if (point == null) {
            Log.e("AED_DATA", "Unknown AED id: $pointId")
        } else {
            Log.d("AED_DATA", "Details loaded: ${point.id}")
        }

        setContent {
            SamosAedTheme {
                DetailsScreen(point = point, onBack = { finish() })
            }
        }
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
