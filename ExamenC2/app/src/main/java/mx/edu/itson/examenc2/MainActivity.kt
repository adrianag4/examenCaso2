package mx.edu.itson.examenc2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var spTipoPoliza: Spinner
    private lateinit var tvCosto: TextView
    private lateinit var etAnios: EditText
    private lateinit var tvPagar: TextView

    private val tiposPoliza = arrayOf("Tipo Póliza", "Autos Sedán", "Camionetas", "Autos Deportivos")
    private val costos = mapOf(
        "Autos Sedán" to 500.0,
        "Camionetas" to 700.0,
        "Autos Deportivos" to 1200.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spTipoPoliza = findViewById(R.id.spTipoPoliza)
        tvCosto = findViewById(R.id.tvCosto)
        etAnios = findViewById(R.id.etAnios)
        tvPagar = findViewById(R.id.tvPagar)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposPoliza)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipoPoliza.adapter = adapter

        spTipoPoliza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                actualizarCostoYTotal()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etAnios.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                actualizarCostoYTotal()
            }
        })
    }

    private fun actualizarCostoYTotal() {
        val tipoSeleccionado = spTipoPoliza.selectedItem?.toString() ?: "Tipo Póliza"

        if (tipoSeleccionado == "Tipo Póliza") {
            tvCosto.text = "Costo"
            tvPagar.text = "Pagar $"
            return
        }

        val costoAnual = costos[tipoSeleccionado] ?: 0.0
        tvCosto.text = "Costo: $${String.format("%.2f", costoAnual)}"

        val anios = etAnios.text.toString().toIntOrNull() ?: 0
        val total = costoAnual * anios
        tvPagar.text = "Pagar $${String.format("%.2f", total)}"
    }
}