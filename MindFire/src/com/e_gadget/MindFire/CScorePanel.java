package com.e_gadget.MindFire;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CScorePanel extends Activity 
{
	Button.OnClickListener btnClick = new Button.OnClickListener() 
	{
        public void onClick(View v) 
        {
        	ClosePanel();
        }
    };	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_panel);
    
        Button button = (Button) findViewById(R.id.closebtn);
        button.setOnClickListener(btnClick);

        final TableLayout table = (TableLayout) findViewById(R.id.infotable);
        
        Context context = CScorePanel.this;
        Vector<CGameScore> scores = new Vector<CGameScore>();
        CScoreFactory.LoadScores(context, scores);
        int nCount = scores.size();
        if(0 < nCount)
        {
        	for(int i = 0; i < nCount; ++i)
        	{
        		postScore(table, scores.elementAt(i));
        	}
        }
        else
        {
        	postNoscoreNote(table);
        }
    }

    private void postScore(TableLayout table, CGameScore score)
    {
       	Context context = CScorePanel.this;
		Resources res = context.getResources();
    	String szPoint = score.GetPointText() + res.getText(R.string.pointlabel).toString();
    	
    	String szLastLable1 = res.getText(R.string.latestlabel).toString(); 
    	String szLastScore1 = score.GetLastScoreIntText(); 

    	String szLastLable2 = res.getText(R.string.latestlabel2).toString(); 
    	String szLastScore2 = score.GetLastScorePercentText() + "%"; 

    	String szHighLable = res.getText(R.string.highlabel).toString(); 
    	String szHighScore = score.GetHighScorePercentText() + "%"; 

    	String szAveLable = res.getText(R.string.avelabel).toString(); 
    	String szAveScore = score.GetAveScorePercentText() + "%"; 

    	String szPlayLable = res.getText(R.string.playlabel).toString(); 
    	String szPlayScore = score.GetPlayText(); 
    	
        addRecord(table, 
        		  szPoint, 
        	      szLastLable1, szLastScore1,
        	      szLastLable2, szLastScore2, 
        	      szHighLable, szHighScore,
        	      szAveLable, szAveScore,
        	      szPlayLable, szPlayScore); 
    }
    
    private void postNoscoreNote(TableLayout table)
    {
       	Context context = CScorePanel.this;
       	
		if(context != null)
		{
			Resources res = context.getResources();
            String strMsg = res.getText(R.string.noscore).toString();
            addSingleRow(table, strMsg);
		}            
    }
    
    private void addRecord(TableLayout table, 
    						String szPoint, 
    						String szLast, 
    						String szLastValue,
    						String szLastPer, 
    						String szLastPerValue,
    						String szHigh, 
    						String szHighValue,
    						String szAve, 
    						String szAveValue,
    						String szPlay, 
    						String szPlayValue) 
    {
        addSingleRow(table, szPoint);

        addTwoTextRow(table, szLast, szLastValue);
        addTwoTextRow(table, szLastPer, szLastPerValue);
        addTwoTextRow(table, szHigh, szHighValue);
        addTwoTextRow(table, szAve, szAveValue);
        addTwoTextRow(table, szPlay, szPlayValue);
        addSeparator(table);
    }

    private void addSingleRow(TableLayout table, String szText)
    {
        TableRow row = new TableRow(this);
        TextView label = new TextView(this);
        label.setText(szText);
        row.addView(label, new TableRow.LayoutParams(1));
        table.addView(row, new TableLayout.LayoutParams());
    }

    private void addTwoTextRow(TableLayout table, String szText1, String szText2)
    {
        TableRow row = new TableRow(this);
        TextView lable1 = new TextView(this);
        lable1.setText(szText1);
        lable1.setPadding(3, 3, 3, 3);

        TextView lable2 = new TextView(this);
        lable2.setText(szText2);
        lable2.setPadding(3, 3, 3, 3);
        lable2.setGravity(Gravity.RIGHT);
        
        row.addView(lable1, new TableRow.LayoutParams(1));
        row.addView(lable2, new TableRow.LayoutParams());
        table.addView(row, new TableLayout.LayoutParams());
    }

    private void addSeparator(TableLayout table)
    {
    	View line = new View(this);
    	line.setBackgroundColor(0xFF909090);
    	TableLayout.LayoutParams param = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 2);
        table.addView(line, param);
    }
    
    private void ClosePanel()
    {
    	CScorePanel.this.setResult(RESULT_OK);
    	CScorePanel.this.finish();
    }
}