package br.universidade.projeto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

// Tela 1: cadastro de produtos
class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val edtCategoria = findViewById<EditText>(R.id.edtCategoria)
        val edtPreco = findViewById<EditText>(R.id.edtPreco)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val btnListar = findViewById<Button>(R.id.btnListar)

        btnSalvar.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            val categoria = edtCategoria.text.toString().trim()
            val preco = edtPreco.text.toString().replace(",", ".").toDoubleOrNull()

            if (nome.isEmpty() || categoria.isEmpty() || preco == null) {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val produto = hashMapOf(
                "nome" to nome,
                "categoria" to categoria,
                "preco" to preco
            )

            // .add() = documento com ID autogerado
            db.collection("Produtos")
                .add(produto)
                .addOnSuccessListener { doc ->
                    Log.d(TAG, "Produto salvo com ID: ${doc.id}")
                    Toast.makeText(this, "Produto cadastrado!", Toast.LENGTH_SHORT).show()
                    edtNome.text.clear()
                    edtCategoria.text.clear()
                    edtPreco.text.clear()
                    edtNome.requestFocus()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Erro ao salvar produto", e)
                    Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        btnListar.setOnClickListener {
            startActivity(Intent(this, ListaProdutosActivity::class.java))
        }
    }

    companion object {
        private const val TAG = "Firestore"
    }
}
