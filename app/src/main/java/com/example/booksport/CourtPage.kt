package com.example.booksport

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourtPage(courtId: Int, navController: NavController) {

    var court by remember { mutableStateOf<Court?>(null) }

    LaunchedEffect(courtId) {
        court = CourtRepository.getCourtById(courtId)
    }

    if (court == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading...")
        }
        return
    }

    val context = LocalContext.current

    // Booking date and time
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    // Court dropdown
    var selectedCourt by remember { mutableStateOf(1) }
    var expanded by remember { mutableStateOf(false) }

    // Booker's name
    var bookerName by remember { mutableStateOf("") }

    // Booleans
    var showDatePicker by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var timeError by remember { mutableStateOf(false) }
    var bookingModal by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = court!!.image),
                contentDescription = "Court Image",
                modifier = Modifier.fillMaxWidth().height(256.dp),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .offset(y = (-24).dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(top = 36.dp, bottom = 36.dp, start = 32.dp, end = 32.dp)
            ) {
                Text(court!!.name, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(court!!.location, color = Color.Gray, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${court!!.courts} courts", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Rp${court!!.price}/hour", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(court!!.hours, fontSize = 16.sp)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    Text("Select Court")
                    Box {
                        OutlinedButton(
                            onClick ={expanded = true},
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        {
                            Text("Court ${selectedCourt}")
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            (1..(court!!.courts)).forEach { index ->
                                DropdownMenuItem(
                                    text = {Text("Court ${index}")},
                                    onClick = {
                                        selectedCourt = index
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // --- DATE PICKER ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "Booking Date")
                        if(dateError){
                            Text(text = "invalid date!", color = Color.Red, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { showDatePicker = true }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (selectedDate == null) "Pick a date" else "${selectedDate}",
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                            )

                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Calendar Icon",
                                tint = Color.Gray
                            )
                        }
                    }

                    if (showDatePicker) {
                        val datePickerState = rememberDatePickerState()
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    showDatePicker = false
                                    val selectedDateMillis = datePickerState.selectedDateMillis
                                    if (selectedDateMillis != null) {
                                        val bufSelectedDate = Instant.ofEpochMilli(selectedDateMillis)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate()

                                        if(bufSelectedDate.isBefore(LocalDate.now())){
                                            dateError = true;
                                        }else{
                                            dateError = false;
                                            selectedDate = bufSelectedDate;
                                        }
                                    }


                                }) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) {
                                    Text("Cancel")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)

                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "Booking Time")
                        if(timeError){
                            Text(text = "invalid time input!", color = Color.Red, fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.5f)
                                .height(48.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    val now = Calendar.getInstance()
                                    TimePickerDialog(
                                        context,
                                        { _, hourOfDay, minute ->
                                            startTime = String.format("%02d:%02d", hourOfDay, minute)
                                        },
                                        now.get(Calendar.HOUR_OF_DAY),
                                        now.get(Calendar.MINUTE),
                                        true
                                    ).show()}
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = if (startTime.isEmpty()) "Start time" else startTime,
                                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                                )

                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Calendar Icon",
                                    tint = Color.Gray
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    val now = Calendar.getInstance()
                                    TimePickerDialog(
                                        context,
                                        { _, hourOfDay, minute ->
                                            val startParts = startTime.split(":")
                                            if (startParts.size == 2) {
                                                val startHour = startParts[0].toInt()
                                                val startMinute = startParts[1].toInt()

                                                val startCal = Calendar.getInstance().apply {
                                                    set(Calendar.HOUR_OF_DAY, startHour)
                                                    set(Calendar.MINUTE, startMinute)
                                                }
                                                val endCal = Calendar.getInstance().apply {
                                                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                                                    set(Calendar.MINUTE, minute)
                                                }

                                                if (endCal.before(startCal)) {
                                                    timeError = true;
                                                } else {
                                                    timeError = false;
                                                    endTime = String.format("%02d:%02d", hourOfDay, minute)
                                                }
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Please select start time first",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        },
                                        now.get(Calendar.HOUR_OF_DAY),
                                        now.get(Calendar.MINUTE),
                                        true
                                    ).show()}
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = if (endTime.isEmpty()) "End time" else endTime,
                                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                                )

                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Calendar Icon",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "Booker's name")
                        if(nameError){
                            Text(text = "invalid name input!", color = Color.Red, fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = bookerName,
                        onValueChange = {
                            bookerName = it
                            nameError = !it.matches(Regex("^[A-Za-z ]+\$")) || it.isBlank()
                        },
                        isError = nameError,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(Color(0xFF00C853), Color(0xFFB2FF59))
                                ),
                                shape = MaterialTheme.shapes.medium
                            )
                    ) {
                        Button(
                            onClick =
                                {
                                    if(bookerName.isBlank() || !bookerName.matches(Regex("^[A-Za-z ]+\$"))){
                                        nameError = true
                                    }
                                    if(selectedDate == null){
                                        dateError = true
                                    }
                                    if(startTime.isBlank()){
                                        timeError = true
                                    }
                                    if(endTime.isBlank()){
                                        timeError = true
                                    }
                                    if(!nameError && !dateError && !timeError){
                                        bookingModal = true
                                    }
                                },
                            colors = ButtonDefaults.buttonColors(
                                Color.Transparent
                            ),
                            elevation = null,
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text("Book", color = Color.White)
                        }
                    }

                }
            }
        }
        if(bookingModal){
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.75f)), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(Color.White).fillMaxWidth(0.6f).aspectRatio(1f), contentAlignment = Alignment.Center){
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(24.dp)){
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Text("Booking success!", fontSize = 24.sp)
                            Spacer(modifier = Modifier.height(32.dp))
                            Image(
                                painter = painterResource(id = R.drawable.check_icon),
                                contentDescription = "check icon",
                                modifier = Modifier.scale(1.5f),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Text("Ok", modifier = Modifier
                                .clickable{
                                    println("Submit booking(" +
                                            "sport field name: ${court!!.name}, " +
                                            "location: ${court!!.location}, " +
                                            "sport's type: ${court!!.jenisOlahraga}, " +
                                            "booker's name: $bookerName, " +
                                            "selected court: $selectedCourt, " +
                                            "booking date: $selectedDate, " +
                                            "booking start time: $startTime, " +
                                            "booking end time: $endTime)"
                                    )
                                    bookingModal = false;
                                    navController.navigate("ExploreScreen")
                                },
                                fontSize = 16.sp
                            )
                        }
                    }

                }
            }
        }

        BottomNavigationBar(
            navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)

        )
    }
}