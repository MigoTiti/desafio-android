package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.lucasrodrigues.apodnasa.domain.model.Failure

@Composable
fun ErrorItem(
    error: Failure,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    onClickRetry: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = when (error) {
                Failure.NetworkConnection -> "Erro na sua conexão com a internet"
                is Failure.ServerError -> error.value
                is Failure.UnknownError -> error.value
            },
            style = textStyle,
        )
        Button(
            onClick = onClickRetry,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Text(text = "Tentar novamente")
        }
    }
}