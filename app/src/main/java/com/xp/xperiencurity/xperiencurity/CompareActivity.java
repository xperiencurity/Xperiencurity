package com.xp.xperiencurity.xperiencurity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CompareActivity extends Activity {

    private EditText mEditText1 = null;
    private EditText mEditText2 = null;
    private Button mCompareButton = null;
    private Button mClearButton1 = null;
    private Button mClearButton2 = null;
    private TextView mResultTV = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        mEditText1 = (EditText) findViewById(R.id.edit_txt1);
        mClearButton1 = (Button) findViewById(R.id.ClearButton1);
        mEditText2 = (EditText) findViewById(R.id.edit_txt2);
        mClearButton2 = (Button) findViewById(R.id.ClearButton2);
        mCompareButton = (Button) findViewById(R.id.CompareButton);
        mResultTV = (TextView) findViewById(R.id.label_result);

        mCompareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on clicks
                Editable InputEdit1 = mEditText1.getText();
                String sInputText1 = InputEdit1.toString();
                Editable InputEdit2 = mEditText2.getText();
                String sInputText2 = InputEdit2.toString();
                // if sInputText1 or sInputText2 is not null or empty
                if ((sInputText1 != "" || sInputText1 != null) && (sInputText2 != "" || sInputText2 != null)) {
                    String sText = "";
                    int IsIdentical = sInputText1
                            .compareToIgnoreCase(sInputText2);
                    if (IsIdentical == 0) {
                        if (sInputText1.equals("") || sInputText2.equals("")) {
                            sText = "Either one or both the fields are empty!";
                            mResultTV.setTextColor(Color.YELLOW);
                        } else {
                            sText = getString(R.string.IdenticalHashes);
                            mResultTV.setTextColor(Color.GREEN);
                        }
                    } else {
                        sText = getString(R.string.DifferentHashes);
                        mResultTV.setTextColor(Color.RED);
                    }
                    if (mResultTV != null)
                        mResultTV.setText(sText);
                }
            }
        });

        mClearButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on clicks
                mEditText1.setText("");
                mResultTV.setText("");
            }
        });

        mClearButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on clicks
                mEditText2.setText("");
                mResultTV.setText("");
            }
        });
    }
}