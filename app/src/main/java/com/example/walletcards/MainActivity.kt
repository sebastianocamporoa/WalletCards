package com.example.walletcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.walletcards.ui.screens.login.LoginScreen
import com.example.walletcards.ui.screens.register.RegisterScreen
import com.example.walletcards.ui.screens.welcome.WelcomeScreen
import com.example.walletcards.ui.theme.WalletCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalletCardsTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        // Pantalla de bienvenida
        composable("welcome") {
            WelcomeScreen(
                onLoginClicked = { navController.navigate("login") },
                onRegisterClicked = { navController.navigate("register") }
            )
        }

        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(onLoginSuccess = {
                // Vuelve a la pantalla principal después del inicio de sesión
                navController.popBackStack("welcome", inclusive = false)
            })
        }

        // Pantalla de registro
        composable("register") {
            RegisterScreen(onRegisterSuccess = {
                // Vuelve a la pantalla principal después del registro
                navController.popBackStack("welcome", inclusive = false)
            })
        }
    }
}
