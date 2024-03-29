package com.example.skysight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skysight.ui.theme.SkySightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SkySightTheme {
                App()
            }
        }

    }
}

@Composable
fun App() {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = "main" ) {
        composable("main/{title}/{amount}/{type}", arguments = listOf(
            navArgument("title"){
                type = NavType.StringType
            }, navArgument("amount"){
                type = NavType.StringType
            },
            navArgument("type"){
                type = NavType.StringType
            }
        ) ){
            val title = it.arguments!!.getString("title")
            val amount = it.arguments!!.getString("amount")
            val type = it.arguments!!.getString("type")
            MainScreen(navController,title,amount,type)
        }

        composable("transaction",){
            AddTransaction() {
                navController.navigate("main/${it}")
            }
        }
    }

}

@Composable
fun AddTransaction(onClick: (title: String?, amount: String?, type: String?) -> Unit) {
    val title = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val amount = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val type = remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column {
        TextField(value = title.value, onValueChange = { title.value = it }, label = { Text(text = "Title") })
        TextField(value = amount.value, onValueChange = { amount.value = it }, label = { Text(text = "Amount") })
        TextField(value = type.value, onValueChange = { type.value = it }, label = { Text(text = "Transaction Type") })
        Button(onClick = { onClick(title.value.text, amount.value.text, type.value.text) }) {
            Text(text = "ADD TRANSACTION")
        }
        }

}

@Composable
fun MainScreen(navController: NavHostController, title: String?, amount: String?, type: String?) {
    Column {
        Card (
            modifier = Modifier.fillMaxWidth().clickable {
                navController.navigate("transaction")
            }
        ){
            Text(text = "Incomes")
            Text(text = "R$ $amount")
        }
        Card (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Expenses")
            Text(text = "R$ -300")
        }

        Card (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Total")
            Text(text = "R$ $amount")
        }

        Card (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Last Transactions" )
            DisplayTable(title, amount, type)
        }

    }
}

@Composable
fun DisplayTable(title: String?, amount: String?, type: String?){
    @Composable
    fun SimpleTable() {
        val tableData = listOf(
            listOf("Income", "R$ 390", "2023-08-01"),
            listOf("Expense", "R$ 50", "2023-08-02"),
        )

        Column {
            // Header row
            Row {
                Text(text = "Description", modifier = Modifier.weight(1f))
                Text(text = "Amount", modifier = Modifier.weight(1f))
                Text(text = "Date", modifier = Modifier.weight(1f))
            }

            // Data rows
            tableData.forEach { row ->
                Row {
                    Text(text = row[0], modifier = Modifier.weight(1f))
                    Text(text = row[1], modifier = Modifier.weight(1f))
                    Text(text = row[2], modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
