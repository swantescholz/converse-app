package de.sscholz.converse

object MyHistory {
    // strings that have been used for questions/dares/statements/challenges and should not be used again in this session
    val usedLines = HashSet<String>()

    var historyString = ""

    fun clear() {
        usedLines.clear()
        historyString = ""
    }

    fun addUsedLine(line: String) {
        usedLines.add(line)
    }

    fun addMessage(message: String) {
        historyString = "$message\n\n" + MyHistory.historyString
    }
}