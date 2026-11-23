package com.lkountoura.chemiq.ui.component.balancePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

@Composable
fun BalanceHint(
    modifier: Modifier = Modifier,
) {
    val gray = Color(0xFF9A97AE)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_lightbulb),
            contentDescription = null,
            tint = gray,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = "Στο εύκολο και στο μέτριο επίπεδο ο πίνακας ατόμων ενημερώνεται σε κάθε αλλαγή. Στο δύσκολο εμφανίζεται μόνο μετά τον έλεγχο.",
            color = gray,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}