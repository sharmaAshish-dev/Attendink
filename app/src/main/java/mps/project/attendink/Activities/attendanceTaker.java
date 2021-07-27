package mps.project.attendink.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.wenchao.cardstack.CardStack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mps.project.attendink.Adapter.cardStackAdapter;
import mps.project.attendink.Model.studentModel;
import mps.project.attendink.R;

public class attendanceTaker extends AppCompatActivity {

    private CardStack mCardStack;
    private cardStackAdapter mCardAdapter;
    private JSONObject object;
    private ArrayList<studentModel> studentList = new ArrayList<>();
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private int presentStudent = 0;
    private int absentStudents = 0;
    private int counter = 1;
    private TextView totalStudent;
    private TextView resetBtn, undoBtn;
    private ImageView helpPopup;
    private Dialog help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_taker);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        totalStudent = findViewById(R.id.totalStudent);
        resetBtn = findViewById(R.id.resetBtn);
        undoBtn = findViewById(R.id.undoBtn);
        helpPopup = findViewById(R.id.helpPopup);

        mCardStack = findViewById(R.id.container);

        help = new Dialog(this);

        GetStudentData();

        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int direction, float distance) {
                //if "return true" the dismiss animation will be triggered
                //if false, the card will move back to stack
                //distance is finger swipe distance in dp

                //the direction indicate swipe direction
                //there are four directions
                //  0  |  1
                // ----------
                //  2  |  3

                return distance > 300;
            }

            @Override
            public boolean swipeStart(int direction, float distance) {

                return true;
            }

            @Override
            public boolean swipeContinue(int direction, float distanceX, float distanceY) {

                return true;
            }

            @Override
            public void discarded(int id, int direction) {

//                String d = String.valueOf(direction);

                if (direction == 1 || direction == 3) {
                    Toast.makeText(attendanceTaker.this, "Present", Toast.LENGTH_SHORT).show();

                    presentStudent++;

                } else if (direction == 0 || direction == 2) {
                    Toast.makeText(attendanceTaker.this, "Absent", Toast.LENGTH_SHORT).show();

                    absentStudents++;
                }

                if (id == studentList.size()) {
                    startNewActivity("10", String.valueOf(presentStudent), String.valueOf(absentStudents));
                }

                //this callback invoked when dismiss animation is finished.
            }

            @Override
            public void topCardTapped() {
                //this callback invoked when a top card is tapped by user.
            }

        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                absentStudents = 0;
                presentStudent = 0;
                mCardStack.reset(true);
            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (absentStudents <= 0) {

                } else {
                    absentStudents = absentStudents - 1;
                    mCardStack.undo();
                }

            }
        });

        helpPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpPopup(view);
            }
        });

    }

    private void GetStudentData() {

        requestQueue = Volley.newRequestQueue(this);

        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {

                    progressDialog.dismiss();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            object = response.getJSONObject(i);

                            studentModel studentModel = new studentModel(
                                    object.getString("id"),
                                    object.getString("name"),
                                    object.getString("username"),
                                    object.getString("email"),
                                    object.getString("phone"),
                                    object.getString("website"),
                                    "https://randomuser.me/api/portraits/men/" + String.valueOf(counter++) + ".jpg"
                            );

                            studentList.add(studentModel);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    totalStudent.append(String.valueOf(studentList.size()));

                    mCardAdapter = new cardStackAdapter(getApplicationContext(), R.layout.card_content, studentList);

                    mCardStack.setAdapter(mCardAdapter);

                    mCardStack.reset(true);

                },
                error -> {

                    progressDialog.dismiss();
                    Toast.makeText(attendanceTaker.this, "unable to fetch student data", Toast.LENGTH_SHORT).show();
                    finish();

                }
        );

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);

    }

    private void startNewActivity(String total, String present, String absent) {

        Intent intent = new Intent(attendanceTaker.this, studentAttendenceResult.class);
        intent.putExtra("total", total);
        intent.putExtra("present", present);
        intent.putExtra("absent", absent);
        startActivity(intent);
        finish();

//        Toast.makeText(this, "total = " + total + "\n" + "present = " + present + "\n" + "absent = " + absent, Toast.LENGTH_SHORT).show();

    }

    public void showHelpPopup(View view) {
        TextView closeBtn;
        help.setContentView(R.layout.popup1);
        closeBtn = help.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(view1 -> help.dismiss());
        help.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = help.getWindow();
        window.setGravity(Gravity.CENTER);
//        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        help.show();
    }

}
