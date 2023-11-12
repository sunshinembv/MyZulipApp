package com.example.myzulipapp.navigation.destination

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myzulipapp.contacts.di.DaggerContactsComponent
import com.example.myzulipapp.contacts.presentation.ContactsRoute
import com.example.myzulipapp.contacts.presentation.ContactsViewModel
import com.example.myzulipapp.utils.appComponent
import com.example.myzulipapp.utils.daggerViewModel

const val CONTACTS_SCREEN_ROUTE = "contacts"

fun NavGraphBuilder.contactsScreen(popBackStack: () -> Unit) {

    composable(CONTACTS_SCREEN_ROUTE) {

        val component = DaggerContactsComponent.factory().create(LocalContext.current.appComponent)

        val viewModel: ContactsViewModel = daggerViewModel {
            component.getViewModel()
        }

        ContactsRoute(
            viewModel = viewModel,
            popBackStack = popBackStack
        )
    }
}

fun NavController.navigateToContactsScreen() {
    navigate(CONTACTS_SCREEN_ROUTE)
}
