package com.example.pertemuan6

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pertemuan6.databinding.ActivityMainBinding
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
//            Get Array
            val monthList = resources.getStringArray(R.array.month)
            val kehadiranList = resources.getStringArray(R.array.kehadiran)

//            Initiate
            var selectedTime ="${timePicker.hour}:${timePicker.minute}"
            val _tempCalendar : Calendar = Calendar.getInstance()
            _tempCalendar.timeInMillis = System.currentTimeMillis()
            var selectedDate = "${_tempCalendar.get(Calendar.DAY_OF_MONTH)} " +
                    "${monthList[_tempCalendar.get(Calendar.MONTH)]} ${_tempCalendar.get(Calendar.YEAR)}"


//            Kehadiran Dropdown=======================================
            val adapterKehadiran = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                kehadiranList
            )
            adapterKehadiran.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            kehadiranSpinner.adapter = adapterKehadiran

//            Selected Kehadiran
            kehadiranSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            when (kehadiranList[position]) {
                                "Hadir tepat waktu" -> {
                                    keteranganEdittext.visibility = View.GONE
                                }
                                "Hadir terlambat" -> {
                                    keteranganEdittext.visibility = View.VISIBLE
                                }
                                "Izin" -> {
                                    keteranganEdittext.visibility = View.VISIBLE
                                }
                            }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            submitButton.setOnClickListener{
                if (kehadiranSpinner.selectedItem == "Hadir tepat waktu") {
                    Toast.makeText(this@MainActivity, "Presensi berhasil " +
                            "$selectedDate jam $selectedTime", Toast.LENGTH_SHORT).show()
                } else if (kehadiranSpinner.selectedItem == "Hadir terlambat"){
                    Toast.makeText(this@MainActivity, "Presensi $selectedDate terlambat jam " +
                            "$selectedTime. alasan: ${keteranganEdittext.text}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Berhasil izin $selectedDate jam " +
                            "$selectedTime. alasan: ${keteranganEdittext.text}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}