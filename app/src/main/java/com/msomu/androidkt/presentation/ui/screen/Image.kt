import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.msomu.androidkt.presentation.ui.screen.ImageUiState
import com.msomu.androidkt.presentation.viewmodel.ImageViewModel
import kotlinx.coroutines.launch

@Composable
internal fun ImageRoute(
    viewModel: ImageViewModel = hiltViewModel()
) {
    val imageUiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    ImageScreen(
        uiState = imageUiState,
        onGenerateClicked = { prompt ->
            coroutineScope.launch { viewModel.generateImage(prompt) }
        }
    )
}

@Composable
fun ImageScreen(
    uiState: ImageUiState = ImageUiState.Initial,
    onGenerateClicked: (String) -> Unit = { }
) {
    var userPrompt by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Image Display Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    ImageUiState.Initial -> {
                        Text("Enter a prompt below to generate an image")
                    }
                    ImageUiState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is ImageUiState.Success -> {
                        Image(
                            bitmap = uiState.bitmap.asImageBitmap(),
                            contentDescription = "Generated Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    is ImageUiState.Error -> {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        // Prompt Input Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userPrompt,
                    onValueChange = { userPrompt = it },
                    label = { Text("Enter prompt") },
                    placeholder = { Text("Describe the image you want to generate...") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                Button(
                    onClick = { onGenerateClicked(userPrompt) },
                    enabled = userPrompt.isNotBlank()
                ) {
                    Text("Generate")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageScreenPreview() {
    ImageScreen()
}