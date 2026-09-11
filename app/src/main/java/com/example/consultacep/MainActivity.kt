package com.example.consultacep

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appconsultacep.api.ViaCepClient
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
        val btnConsultar = findViewById<Button>(R.id.idBtnConsultar)
        val edtCep = findViewById<EditText>(R.id.edtCep)
        val edtLogradouro = findViewById<EditText>(R.id.idtxtLog)
        val edtBairro = findViewById<EditText>(R.id.idtxtbairro)
        val edtDDD = findViewById<EditText>(R.id.idtxtDDD)
        val edtUf = findViewById<EditText>(R.id.idtxtUF)
        val edtCidade = findViewById<EditText>(R.id.idtxtCidade)
        val txtinputL = findViewById<TextInputLayout>(R.id.textInputLayout)

        btnConsultar.setOnClickListener {
            val cep = edtCep.text.toString().filter { it.isDigit() }
            lifecycleScope.launch {
                val endereco = ViaCepClient.instance.buscarEndereco(cep)
                edtLogradouro.setText(endereco.logradouro)
                edtBairro.setText(endereco.bairro)
                edtDDD.setText(endereco.ddd)
                edtCidade.setText(endereco.localidade)
                edtUf.setText(endereco.uf)
            }
        }

        edtCep.addTextChangedListener(object : TextWatcher {

            private var isUpdating = false

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {}

            override fun afterTextChanged(s: Editable?) {

                if (isUpdating) return

                isUpdating = true

                // Remove o hífen para trabalhar apenas com os números
                var cep = s.toString().replace("-", "")

                // Limita a 8 dígitos
                if (cep.length > 8) {
                    cep = cep.substring(0, 8)
                }

                // Formata 01001000 -> 01001-000
                val formatado = if (cep.length > 5) {
                    cep.substring(0, 5) + "-" + cep.substring(5)
                } else {
                    cep
                }

                if (s.toString() != formatado) {
                    edtCep.setText(formatado)
                    edtCep.setSelection(formatado.length)
                }

                isUpdating = false

                when {
                    cep.isEmpty() -> {
                        txtinputL.error = "Coloque o CEP"
                    }

                    cep.length != 8 -> {
                        txtinputL.error = "Digite um CEP válido"
                    }

                    else -> {
                        txtinputL.error = null
                    }
                }
            }
        })
    }
}