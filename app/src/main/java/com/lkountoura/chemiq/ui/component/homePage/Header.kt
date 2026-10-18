package com.lkountoura.chemiq.ui.component.homePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

@Composable
fun Header(
    state: HeaderState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = state.helpText)

                Text(
                    text = state.text,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            }

            state.seriDays?.let { seriDays ->
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Row(
                        modifier = modifier
                            .background(
                                color = Color(0xFFFFEDDF),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp),

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_flame),
                            contentDescription = null,
                            tint = Color(0xFFF97316),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            color = Color(0xFFF97316),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            text = seriDays
                        )
                    }
                }
            }
        }
    }
}

@Immutable
data class HeaderState(
    val text: String,
    val helpText: String,
    val seriDays: String? = null
) {
    fun setHeaderState(
        text: String,
        helpText: String,
        seriDays: String
    ): HeaderState =
        copy(text = text, helpText = helpText, seriDays = seriDays)
}
