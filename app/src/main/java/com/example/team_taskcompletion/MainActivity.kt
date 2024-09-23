package com.example.team_taskcompletion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.team_taskcompletion.ui.theme.TeamTaskCompletionTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TeamTaskCompletionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskCompletion(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun TaskCompletion(modifier: Modifier){
    var taskList = remember{ mutableStateListOf<Triple<String,Int,Boolean>>() }
    var first by remember{ mutableStateOf("") }
    var second by remember{ mutableStateOf("") }
    var (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }


    fun isNumeric(toCheck: String): Boolean{
        return toCheck.toIntOrNull() != null
    }


    fun textColor(){

    }

    Column(modifier=Modifier.fillMaxSize()){

        //Spacer(modifier=Modifier.height(16.dp))
        Row(){
            OutlinedTextField(value=first, onValueChange = {first=it}, label={Text("Item Name")})
        }
        Row(){
            OutlinedTextField(value=second, onValueChange={second=it}, label={Text("Item Quantity/Size")})
        }

        Row(){
            Button(onClick={
                if (first.isNotEmpty() && second.isNotEmpty() && isNumeric(second)){
                    taskList.add(Triple(first, second.toInt(), false))
                    first=""
                    second=""
                } else if (!isNumeric(second)){
                    first=""
                    second=""

                    setSnackBarState(!snackbarVisibleState)
                }
            }){
                Text("Add Item")
            }
            Spacer(modifier=Modifier.size(16.dp))
            Button(onClick={
                for(items in taskList){
                    if (items.third){
                        taskList.remove(items)
                    }
                }
            }){
                Text("Clear completed tasks")
            }
        }




        if (snackbarVisibleState) {
            Snackbar(
                action = {
                    Button(onClick = {
                        setSnackBarState(!snackbarVisibleState)
                    }) {
                        Text("Close")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) { Text(text = "Please enter a numeric value in the second field!!!") }
        }

        Spacer(modifier=Modifier.height(16.dp))
        Row(modifier=Modifier.padding(16.dp)){
            LazyColumn(
                modifier=Modifier.fillMaxSize()

            ){
                items(taskList){
                        pair ->
                    Row(){

                        Checkbox(
                            checked= pair.third,
                            onCheckedChange = { checked ->
                                // Update the specific pair's checked state
                                val index = taskList.indexOf(pair)
                                if (index>=0){
                                    taskList[index]= Triple(pair.first, pair.second, checked)

                                }

                            }
                        )

                        Text(
                            text= "Name: ${pair.first}, Quantity/Size: ${pair.second}  ",
                            color= if (pair.third) Color.Red else Color.Black
                        )
                    }

                }

            }

        }
    }

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeamTaskCompletionTheme {
        Greeting("Android")
    }
}