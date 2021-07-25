package synerg.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/*
Η activity Testakia βοηθάει στην υλοποίηση των τεστ και του διαγωνίσματος και στη συνέχεια στέλνει
τα αποτελέσματα στην activity Apotelesmata όπου είναι όλα τα αποτελέσματα συγκενρωμένα.
 */


public class Testakia extends AppCompatActivity implements View.OnClickListener {

    private TextView TVNoQues, TVQuestion;
    private TextView[] TVAnswers;
    private Button BNext;
    private Button BBack;
    private Button BKatax;
    private ImageView IMtest;
    private Question CurQ;
    private Questionnaire Erwthseis;
    private ArrayList<String> Orthothta = new ArrayList<>();
    private int CurQNum;
    private int SelAns;
    private double grade;
    private int K;
    private Drawable BackDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testakia);

        IMtest = findViewById(R.id.IMtest);
        TVNoQues = findViewById(R.id.TVNoQues);
        TVQuestion = findViewById(R.id.TVQuestion);
        TVAnswers = new TextView[4];
        TVAnswers[0] = findViewById(R.id.TVAnswers1);
        TVAnswers[1] = findViewById(R.id.TVAnswers2);
        TVAnswers[2] = findViewById(R.id.TVAnswers3);
        TVAnswers[3] = findViewById(R.id.TVAnswers4);
        for (int i = 0; i < 4; i++) {
            TVAnswers[i].setOnClickListener(this);
        }
        for (int i = 0; i < 4; i++) {
            Orthothta.add(i, "-");
        }
        BNext = findViewById(R.id.BNext);
        BKatax = findViewById(R.id.BKatax);
        BBack = findViewById(R.id.BBack);
        BNext.setOnClickListener(this);
        BBack.setOnClickListener(this);
        BKatax.setOnClickListener(this);
        Intent intent = getIntent();
        K = intent.getIntExtra("kleidi", 0);

        try {                                                // διαλέγει το τεστ που επέλεξε ο χρήστης είτε απο το αρχικό μενού είτε απο την θεωρία
            Erwthseis = Questionnaire.GetInstance(this);
            if (K == 11) {
                InputStream is = this.getResources().openRawResource(R.raw.testaki1);
                Erwthseis.LoadDatabase(is);

            } else if (K == 12) {
                InputStream is = this.getResources().openRawResource(R.raw.testaki2);
                Erwthseis.LoadDatabase(is);

            } else if (K == 13) {
                InputStream is = this.getResources().openRawResource(R.raw.testaki3);
                Erwthseis.LoadDatabase(is);

            } else if (K == 14) {
                InputStream is = this.getResources().openRawResource(R.raw.testaki4);
                Erwthseis.LoadDatabase(is);
                for (int i = 4; i < 8; i++) {
                    Orthothta.add("-");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        IMtest.setVisibility(View.INVISIBLE);               //η εικόνα είναι αόρατη. γίνεται οράτη μόνο οταν ο χρήστης είναι σε ερώτηση η οποία συνοδεύεται απο εικόνα

        grade=0.0;                                          //αρχικοποίηση βαθμού
        DoNext(grade);

    }

    @Override
    public void onClick(View v) {


        if (v == BBack) {                   // δίνεται η επιλογή να πάει στην προηγούμενη αναπάντητη ερώτηση
            Erwthseis.GoPrevUnAns();
            Erwthseis.setCurQuest(Erwthseis.getCurQuest() - 1);
            DoNext(grade);
        }
        if (v == BKatax) {                  // καταχωρεί την απάντηση του χρήστη και εισάγει την απάντηση του στην λίστα
            if (SelAns == -1)
                return;
            CurQ.setUserAns(SelAns);
            if (SelAns == (CurQ.getCorrectAns()-1)) {   // αν η απάντηση που έδωσε ο χρήστης είναι η σωστή τότε εμφανίζεται μήνυμα επιτυχίας
                Toast Tst = Toast.makeText(getApplicationContext(), "Μπράβο, η απάντηση είναι σωστή!", Toast.LENGTH_SHORT);
                Tst.show();
                Orthothta.add(CurQNum-1, "σωστή");     // προσθήκη στην σωστή θέση οτι η απάντηση που δώθηκε είναι σωστή
                    if(K==14){                                       // στη συνέχεια υπολογίζεται ο βαθμός, αν είναι στο διαγώνισμα ο χρήστης
                        if( (CurQNum-1)<=4 ){                        // η βαθμολογία είναι κλιμακούμενη για αυτό γίνονται οι ανάλογοι έλεγχοι
                            grade += 0.5;
                        }else if((CurQNum-1)==5){
                            grade += 1.5;
                        }else if((CurQNum-1)==6){
                            grade += 2.5;
                        }else if((CurQNum-1)==7){
                            grade += 3.5;
                        }
                    }else {                     // διαφορετικά αν είναι σε κάποιο άλλο τεστ , κάθε ερώτηση πιάνει 2.5 μονάδες
                        grade += 2.5;
                    }
            } else {                            // διαφορετικά εμφανίζεται μήνυμα
                Toast Tst = Toast.makeText(getApplicationContext(), "Συνέχισε την μελέτη και την προσπάθεια σου.", Toast.LENGTH_SHORT);
                Tst.show();
                Orthothta.add(CurQNum-1, "λάθος");
            }
            DoNext(grade);
        }
        if (v == BNext) {                   // προχωράει στην επόμενη ερώτηση
            DoNext(grade);
        }
        for (int i = 0; i < 4; i++) {
            if (v == TVAnswers[i])
                SelAns = i;
        }
        DoChangeAnswer();               // υπογραμμίζεται με έντονο χρώμα η απάντηση που επέλεξε ο χρήστης
    }


    void DoNext(double vathmos) {     // χρησιμοποιείται για να αποφασιστεί η επόμενη ενέργεια όταν απαντηθεί μια ερώτηση

        if (Erwthseis.GetNoUnAnsweredQuestions() == 0) {    // αν έχουν απαντηθεί όλες οι ερωτήσεις, στέλνει τα αποτελέσματα στην activity Apotelesmata και εκτυπώνεται στην οθόνη ο βαθμός μαζί με μία συμβουλή ανάλογη με τον βαθμό που πήρε.

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            Gson gson = new Gson();
            if(K==14)
                vathmos+=1;
            int count=0;
            String s="";
            for(int i=0; i<Erwthseis.GetNoAnsweredQuestions(); i++){    // Μετράει πόσες είναι οι σωστά απαντημένες ερωτήσεις
                if( (s=Orthothta.get(i) )== "σωστή")
                    count++;
            }
            int revcount=Erwthseis.GetNoAnsweredQuestions()-count;

            StringBuilder sb = new StringBuilder();
            if(vathmos!=0.0) {      // Αν δεν είναι όλες οι ερωτήσεις λάθος απαντημένες

                if (vathmos == 10.0) {  //Αν είναι όλες σωστα απαντημένες
                        sb.append("απαντήθηκαν όλες οι ερωτήσεις σωστά");
                } else {                //διαφορετικα διαλεγει ποιες ειναι οι σωστες
                    sb.append("οι σωστά απαντημένες ερωτήσεις είναι:");
                    for (int i = 0; i < Erwthseis.GetNoAnsweredQuestions(); i++) {
                        if (Orthothta.get(i) == "σωστή") {
                            sb.append(" " + String.valueOf(i + 1));
                            if (count == 1) {
                                break;
                            } else {
                                sb.append(",");
                            }
                            count--;
                        }
                    }
                }
            }
            if(vathmos!=10.0 && vathmos!=0.0)
            {
                sb.append(" και ");
            }
            if(vathmos!=10.0) {     // Αν δεν είναι όλες οι ερωτήσεις σωστά απαντημένες

                if (vathmos == 0.0) {   // Αν είναι ολες λάθος απαντημένες
                    sb.append("απαντήθηκαν όλες οι ερωτήσεις λανθασμένα");
                } else {                // Διαφορετικά διαλέγει ποιες είναι οι λάθος
                    sb.append(" οι λάθος απαντημένες ερωτήσεις είναι:");
                    for (int i = 0; i < Erwthseis.GetNoAnsweredQuestions(); i++) {
                        if (Orthothta.get(i) != "σωστή") {
                            sb.append(" " + String.valueOf(i + 1));
                            if (revcount == 1) {
                                break;
                            } else {
                                sb.append(",");
                            }
                            revcount--;
                        }
                    }
                }
            }
            sb.append(".");
            
            if (K == 11) {
                if(vathmos>=0.0 && vathmos<=4.9){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) + " Η μελέτη σου πρέπει να γίνει πιο συστηματική. Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. Συνέχισε την προσπάθεια σου. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr.", Toast.LENGTH_LONG);
                    Tst.show();
                }else if (vathmos>=5.0 && vathmos<=6.9 ){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) + " Καλή προσπάθεια!!! Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr ", Toast.LENGTH_LONG);
                    Tst.show();
                }else if ( vathmos>=7.0 && vathmos<=8.4){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +" Πολύ καλή προσπάθεια!!! Για ακόμα περισσότερες γνώσεις μπορείς να συμβουλευτείς το έξτρα υλικό που δίνεται. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr", Toast.LENGTH_LONG);
                    Tst.show();
                }else{
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι " + vathmos +" αφού " + String.valueOf(sb) +" ΣΥΓΧΑΡΗΤΗΡΙΑ!!! Συνέχισε με τον ίδιο τρόπο. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr", Toast.LENGTH_LONG);
                    Tst.show();
                }

                String Ortho1 = gson.toJson(Orthothta);
                editor.putString("Ortho1", Ortho1);
            }
            if (K == 12) {
                if(vathmos>=0.0 && vathmos<=4.9){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) + " Η μελέτη σου πρέπει να γίνει πιο συστηματική. Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. Συνέχισε την προσπάθεια σου. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr.", Toast.LENGTH_LONG);
                    Tst.show();
                }else if (vathmos>=5.0 && vathmos<=6.9 ){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +  " Καλή προσπάθεια!!! Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr ", Toast.LENGTH_LONG);
                    Tst.show();
                }else if ( vathmos>=7.0 && vathmos<=8.4){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +  " Πολύ καλή προσπάθεια!!! Για ακόμα περισσότερες γνώσεις μπορείς να συμβουλευτείς το έξτρα υλικό που δίνεται. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr", Toast.LENGTH_LONG);
                    Tst.show();
                }else{
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) + " ΣΥΓΧΑΡΗΤΗΡΙΑ!!! Συνέχισε με τον ίδιο τρόπο. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr", Toast.LENGTH_LONG);
                    Tst.show();
                }

                String Ortho2 = gson.toJson(Orthothta);
                editor.putString("Ortho2", Ortho2);
            }
            if (K == 13) {
                if(vathmos>=0.0 && vathmos<=4.9){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +  " Η μελέτη σου πρέπει να γίνει πιο συστηματική. Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. Συνέχισε την προσπάθεια σου. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr.", Toast.LENGTH_LONG);
                    Tst.show();
                }else if (vathmos>=5.0 && vathmos<=6.9 ){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +  " Καλή προσπάθεια!!! Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr ", Toast.LENGTH_LONG);
                    Tst.show();
                }else if ( vathmos>=7.0 && vathmos<=8.4){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +  " Πολύ καλή προσπάθεια!!! Για ακόμα περισσότερες γνώσεις μπορείς να συμβουλευτείς το έξτρα υλικό που δίνεται. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr", Toast.LENGTH_LONG);
                    Tst.show();
                }else{
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι " + vathmos +" αφού " + String.valueOf(sb) + " ΣΥΓΧΑΡΗΤΗΡΙΑ!!! Συνέχισε με τον ίδιο τρόπο. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr", Toast.LENGTH_LONG);
                    Tst.show();
                }


                String Ortho3 = gson.toJson(Orthothta);
                editor.putString("Ortho3", Ortho3);
            }
            if (K == 14) {

                if(vathmos>=0.0 && vathmos<=4.9){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) + " Η μελέτη σου πρέπει να γίνει πιο συστηματική. Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. Συνέχισε την προσπάθεια σου. Για ό,τι απορία προκύπτει μην διστάσεις να με επισκεφθείς στο γραφείο του Πανεπιστημίου Δευτέρα & Πέμπτη, ώρες 12:00-14:00 ή επικοινώνησε με email στο xristina.ariam@uniwa.gr.", Toast.LENGTH_LONG);
                    Tst.show();
                }else if (vathmos>=5.0 && vathmos<=6.9 ){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +  " Καλή προσπάθεια!!! Συμβουλεύσου το υλικό του μαθήματος  καθώς και το έξτρα υλικό που δίνεται. ", Toast.LENGTH_LONG);
                    Tst.show();
                }else if ( vathmos>=7.0 && vathmos<=8.4){
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι "+ vathmos +" αφού " + String.valueOf(sb) +  " Πολύ καλή προσπάθεια!!! Για ακόμα περισσότερες γνώσεις μπορείς να συμβουλευτείς το έξτρα υλικό που δίνεται. Καλή σταδιοδρομία! Παραμένω στην διάθεση σας για οποιαδήποτε διευκρίνιση xristina.ariam@uniwa.gr", Toast.LENGTH_LONG);
                    Tst.show();
                }else{
                    Toast Tst = Toast.makeText(getApplicationContext(), "Ο βαθμός σου είναι " + vathmos +" αφού " + String.valueOf(sb) + " ΣΥΓΧΑΡΗΤΗΡΙΑ!!! Έχεις κατανοήσει πλήρως την διδακτέα ύλη, συνέχισε με τον ίδιο τρόπο. Καλή σταδιοδρομία!", Toast.LENGTH_LONG);
                    Tst.show();
                }

                String Ortho4 = gson.toJson(Orthothta);
                editor.putString("Ortho4", Ortho4);
            }
            editor.apply();

            startActivity(intent);

            Orthothta.clear();
            Erwthseis.PrintAnswers();
            Erwthseis.clearInstance();
        }else{
            if(K==14) {                                                // αν ο χρήστης βρίσκεται στο διαγώνισμα
                if (CurQNum == 5) {                                    // και είναι στην 5η ερώτηση
                    IMtest.setImageResource(R.drawable.test_eikona_1); // ανάθεση της κατάλληλης εικόνας στο Image View
                    IMtest.setVisibility(View.VISIBLE);                // κάνει το Image View ορατό στον χρήστη
                } else if (CurQNum == 7) {                             // και είναι στην 7η ερώτηση
                    IMtest.setImageResource(R.drawable.test_eikona_2); // ανάθεση της κατάλληλης εικόνας στο Image View
                    IMtest.setVisibility(View.VISIBLE);                // κάνει το Image View ορατό στον χρήστη
                } else {
                    IMtest.setVisibility(View.INVISIBLE);              // διαφορετικά κάνει την εικόνα αόρατη ξανά
                }
            }
            CurQ = Erwthseis.GetQuestion();                            // εισάγει τα στοιχεία στην φόρμα της ερώτησης
            for (int i = 0; i < 4; i++) {
                TVAnswers[i].setEnabled(true);
            }

            TVQuestion.setText(CurQ.getQueText());
            for (int i = 0; i < CurQ.GetNoAnswers(); i++)
                TVAnswers[i].setText(CurQ.getAnswer(i));
            for (int i = CurQ.GetNoAnswers(); i < 4; i++) {
                TVAnswers[i].setEnabled(false);

                TVAnswers[i].setText("");
            }
            CurQNum = Erwthseis.GoNextUnAns();
        }
        SelAns = -1;
    }


    void DoChangeAnswer() {                 // υπογραμμίζει με έντονο χρώμα την απάντηση που επελέξε ο χρήστης
        int i;
        for (i = 0; i < 4; i++) {
            TVAnswers[i].setBackground(BackDraw);
            if (i == SelAns)
                TVAnswers[i].setBackgroundColor(Color.MAGENTA);
        }
    }
}