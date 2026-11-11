# Απινιδωτές Σάμου — Παράδειγμα ενός Android Studio project

Έτοιμο παράδειγμα ενός Android Studio project. Περιλαμβάνει Kotlin, Jetpack Compose, Google Maps και δύο Activities.

**Όλες οι καταχωρίσεις είναι υποθετικές. Οι γεωγραφικές συντεταγμένες και τα κείμενα δεν αφορούν πραγματικές θέσεις απινιδωτών.**

## Γρήγορη εκτέλεση

1. Αποσυμπιέστε το ZIP σε τοπικό φάκελο με δικαίωμα εγγραφής.  
2. Στο Android Studio επιλέξτε **Open** και τον φάκελο `SamosAED`, εκεί όπου βρίσκεται το `settings.gradle.kts`. Περιμένετε να ολοκληρωθεί το Gradle Sync.  
3. Εγκαταστήστε από το SDK Manager το **Android SDK Platform 35**, τα **SDK Build Tools 35.0.0**, τα Platform-Tools και τον Android Emulator, αν λείπουν.  
4. Επιλέξτε ως Gradle JDK το ενσωματωμένο JDK του συμβατού Android Studio (JDK 17 ή 21). Για AGP 8.9 απαιτείται Android Studio Meerkat ή νεότερο IDE που υποστηρίζει τη συγκεκριμένη έκδοση plugin. Μην αποδεχθείτε αυτόματη αναβάθμιση των εκδόσεων στην φάση της 1ης εκτέλεσης.  
5. Στο **Device Manager** δημιουργήστε ένα Phone AVD με εικόνα **API 35 και Google APIs ή Google Play**. Επιλέξτε εικόνα κατάλληλη για την αρχιτεκτονική του υπολογιστή σας. Ξεκινήστε την εικονική συσκευή.  
6. Επιλέξτε το module `app`, την εικονική συσκευή και **Run**.

**Χωρίς Maps API key:** το project μεταγλωττίζεται και η εφαρμογή εκτελείται. Στη θέση του χάρτη εμφανίζεται σχετικό μήνυμα. Το φίλτρο, η λίστα, οι λεπτομέρειες, ο κύκλος ζωής και το Logcat λειτουργούν ανεξάρτητα από το κλειδί.

## Ενεργοποίηση Google Maps

1. Δημιουργήστε ή επιλέξτε Google Cloud project με ενεργοποιημένο billing.

2. Ενεργοποιήστε το **Maps SDK for Android** και δημιουργήστε API key.

3. Στους περιορισμούς του κλειδιού επιλέξτε το συγκεκριμένο SDK και εφαρμογές Android. Το application ID αυτού του project είναι **`gr.edu.samosaed`**.

4. Βρείτε το SHA-1 του debug πιστοποιητικού σας μέσω της εργασίας `signingReport` του Gradle. Προσθέστε τον συνδυασμό package και SHA-1 στους περιορισμούς. Κάθε υπολογιστής συνήθως έχει διαφορετικό debug πιστοποιητικό. Προετοιμάστε τους περιορισμούς για όσα builds θα χρησιμοποιήσετε.

5. Αντιγράψτε το `secrets.properties.example` ως `secrets.properties` στον ίδιο ριζικό φάκελο και συμπληρώστε:

   ```properties

   MAPS_API_KEY=YOUR_ACTUAL_RESTRICTED_KEY

   ```

6. Κάντε **Gradle Sync** και ξανά **Run**. Η αλλαγή του κλειδιού απαιτεί νέο build.

Το `app/build.gradle.kts` διαβάζει το αρχείο με χρήση της κλάσης `java.util.Properties`. Περνά το κλειδί στο manifest μέσω placeholder και δημιουργεί το boolean `BuildConfig.MAPS_CONFIGURED`. Το πραγματικό κλειδί εξαιρείται από το Git. Οι περιορισμοί (key restrictions) παραμένουν απαραίτητοι, καθώς το κλειδί περιλαμβάνεται στο εγκαταστάσιμο APK.

Εντολές από τον ριζικό φάκελο:

| Σκοπός | Windows PowerShell/CMD | Linux/macOS |
| :---- | :---- | :---- |
| Πιστοποιητικό debug | `.\gradlew.bat :app:signingReport` | `./gradlew :app:signingReport` |
| APK debug | `.\gradlew.bat :app:assembleDebug` | `./gradlew :app:assembleDebug` |
| Έλεγχοι | `.\gradlew.bat :app:testDebugUnitTest :app:lintDebug` | `./gradlew :app:testDebugUnitTest :app:lintDebug` |

