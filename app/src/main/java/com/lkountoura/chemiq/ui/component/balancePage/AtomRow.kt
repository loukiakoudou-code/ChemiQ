package com.lkountoura.chemiq.ui.component.balancePage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

@Composable
fun AtomRow(
    state: AtomRowState,
    modifier: Modifier = Modifier,
) {
    val status = state.status
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(status.backgroundColor)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = state.atom,
            modifier = Modifier.width(64.dp),
            color = Color(0xFF16132B),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = state.reactants.toString(),
            modifier = Modifier.weight(1f),
            color = Color(0xFF16132B),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = state.products.toString(),
            modifier = Modifier.weight(1f),
            color = status.productsColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Icon(
            painter = painterResource(status.icon),
            contentDescription = null,
            tint = status.iconColor,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Immutable
data class AtomRowState(
    val atom: String = "",
    val reactants: Int = 0,
    val products: Int = 0,
    val status: AtomRowStatus = AtomRowStatus.BALANCED
)

enum class AtomRowStatus(
    val backgroundColor: Color,
    val productsColor: Color,
    val iconColor: Color,
    @param:DrawableRes val icon: Int,
) {
    BALANCED(
        backgroundColor = Color.White,
        productsColor = Color(0xFF16132B),
        iconColor = Color(0xFF12A150),
        icon = R.drawable.ic_check_circle,
    ),
    UNBALANCED(
        backgroundColor = Color(0xFFFDECEC),
        productsColor = Color(0xFFE5484D),
        iconColor = Color(0xFFE5484D),
        icon = R.drawable.ic_x_circle,
    )
}