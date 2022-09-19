package com.example.paymenttracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paymenttracker.R
import com.example.paymenttracker.models.Payment

class PaymentAdapter(
    val context: Context,
    var payments: ArrayList<Payment>,
    val onDelete: (deleteId: Int) -> Unit
) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val costTxt: TextView = item.findViewById(R.id.costTxt)
        val paymentDate: TextView = item.findViewById(R.id.paymentDateTxt)
        val deleteButton: ImageView = item.findViewById(R.id.paymentDeleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.payment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cost = (payments[position].cost.toString() + " ₺")
        holder.costTxt.text = cost
        holder.paymentDate.text = payments[position].paymentDate.toString()
        holder.deleteButton.setOnClickListener {
            onDelete(payments[position].id!!)
        }
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    fun refreshList(newList: ArrayList<Payment>) {
        payments = newList
        notifyDataSetChanged()
    }

}