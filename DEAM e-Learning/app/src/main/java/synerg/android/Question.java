package synerg.android;

import java.util.ArrayList;

public class Question
{
    private String QueText;
    private ArrayList<String> Answers;
    private int CorrectAns;
    private int UserAns;

    public Question ()
    {
        Answers = new ArrayList ();
        CorrectAns = -1;
        UserAns = -1;
    }

    public String getQueText ()
    {
        return QueText.toString();
    }

    public void setQueText (String queText)
    {
        QueText = queText;
    }

    public int getCorrectAns ()
    {
        return CorrectAns;
    }

    public void setCorrectAns (int correctAns)
    {
        CorrectAns = correctAns;
    }

    public int getUserAns ()
    {
        return UserAns;
    }

    public void setUserAns (int userAns)
    {
        UserAns = userAns;
    }

    public String getAnswer (int AnsNum)
    {
        return Answers.get (AnsNum).toString();
    }

    public void AddAnswer (String Ans)
    {
        Answers.add (Ans);
    }

    public boolean isAnswered ()
    {
        if (UserAns == -1)
            return false;
        else
            return true;
    }

    public boolean isCorrect ()
    {
        return UserAns == CorrectAns;
    }

    public int GetNoAnswers ()
    {
        return Answers.size ();
    }


}
