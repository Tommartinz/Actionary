package com.tomartin.actionary.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SettingItem(
    headLine: String,
    supportiveContent: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = headLine,
            fontSize = 18.sp,
        )
        Text(
            text = supportiveContent,
            fontSize = 14.sp
        )
    }
}