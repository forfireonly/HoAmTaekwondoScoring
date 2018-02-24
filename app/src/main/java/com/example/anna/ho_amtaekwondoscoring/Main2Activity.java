package com.example.anna.ho_amtaekwondoscoring;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;
import static java.security.AccessController.getContext;

public class Main2Activity extends AppCompatActivity {
    int competitor1Score;
    int competitor2Score;
    LinearLayout mainView;
    TextView competitor1ScoreView;
    TextView competitorOneStrikeView;
    TextView competitorTwoStrikeView;
    TextView tView;
    int competitorOneStrike;
    int competitorTwoStrike;
    TextView competitor2ScoreView;
    long timeLeft;


    static final String COMPETITOR_1_Score="score";
    static final String COMPETITOR_2_Score="score";
    static final String COMPETITOR_1_Strike="strike";
    static final String COMPETITOR_2_Strike="strike";
    static final String TIMER="seconds";
    //Declare a variable to hold count down timer's paused status
    private boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    private boolean isCanceled = false;
    //Declare a variable to hold CountDownTimer remaining time
    private long timeRemaining = 0;

    ImageView firstCompetitorWinner;
    ImageView secondCompetitorWinner;
    Dialog competitorOneWon;
    Dialog competitorTwoWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Get reference of the XML layout's widgets
         tView = (TextView) findViewById(R.id.tv);
        final Button btnStart = (Button) findViewById(R.id.btn_start);
        final Button btnPause = (Button) findViewById(R.id.btn_pause);
        final Button btnResume = (Button) findViewById(R.id.btn_resume);
        final Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        competitor1ScoreView = findViewById(R.id.team_a_score);
        competitor2ScoreView = findViewById(R.id.team_b_score);
        competitorOneStrikeView = findViewById(R.id.team_a_strike);
        competitorTwoStrikeView = findViewById(R.id.team_b_strike);
        firstCompetitorWinner=findViewById(R.id.first_competitor);
        secondCompetitorWinner=findViewById(R.id.second_competitor);
        mainView = findViewById(R.id.main_view);


        competitorOneWon = new Dialog(this);
        competitorOneWon.setTitle("The Winner of this Match");
        competitorTwoWon = new Dialog(this);
        competitorTwoWon.setTitle("The Winner of this Match");
       // competitorOneWon.setContentView(R.layout.popup1);





//Initially disabled the pause, resume and cancel button
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnCancel.setEnabled(false);


        //Set a Click Listener for start button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isPaused = false;
                isCanceled = false;

                //Disable the start and pause button
                btnStart.setEnabled(false);
                btnResume.setEnabled(false);
                //Enabled the pause and cancel button
                btnPause.setEnabled(true);
                btnCancel.setEnabled(true);

                CountDownTimer timer;
                long millisInFuture = 300000; //30 seconds
                long countDownInterval = 1000; //1 second


