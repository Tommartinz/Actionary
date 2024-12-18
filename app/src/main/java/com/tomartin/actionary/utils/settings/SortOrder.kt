package com.tomartin.actionary.utils.settings

import com.tomartin.actionary.R

enum class SortOrder(val label: Int, val value: Boolean) {
    ASCENDING(R.string.ascending, true),
    DESCENDING(R.string.descending, false)
}