Αν χρειάζεται σε Linux/macOS: `chmod +x gradlew`. Ο Gradle Wrapper και το checksum της διανομής περιλαμβάνονται. Το APK δημιουργείται στο `app/build/outputs/apk/debug/app-debug.apk`.

## Λειτουργίες και αναμενόμενα αποτελέσματα

| Επιλεγμένη περιοχή μετά το «Εμφάνιση» | Καταχωρίσεις |
| :---- | ----: |
| Όλες οι περιοχές | 5 |
| Βαθύ | 2 |
| Καρλόβασι | 2 |
| Πυθαγόρειο | 1 |
| Μαραθόκαμπος | 0, με μήνυμα κενού αποτελέσματος |

Η επιλογή από την λίστα επιλογής (drop down menu) επιβεβαιώνεται με το κουμπί «Εμφάνιση». Η αλλαγή περιοχής μετακινεί την κάμερα κοντά στην πρώτη καταχώριση, όταν υπάρχει. Το πάτημα (tap) ενός marker εμφανίζει την επιλογή και το κουμπί «Λεπτομέρειες». Το πάτημα του πλαισίου τίτλου του marker ή μιας γραμμής της λίστας ανοίγει επίσης το δεύτερη Activity (`AedDetailsActivity)`.

Με επιλογή επιστροφής, το `AedDetailsActivity` ολοκληρώνεται και επιστρέφει στο `MainActivity`. Η επιλογή περιοχής αποθηκεύεται με `rememberSaveable`. Το Maps Compose αποθηκεύει την κατάσταση που βρίσκεται ο χάρτης (τμήμα του χάρτη που εμφανίζεται, zoom, κ.α.) μέσω `rememberCameraPositionState`. Η περιστροφή του κινητού αναδημιουργεί την Activity και δίνει νέο instance ID στο Logcat. Τα λεκτικά (strings) της εφαρμογής βρίσκονται στο `res/values/strings.xml`.

## Πού βρίσκεται κάθε μέρος

Όλα τα Kotlin αρχεία βρίσκονται κάτω από `app/src/main/java/gr/edu/samosaed/`.

| Αρχείο | Ρόλος |
| :---- | :---- |
| `MainActivity.kt` | Εκκίνηση, Compose UI, explicit Intent και callbacks κύκλου ζωής |
| `AedDetailsActivity.kt` | Εμφάνιση λεπτομερειών, αναζήτηση καταχώρισης, `finish()` και callbacks |
| `ui/MainScreen.kt` | Μενού, φίλτρο, χάρτης, markers, λίστα και Preview χωρίς κλειδί |
| `ui/DetailsScreen.kt` | Στοιχεία καταχώρισης και κύλιση |
| `ui/PointLabels.kt` | Αντιστοίχιση IDs και περιοχών σε string resources |
| `ui/Theme.kt` | Χρώματα Material 3 |
| `data/AedPoint.kt` | Μοντέλο καταχώρισης και enum περιοχών |
| `data/AedRepository.kt` | Υποθετικά δεδομένα, φιλτράρισμα και αναζήτηση |
| `debug/LifecycleLog.kt` | Tag και αναγνωριστικό αντικειμένου Activity |
| `debug/DemoSettings.kt` | Διακόπτης ελεγχόμενου σφάλματος, αρχικά `false` |

Το `AndroidManifest.xml` δηλώνει την Activity με `MAIN`/`LAUNCHER` και τη δεύτερη Activity ως `exported=false`. Δεν χρησιμοποιούνται πληροφορία τοποθεσίας (Location information), λογαριασμοί χρήστη, backend ή βάση δεδομένων.

## Logcat και Debug

Επιλέξτε τη σωστή συσκευή από **View → Tool Windows → Logcat**.

| Φίλτρο | Σκοπός |
| :---- | :---- |
| `package:mine` | Μηνύματα των packages του ανοικτού project |
| `package:mine tag:AED_LIFECYCLE` | Callbacks των δύο Activities |
| `package:mine tag:AED_ACTION` | Φίλτρο, markers και άνοιγμα λεπτομερειών |
| `package:mine tag:AED_DATA` | Αναζήτηση στοιχείων καταχώρισης |
| `package:mine level:ERROR` | Σφάλματα |
| `package:mine is:crash` | Μηνύματα απροσδόκητου τερματισμού της εφαρμογής |

Ενδεικτικό μήνυμα: `MainActivity#61de416.onPause`. Το τμήμα μετά το `#` αλλάζει κάθε φορά που θα δημιουργηθεί νέο αντικείμενο.

Μπορούμε να σταματήσουμε την εφαρμογή σε ένα σημείο με ένα breakpoint: Ανοίξτε το `AedDetailsActivity.kt` και στη γραμμή `val point = AedRepository.findById(pointId)` προσθέστε ένα breakpoint. Επιλέξτε **Debug** αντί για Run και ανοίξτε την καρτέλα Debug αν δεν έχει ανοίξει ήδη. Ελέγξτε το `pointId`, κάντε **Step Over** για να υπολογιστεί το `point`, και συνεχίστε με **Resume Program**.

## Ελεγχόμενο σφάλμα παρουσίασης

Στο `debug/DemoSettings.kt` αλλάξτε προσωρινά το `SIMULATE_DETAIL_CRASH` σε `true`, κάντε Run και ανοίξτε καταχώριση. Το `AedDetailsActivity.kt` θα προκαλέσει `IllegalStateException` με μήνυμα `Workshop demo crash: opening AED details`. Αναζητήστε το στο Logcat, πατήστε τη σχετική γραμμή του δικού μας κώδικα στο stack trace, και επαναφέρετε τον διακόπτη σε `false`. Κάντε ξανά Run πριν συνεχίσετε. Το σφάλμα ενεργοποιείται μόνο σε debug build και μόνο μετά τη ρητή αλλαγή του διακόπτη.

## Εκδόσεις του παραδείγματος

| Στοιχείο | Έκδοση |
| :---- | :---- |
| Android Gradle Plugin | 8.9.2 |
| Gradle Wrapper | 8.11.1 |
| Kotlin και Compose compiler plugin | 2.1.20 |
| Java bytecode target | 17 |
| `compileSdk` / `targetSdk` / `minSdk` | 35 / 35 / 23 |
| Compose BOM | 2025.04.01 |
| Activity Compose | 1.10.1 |
| Maps Compose | 6.4.1 |

Οι εκδόσεις είναι σταθερά ορισμένες για αναπαραγωγιμότητα του εργαστηρίου.

## Αντιμετώπιση συνηθισμένων προβλημάτων

- **Αποτυχία Sync/build:** κοιτάξτε το πρώτο σχετικό μήνυμα του Gradle, ελέγξτε σύνδεση, Gradle JDK και εγκατάσταση Platform 35 / Build Tools 35.0.0.  
- **Ο emulator δεν ξεκινά:** ελέγξτε συμβατή αρχιτεκτονική εικόνας, υποστήριξη virtualization και διαθέσιμους πόρους. Μπορείτε να χρησιμοποιήσετε φυσική συσκευή με ενεργοποιημένο USB debugging και εξουσιοδότηση του υπολογιστή.  
- **Μήνυμα για API key:** το `secrets.properties` λείπει ή είναι κενό. Προσθέστε το κλειδί, κάντε Sync και νέο Run.  
- **Κενός χάρτης παρότι υπάρχει κλειδί:** ελέγξτε ενεργοποίηση SDK/billing, περιορισμούς package και SHA-1, δίκτυο και Google Play services. Δείτε στο Logcat και μηνύματα του Google Maps SDK με το φίλτρο `package:mine`.  
- **Δεν εμφανίζεται stack trace:** αφαιρέστε το φίλτρο `tag:AED_LIFECYCLE` ή `tag:AED_ACTION` και χρησιμοποιήστε `package:mine is:crash`.

## Επίσημη τεκμηρίωση

- [Android Studio projects](https://developer.android.com/studio/projects)  
- [Δημιουργία AVD](https://developer.android.com/studio/run/managing-avds)  
- [Activity lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)  
- [Back stack](https://developer.android.com/guide/components/activities/tasks-and-back-stack)  
- [Logcat](https://developer.android.com/studio/debug/logcat)  
- [Debugging](https://developer.android.com/studio/debug)  
- [Maps Compose](https://developers.google.com/maps/documentation/android-sdk/maps-compose)  
- [API key και περιορισμοί](https://developers.google.com/maps/documentation/android-sdk/get-api-key)

&nbsp;
