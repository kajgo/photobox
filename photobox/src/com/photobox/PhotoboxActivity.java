package com.photobox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhotoboxActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TextView tv = (TextView) findViewById(R.id.textOutputField);
        final EditText et = (EditText) findViewById(R.id.nameInputField);
        final Button b = (Button) findViewById(R.id.nameInputButton);
        b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				tv.setText(et.getText());
			}
		});
    }
}