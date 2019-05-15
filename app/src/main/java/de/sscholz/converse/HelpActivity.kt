package de.sscholz.converse

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        setSupportActionBar(myToolbar)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val name = prefs.getString("edit_text_name", "Martin")
        val isAwesome = prefs.getBoolean("checkbox_awesome", false)
        val isCrazy = prefs.getBoolean("switch_crazy", false)
        var extraLine = "Regards to my dear friend $name. You are${if (isCrazy) "" else " not"} crazy! You are${if (isAwesome) "" else " not"} awesome!"

        help_text.text = "${help_text.text}\n\n$extraLine"
    }

}
