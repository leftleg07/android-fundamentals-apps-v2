package com.example.android.kotlin.droidcafeinput

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.android.kotlin.droidcafeinput.MainActivity.Companion.EXTRA_ORDER_MESSAGE

class OrderActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val label = mAdapter.getItem(position).toString()
        displayToast(label)
    }

    private val mOrderText by lazy { findViewById<TextView>(R.id.order_textview) }
    private val mLabelSpinner by lazy { findViewById<Spinner>(R.id.label_spinner) }
    private val mAdapter: SpinnerAdapter by lazy { ArrayAdapter.createFromResource(this, R.array.labels_array, R.layout.support_simple_spinner_dropdown_item) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val message = intent.getStringExtra(EXTRA_ORDER_MESSAGE)
        mOrderText.text = message

        mLabelSpinner.onItemSelectedListener = this
        mLabelSpinner.adapter = mAdapter

    }

    fun onRadioButtonClicked(view: View) {
        val clicked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.sameday -> if (clicked) displayToast(getString(R.string.same_day_messenger_service))
            R.id.nextday -> if (clicked) displayToast(getString(R.string.next_day_ground_delivery))
            R.id.pickup -> if (clicked) displayToast(getString(R.string.pick_up))
            else -> null /* nothing */

        }
    }

    inline fun displayToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
