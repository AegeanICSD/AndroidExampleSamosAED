package gr.edu.samosaed.data

object AedRepository {
    // ΥΠΟΘΕΤΙΚΕΣ καταχωρίσεις. Οι συντεταγμένες εξυπηρετούν μόνο την επίδειξη.
    // Τα κείμενα εμφάνισης βρίσκονται στο res/values/strings.xml.
    private val points = listOf(
        AedPoint("DEMO-V1", Area.VATHY, 37.7551, 26.9772),
        AedPoint("DEMO-V2", Area.VATHY, 37.7508, 26.9835),
        AedPoint("DEMO-K1", Area.KARLOVASI, 37.7923, 26.7045),
        AedPoint("DEMO-K2", Area.KARLOVASI, 37.7887, 26.6992),
        AedPoint("DEMO-P1", Area.PYTHAGOREIO, 37.6907, 26.9432)
    )

    fun filterByArea(area: Area): List<AedPoint> =
        points.filter { area == Area.ALL || it.area == area }

    fun findById(id: String?): AedPoint? = points.find { it.id == id }
}
