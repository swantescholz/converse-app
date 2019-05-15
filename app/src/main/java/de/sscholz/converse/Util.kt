package de.sscholz.converse

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText

fun println(vararg items: Any) {
    kotlin.io.println(items.iterator().asSequence().map{it.toString()}.joinToString(" "))
}

fun snackbar(view: View, notificationText: String) {
    //    Toast.makeText(this, notificationText, Toast.LENGTH_SHORT).show()
    Snackbar.make(view, notificationText, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snackbar(notificationText: String) {
    activity!!.snackbar(notificationText)
}

fun Activity.snackbar(notificationText: String) {
    snackbar(findViewById(android.R.id.content), notificationText)
}

fun Context.displaySimpleAlert(message: String, buttonTitle: String = "OK") {
    val dialog = AlertDialog.Builder(this).setTitle(null).setMessage(message)
            .setPositiveButton(buttonTitle, null)
    dialog.show()
}
fun Fragment.displaySimpleAlert(message: String, buttonTitle: String = "OK") {
    context!!.displaySimpleAlert(message, buttonTitle)
}

fun EditText.getMyText(): String {
    return this.text.toString()
}
fun EditText.setMyText(newText: String) {
    this.text.clear()
    this.text.append(newText)
}

fun Context.copyTextToClipboard(textToBeCopied: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("FlunkyBuddyClip", textToBeCopied)
    clipboard.primaryClip = clip
}

val Activity.screenWidth: Int
    get() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

val Activity.screenHeight: Int
    get() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
