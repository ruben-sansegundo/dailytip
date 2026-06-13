package com.example.dailytip.ui.tiplist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTipBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: (text: String, source: String?) -> Unit
) {
    var tipText by remember { mutableStateOf("") }
    var sourceText by remember { mutableStateOf("") }
    val isValid = tipText.isNotBlank()

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Añadir consejo", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                value = tipText,
                onValueChange = { tipText = it },
                label = { Text("Consejo *") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 6
            )
            OutlinedTextField(
                value = sourceText,
                onValueChange = { sourceText = it },
                label = { Text("Fuente (libro, película, etc.)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Button(
                onClick = {
                    onConfirm(tipText.trim(), sourceText.trim().ifBlank { null })
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isValid
            ) {
                Text("Guardar")
            }
        }
    }
}
