package com.example.paymenttracker.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        val ID = "Id"
        val TABLE_PAYMENT_TYPE = "PaymentType"
        val PAYMENT_TYPE_TITLE = "Title"
        val PAYMENT_TYPE_PERIOD_TYPE = "PeriodType"
        val PAYMENT_TYPE_PERIOD_VALUE = "PeriodValue"
        val TABLE_PAYMENT = "Payment"
        val PAYMENT_COST = "Cost"
        val PAYMENT_PAYMENT_DATE = "PaymentDate"
        val PAYMENT_PAYMENT_TYPE_ID = "PaymentTypeId"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val paymentTypeCreateQuery =
            "CREATE TABLE $TABLE_PAYMENT_TYPE($ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, $PAYMENT_TYPE_TITLE TEXT, $PAYMENT_TYPE_PERIOD_TYPE TEXT,$PAYMENT_TYPE_PERIOD_VALUE TEXT)"
        val paymentCreateQuery =
            "CREATE TABLE $TABLE_PAYMENT($ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,$PAYMENT_COST REAL,$PAYMENT_PAYMENT_DATE TEXT,$PAYMENT_PAYMENT_TYPE_ID INTEGER, FOREIGN KEY($PAYMENT_PAYMENT_TYPE_ID) REFERENCES $TABLE_PAYMENT_TYPE($ID) ON UPDATE CASCADE ON DELETE CASCADE)"
        p0!!.execSQL(paymentTypeCreateQuery)
        p0.execSQL(paymentCreateQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}