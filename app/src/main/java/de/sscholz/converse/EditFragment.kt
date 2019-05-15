package de.sscholz.converse


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_edit.*


class EditFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        val playersButton = view.findViewById<Button>(R.id.myPlayerButton)
        val questionsButton = view.findViewById<Button>(R.id.myQuestionsButton)
        val truthsButton = view.findViewById<Button>(R.id.myTruthsButton)
        val liesButton = view.findViewById<Button>(R.id.myLiesButton)
        val challengesButton = view.findViewById<Button>(R.id.myChallengesButton)
        val daresButton = view.findViewById<Button>(R.id.myDaresButton)

        playersButton.setOnClickListener { _ ->
            startActivity(Intent(activity, MyEditFileActivity::class.java).putExtra(MyEditFileActivity.INTENT_PATH, getString(R.string.file_players)))
        }
        questionsButton.setOnClickListener { _ ->
            startActivity(Intent(activity, MyEditFileActivity::class.java).putExtra(MyEditFileActivity.INTENT_PATH, getString(R.string.file_questions)))
        }
        truthsButton.setOnClickListener { _ ->
            startActivity(Intent(activity, MyEditFileActivity::class.java).putExtra(MyEditFileActivity.INTENT_PATH, getString(R.string.file_truths)))
        }
        liesButton.setOnClickListener { _ ->
            startActivity(Intent(activity, MyEditFileActivity::class.java).putExtra(MyEditFileActivity.INTENT_PATH, getString(R.string.file_lies)))
        }
        challengesButton.setOnClickListener { _ ->
            startActivity(Intent(activity, MyEditFileActivity::class.java).putExtra(MyEditFileActivity.INTENT_PATH, getString(R.string.file_challenges)))
        }
        daresButton.setOnClickListener { _ ->
            startActivity(Intent(activity, MyEditFileActivity::class.java).putExtra(MyEditFileActivity.INTENT_PATH, getString(R.string.file_dares)))
        }

        return view
    }


    companion object {
        fun newInstance(): EditFragment = EditFragment()
    }


}
