package br.universidade.projeto

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

// Tela 2: lista todos os produtos da coleção "Produtos"
class ListaProdutosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val adapter = ProdutoAdapter()
    private var listener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_produtos)

        val rv = findViewById<RecyclerView>(R.id.rvProdutos)
        val txtVazio = findViewById<TextView>(R.id.txtVazio)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // Listener em tempo real (padrão Observer): atualiza a lista sozinho
        listener = db.collection("Produtos")
            .orderBy("nome")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("Firestore", "Erro ao buscar produtos", e)
                    return@addSnapshotListener
                }
                val produtos = snapshot?.toObjects(Produto::class.java) ?: emptyList()
                adapter.atualizar(produtos)
                txtVazio.visibility = if (produtos.isEmpty()) View.VISIBLE else View.GONE
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.remove()
    }
}
