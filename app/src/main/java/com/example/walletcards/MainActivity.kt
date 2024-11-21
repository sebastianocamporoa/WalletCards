package com.example.walletcards

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.addCallback
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.walletcards.ui.screens.main.MainScreen
import com.example.walletcards.ui.screens.onboarding.CreateCardStepsScreen
import com.example.walletcards.ui.screens.welcome.WelcomeScreen
import com.example.walletcards.ui.screens.login.LoginScreen
import com.example.walletcards.ui.screens.register.RegisterScreen
import com.example.walletcards.ui.theme.WalletCardsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.walletcards.data.repository.FirestoreRepository

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Acceso a SharedPreferences
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        // Inicializar is_logged_in como false si es la primera vez que se abre la app
        if (!sharedPref.contains("is_logged_in")) {
            sharedPref.edit().putBoolean("is_logged_in", false).apply()
        }

        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        setContent {
            val currentScreen = remember { mutableStateOf("welcome") }

            WalletCardsTheme {
                when (currentScreen.value) {
                    "welcome" -> WelcomeScreen(
                        onLoginClicked = {
                            currentScreen.value = "login"
                        },
                        onRegisterClicked = {
                            currentScreen.value = "register"
                        }
                    )
                    "login" -> LoginScreen(onLoginSuccess = {
                        sharedPref.edit().putBoolean("is_logged_in", true).apply()
                        checkForCards(currentScreen)
                    })
                    "register" -> RegisterScreen(onRegisterSuccess = {
                        sharedPref.edit().putBoolean("is_logged_in", true).apply()
                        checkForCards(currentScreen)
                    })
                    "create_card" -> CreateCardStepsScreen(onComplete = {
                        currentScreen.value = "main"
                    })
                    "main" -> MainScreen()
                }
            }

            // Manejo del botón de "atrás"
            onBackPressedDispatcher.addCallback(this) {
                when (currentScreen.value) {
                    "login", "register" -> currentScreen.value = "welcome"
                    "create_card" -> currentScreen.value = if (isLoggedIn) "main" else "welcome"
                    "main" -> moveTaskToBack(true) // Envía la app al fondo en lugar de cerrarla
                    else -> finish()
                }
            }
        }
    }

    private fun checkForCards(currentScreen: androidx.compose.runtime.MutableState<String>) {
        val repository = FirestoreRepository() // Instancia del repositorio de Firestore

        // Lógica asíncrona para verificar tarjetas
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cards = repository.getUserCards() // Obtener todas las tarjetas del usuario
                if (cards.isEmpty()) {
                    currentScreen.value = "create_card" // Redirigir a la creación de tarjetas
                } else {
                    currentScreen.value = "main" // Redirigir a MainScreen si hay tarjetas
                }
            } catch (e: Exception) {
                // Manejo de errores (por ejemplo, conexión fallida)
                currentScreen.value = "create_card"
            }
        }
    }
}
