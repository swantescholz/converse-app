package de.sscholz.converse

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_history.*

class MainActivity : AppCompatActivity() {

    private var currentFragment = R.id.navigation_play

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_play -> {
                myToolbar.title = getString(R.string.title_play)
                currentFragment = R.id.navigation_play
                openFragment(PlayFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_edit -> {
                myToolbar.title = getString(R.string.title_edit)
                currentFragment = R.id.navigation_edit
                openFragment(EditFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                myToolbar.title = getString(R.string.title_history)
                currentFragment = R.id.navigation_history
                openFragment(HistoryFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fun clearHistory() {
            MyHistory.clear()
            if (currentFragment == R.id.navigation_history) {
                openFragment(HistoryFragment.newInstance())
            }
        }
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.action_help -> {
                startActivity(Intent(this, HelpActivity::class.java))
            }
            R.id.action_clear_history -> {
                clearHistory()
                snackbar("History deleted.")
            }
            R.id.action_reset_all_data -> {
                clearHistory()
                MyFile.basePath.deleteRecursively()
                snackbar("All data reset.")
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        openFragment(when (currentFragment) {
            R.id.navigation_play -> PlayFragment.newInstance()
            R.id.navigation_edit -> EditFragment.newInstance()
            else -> HistoryFragment.newInstance()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyFile.setFilesDir(getExternalFilesDir(null))
        setContentView(R.layout.activity_main)
        myToolbar.title = getString(R.string.title_play)
        setSupportActionBar(myToolbar)
        openFragment(PlayFragment.newInstance())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.myContainer, fragment)
        transaction.commit()
    }
}
