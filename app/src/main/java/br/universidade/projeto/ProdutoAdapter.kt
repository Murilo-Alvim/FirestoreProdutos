package br.universidade.projeto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class ProdutoAdapter : RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    private val lista = mutableListOf<Produto>()
    private val moeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    fun atualizar(novos: List<Produto>) {
        lista.clear()
        lista.addAll(novos)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtCategoria: TextView = view.findViewById(R.id.txtCategoria)
        val txtPreco: TextView = view.findViewById(R.id.txtPreco)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = lista[position]
        holder.txtNome.text = p.nome
        holder.txtCategoria.text = p.categoria
        holder.txtPreco.text = moeda.format(p.preco)
    }

    override fun getItemCount() = lista.size
}
