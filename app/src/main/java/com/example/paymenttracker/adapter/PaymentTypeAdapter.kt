package com.example.paymenttracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paymenttracker.R
import com.example.paymenttracker.models.PaymentType

class PaymentTypeAdapter(
    val context: Context,
    var paymentTypes: ArrayList<PaymentType>,
    val itemOnClick: (paymentType: PaymentType) -> Unit
) : RecyclerView.Adapter<PaymentTypeAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val paymentTypeTitle: TextView = item.findViewById(R.id.paymentTypeTitle)
        val periodValue: TextView = item.findViewById(R.id.periodValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val paymentTypeItem =
            LayoutInflater.from(context).inflate(R.layout.payment_type_item, parent, false)
        return ViewHolder(paymentTypeItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val periodStr = checkAndReturnPeriodStr(
            paymentTypes[position].periodType,
            paymentTypes[position].periodValue
        )
        holder.paymentTypeTitle.text = paymentTypes[position].title
        holder.periodValue.text = periodStr
        holder.itemView.setOnClickListener {
            itemOnClick(paymentTypes[position])
        }
    }

    override fun getItemCount(): Int {
        return paymentTypes.size
    }

    fun refreshItems(newList: ArrayList<PaymentType>) {
        paymentTypes = newList
        notifyDataSetChanged()
    }

    private fun checkAndReturnPeriodStr(periodType: String?, periodValue: String?): String {
        if (periodType.isNullOrEmpty()) {
            return ""
        } else {
            return "$periodType : $periodValue"
        }
    }
}