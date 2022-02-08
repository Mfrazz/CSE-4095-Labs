package com.example.a2022_01_26

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.a2022_01_26.controllers.NextQuestion
import com.example.a2022_01_26.model.AllQuestions
import com.example.a2022_01_26.model.Score

class MainActivity : AppCompatActivity() {

    var trueButton: Button? = null // if we declare a variable with a question mark, that var can be nullable
    var falseButton: Button? = null
    var nextButton: Button? = null
    var doneButton: Button? = null
    var questionText: TextView? = null
    val toScoreActivityText: TextView
        get() = findViewById(R.id.to_score_activity)
    var total: Int = 0


    private val allQuestions: AllQuestions = AllQuestions()

    private val buttonToScoreActivity: Button get() = findViewById(R.id.button_to_ScoreActivity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        doneButton = findViewById(R.id.button_to_ScoreActivity)


        questionText = findViewById(R.id.questionTextView)


        var score = 0;
        val checkScore: Score =  Score()
        val nextQuestion: NextQuestion = NextQuestion()
        questionText?.setText(nextQuestion.linearNextQuestion())

        trueButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (total >= 9) {
                    questionText?.setText("All done! Click \"Score Summary\" to see your final score!")
                }
                else {
                    total += 1
                    if (nextQuestion.isCorrect()) {
                        score = checkScore.inc()
                        toScoreActivityText.text = score.toString()
                        Toast.makeText(baseContext, "Correct! Score = $score", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        score = checkScore.dec()
                        toScoreActivityText.text = score.toString()
                        Toast.makeText(baseContext, "Incorrect! Score = $score", Toast.LENGTH_SHORT).show()
                    }
                    questionText?.setText(nextQuestion.linearNextQuestion())
                }

//                Toast.makeText(baseContext, "Clicked TRUE", Toast.LENGTH_SHORT).show()
            }
        })

        falseButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (total > 9) {
                    questionText?.setText("All done! Click \"Score Summary\" to see your final score!")
                }
                else {
                    total += 1
                    if (!nextQuestion.isCorrect()) {
                        score = checkScore.inc()
                        toScoreActivityText.text = score.toString()
                        Toast.makeText(baseContext, "Correct! Score = $score", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        score = checkScore.dec()
                        toScoreActivityText.text = score.toString()
                        Toast.makeText(baseContext, "Incorrect! Score = $score", Toast.LENGTH_SHORT).show()
                    }
                    questionText?.setText(nextQuestion.linearNextQuestion())
                }

//                Toast.makeText(baseContext, "Clicked FALSE", Toast.LENGTH_SHORT).show()
            }
        })

        nextButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (total > 9) {
                    questionText?.setText("All done! Click \"Score Summary\" to see your final score!")
                }
                else {
                    total += 1
                    score -= 1
                    questionText?.setText(nextQuestion.linearNextQuestion())
                    Toast.makeText(baseContext, "Clicked NEXT", Toast.LENGTH_SHORT).show()
                }
            }
        })

        intent?.let {
                    val myStr = it.getStringExtra("FROM_MAIN")
                    //toScoreActivity.setText("Main Activity")
//            toScoreActivity?.setText(myStr!!) "You scored a total of $score points!"
                }

        buttonToScoreActivity.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
//
                if (total == 9 || total > 9) {
                    Intent(baseContext, ScoreActivity::class.java).also {
                            scoreActivity ->
                        scoreActivity.putExtra("FROM_MAIN",
                            "You scored a total of $score points!".toString())
                        startActivity(scoreActivity)
                    }
                }
                else {
                    Toast.makeText(baseContext, "PLEASE ANSWER ALL QUESTIONS FIRST!", Toast.LENGTH_SHORT).show()
                }

            }
        })


    }
}