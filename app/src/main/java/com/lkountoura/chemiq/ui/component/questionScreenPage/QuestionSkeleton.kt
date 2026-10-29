package com.lkountoura.chemiq.ui.component.questionScreenPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lkountoura.chemiq.ui.component.common.SkeletonBox

@Composable
fun QuestionSkeleton(
    modifier: Modifier = Modifier,
    answersCount: Int = 4,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SkeletonBox(
            modifier = Modifier.fillMaxWidth().height(8.dp),
            shape = RoundedCornerShape(4.dp)
        )
        SkeletonBox(
            modifier = Modifier.fillMaxWidth().height(120.dp),
            shape = RoundedCornerShape(20.dp)
        )
        repeat(answersCount) {
            SkeletonBox(
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}
