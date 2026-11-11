package gr.edu.samosaed.debug

import android.app.Activity
import android.util.Log

fun Activity.logLifecycle(event: String) {
    // Το αναγνωριστικό διακρίνει την ίδια Activity από μια νέα μετά από περιστροφή.
    val instanceId = Integer.toHexString(System.identityHashCode(this))
    Log.d("AED_LIFECYCLE", "${javaClass.simpleName}#$instanceId.$event")
}
