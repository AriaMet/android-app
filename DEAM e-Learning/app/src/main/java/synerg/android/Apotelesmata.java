package synerg.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;

/*
Η activity Apotelesmata συγκεντρώνει τα αποτελέσματα των τεστ και του διαγωνίσματος και τυπώνει
στην οθόνη της συσκευής του χρήστη τον μέσο όρο των βαθμών. Ακόμη τυπώνει σε μία λίστα τις ερωτήσεις
αναλόγως αν ήταν σωστές ή λάθος καθώς και τον βαθμό που κατάφερε στο τεστ.
 */

public class Apotelesmata extends AppCompatActivity implements View.OnClickListener {

    private TextView TVTelikosVathmos;
    private TextView TVAnalysh;
    private Button BArxh;
    private int i = 0;
    private int k =0;
    private double res=0.0;
    private String c;
    private String key2 = "Ortho";
    private Boolean[] examDone = new Boolean[4];
    private double[] mo= new double[4];
    private double average=0.0;
    private final String right = "σωστή";   // χρησιμοποιείται για να αποφασίστει αν μια ερώτηση ήταν σωστή ή λάθος
    private ArrayList<String> List2 = new ArrayList<>();
    private ArrayList<String> Lista = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apotelesmata);

        TVTelikosVathmos = findViewById(R.id.TVTelikosVathmos);
        TVAnalysh = findViewById(R.id.TVAnalysh);
        BArxh = findViewById(R.id.BArxh);
        BArxh.setOnClickListener(this);

        Arrays.fill(examDone, Boolean.FALSE);      // Αρχικοποίηση του πίνακα με false

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        Type type = new TypeToken<java.util.List<String>>() {
        }.getType();

        for(int i=0; i<23; i++){        // Αρχικοποίηση της λίστας
            List2.add(i,"λάθος");
        }
        for(int i=0; i<4; i++){         // Αρχικοποίηση του πίνακα με 0.0
            mo[i]=0.0;
        }

        TVAnalysh.setText("Δεν έχεις κάνει κάποιο τεστ!\n");
        TVTelikosVathmos.setText("0.0/10");

        for (int i = 0; i <4 ; i++) {
            c = String.valueOf(i + 1);
            examDone[i] = checkSharedPreferencesEmpty( key2 + c, sharedPref);       //Βάζει true αν έχει γίνει το αντίστοιχο τεστ ή false αν δεν έχει γίνει
            switch (i){
                case 0:
                    if (!examDone[i]) {
                        populateLists(gson, type, sharedPref,  key2 + c, "\nΚεφάλαιο " + c + "ο: \n", 0, 4, 4);
                        mo[i] =  calculatelists(gson, type, sharedPref, key2 + c, 0, 4);
                    }
                    else
                        TVAnalysh.setText("Δεν έχεις κάνει το τεστ του 1ου κεφαλαίου \n");
                    break;
                case 1:
                    if (!examDone[i]) {
                        populateLists(gson, type, sharedPref,  key2 + c, "\nΚεφάλαιο " + c + "ο: \n", 5, 9, 4);
                        mo[i] = calculatelists(gson, type, sharedPref, key2 + c, 5, 4);
                    }
                    else
                        TVAnalysh.append("\nΔεν έχεις κάνει το τεστ του 2ου κεφαλαίου \n");
                    break;
                case 2:
                    if (!examDone[i]) {
                        populateLists(gson, type, sharedPref,  key2 + c, "\nΚεφάλαιο " + c + "ο: \n", 10, 14, 4);
                        mo[i] = calculatelists(gson, type, sharedPref, key2 + c, 10, 4);
                    }
                    else
                        TVAnalysh.append("\nΔεν έχεις κάνει το τεστ του 3ου κεφαλαίου \n");
                    break;
                case 3:
                    if (!examDone[i]) {
                        populateLists(gson, type, sharedPref, key2 + c, "\nΤελικό Τεστ: \n", 15, 23, 8);
                        mo[i] = calculatelists(gson, type, sharedPref, key2 + c, 15, 8);
                    }
                    else
                        TVAnalysh.append("\nΔεν έχεις κάνει το τελικό τεστ\n");
                    break;
                default:
                    break;
            }
        }

        for(int k=0; k<4; k++){
            average+= mo[k];        // Το άθροισμα των βαθμών από τα τεστ
        }

        res= average*0.25d;         //Υπολογισμός μέσου όρου

        TVTelikosVathmos.setText("Ο μέσος όρος είναι: " + String.valueOf(df2.format(res)));
    }

    /*
    Παρακάτω γίνεται έλεγχος στις λίστες
    Αν επιστρέψει false,τότε οι λίστες δεν είναι κενές
     */
    private boolean checkSharedPreferencesEmpty( String key2, SharedPreferences sharedPreferences) {
        return  sharedPreferences.getString(key2, "").equals("");
    }

    /*
    Παρακάτω γίνεται εισαγωγή του κειμένου που θα προβληθεί στο textview
     */
    private void populateLists(Gson gson, Type type, SharedPreferences sharedPref, String key2, String kefalaio, int start, int finish, int ccMax) {
        ArrayList<String> ex2 = gson.fromJson(sharedPref.getString(key2, ""), type);
        double grade=0.0;

        Lista.add(kefalaio);
        int p=0;
        for (i = start; i < finish; i++) {
            List2.add(i,ex2.get(p));
            Lista.add("Η ερώτηση " + String.valueOf(p+1) + " είναι " + List2.get(i) + "\n");
            p++;
        }

        grade=calculatelists(gson,type,sharedPref,key2, start,ccMax);
        Lista.add("Ο βαθμός σου είναι: " + grade + "/10 \n");

        ex2.clear();

        StringBuilder s = new StringBuilder();
        for (i = 0; i < Lista.size(); i++) {
            s.append(Lista.get(i));
        }
        TVAnalysh.setText(s.toString());
    }

    /*
    Παρακάτω υπολογίζεται ο βαθμός
     */
    private double calculatelists(Gson gson, Type type, SharedPreferences sharedPref, String key2, int start,int ccMax){
        ArrayList<String> ex2 = gson.fromJson(sharedPref.getString(key2, ""), type);
        float score=0.0f;

        if (start==15) {
            for (int j = 0; j < ccMax; j++) {
                if ( j <= 4) {
                    if (ex2.get(j).equals(right)) {
                       score += 0.5;
                    }
                } else if (j == 5) {
                    if (ex2.get(j).equals(right)) {
                        score += 1.5;
                    }
                } else if (j == 6) {
                    if (ex2.get(j).equals(right)) {
                        score += 2.5;
                    }
                } else if (j == 7) {
                    if (ex2.get(j).equals(right)) {
                        score += 3.5;
                    }
                }
            }
        }else{
            for(int j=0; j<ccMax; j++){
                if (ex2.get(j).equals(right)) {
                    score += 2.5;
                }
            }
        }
         ex2.clear();
        return score;

    }

    /*
     Για να εμφανίζει μόνο 2 δεκαδικά ψηφία
    */
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    public void onClick(View v) {

        if (v == BArxh) {
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }
    }
}