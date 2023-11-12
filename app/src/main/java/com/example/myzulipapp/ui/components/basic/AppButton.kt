package com.example.myzulipapp.ui.components.basic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun AppButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MyZulipAppTheme.colors.buttonColor,
    shape: Shape = MyZulipAppTheme.shapes.cornersStyle,
    @DrawableRes iconId: Int? = null,
    contentDescription: String? = null,
    elevation: ButtonElevation = ButtonDefaults.elevation(
        dimensionResource(id = R.dimen.indent_0dp),
        dimensionResource(id = R.dimen.indent_0dp),
        dimensionResource(id = R.dimen.indent_0dp),
        dimensionResource(id = R.dimen.indent_0dp),
        dimensionResource(id = R.dimen.indent_0dp),
    )
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.button_height)),
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = backgroundColor.copy(
                alpha = 0.3f
            )
        ),
        elevation = elevation
    ) {
        Text(
            text = label,
            style = MyZulipAppTheme.typography.body,
            color = MyZulipAppTheme.colors.contentColor
        )
        iconId?.let {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconId),
                contentDescription = contentDescription
            )
        }
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    MyZulipAppTheme {
        AppButton(
            label = "Button",
            onClick = {}
        )
    }
}
