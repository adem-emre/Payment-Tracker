package com.example.paymenttracker.view

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.paymenttracker.R
import com.example.paymenttracker.data.PaymentOperation
import com.example.paymenttracker.databinding.ActivityAddPaymentTypeBinding
import com.example.paymenttracker.models.PaymentType

class AddPaymentTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val paymentOperation = PaymentOperation(this)

        var periodType: String? = null

        val spAdapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.period_types)
        ) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }
        }

        binding.spPeriodTypes.adapter = spAdapter

        binding.spPeriodTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    binding.periodValueEditText.isEnabled = true
                    periodType = p0!!.getItemAtPosition(p2).toString()
                } else {
                    binding.periodValueEditText.isEnabled = false
                    (p1 as TextView).setTextColor(Color.GRAY)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        }
        fun navigateToHome() {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val paymentType = intent.getParcelableExtra<PaymentType>("paymentType")
        if (paymentType != null) {
            binding.apply {
                titleEditText.setText(paymentType.title)
                periodValueEditText.setText(paymentType.periodValue)
                periodType = paymentType.periodType
                if (periodType != null) {
                    val spinnerPosition = spAdapter.getPosition(periodType)
                    spPeriodTypes.setSelection(spinnerPosition)
                }
                paymentTypeDeleteBtn.visibility = View.VISIBLE
                paymentTypeDeleteBtn.setOnClickListener {
                    val builder = AlertDialog.Builder(this@AddPaymentTypeActivity)
                    builder.setMessage("Bu Ödeme Tipini silmek istediğinize emin misiniz?")
                        .setPositiveButton("Sil",
                            DialogInterface.OnClickListener { dialog, id ->
                                paymentOperation.deletePaymentType(paymentType.id!!)
                                navigateToHome()
                            })
                        .setNegativeButton(
                            "İptal",
                            null
                        )
                    val dialog = builder.create()
                    dialog.show()

                }

            }
        }




        binding.savePaymentTypeBtn.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val periodValue = binding.periodValueEditText.text.toString()
            if (title.isNullOrEmpty()) {
                binding.titleEditText.error = "Bu alan boş geçilemez"
            } else {
                if (paymentType != null) {
                    paymentOperation.updatePaymentType(
                        paymentType.copy(
                            title = title,
                            periodType = periodType,
                            periodValue = periodValue
                        )
                    )
                    navigateToHome()
                } else {
                    paymentOperation.addPaymentType(
                        PaymentType(
                            null,
                            title,
                            periodType,
                            periodValue
                        )
                    )
                    setResult(RESULT_OK)
                    finish()
                }
            }

        }


    }
}