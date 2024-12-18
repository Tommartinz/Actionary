package com.tomartin.actionary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomartin.actionary.R
import com.tomartin.actionary.ui.viewmodels.TasksViewModel
import com.tomartin.actionary.utils.settings.SortOrder
import com.tomartin.actionary.utils.settings.TypeOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: TasksViewModel
) {
    val uiState by viewModel.tasksUiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        viewModel.getTypeOrder()
        viewModel.getSortOrder()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 24.dp
                )
        ) {
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth().padding(end = 6.dp)) {
                SortOrder.entries.forEachIndexed { index, sortOrder ->
                    SegmentedButton(
                        onClick = { viewModel.setSortOrder(sortOrder) },
                        selected = uiState.sortOrder == sortOrder,
                        label = { Text(stringResource(sortOrder.label)) },
                        shape = SegmentedButtonDefaults.itemShape(index, SortOrder.entries.size),
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 8.dp, end = 4.dp)
                    .selectableGroup()
            ) {
                HorizontalDivider(Modifier.padding(end = 8.dp))
                Spacer(Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.name),
                        style = MaterialTheme.typography.titleMedium
                    )
                    RadioButton(
                        selected = uiState.typeOrder == TypeOrder.NAME,
                        onClick = { viewModel.setTypeOrder(TypeOrder.NAME) }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.date_added),
                        style = MaterialTheme.typography.titleMedium
                    )
                    RadioButton(
                        selected = uiState.typeOrder == TypeOrder.DATE_ADDED,
                        onClick = { viewModel.setTypeOrder(TypeOrder.DATE_ADDED) }
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}