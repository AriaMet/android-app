package synerg.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
/*
Χρησιμοποιούμε την Questionnaire class για διαβάσουμε τις ερωτήσεις από τα αρχεία .txt και να τις
μετατρέψουμε σε μία "λίστα" ερώτησεων. Επιπλέον, περιέχονται κάποιες χρήσιμες function που κάνουν
την υλοποίηση των τεστ πιο ομαλή.
 */

public class Questionnaire extends Activity
{
    private final ArrayList<Question> questions = new ArrayList<>();
    private int CurQuest;
    private static Questionnaire Instance = null;
    private static Context Cont;

    public static Questionnaire GetInstance (Context Co) throws IOException {
        Cont = Co;
        if (Instance == null)
            Instance = new Questionnaire ();
        return Instance;
    }
    public void clearInstance()
    {
        Instance = null;
    }

    public int getCurQuest ()
    {
        return CurQuest;
    }

    public void setCurQuest (int curQuest)
    {
        CurQuest = curQuest;
    }


    private Questionnaire() throws IOException {
        CurQuest = 0 ;
    }

    public void LoadDatabase (InputStream is) throws IOException {
        ArrayList<String> myList = new ArrayList<>();
        String string = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                stringBuilder.append(string).append("\n");
                if ((string = reader.readLine()) == null) break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }
        is.close();
        Iterable<String> sc = () ->
                new Scanner(stringBuilder.toString()).useDelimiter("\n");
        for (String line: sc) {
            myList.add(line);
        }
        int NoQ = 0,NoA = 0,index = 0;
        NoQ = Integer.parseInt(myList.get(index));


        while (index < myList.size() - 1){
            Question q = new Question ();

            index++;
            q.setQueText (myList.get(index));

            try {
                index++;
                NoA = Integer.parseInt(myList.get(index));

                index++;
                q.setCorrectAns(Integer.parseInt(myList.get(index)));
            }catch (NumberFormatException e){
                Log.e(e.toString(),index + myList.get(index));
            }

            index++;
            for (int i = 0; i < NoA; i++){
                q.AddAnswer(myList.get(index + i));
            }

            index += (NoA - 1);

            questions.add(q);


        }


    }


    public int GetNoQuestions ()
    {
       return questions.size ();

    }
    /*public int QuestionNum(Question q )
    {
        int i;
        for( i=0; i< GetNoQuestions(); i++){
            if(q.getQueText() == questions.get(i).getQueText())
            {
                return i;
            }
        }
    }*/

    public int GetNoAnsweredQuestions ()
    {
        int Count;
        int i;
        for (Count = 0, i = 0; i < GetNoQuestions (); i++)
            if (GetQuestion (i).getUserAns () != -1)
                Count ++;
        return Count;
    }

    public int GetNoUnAnsweredQuestions ()
    {
        int Count;
        int i;
        for (Count = 0, i = 0; i < GetNoQuestions (); i++)
            if (GetQuestion (i).getUserAns () == -1)
                Count ++;
        return Count;
    }

    public Question GetQuestion (int QN)
    {
        return questions.get (QN);
    }

    public Question GetQuestion ()
    {
        return questions.get (CurQuest);
    }

    public int GoNext ()
    {
        CurQuest++;
        if (CurQuest == GetNoQuestions ())
            CurQuest = 0;
        return CurQuest;
    }

    public int GoPrevious ()
    {
        CurQuest--;
        if (CurQuest == -1)
            CurQuest = GetNoQuestions () - 1;
        return CurQuest;
    }

    public int GoNextUnAns ()
    {
        if (GetNoUnAnsweredQuestions () == 0)
            return -1;
        do
        {
            CurQuest++;
            if (CurQuest == GetNoQuestions ())
                CurQuest = 0;
        }
        while (GetQuestion (CurQuest).isAnswered ());
        return CurQuest;
    }

    public int GoPrevUnAns ()
    {
        if (GetNoUnAnsweredQuestions () == 0)
            return -1;
        do
        {
            CurQuest--;
            if (CurQuest == -1 )
                CurQuest = GetNoQuestions () - 1;
        }
        while (GetQuestion (CurQuest).isAnswered ());
        return CurQuest;
    }

    public void PrintAll ()
    {
        int i, j;
        System.out.println ("***HERE!!!!!!!");
        for (i = 0; i < GetNoQuestions (); i++)
        {
            Question Q = GetQuestion (i);
            System.out.println ("***" + "Question : " + (i + 1) + " " + Q.getQueText ());
            System.out.println ("***" + "Number of Answers : " + Q.GetNoAnswers ());
            System.out.println ("***" + "Correct Answer     : " + Q.getCorrectAns ());
            for (j = 0; j < Q.GetNoAnswers (); j++)
            {
                System.out.println ("***" + "Answer : "  + Q.getAnswer (j));
            }
        }

    }

    public void PrintAnswers ()
    {
        int i;
        for (i = 0; i < GetNoQuestions (); i++)
            System.out.println ("*** Question Number : " + (i + 1) + "Answer : " + GetQuestion (i).getUserAns ());
    }



}

