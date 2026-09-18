package com.example.listycity3

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.listycity3.ui.theme.ListyCity3Theme
import org.w3c.dom.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.ui.graphics.Color

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onEditCity: (City, Int) -> Unit,
    modifier: Modifier = Modifier
) {


    //boolean to track editing city
    var selectedCityIndex by remember{mutableStateOf(-1)}

    //boolean to track to show the text field or noto
    var showEditCityField by remember { mutableStateOf(false) }
    var showEditButton by remember {mutableStateOf(false)}
    var showAddCityFields by remember { mutableStateOf(false) }

    //To remember the text in the add city field
    var newCityName by remember{ mutableStateOf("") }
    var newProvinceName by remember{mutableStateOf("")}
    Column(modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (selectedCityIndex != -1 && !showEditButton){
                //Edit button
                FloatingActionButton(
                    modifier = Modifier
                        .padding(16.dp),
                    onClick = {
                        showEditButton = false
                        showAddCityFields = false
                        showEditCityField = true
                    }
                ){
                    Text("Edit")
                }
            }
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    if(showAddCityFields) {
                        showAddCityFields = false
                    }else {
                        showAddCityFields = true
                        //Turning the add city field and removing edit city field
                        showEditButton = false
                        showEditCityField = false
                        selectedCityIndex = -1
                    }

                }
            ) {
                if (showAddCityFields) {
                    Text("x", fontSize = 15.sp)
                }else{
                    Text("+", fontSize = 15.sp)
                }
            }
        }
        if(showAddCityFields || selectedCityIndex!= -1 && showEditCityField){
        Row(modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 30.dp)
            .fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            //Two text fields that edit the variables of the class
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = newProvinceName,
                onValueChange = { newProvinceName = it },
                label = { Text("Province") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            //Adding the button logic to add city
            FilledTonalButton(
                modifier = Modifier.padding(vertical = 10.dp),
                onClick = {
                    if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                        val _city = City(
                            name = newCityName,
                            province = newProvinceName
                        )
                        if(showAddCityFields) {
                            onAddCity(_city)
                        }else{
                            onEditCity(_city,selectedCityIndex)
                        }
                        newCityName = ""
                        newProvinceName = ""
                        showAddCityFields = false
                        showEditButton = false
                        showEditCityField = false
                        selectedCityIndex = -1
                    }

                }) {
                if(showAddCityFields) {
                    Text("Add City")
                }else {
                    Text("Edit City")
                }
            }

        }
    }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
             itemsIndexed(cities) { index, city ->
                 Row {
                     CityRow(
                         city = city,
                         Modifier
                             .clickable(onClick = {
                                 selectedCityIndex = index
                             })
                             .then(
                                 if (index == selectedCityIndex) {
                                     Modifier.padding(8.dp)
                                         .background(color = MaterialTheme.colorScheme.primaryContainer)
                                 } else {
                                     Modifier
                                 }
                             )
                     )
                 }

                 if (index < cities.lastIndex) {
                     HorizontalDivider()
                 }
             }
         }
     }
}

@Composable
fun CityRow(city: City,  modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 25.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 25.sp,
            modifier = Modifier.weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    val cityRepository = CityRepository()
    ListyCity3Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CityListScreen(
                cities = cityRepository.cities,
                modifier = Modifier.padding(innerPadding),
                onAddCity = {city -> cityRepository.add_city(city)},
                onEditCity = {city ,index -> cityRepository.edit_city(index, city)}
            )
        }
    }
}