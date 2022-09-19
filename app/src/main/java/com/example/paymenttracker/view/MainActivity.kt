package com.example.paymenttracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.paymenttracker.R
import com.example.paymenttracker.adapter.PaymentTypeAdapter
import com.example.paymenttracker.data.PaymentOperation
import com.example.paymenttracker.databinding.ActivityMainBinding
import com.example.paymenttracker.models.PaymentType

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var paymentTypes: ArrayList<PaymentType>
    private lateinit var paymentOperation: PaymentOperation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paymentTypeAddBtn.setOnClickListener {
            val intent = Intent(this, AddPaymentTypeActivity::class.java)
            addPaymentTypeLauncher.launch(intent)


        }

        paymentOperation = PaymentOperation(this)



        paymentTypes = paymentOperation.getPaymentTypes()


        val paymentTypeAdapter = PaymentTypeAdapter(this, paymentTypes, this::onPaymentTypeClick)
        binding.rcPaymentTypes.adapter = paymentTypeAdapter

    }

    val addPaymentTypeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                println("RESULT SUCCESS")
                paymentTypes = paymentOperation.getPaymentTypes()
                (binding.rcPaymentTypes.adapter as PaymentTypeAdapter).refreshItems(paymentTypes)
            }
        }

    fun onPaymentTypeClick(paymentType: PaymentType) {
        val intent = Intent(this, PaymentsActivity::class.java)
        intent.putExtra("paymentType", paymentType)
        startActivity(intent)
    }


}