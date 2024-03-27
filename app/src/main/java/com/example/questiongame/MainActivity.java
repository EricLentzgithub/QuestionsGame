package com.example.questiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    TextView yourAnswer;
    Button answerA, answerB, answerC, answerD;
    Button submitBtn;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        yourAnswer = findViewById(R.id.selectedAnswer);

        answerA = findViewById(R.id.ans_A);
        answerB = findViewById(R.id.ans_B);
        answerC = findViewById(R.id.ans_C);
        answerD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : "+totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        onReQuest(view);
    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion){
            finishQuiz();
            return;
        }
        yourAnswer.setText("");
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        answerA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        answerB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        answerC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        answerD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
    }

    public void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion * .6){
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score+"out of "+ totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
    void dialogNextQues(){
        if(selectedAnswer.equals(QuestionAnswer.correctAnswer[currentQuestionIndex])){
            answerA.setBackgroundColor(Color.WHITE);
            answerB.setBackgroundColor(Color.WHITE);
            answerC.setBackgroundColor(Color.WHITE);
            answerD.setBackgroundColor(Color.WHITE);
            currentQuestionIndex++;
            score++;
            loadNewQuestion();
        }else {
            currentQuestionIndex++;
            loadNewQuestion();
        }
    }

    void colorTheRightAnswers(Object view){
        Button[] clickedButton = {answerA, answerB, answerC, answerD};

        String rightAnswer = QuestionAnswer.correctAnswer[currentQuestionIndex];
        String button1 = (String) clickedButton[0].getText();
        String button2 = (String) clickedButton[1].getText();
        String button3 = (String) clickedButton[2].getText();
        String button4 = (String) clickedButton[3].getText();


        if(button1 == rightAnswer){
            clickedButton[0].setBackgroundColor(Color.GREEN);
        }
        else {
            clickedButton[0].setBackgroundColor(Color.WHITE);
        }

        if(button2 == rightAnswer){
            clickedButton[1].setBackgroundColor(Color.GREEN);
        }
        else {
            clickedButton[1].setBackgroundColor(Color.WHITE);
        }
        if(button3 == rightAnswer){
            clickedButton[2].setBackgroundColor(Color.GREEN);
        }
        else {
            clickedButton[2].setBackgroundColor(Color.WHITE);
        }
        if(button4 == rightAnswer){
            clickedButton[3].setBackgroundColor(Color.GREEN);
        }
        else {
            clickedButton[3].setBackgroundColor(Color.WHITE);
        }
    }
    void colorTheWrongAnswers(Object view){
        Button[] ButtonColor = {answerA, answerB, answerC, answerD};

        String button1 = (String) ButtonColor[0].getText();
        String button2 = (String) ButtonColor[1].getText();
        String button3 = (String) ButtonColor[2].getText();
        String button4 = (String) ButtonColor[3].getText();

        if(!selectedAnswer.equals(QuestionAnswer.correctAnswer[currentQuestionIndex])){
            String newColorText = selectedAnswer;

            if(newColorText == button1){
                ButtonColor[0].setBackgroundColor(Color.RED);
                ButtonColor[1].setBackgroundColor(Color.WHITE);
                ButtonColor[2].setBackgroundColor(Color.WHITE);
                ButtonColor[3].setBackgroundColor(Color.WHITE);
            }
            if(newColorText == button2){
                ButtonColor[0].setBackgroundColor(Color.WHITE);
                ButtonColor[1].setBackgroundColor(Color.RED);
                ButtonColor[2].setBackgroundColor(Color.WHITE);
                ButtonColor[3].setBackgroundColor(Color.WHITE);
            }
            if(newColorText == button3){
                ButtonColor[0].setBackgroundColor(Color.WHITE);
                ButtonColor[1].setBackgroundColor(Color.WHITE);
                ButtonColor[2].setBackgroundColor(Color.RED);
                ButtonColor[3].setBackgroundColor(Color.WHITE);
            }
            if(newColorText == button4){
                ButtonColor[0].setBackgroundColor(Color.WHITE);
                ButtonColor[1].setBackgroundColor(Color.WHITE);
                ButtonColor[2].setBackgroundColor(Color.WHITE);
                ButtonColor[3].setBackgroundColor(Color.RED);
            }
        }
    }
    @SuppressLint("SetTextI18n")
    void onReQuest(Object view){
        answerA.setBackgroundColor(Color.WHITE);
        answerB.setBackgroundColor(Color.WHITE);
        answerC.setBackgroundColor(Color.WHITE);
        answerD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswer[currentQuestionIndex])){

                colorTheRightAnswers(view);
                new AlertDialog.Builder(this)
                        .setTitle("Correct")
                        .setMessage(selectedAnswer + " is right.")
                        .setPositiveButton("Next", (dialogInterface, i) -> dialogNextQues())
                        .setNegativeButton("Restart", (dialogInterface, i) -> yourAnswer.setText("Correct"))
                        .setCancelable(false)
                        .show();
                        yourAnswer.setText(selectedAnswer);
            } else {

                new AlertDialog.Builder(this)
                        .setTitle("InCorrect")
                        .setMessage(selectedAnswer + " is the wrong answer.")
                        .setPositiveButton("Next", (dialogInterface, i) -> dialogNextQues())
                        .setNegativeButton("Restart", (dialogInterface, i) ->  yourAnswer.setText(selectedAnswer +" is InCorrect"))
                        .setCancelable(false)
                        .show();
                         yourAnswer.setText(selectedAnswer);
                colorTheWrongAnswers(view);
            }
        }
        else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }
    }

}