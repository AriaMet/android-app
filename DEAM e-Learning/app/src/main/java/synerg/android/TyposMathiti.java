package synerg.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/*
H activity TyposMathiti ύστερα από την ολοκλήρωση ενός τεστ 12 ερωτήσεων δείχνει στον χρήστη αν
είναι οπτικός, ακουστικος ή κιναισθητικός τύπος φοιτητή.
 */

public class TyposMathiti extends AppCompatActivity implements View.OnClickListener{

    private Button BB;
    private Button BM;
    private Button Bk;
    private TextView TVar;
    private TextView TVKeimenoer;
    private TextView[] TVApanthsh;
    private Questionnaire Type;
    private Question CurQ;
    private Drawable BackDraw;
    private int CurQNum;
    private ArrayList<Integer> abc = new ArrayList<Integer>();
    private int SelAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typos_mathiti);

        TVar = findViewById(R.id.TVar);
        TVKeimenoer = findViewById(R.id.TVkeimenoer);
        TVApanthsh = new TextView[4];
        TVApanthsh[0]=findViewById(R.id.TVApanthsh1);
        TVApanthsh[1]=findViewById(R.id.TVApanthsh2);
        TVApanthsh[2]=findViewById(R.id.TVApanthsh3);
        for(int i =0; i<3; i++){
            TVApanthsh[i].setOnClickListener(this);
        }
        BB=findViewById(R.id.BB);
        BM=findViewById(R.id.BM);
        Bk=findViewById(R.id.Bk);
        BB.setOnClickListener(this);
        BM.setOnClickListener(this);
        Bk.setOnClickListener(this);

        for (int i=0; i<12; i++){               // αρχικοποιώ την λίστα adc την οποία ύστερα θα χρησιμοποιήσω για να βρω τον τύπο του μαθητή σύμφωνα με τις απαντήσεις που έδωσε
            abc.add(-1);
        }
        try {
            Type = Questionnaire.GetInstance(this);
            InputStream is = this.getResources().openRawResource(R.raw.typos);
            Type.LoadDatabase(is);
        }catch(IOException e) {
            e.printStackTrace();
        }
        DoNext(abc);
    }

    @Override
    public void onClick(View v) {
        if (v == BB) {                          // δίνεται η επιλογή να πάει στην προηγούμενη αναπάντητη ερώτηση
            Type.GoPrevUnAns();
            Type.setCurQuest(Type.getCurQuest() - 1);
            DoNext(abc);
        }
        if (v == Bk) {                          // καταχωρεί την απάντηση του χρήστη και εισάγει την απάντηση του στην λίστα
            if (SelAns == -1)
                return;
            CurQ.setUserAns(SelAns);
            abc.add(CurQNum,SelAns);
            DoNext(abc);
        }
        if (v == BM) {                          // προχωράει στην επόμενη ερώτηση
            DoNext(abc);
        }
        for (int i = 0; i < 3; i++) {
            if (v == TVApanthsh[i])
                SelAns = i;
        }
        DoChangeAnswer();

    }

    void DoNext(ArrayList<Integer> GivenAnswerList) {  // χρησιμοποιείται για να πάει στην επόμενη ερώτηση ή αν έχουν απαντηθεί όλες οι ερωτήσεις να δείξει το αποτέλεσμα και να επιστρέψει στο αρχικό μενού
        if (Type.GetNoUnAnsweredQuestions() > 0)       // αν δεν έχουν τελειώσει οι ερωτήσεις
            showNextQuestion();                        // συνεχίζει στην επόμενη ή προηγούμενη αναλόγως τι εντολή έδωσε ο χρήστης
        else                                           // αν έχουν απαντηθεί όλες οι ερωτήσεις
            showStudentType(GivenAnswerList);          // δείχνει σε toast το αποτέλεσμα και επιστρέχει στο αρχικό μενού

        SelAns = -1;

    }

    private void showStudentType(ArrayList<Integer> GivenAnswerList){   // δείχνει τον τύπο του φοιτητή
        showToast("Τέλος τεστ...");
        int a=0;
        int b=0;
        int c=0;
        for (int i = 0; i < GivenAnswerList.size();i++){
            if(GivenAnswerList.get(i)==0)
                a++;
            if(GivenAnswerList.get(i)==1)
                b++;
            if(GivenAnswerList.get(i)==2)
                c++;
        }

        int max=a;
        if((a==b) && (b==c)){
            showToast(" Είσαι το ίδιο οπτικός, ακουστικός και κιναισθητικός τύπος φοιτητή ");
        }else if((a==b) && (c<b)){
            showToast(" Είσαι το ίδιο οπτικός και ακουστικός τύπος φοιτητή ");
        }else if((b==c) && (a<b)){
            showToast(" Είσαι το ίδιο ακουστικός και κιναισθητικός τύπος φοιτητή ");
        }else if((a==c) && (b<a)){
            showToast(" Είσαι το ίδιο οπτικός και κιναισθητικός τύπος φοιτητή ");
        }else if((max<b) && (c<b)){
            showToast(" Είσαι ακουστικός τύπος φοιτητή ");
        }else if((max<c) && (b<c)){
            showToast(" Είσαι κιναιστθητικός τύπος φοιτητή ");
        }else{
            showToast(" Είσαι οπτικός τύπος φοιτητή ");
        }

        Type.clearInstance();
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);    // επιστροφή στο αρχικό μενού
        startActivity(intent);
    }

    private void showNextQuestion(){                // προχωράει στην επόμενη ερώτηση
        CurQ = Type.GetQuestion();
        for (int i = 0; i < 3; i++) {
            TVApanthsh[i].setEnabled(true);
        }
        TVar.setText(String.valueOf(CurQNum + 1));
        TVKeimenoer.setText(CurQ.getQueText());
        for (int i = 0; i < CurQ.GetNoAnswers(); i++)
            TVApanthsh[i].setText(CurQ.getAnswer(i));
        for (int i = CurQ.GetNoAnswers(); i < 3; i++) {
            TVApanthsh[i].setEnabled(false);
            TVApanthsh[i].setText("");
        }
        CurQNum = Type.GoNextUnAns();
    }

    private void showToast(String str){         // εμφανίζει τα toast
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    void DoChangeAnswer() {                     // υπογραμμίζει με έντονο χρώμα την απάντηση που επελέξε ο χρήστης
        int i;
        for (i = 0; i < 3; i++) {
            TVApanthsh[i].setBackground(BackDraw);
            if (i == SelAns)
                TVApanthsh[i].setBackgroundColor(Color.MAGENTA);
        }
    }
}