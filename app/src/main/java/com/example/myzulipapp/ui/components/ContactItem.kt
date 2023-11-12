package com.example.myzulipapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.myzulipapp.R
import com.example.myzulipapp.contacts.presentation.ui_model.ContactUIModel
import com.example.myzulipapp.ui.components.basic.ComponentCircle
import com.example.myzulipapp.ui.components.basic.ComponentRectangleLineLong
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun ContactItem(
    uiModel: ContactUIModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.indent_16dp),
                vertical = dimensionResource(id = R.dimen.indent_4dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = uiModel.contactIcon,
            contentDescription = uiModel.contactIcon,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.contact_icon_size))
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.indent_8dp)),
        ) {
            Text(
                text = uiModel.contactName,
                color = MyZulipAppTheme.colors.primaryTextColor,
                style = MyZulipAppTheme.typography.body
            )
            Text(
                text = stringResource(id = uiModel.contactStatus),
                color = MyZulipAppTheme.colors.secondaryTextColor,
                style = MyZulipAppTheme.typography.body
            )
        }
    }
}

@Composable
fun ContactItemShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.indent_16dp),
                vertical = dimensionResource(id = R.dimen.indent_4dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ComponentCircle()
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.indent_8dp)),
        ) {
            ComponentRectangleLineLong()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.indent_8dp)))
            ComponentRectangleLineLong()
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ContactItemPreview() {
    MyZulipAppTheme {
        ContactItem(
            ContactUIModel(
                1,
                "User Name",
                "email",
                R.string.online,
                "Icon"
            )
        )
    }
}
