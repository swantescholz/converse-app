package de.sscholz.converse

import android.app.Activity
import android.os.Environment
import java.io.File

/**
 * A class for immediately synching a string on any change with a file
 * important: calls setFilesDir (with the normal filesDir), before creating any MyFiles
 */
class MyFile(val activity: Activity, val filenameWithoutEnding: String) {
    companion object {
        const val fileEnding = ".txt"
        var basePath = File("")
        fun setFilesDir(filesDir: File?) {
            basePath = File(filesDir, "converse_files")
        }


    }

    val fullPath = File(basePath, filenameWithoutEnding + fileEnding)

    var text: String
        get() = content
        set(value) {
            if (value != content) {
                content = value
                writeFile()
            }
        }

    val lines: List<String>
        get() = content.split("\n").map{it.trim()}.filter{!it.isEmpty()}.toList()

    private var content = ""

    init {
        readFile()
    }

    private fun readFile() {

        try {
            if (!fullPath.exists()) {
                // if file doesn't exist yet, write default text before reading
                basePath.mkdirs()
                val defaultStringForFile = arrayListOf(
                        Pair(R.string.file_players, R.string.default_players),
                        Pair(R.string.file_questions, R.string.default_questions),
                        Pair(R.string.file_lies, R.string.default_lies),
                        Pair(R.string.file_truths, R.string.default_truths),
                        Pair(R.string.file_dares, R.string.default_dares),
                        Pair(R.string.file_challenges, R.string.default_challenges)
                ).map{Pair(activity.getString(it.first), activity.getString(it.second))}.
                        first { it.first == filenameWithoutEnding }.second!!
                text = defaultStringForFile.split("\n").map{it.trim()}.filter{!it.isEmpty()}.joinToString("\n")
                fullPath.writeText(text)
                println("Created new file '$fullPath'")
            }
            content = fullPath.readText()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun writeFile() {
        try {
            fullPath.writeText(content)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

