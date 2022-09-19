package com.example.paymenttracker.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.paymenttracker.models.Payment
import com.example.paymenttracker.models.PaymentType
import java.util.*
import kotlin.collections.ArrayList

class PaymentOperation(context: Context) {

    var sqLiteDatabase: SQLiteDatabase? = null
    var dbOpenHelper: DatabaseOpenHelper

    init {
        dbOpenHelper = DatabaseOpenHelper(context, "PaymentTrackerDB", null, 1)
    }

    fun open() {
        sqLiteDatabase = dbOpenHelper.writableDatabase
    }


    fun close() {
        if (sqLiteDatabase != null && sqLiteDatabase!!.isOpen) {
            sqLiteDatabase!!.close()
        }
    }

    fun addPaymentType(paymentType: PaymentType) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseOpenHelper.PAYMENT_TYPE_TITLE, paymentType.title)
        contentValues.put(DatabaseOpenHelper.PAYMENT_TYPE_PERIOD_TYPE, paymentType.periodType)
        contentValues.put(DatabaseOpenHelper.PAYMENT_TYPE_PERIOD_VALUE, paymentType.periodValue)

        open()
        sqLiteDatabase!!.insert(DatabaseOpenHelper.TABLE_PAYMENT_TYPE, null, contentValues)
        close()
    }

    fun updatePaymentType(paymentType: PaymentType) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseOpenHelper.PAYMENT_TYPE_TITLE, paymentType.title)
        contentValues.put(DatabaseOpenHelper.PAYMENT_TYPE_PERIOD_TYPE, paymentType.periodType)
        contentValues.put(DatabaseOpenHelper.PAYMENT_TYPE_PERIOD_VALUE, paymentType.periodValue)

        open()
        sqLiteDatabase!!.update(
            DatabaseOpenHelper.TABLE_PAYMENT_TYPE, contentValues, "${DatabaseOpenHelper.ID} = ?",
            arrayOf(paymentType.id.toString())
        )
        close()
    }

    private fun selectAllPaymentType(): Cursor {
        val selectAllQuery = "Select * from ${DatabaseOpenHelper.TABLE_PAYMENT_TYPE}"
        return sqLiteDatabase!!.rawQuery(selectAllQuery, null)
    }

    fun deletePaymentType(deleteId: Int) {
        open()
        sqLiteDatabase!!.delete(
            DatabaseOpenHelper.TABLE_PAYMENT_TYPE, "${DatabaseOpenHelper.ID} = ?",
            arrayOf(deleteId.toString())
        )
        close()
    }

    fun getPaymentTypes(): ArrayList<PaymentType> {
        val paymentTypes = ArrayList<PaymentType>()
        open()
        val cursor = selectAllPaymentType()
        if (cursor.moveToFirst()) {
            do {
                val paymentType = PaymentType(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.PAYMENT_TYPE_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.PAYMENT_TYPE_PERIOD_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.PAYMENT_TYPE_PERIOD_VALUE))

                )
                paymentTypes.add(paymentType)

            } while (cursor.moveToNext())
        }
        close()

        return paymentTypes
    }

    fun addPayment(payment: Payment) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseOpenHelper.PAYMENT_COST, payment.cost)
        contentValues.put(DatabaseOpenHelper.PAYMENT_PAYMENT_DATE, payment.paymentDate)
        contentValues.put(DatabaseOpenHelper.PAYMENT_PAYMENT_TYPE_ID, payment.paymentTypeId)

        open()
        sqLiteDatabase!!.insert(DatabaseOpenHelper.TABLE_PAYMENT, null, contentValues)
        close()
    }

    fun updatePayment(payment: Payment) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseOpenHelper.PAYMENT_COST, payment.cost)
        contentValues.put(DatabaseOpenHelper.PAYMENT_PAYMENT_DATE, payment.paymentDate)


        open()
        sqLiteDatabase!!.update(
            DatabaseOpenHelper.TABLE_PAYMENT, contentValues, "${DatabaseOpenHelper.ID} = ?",
            arrayOf(payment.id.toString())
        )
        close()
    }

    fun deletePayment(deleteId: Int) {
        open()
        sqLiteDatabase!!.delete(
            DatabaseOpenHelper.TABLE_PAYMENT, "${DatabaseOpenHelper.ID} = ?",
            arrayOf(deleteId.toString())
        )
        close()
    }

    private fun selectPaymentsByPaymentTypeId(paymentTypeId: Int): Cursor {
        val selectAllQuery =
            "Select * from ${DatabaseOpenHelper.TABLE_PAYMENT} where ${DatabaseOpenHelper.PAYMENT_PAYMENT_TYPE_ID} = ? ORDER BY ${DatabaseOpenHelper.PAYMENT_PAYMENT_DATE} DESC"
        return sqLiteDatabase!!.rawQuery(selectAllQuery, arrayOf(paymentTypeId.toString()))
    }


    fun getPaymentsById(paymentTypeId: Int): ArrayList<Payment> {
        val payments = ArrayList<Payment>()
        open()
        val cursor = selectPaymentsByPaymentTypeId(paymentTypeId)
        if (cursor.moveToFirst()) {
            do {
                val paymentType = Payment(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.ID)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.PAYMENT_COST)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.PAYMENT_PAYMENT_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseOpenHelper.PAYMENT_PAYMENT_TYPE_ID))

                )
                payments.add(paymentType)

            } while (cursor.moveToNext())
        }
        close()

        return payments
    }


}