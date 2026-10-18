package com.lkountoura.chemiq.ui.component.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChemiQButton(
    modifier: Modifier = Modifier,
    state: ButtonState,
    events: ButtonActionsEvents? = null,
) {
    when (state.buttonLayout) {
        ButtonLayout.ONE_BUTTON -> {
            ButtonLayoutOne(
                state = state,
                events = events,
                modifier = modifier
            )
        }

        ButtonLayout.TWO_BUTTON -> {
            ButtonLayoutTwo(
                state = state,
                events = events,
                modifier = modifier
            )
        }

        ButtonLayout.THREE_BUTTON -> {
            ButtonLayoutThree(
                state = state,
                events = events,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ButtonLayoutOne(
    modifier: Modifier = Modifier,
    state: ButtonState,
    events: ButtonActionsEvents? = null,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        CustomButton(
            text = state.mainButtonText,
            containerColor = state.mainButtonColor,
            contentColor = state.mainTextButtonColor,
            onClick = { events?.onMainButtonPressed?.invoke(state.tag) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ButtonLayoutTwo(
    modifier: Modifier = Modifier,
    state: ButtonState,
    events: ButtonActionsEvents? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomButton(
            text = state.mainButtonText,
            containerColor = state.mainButtonColor,
            contentColor = state.mainTextButtonColor,
            onClick = { events?.onMainButtonPressed?.invoke(state.tag) },
            modifier = Modifier.weight(1f)
        )
        CustomButton(
            text = state.secondaryButtonText ?: "",
            containerColor = state.secondaryButtonColor,
            contentColor = state.secondaryTextButtonColor,
            onClick = { events?.onSecondaryButtonPressed?.invoke(state.tag) }
        )
    }
}

@Composable
private fun ButtonLayoutThree(
    modifier: Modifier = Modifier,
    state: ButtonState,
    events: ButtonActionsEvents? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CustomButton(
            text = state.mainButtonText,
            containerColor = state.mainButtonColor,
            contentColor = state.mainTextButtonColor,
            onClick = { events?.onMainButtonPressed?.invoke(state.tag) },
            modifier = Modifier.weight(1f)
        )
        CustomButton(
            text = state.secondaryButtonText ?: "",
            containerColor = state.secondaryButtonColor,
            contentColor = state.secondaryTextButtonColor,
            onClick = { events?.onSecondaryButtonPressed?.invoke(state.tag) }
        )
        CustomButton(
            text = state.tertiaryButtonText ?: "",
            containerColor = state.tertiaryButtonColor,
            contentColor = state.tertiaryTextButtonColor,
            onClick = { events?.onTertiaryButtonPressed?.invoke(state.tag) }
        )
    }
}


@Composable
private fun CustomButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = border,
        contentPadding = PaddingValues(horizontal = 24.dp),
        elevation = null
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Immutable
data class ButtonState(
    val tag: String,
    private val _mainButtonText: String,
    private val _secondaryButtonText: String? = null,
    private val _tertiaryButtonText: String? = null,
    private val _buttonLayout: ButtonLayout,
) {
    fun setButtonActionsState(
        buttonLayout: ButtonLayout,
        mainButtonText: String,
        secondaryButtonText: String? = null,
        tertiaryButtonText: String? = null
    ): ButtonState = copy(
        _buttonLayout = buttonLayout,
        _mainButtonText = mainButtonText,
        _secondaryButtonText = secondaryButtonText,
        _tertiaryButtonText = tertiaryButtonText
    )

    val mainButtonColor: Color
        get() = Color(0xFF5B3DF5)
    val secondaryButtonColor: Color
        get() = Color(0xFFFFFFFF)
    val tertiaryButtonColor: Color
        get() = Color(0xFFEEEAFE)
    val mainTextButtonColor: Color
        get() = Color(0xFFFFFFFF)
    val secondaryTextButtonColor: Color
        get() = Color(0xFF16132B)
    val tertiaryTextButtonColor: Color
        get() = Color(0xFF5B3DF5)
    val mainButtonText: String
        get() = _mainButtonText
    val secondaryButtonText: String?
        get() = _secondaryButtonText
    val tertiaryButtonText: String?
        get() = _tertiaryButtonText
    val buttonLayout: ButtonLayout
        get() = _buttonLayout
}

data class ButtonActionsEvents(
    val onMainButtonPressed: ((tag: String) -> Unit)? = null,
    val onSecondaryButtonPressed: ((tag: String) -> Unit)? = null,
    val onTertiaryButtonPressed: ((tag: String) -> Unit)? = null
)

enum class ButtonLayout {
    ONE_BUTTON,
    TWO_BUTTON,
    THREE_BUTTON
}
