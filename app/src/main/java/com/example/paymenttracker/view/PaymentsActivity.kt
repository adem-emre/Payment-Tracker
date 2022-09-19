package com.example.paymenttracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.paymenttracker.adapter.PaymentAdapter
import com.example.paymenttracker.adapter.PaymentTypeAdapter
import com.example.paymenttracker.data.PaymentOperation
import com.example.paymenttracker.databinding.ActivityPaymentsBinding
import com.example.paymenttracker.models.PaymentType

class PaymentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentsBinding
    private lateinit var paymentOperation: PaymentOperation
    private lateinit var paymentType: PaymentType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        paymentType = intent.getParcelableExtra<PaymentType>("paymentType")!!
        paymentOperation = PaymentOperation(this)
        val payments = paymentOperation.getPaymentsById(paymentType.id!!)
        payments.forEach {
            println("RESULT : $it")
        }

        binding.rcPayments.adapter = PaymentAdapter(this, payments, ::onDelete)

        binding.paymentsTitle.text = paymentType?.title

        binding.paymentTypeEditBtn.setOnClickListener {
            val intent = Intent(this, AddPaymentTypeActivity::class.java)
            intent.putExtra("paymentType", paymentType)
            startActivity(intent)
        }

        val addPaymentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    println("RESULT SUCCESS")
                    (binding.rcPayments.adapter as PaymentAdapter).refreshList(
                        paymentOperation.getPaymentsById(
                            paymentType.id!!
                        )
                    )

                }
            }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddPaymentActivity::class.java)
            intent.putExtra("paymentTypeId", paymentType.id)
            addPaymentLauncher.launch(intent)

        }


    }

    fun onDelete(deleteId: Int) {
        paymentOperation.deletePayment(deleteId)
        (binding.rcPayments.adapter as PaymentAdapter).refreshList(
            paymentOperation.getPaymentsById(
                paymentType.id!!
            )
        )
    }


}