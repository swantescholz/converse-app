package de.sscholz.converse

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_my_edit_file.*

class MyEditFileActivity : AppCompatActivity() {

    companion object {
        const val INTENT_PATH = "intent_path"
    }

    private var myFile: MyFile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_edit_file)
        setSupportActionBar(myToolbar)

        val filename = intent.getStringExtra(INTENT_PATH)
                ?: throw IllegalStateException("field $INTENT_PATH missing in Intent")
        title = filename
        myFile = MyFile(this, filename)
        fileEditText.setMyText(myFile!!.text)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_file_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                text = ""
                snackbar("Cleared.")
                true
            }
            R.id.action_sort -> {
                text = text.split("\n").asSequence().map { it.trim() }.filter { !it.isEmpty() }.sorted().joinToString("\n")
                snackbar("Sorted.")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveFile() {
        myFile!!.text = text
    }

    override fun onBackPressed() {
        if (text == myFile!!.text) {
            super.onBackPressed()
            return
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val prefAutosave = prefs.getBoolean("checkbox_auto_save_on_exit_edit_mode", false)
        if (prefAutosave) {
            saveFile()
            super.onBackPressed()
            return
        }
        val dialog = AlertDialog.Builder(this).setTitle(null).setMessage("Do you want to save your changes?")
                .setNegativeButton("Discard changes", { _, _ -> super.onBackPressed() })
                .setPositiveButton("Save & quit", { _, _ ->
                    saveFile()
                    super.onBackPressed()
                })
        dialog.show()
    }

    private var text: String
        get() = fileEditText.getMyText()
        set(value) {
            fileEditText.setText(value)
            myFile!!.text = value
        }

}
