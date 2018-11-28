package com.example.android.kotlin.droidcafe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val mFab by lazy { findViewById<FloatingActionButton>(R.id.fab) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        mFab.setOnClickListener { val intent = Intent(this, OrderActivity::class.java);  startActivity(intent)}
    }

    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param menu Menu to inflate.
     * @return Returns true if the menu inflated.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Handles app bar item clicks.
     *
     * @param item Item clicked.
     * @return True if one of the defined items was clicked.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        // This comment suppresses the Android Studio warning about simplifying
        // the return statements.

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun showDonutOrder(view: View) {
        displayMessage(getString(R.string.donut_order_message))
    }

    fun showIceCreamOrder(view: View) {
        displayMessage(getString(R.string.ice_cream_order_message))
    }

    fun showFroyoOrder(view: View) {
        displayMessage(getString(R.string.froyo_order_message))
    }

    inline fun displayMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}
