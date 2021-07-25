package synerg.android;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
Η activity Thewria χρησιμοποιείται για την ανάγνωση της θεωρίας από αρχεία .txt και τοποθετούνται
εικόνες όπου είναι απαραίτητες.
 */

public class Thewria extends AppCompatActivity implements View.OnClickListener {
    private Button BTest2;
    private Button Bpisw;
    private int K;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thewria);

        TextView TVTitlosKef2 = findViewById(R.id.TVTitlosKef2);
        TextView TVTheoria2 = findViewById(R.id.TVTheoria2);
        BTest2 = findViewById(R.id.BTest2);
        Bpisw = findViewById(R.id.Bpisw);
        Bpisw.setOnClickListener(this);

        BTest2.setOnClickListener(v -> {
            Intent intent = getIntent();
            int K = intent.getIntExtra("kleidi", 0);// Χρησιμοποιούμε το Κ για να αναγνωρίσουμε ποιο κεφάλαιο θέλει να αναγνώσει ο χρήστης

            if (K == 1){
                Intent myintent2 = new Intent(getApplicationContext(), Testakia.class);
                myintent2.putExtra("kleidi",11);
                startActivity(myintent2);
            }else if (K == 2) {
                Intent myintent2 = new Intent(getApplicationContext(), Testakia.class);
                myintent2.putExtra("kleidi", 12);
                startActivity(myintent2);
            }else if (K == 3) {
                Intent myintent2 = new Intent(getApplicationContext(), Testakia.class);
                myintent2.putExtra("kleidi", 13);
                startActivity(myintent2);
            }
        });

        Intent intent = getIntent();
        int K = intent.getIntExtra("kleidi", 0);// Χρησιμοποιούμε το Κ για να αναγνωρίσουμε ποιο κεφάλαιο θέλει να αναγνώσει ο χρήστης

        if (K == 1) {                                                         //Ελέγχουμε για θέλει το 1ο κεφάλαιο
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            InputStream is = this.getResources().openRawResource(R.raw.theoria1a);
            ssb.append("\n");
            try {                                                             // στη συνέχεια περνάμε την θεωρία από το αρχείο σε spannable string builder διότι θα θα βάλουμε και εικόνα
                Iterable<String> ms;
                ms = loadFromTxt(is);
                for (String s : ms) {
                    ssb.append(s);
                    ssb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ssb.delete(ssb.length()-1,ssb.length());

            ssb.setSpan(                                                        // εισαγωγή εικόνας στο spannable string builder
                    new ImageSpan(getApplicationContext(), R.drawable.eikona_1),
                    ssb.length()-1, ssb.length(), 0);

            ssb.append("\n");
            is = this.getResources().openRawResource(R.raw.theoria1b);           // συνεχίζουμε με την υπόλοιπη θεωρία απο το 2ο αρχείο
            try {
                Iterable<String> ms;
                ms = loadFromTxt(is);
                for (String s : ms) {
                    ssb.append(s);
                    ssb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            TVTitlosKef2.setText("Κεφάλαιο 1ο: Εφοδιαστική Αλυσίδα - Logistics");   // τίτλος κεφαλαίου
            TVTheoria2.setText(ssb, TextView.BufferType.SPANNABLE);                 // η θεωρία με την εικόνα στο textview

        }
        if (K == 2) {                                                               //Ελέγχουμε για θέλει το 2ο κεφάλαιο

            SpannableStringBuilder ssb = new SpannableStringBuilder();
            InputStream is = this.getResources().openRawResource(R.raw.theoria2a);
            ssb.append("\n");
            try {                                                                   // στη συνέχεια περνάμε την θεωρία από το αρχείο σε spannable string builder διότι θα θα βάλουμε και εικόνες
                Iterable<String> ms;
                ms = loadFromTxt(is);
                for (String s : ms) {
                    ssb.append(s);
                    ssb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ssb.delete(ssb.length()-1,ssb.length());

            ssb.setSpan(                                                            // εισαγωγή εικόνας στο spannable string builder
                    new ImageSpan(getApplicationContext(), R.drawable.test_eikona_1),
                    ssb.length()-1, ssb.length(), 0);
            ssb.append("\n");
            is = this.getResources().openRawResource(R.raw.theoria2b);
            try {                                                                   // συνεχίζουμε με την υπόλοιπη θεωρία απο το 2ο αρχείο
                Iterable<String> ms;
                ms = loadFromTxt(is);
                for (String s : ms) {
                    ssb.append(s);
                    ssb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ssb.delete(ssb.length()-1,ssb.length());

            ssb.setSpan(                                                            // εισαγωγή εικόνας στο spannable string builder
                    new ImageSpan(getApplicationContext(), R.drawable.eikona_4),
                    ssb.length()-1, ssb.length(), 0);
            ssb.append("\n");
            is = this.getResources().openRawResource(R.raw.theoria2c);
            try {                                                                   // συνεχίζουμε με την υπόλοιπη θεωρία απο το 3ο αρχείο
                Iterable<String> ms;
                ms = loadFromTxt(is);
                for (String s : ms) {
                    ssb.append(s);
                    ssb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            TVTitlosKef2.setText("Κεφάλαιο 2ο: Κλάδοι Μαθηματικών");                // τίτλος κεφαλαίου
            TVTheoria2.setText(ssb, TextView.BufferType.SPANNABLE);                 // η θεωρία με τις εικόνες στο textview

        }
        if (K == 3) {                                                               //Ελέγχουμε για θέλει το 3ο κεφάλαιο

            SpannableStringBuilder ssb = new SpannableStringBuilder();
            InputStream is = this.getResources().openRawResource(R.raw.theoria3);
            ssb.append("\n");
            try {                                                                   // στη συνέχεια περνάμε την θεωρία από το αρχείο σε spannable string builder
                Iterable<String> ms;
                ms = loadFromTxt(is);
                for (String s : ms) {
                    ssb.append(s);
                    ssb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            TVTitlosKef2.setText("Κεφάλαιο 3ο: Σύνδεση των κλάδων των μαθηματικών με την εφοδιαστική αλυσίδα"); // τίτλος κεφαλαίου
            TVTheoria2.setText(ssb, TextView.BufferType.SPANNABLE);                 // η θεωρία στο textview
        }
    }

    @Override
    public void onClick(View v) {

        if( v == Bpisw){                                                            // διαφορετικά μπορεί να πάει πίσω στο αρχικό μενού και να συνεχίσει όπως επιθυμεί
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }

    }

    private Iterable<String> loadFromTxt(InputStream is) throws IOException {       // χρησιμοποιείται για να πάρουμε το κείμενο απο το αρχείο .txt
        String string = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                    stringBuilder.append(string);
                    stringBuilder.append("\n");
                if ((string = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        is.close();

        return () ->
                new Scanner(stringBuilder.toString()).useDelimiter("\n");
    }
}