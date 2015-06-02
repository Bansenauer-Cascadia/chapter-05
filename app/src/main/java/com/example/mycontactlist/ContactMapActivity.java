package com.example.mycontactlist;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.RelativeLayout;

public class ContactMapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_map);

		initBGSetting();
	}

	private void initBGSetting()
	{
		String bgColor = getSharedPreferences("MyBackgroundColorPreferences", Context.MODE_PRIVATE).getString("bgcolor", "#F5F5DC");

		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.activity_contact_map);
		thisLayout.setBackgroundColor(Color.parseColor(bgColor));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_map, menu);
		return true;
	}

}
