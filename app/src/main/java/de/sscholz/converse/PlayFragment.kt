package de.sscholz.converse

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class PlayFragment : Fragment() {

    private var currentAlert: AlertDialog? = null

    fun AlertDialog.Builder.myshow() {
        currentAlert = this.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val playersButton = view.findViewById<Button>(R.id.myPlayRandomPlayersButton)
        val teamsButton = view.findViewById<Button>(R.id.myPlayRandomTeamsButton)
        val truthsButton = view.findViewById<Button>(R.id.myPlayTruthsButton)
        val questionsButton = view.findViewById<Button>(R.id.myPlayQuestionButtion)
        val challengeButton = view.findViewById<Button>(R.id.myPlayChallengeButtion)
        val daresButton = view.findViewById<Button>(R.id.myPlayDaresButton)
        val golButton = view.findViewById<Button>(R.id.myPlayGolButton)
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val prefUseTwoLies = prefs.getBoolean("checkbox_two_lies", false)
        if (prefUseTwoLies) {
            truthsButton.text = "2 Lies 1 Truth"
        } else {
            truthsButton.text = "2 Truths 1 Lie"
        }
        fun getLines(stringId: Int) = MyFile(activity!!, getString(stringId)).lines.filter { it !in MyHistory.usedLines }
        fun wrapCatch(f: () -> Unit) {
            try {
                f()
            } catch (ex: Exception) {
                displaySimpleAlert("Not enough data to perform this action!\nClear your history or add more lines to you data file.")
            }
        }
        fun preprocessMessage(message: String) = message.replace("|", "\n")
        fun Button.onClickWithCatchErrorAndNextButton(getMessage: () -> String) {
            this.setOnClickListener { _ ->
                wrapCatch {
                    val message = preprocessMessage(getMessage())
                    MyHistory.addMessage(message)
                    val dialog = AlertDialog.Builder(context).setTitle(null).setMessage(message)
                            .setNegativeButton("Close", null)
                            .setPositiveButton("Again", { _, _ -> this.performClick() })
                    dialog.myshow()
                }
            }
        }
        playersButton.onClickWithCatchErrorAndNextButton {
            val player = getLines(R.string.file_players).shuffled().get(0)
            "Randomized player:\n$player"
        }
        teamsButton.setOnClickListener { _ ->
            val players = getLines(R.string.file_players).shuffled()
            if (players.size < 2) {
                displaySimpleAlert("Not enough players!")
            } else {
                val i = players.size / 2
                val team1 = players.subList(0, i).sorted().joinToString(", ")
                val team2 = players.subList(i, players.size).sorted().joinToString(", ")
                val message = "Team1:\n$team1\n\nTeam2:\n$team2"
                MyHistory.addMessage(message)
                displaySimpleAlert(message)
            }
        }
        truthsButton.setOnClickListener { _ ->
            wrapCatch {
                var truths = getLines(R.string.file_truths).shuffled().subList(0, 2).toMutableList()
                var lie = getLines(R.string.file_lies).shuffled().get(0)
                if (prefUseTwoLies) {
                    truths = getLines(R.string.file_lies).shuffled().subList(0, 2).toMutableList()
                    lie = getLines(R.string.file_truths).shuffled().get(0)
                }
                truths.add(lie)
                truths.shuffle()
                truths.forEach { MyHistory.addUsedLine(it) }
                val answer = truths.indexOf(lie) + 1
                val msg1 = preprocessMessage("What is the ${if (prefUseTwoLies) "truth" else "lie"}?\n1) ${truths.get(0)}\n2) ${truths.get(1)}\n3) ${truths.get(2)}")
                val msg2 = preprocessMessage("$msg1\n\nThe ${if (prefUseTwoLies) "truth" else "lie"} was:\n$answer) $lie")
                AlertDialog.Builder(context!!).setTitle(null).setMessage(msg1)
                        .setPositiveButton("OK", null)
                        .setOnDismissListener {
                            MyHistory.addMessage(msg2)
                            AlertDialog.Builder(context!!).setTitle(null).setMessage(msg2)
                                    .setNegativeButton("Close", null)
                                    .setPositiveButton("Again", { _, _ -> truthsButton.performClick() }).myshow()
                        }.myshow()
            }
        }
        questionsButton.onClickWithCatchErrorAndNextButton {
            val question = getLines(R.string.file_questions).shuffled().get(0)
            MyHistory.addUsedLine(question)
            "Randomized question:\n$question"
        }

        challengeButton.onClickWithCatchErrorAndNextButton {
            val challenge = getLines(R.string.file_challenges).shuffled().get(0)
            MyHistory.addUsedLine(challenge)
            "Randomized challenge:\n$challenge"
        }
        daresButton.onClickWithCatchErrorAndNextButton {
            val dare = getLines(R.string.file_dares).shuffled().get(0)
            MyHistory.addUsedLine(dare)
            "Randomized dare:\n$dare"
        }
        golButton.setOnClickListener { _ ->
            startActivity(Intent(activity, GolActivity::class.java))
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        currentAlert?.dismiss()
        currentAlert = null
    }

    companion object {
        fun newInstance(): PlayFragment = PlayFragment()
    }
}