                //Initialize a new CountDownTimer instance
                timer = new CountDownTimer(millisInFuture, countDownInterval) {
                    public void onTick(long millisUntilFinished) {
                        //do something in every tick
                        if (isPaused || isCanceled) {
                            //If the user request to cancel or paused the
                            //CountDownTimer we will cancel the current instance
                            cancel();
                        } else {
                            //Display the remaining seconds to app interface
                            //1 second = 1000 milliseconds
                            tView.setText("" + millisUntilFinished / 1000+" SECONDS LEFT ");
                            //Put count down timer remaining time in a variable
                            timeRemaining = millisUntilFinished;

                        }
                    }

                    public void onFinish() {
                        if (competitor2Score == competitor1Score) {tView.setText("SUDDEN DEATH/NEXT POINT WINS");}
                        else
                        {
                        //Do something when count down finished
                        tView.setText("MATCH IS OVER/SEE THE RESULTS BELOW");
                        if (competitor1Score > competitor2Score) {
                            showWinner1();
                            }
                        if (competitor1Score < competitor2Score) {
                            showWinner2();
                            }}

                        //Enable the start button
                        btnStart.setEnabled(true);
                        //Disable the pause, resume and cancel button
                        btnPause.setEnabled(false);
                        btnResume.setEnabled(false);
                        btnCancel.setEnabled(false);
                    }
                }.start();
            }
        });

        //Set a Click Listener for pause button
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When user request to pause the CountDownTimer
                isPaused = true;

                //Enable the resume and cancel button
                btnResume.setEnabled(true);
                btnCancel.setEnabled(true);
                //Disable the start and pause button
                btnStart.setEnabled(false);
                btnPause.setEnabled(false);
            }
        });

        //Set a Click Listener for resume button
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Disable the start and resume button
                btnStart.setEnabled(false);
                btnResume.setEnabled(false);
                //Enable the pause and cancel button
                btnPause.setEnabled(true);
                btnCancel.setEnabled(true);

                //Specify the current state is not paused and canceled.
                isPaused = false;
                isCanceled = false;

                //Initialize a new CountDownTimer instance
                long millisInFuture = timeRemaining;
                long countDownInterval = 1000;
                new CountDownTimer(millisInFuture, countDownInterval) {
                    public void onTick(long millisUntilFinished) {
                        //Do something in every tick
                        if (isPaused || isCanceled) {
                            //If user requested to pause or cancel the count down timer
                            cancel();
                        } else {
                            tView.setText("" + millisUntilFinished / 1000 + " SECONDS LEFT");
                            //Put count down timer remaining time in a variable
                            timeRemaining = millisUntilFinished;

                        }
                    }

                    public void onFinish() {
                        //Do something when count down finished
                        if (competitor2Score == competitor1Score) {tView.setText("TIME IS UP/NEXT POINT WINS");}
                         else
                        {tView.setText("TIME IS UP");
                        if (competitor1Score > competitor2Score) {
                            showWinner1();
                            }
                        if (competitor1Score < competitor2Score) {
                            showWinner2();
                            }}

                        //Disable the pause, resume and cancel button
                        btnPause.setEnabled(false);
                        btnResume.setEnabled(false);
                        btnCancel.setEnabled(false);
                        //Enable the start button
                        btnStart.setEnabled(true);
                    }
                }.start();

                //Set a Click Listener for cancel/stop button
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //When user request to cancel the CountDownTimer
                        isCanceled = true;

                        //Disable the cancel, pause and resume button
                        btnPause.setEnabled(false);
                        btnResume.setEnabled(false);
                        btnCancel.setEnabled(false);
                        //Enable the start button
                        btnStart.setEnabled(true);

                        //Notify the user that CountDownTimer is canceled/stopped
                        tView.setText("CountDown 300 seconds");
                    }
                });
            }
        });

        //Set a Click Listener for cancel/stop button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When user request to cancel the CountDownTimer
                isCanceled = true;

                //Disable the cancel, pause and resume button
                btnPause.setEnabled(false);
                btnResume.setEnabled(false);
                btnCancel.setEnabled(false);
                //Enable the start button
                btnStart.setEnabled(true);

                //Notify the user that CountDownTimer is canceled/stopped
                tView.setText("CountDown 300 seconds");
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COMPETITOR_1_Score, competitor1Score);
        outState.putInt(COMPETITOR_2_Score, competitor2Score);
        outState.putInt(COMPETITOR_1_Strike, competitorOneStrike);
        outState.putInt(COMPETITOR_2_Strike, competitorTwoStrike);
        outState.putLong(TIMER, timeLeft);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        competitor1Score = savedInstanceState.getInt(COMPETITOR_1_Score);
        competitor1ScoreView .setText(String.valueOf(competitor1Score));
        competitor2Score = savedInstanceState.getInt(COMPETITOR_2_Score);
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        competitorOneStrike=savedInstanceState.getInt(COMPETITOR_1_Score);
        competitorOneStrikeView.setText(String.valueOf(competitorOneStrike));
        competitorTwoStrike = savedInstanceState.getInt(COMPETITOR_2_Strike);
        competitorTwoStrikeView.setText(String.valueOf(competitorTwoStrike));
        timeLeft = savedInstanceState.getInt(TIMER);
        tView.setText(String.valueOf(timeLeft));

        }
    public void showWinner1() {
        firstCompetitorWinner.setVisibility(View.VISIBLE);
        competitorOneWon.show();
        TextView PopUpClose;
        competitorOneWon.setContentView(R.layout.popup1);
        PopUpClose =(TextView) competitorOneWon.findViewById(R.id.dismiss1);
        PopUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competitorOneWon.dismiss();
                Toast.makeText(getApplicationContext(), "Please, Reset Score and Timer", Toast.LENGTH_LONG).show();

            }
        });}
    public void showWinner2() {
        secondCompetitorWinner.setVisibility(View.VISIBLE);
        competitorTwoWon.show();
        TextView PopUpClose2;
        competitorTwoWon.setContentView(R.layout.popup2);
        PopUpClose2 =(TextView) competitorTwoWon.findViewById(R.id.dismiss2);
        PopUpClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competitorTwoWon.dismiss();
                Toast.makeText(getApplicationContext(), "Please, Reset Score and Timer", Toast.LENGTH_LONG).show();
            }
        });}
    public void threeA(View view) {
        competitor1Score = competitor1Score + 1;
        competitor1ScoreView.setText(String.valueOf(competitor1Score));
        if (competitor1Score >= 5) {
        showWinner1();
                }
            };


    public void twoA(View view) {
        competitor1Score = competitor1Score + 2;
        competitor1ScoreView.setText(String.valueOf(competitor1Score));
        if (competitor1Score >= 5) {
            showWinner1();
            }


    }

    public void oneA(View view) {
        competitor1Score = competitor1Score + 3;

        competitor1ScoreView.setText(String.valueOf(competitor1Score));
        if (competitor1Score >= 5) {
            showWinner1();
            }

    }

    public void threeB(View view) {
        competitor2Score = competitor2Score + 1;
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        if (competitor2Score >= 5) {
        showWinner2();
        }

    }

    public void twoB(View view) {
        competitor2Score = competitor2Score + 2;
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        if (competitor2Score >= 5) {
            showWinner2();
            }

    }

    public void oneB(View view) {
        competitor2Score = competitor2Score + 3;
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        if (competitor2Score >= 5) {
            showWinner2();
            }

    }

    public void reset(View view) {
        competitor2Score = 0;
        competitor1Score = 0;
        competitorOneStrike = 0;
        competitorTwoStrike = 0;
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        competitor1ScoreView.setText(String.valueOf(competitor1Score));

        competitorOneStrikeView.setText(String.valueOf(competitorOneStrike));
        competitorTwoStrikeView.setText(String.valueOf(competitorTwoStrike));
        secondCompetitorWinner.setVisibility(View.INVISIBLE);
        firstCompetitorWinner.setVisibility(View.INVISIBLE);
    }

    public void strikeA(View view) {
        competitorOneStrike = competitorOneStrike + 1;
        competitorOneStrikeView.setText(String.valueOf(competitorOneStrike));
        if (competitorOneStrike == 2) {
            competitor2Score = competitor2Score + 1;
            if (competitor2Score >= 5) {
                showWinner2();
                }
        }
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        if (competitorOneStrike >= 3) {
            showWinner2();
             }



    }

    public void strikeB(View view) {
        competitorTwoStrike = competitorTwoStrike + 1;
        competitorTwoStrikeView.setText(String.valueOf(competitorTwoStrike));
        if (competitorTwoStrike == 2) {
            competitor1Score = competitor1Score + 1;
            if (competitor1Score >= 5) {
                showWinner1();
                }
        }
        competitor1ScoreView.setText(String.valueOf(competitor1Score));
        if (competitorTwoStrike >= 3) {
            showWinner1();
            }


    }
}






