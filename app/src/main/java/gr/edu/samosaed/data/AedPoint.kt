package gr.edu.samosaed.data

// Τα αναγνωριστικά περιοχών είναι ανεξάρτητα από τις ετικέτες της διεπαφής.
enum class Area { ALL, VATHY, KARLOVASI, PYTHAGOREIO, MARATHOKAMPOS }

data class AedPoint(
    val id: String,
    val area: Area,
    val latitude: Double,
    val longitude: Double
)
