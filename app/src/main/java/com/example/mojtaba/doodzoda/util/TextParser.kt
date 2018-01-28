package com.example.mojtaba.doodzoda.util

import java.text.NumberFormat
import java.util.*

fun numberFormat(str: String): String = NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(str))
