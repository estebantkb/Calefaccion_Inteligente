package com.example.aplicacion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import com.example.aplicacion.ui.theme.RoomManager

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerRooms: Spinner
    private lateinit var seekBarTemperature: SeekBar
    private lateinit var btnControlHeat: Button
    private lateinit var textViewTemperature: TextView

    // Declarar roomNames y adapter en un ámbito más amplio
    private var roomNames = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa las salas (puedes ajustar el número según tus necesidades)
        RoomManager.initializeRooms(10)

        // Inicializa los elementos de la interfaz
        spinnerRooms = findViewById(R.id.spinnerRooms)
        seekBarTemperature = findViewById(R.id.seekBarTemperature)
        btnControlHeat = findViewById(R.id.btnControlHeat)
        textViewTemperature = findViewById(R.id.textViewTemperature)

        // Inicializa roomNames y adapter con los nombres de las salas
        roomNames = RoomManager.rooms.map { "Sala ${it.id} - ${it.configuredTemperature}°C" }.toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roomNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRooms.adapter = adapter

        // Configura el listener del SeekBar para actualizar el TextView
        seekBarTemperature.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualiza el TextView con la temperatura seleccionada
                val temperatureText = "Temperatura: $progress°C"
                textViewTemperature.text = temperatureText
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementar en este caso
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementar en este caso
            }
        })

        // Configura el listener del botón de controlar la calefacción
        btnControlHeat.setOnClickListener {
            val selectedRoom = spinnerRooms.selectedItemPosition + 1 // +1 porque los índices comienzan en 0
            val desiredTemperature = seekBarTemperature.progress
            RoomManager.controlTemperature(selectedRoom, desiredTemperature)

            // Actualiza el texto en el Spinner para mostrar la temperatura configurada
            val selectedRoomObject = RoomManager.rooms.find { it.id == selectedRoom }
            val roomText = "Sala $selectedRoom - ${selectedRoomObject?.configuredTemperature}°C"
            roomNames[spinnerRooms.selectedItemPosition] = roomText
            adapter.notifyDataSetChanged()
        }
    }
}