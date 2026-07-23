package edu.ucne.romel_ortega_ap2_p2.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import edu.ucne.romel_ortega_ap2_p2.presentation.model.form.FormModelScreen
import edu.ucne.romel_ortega_ap2_p2.presentation.model.list.ListModelScreen


@Composable
fun AppNavHost() {
    val backStack = rememberNavBackStack(Screen.ModelList)

    NavDisplay(
        backStack = backStack,
        modifier = Modifier.fillMaxSize(),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {

            entry<Screen.ModelList> {
                ListModelScreen(
                    onAddClick = { backStack.add(Screen.ModelForm(gastoId = null)) },
                    onGastoClick = { id -> backStack.add(Screen.ModelForm(gastoId = id)) }
                )
            }

            entry<Screen.ModelForm> { key ->
                FormModelScreen(
                    gastoId = key.gastoId,
                    onBack = {
                        if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
                    },
                    onSaved = {
                        if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
                    }
                )
            }
        }
    )
}

