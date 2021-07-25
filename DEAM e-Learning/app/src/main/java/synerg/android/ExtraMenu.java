package synerg.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*
Η activity Extra Menu περιέχει μία λίστα με επιλόγες που δίνονται στον χρήστη για να διαλέξει πως
επιθυμεί να συνεχίσει την μελέτη του.
 */

public class ExtraMenu extends AppCompatActivity implements View.OnClickListener {

    private Button BEpilogh0;
    private Button BEpilogh1;
    private Button BEpilogh2;
    private Button BEpilogh3;
    private Button BEpilogh4;
    private Button BArxMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_menu);

        BEpilogh0 = findViewById(R.id.BEpilogh0);
        BEpilogh1 = findViewById(R.id.BEpilogh1);
        BEpilogh2 = findViewById(R.id.BEpilogh2);
        BEpilogh3 = findViewById(R.id.BEpilogh3);
        BEpilogh4 = findViewById(R.id.BEpilogh4);
        BArxMenu = findViewById(R.id.BArxMenu);
        BEpilogh0.setOnClickListener(this);
        BEpilogh1.setOnClickListener(this);
        BEpilogh2.setOnClickListener(this);
        BEpilogh3.setOnClickListener(this);
        BEpilogh4.setOnClickListener(this);
        BArxMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {       // παρακάτω είναι τα κουμπιά που εμφανίζονται στην οθόνη του χρήστη

        /*αν επιλέξει μια από τις παρακάτω επιλογές τότε ανοίγει ο browser του χρήστη με το link  που αντιστοιχει στην επιλογή που έκανε */

        if (v == BEpilogh0) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=cZFPKQ30g1U"));
            startActivity(browserIntent);
        }
        if (v == BEpilogh1) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/embed/YueJukoFBMU"));
            startActivity(browserIntent);
        }
        if (v == BEpilogh2) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.math.nsc.ru/LBRT/k5/MML-2015(english)/lec1.pdf"));
            startActivity(browserIntent);
        }
        if (v == BEpilogh3) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.opengov.gr/ypoian/?p=4975"));
            startActivity(browserIntent);
        }
        if (v == BEpilogh4) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.or.psu.edu/files/2020/04/OR-diagram-e1586897929559.jpg"));
            startActivity(browserIntent);
        }
        if (v == BArxMenu) {            // αν δεν θέλει κάποια από τις παραπανω επιλογές μπορεί να επιστρέψει στο αρχικό μενού
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }
    }
}