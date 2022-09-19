package com.example.paymenttracker.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paymenttracker.data.PaymentOperation
import com.example.paymenttracker.databinding.ActivityAddPaymentBinding
import com.example.paymenttracker.models.Payment
import java.text.SimpleDateFormat
import java.util.*

class AddPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        val paymentOperation = PaymentOperation(this)
        val paymentTypeId = intent.getIntExtra("paymentTypeId", -1)
        setContentView(binding.root)
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)


        var paymentDate = simpleDateFormat.format(Date(year - 1900, month, day))



        binding.datePickerButton.setOnClickListener {


            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    paymentDate = simpleDateFormat.format(Date(myear - 1900, mmonth, mdayOfMonth))
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.maxDate = Date().time
            datePickerDialog.show()

        }

        binding.savePaymentBtn.setOnClickListener {
            val costText = binding.costEditTxt.text.toString()
            if (costText.isNullOrEmpty()) {
                binding.costEditTxt.error = "Bu alan boş geçilemz"
            } else {
                val cost = costText.toDouble()
                paymentOperation.addPayment(Payment(null, cost, paymentDate, paymentTypeId))
                setResult(RESULT_OK)
                finish()
            }


        }
    }
}