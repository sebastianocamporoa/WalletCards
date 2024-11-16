package com.example.walletcards

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.walletcards.data.repository.BusinessCardRepository
import com.example.walletcards.ui.screens.main.MainScreen
import com.example.walletcards.ui.screens.onboarding.CreateCardStepsScreen
import com.example.walletcards.ui.screens.welcome.WelcomeScreen
import com.example.walletcards.ui.screens.login.LoginScreen
import com.example.walletcards.ui.screens.register.RegisterScreen
import com.example.walletcards.ui.theme.WalletCardsTheme

class MainActivity : ComponentActivity() {

    private val repository = BusinessCardRepository() // Instancia compartida

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
            WalletCardsTheme {
                if (isLoggedIn) {
                    checkForCards()
                } else {
                    WelcomeScreen(
                        onLoginClicked = {
                            setContent {
                                WalletCardsTheme {
                                    LoginScreen(onLoginSuccess = {
                                        sharedPref.edit().putBoolean("is_logged_in", true).apply()
                                        checkForCards()
                                    })
                                }
                            }
                        },
                        onRegisterClicked = {
                            setContent {
                                WalletCardsTheme {
                                    RegisterScreen(onRegisterSuccess = {
                                        sharedPref.edit().putBoolean("is_logged_in", true).apply()
                                        checkForCards()
                                    })
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun checkForCards() {
        setContent {
            WalletCardsTheme {
                if (repository.hasCards()) {
                    MainScreen(repository = repository)  // Pasa la instancia compartida
                } else {
                    CreateCardStepsScreen(repository = repository, onComplete = {
                        // Redirige a MainScreen con la misma instancia de repository después de crear la tarjeta
                        setContent {
                            WalletCardsTheme {
                                MainScreen(repository = repository)
                            }
                        }
                    })
                }
            }
        }
    }
}
