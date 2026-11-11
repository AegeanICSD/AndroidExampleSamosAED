package gr.edu.samosaed.ui

import androidx.annotation.StringRes
import gr.edu.samosaed.R
import gr.edu.samosaed.data.AedPoint
import gr.edu.samosaed.data.Area

@get:StringRes
val Area.labelRes: Int
    get() = when (this) {
        Area.ALL -> R.string.area_all
        Area.VATHY -> R.string.area_vathy
        Area.KARLOVASI -> R.string.area_karlovasi
        Area.PYTHAGOREIO -> R.string.area_pythagoreio
        Area.MARATHOKAMPOS -> R.string.area_marathokampos
    }

@get:StringRes
val AedPoint.nameRes: Int
    get() = when (id) {
        "DEMO-V1" -> R.string.sample_name_v1
        "DEMO-V2" -> R.string.sample_name_v2
        "DEMO-K1" -> R.string.sample_name_k1
        "DEMO-K2" -> R.string.sample_name_k2
        else -> R.string.sample_name_p1
    }
