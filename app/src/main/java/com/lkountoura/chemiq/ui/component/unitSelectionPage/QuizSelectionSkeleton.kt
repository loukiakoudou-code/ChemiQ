package com.lkountoura.chemiq.ui.component.unitSelectionPage

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lkountoura.chemiq.ui.component.common.SkeletonBox

@Composable
fun QuizSelectionSkeleton(
    modifier: Modifier = Modifier,
    unitTabsCount: Int = 5,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkeletonBox(modifier = Modifier.width(180.dp).height(22.dp))
            SkeletonBox(modifier = Modifier.fillMaxWidth(0.85f).height(14.dp))
        }

        repeat(unitTabsCount) {
            UnitTabSkeleton(modifier = Modifier.padding(horizontal = 16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SkeletonBox(modifier = Modifier.width(90.dp).height(18.dp))
            SkeletonBox(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(14.dp)
            )
        }
    }
}

@Composable
private fun UnitTabSkeleton(
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(20.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0xFFE8E6F0), shape = shape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SkeletonBox(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(12.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkeletonBox(modifier = Modifier.fillMaxWidth(0.7f).height(16.dp))
            SkeletonBox(
                modifier = Modifier.fillMaxWidth().height(8.dp),
                shape = RoundedCornerShape(4.dp)
            )
        }
        SkeletonBox(modifier = Modifier.width(40.dp).height(16.dp))
    }
}
