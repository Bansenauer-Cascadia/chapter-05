package com.example.mycontactlist;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.RelativeLayout;

public class ContactListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);

		initBGSetting();
	}

	private void initBGSetting()
	{
		String bgColor = getSharedPreferences("MyBackgroundColorPreferences", Context.MODE_PRIVATE).getString("bgcolor", "#F5F5DC");

		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.activity_contact_list);
		thisLayout.setBackgroundColor(Color.parseColor(bgColor));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

}
