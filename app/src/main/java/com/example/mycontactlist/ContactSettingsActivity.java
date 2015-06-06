package com.example.mycontactlist;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ContactSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_settings);
		
		initSettings();
		initSortByClick();
		initSortOrderClick();
        initListButton();
		initMapButton();
		initSettingsButton();



      final RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout);

        final RadioButton rblavendar = (RadioButton) findViewById(R.id.radiolavendar);
        final RadioButton rbgold = (RadioButton) findViewById(R.id.radiogold);
        final RadioButton rbpink = (RadioButton) findViewById(R.id.radiopink);



        rblavendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setBackgroundResource(R.color.Yellow);
            }
        });


        rbgold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setBackgroundResource(R.color.Green);
            }
        });


        rbpink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setBackgroundResource(R.color.Cyan);
            }
        });






        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                SharedPreferences prefs = getSharedPreferences("color",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                String colourSelected = "";
                switch (checkedId) {
                    case R.id.radiolavendar :
                        colourSelected = "YELLOW";
                        editor.putString("color", colourSelected);
                        editor.commit();
                        break;
                    case R.id.radiogold :
                        colourSelected = "GREEN";
                        editor.putString("color", colourSelected);
                        editor.commit();
                        break;

                    case R.id.radiopink :
                        colourSelected = "CYAN";
                        editor.putString("color", colourSelected);
                        editor.commit();
                        break;
                }
            }
        });
        SharedPreferences prefs = getSharedPreferences("color", MODE_PRIVATE);
        String color = prefs.getString("color", "WHITE");

        if(color.equals("YELLOW")){
            rl.setBackgroundColor(Color.YELLOW);
        }
        else if(color.equals("GREEN")){
            rl.setBackgroundColor(Color.GREEN);
        }
        else if(color.equals("CYAN")){
            rl.setBackgroundColor(Color.CYAN);
        }
        //else{
          //  rl.setBackgroundColor(Color.RED);
        //}

        //  ScrollView.setBackgroundResource(R.color.choice);

       // initColorChoiceByClick();

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_settings, menu);
		return true;
	}

	private void initSettings() {
		String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield", "contactname");
		String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
      //  String colorchoice = getSharedPreferences("color", Context.MODE_PRIVATE).getString("choice", "lavendar");


		RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
		RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
		RadioButton rbBirthDay = (RadioButton) findViewById(R.id.radioBirthday);
		if (sortBy.equalsIgnoreCase("contactname")) {
			rbName.setChecked(true);
		}
		else if (sortBy.equalsIgnoreCase("city")) {
			rbCity.setChecked(true);
		}
		else {
			rbBirthDay.setChecked(true);
		}			

		RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
		RadioButton rbDescending = (RadioButton) findViewById(R.id.radioDescending);
		if (sortOrder.equalsIgnoreCase("ASC")) {
			rbAscending.setChecked(true);
		}
		else {
			rbDescending.setChecked(true);
		}




    }
	
	private void initSortByClick() {
		RadioGroup rgSortBy = (RadioGroup) findViewById(R.id.radioGroup1);
		rgSortBy.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
				RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
//				RadioButton rbBirthDay = (RadioButton) findViewById(R.id.radioBirthdate);
				if (rbName.isChecked()) {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "contactname").commit();
				}
				else if (rbCity.isChecked()) {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "city").commit();
				}
				else {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "birthday").commit();
				}			
			}		
		});
	}



    /*private void initColorChoiceByClick() {
        RadioGroup rgColorChoice = (RadioGroup) findViewById(R.id.radioGroup);
        rgColorChoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rblavendar = (RadioButton) findViewById(R.id.radiolavendar);
                RadioButton rbgold = (RadioButton) findViewById(R.id.radiogold);
//				RadioButton rbpink = (RadioButton) findViewById(R.id.radiopink);
                if (rblavendar.isChecked()) {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("choice", "lavendar").commit();
                }
                else if (rbgold.isChecked()) {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("choice", "gold").commit();
                }
                else {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("choice", "pink").commit();
                }
            }
        });
    }

*/


	private void initSortOrderClick() {
		RadioGroup rgSortOrder = (RadioGroup) findViewById(R.id.radioGroup2);
		rgSortOrder.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
//				RadioButton rbDescending = (RadioButton) findViewById(R.id.radioDescending);
				if (rbAscending.isChecked()) {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").commit();
				}
				else {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").commit();
				}			
			}		
		});
	}




	private void initListButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(ContactSettingsActivity.this, ContactListActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
   			startActivity(intent);
            }
        });
	}

	private void initSettingsButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonSettings);
        list.setEnabled(false);
	}

	private void initMapButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonMap);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		Intent intent = new Intent(ContactSettingsActivity.this, ContactMapActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		   		startActivity(intent);
            }
        });
	}

	
}
