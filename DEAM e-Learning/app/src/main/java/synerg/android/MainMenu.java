package synerg.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
/*
H activity MainActvity είναι πρώτη που βλέπει ο χρήστης και περιέχει το μενού με τις επιλογές που δίνονται στον χρήστη.
 */

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private Button BK1;
    private Button BK2;
    private Button BK3;
    private Button BTest1;
    private Button BTest2;
    private Button BTest3;
    private Button BDiag;
    private Button BLyseis;
    private Button BYliko;
    private Button Btypos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BK1 = findViewById(R.id.BK1);
        BK2 = findViewById(R.id.BK2);
        BK3 = findViewById(R.id.BK3);
        BTest1 = findViewById(R.id.BTest1);
        BTest2 = findViewById(R.id.BTest2);
        BTest3 = findViewById(R.id.BTest3);
        BDiag = findViewById(R.id.BDiag);
        BLyseis = findViewById(R.id.BLyseis);
        BYliko = findViewById(R.id.BYliko);
        Btypos = findViewById(R.id.Btypos);
        Btypos.setOnClickListener(this);
        BYliko.setOnClickListener(this);
        BK1.setOnClickListener(this);
        BK2.setOnClickListener(this);
        BK3.setOnClickListener(this);
        BTest1.setOnClickListener(this);
        BTest2.setOnClickListener(this);
        BTest3.setOnClickListener(this);
        BDiag.setOnClickListener(this);
        BLyseis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == BK1) {
            Intent intent = new Intent(getApplicationContext(), Thewria.class);
            intent.putExtra("kleidi", 1);
            startActivity(intent);
        }
        if (v == BK2) {
            Intent intent = new Intent(getApplicationContext(), Thewria.class);
            intent.putExtra("kleidi", 2);
            startActivity(intent);
        }
        if (v == BK3) {
            Intent intent = new Intent(getApplicationContext(), Thewria.class);
            intent.putExtra("kleidi", 3);
            startActivity(intent);
        }
        if (v == BTest1) {
            Intent intent = new Intent(getApplicationContext(), Testakia.class);
            intent.putExtra("kleidi", 11);
            startActivity(intent);
        }
        if (v == BTest2) {
            Intent intent = new Intent(getApplicationContext(), Testakia.class);
            intent.putExtra("kleidi", 12);
            startActivity(intent);
        }
        if (v == BTest3) {
            Intent intent = new Intent(getApplicationContext(), Testakia.class);
            intent.putExtra("kleidi", 13);
            startActivity(intent);
        }
        if (v==BDiag){
         Intent intent = new Intent (getApplicationContext(), Testakia.class);
             intent.putExtra("kleidi", 14);
             startActivity (intent);
         }
        if (v == BLyseis) {
            Intent intent = new Intent(getApplicationContext(), Apotelesmata.class);
            startActivity(intent);
        }
        if(v == BYliko){
            Intent intent = new Intent(getApplicationContext(), ExtraMenu.class);
            startActivity(intent);
        } if(v == Btypos){
            Intent intent = new Intent(getApplicationContext(), TyposMathiti.class);
            startActivity(intent);
        }
    }
}