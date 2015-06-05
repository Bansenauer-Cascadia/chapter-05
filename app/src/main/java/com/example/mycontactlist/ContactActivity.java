package com.example.mycontactlist;

import com.example.mycontactlist.DatePickerDialog.SaveDateListener;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ContactActivity extends FragmentActivity implements SaveDateListener {
	
	private Contact currentContact;
	private ImageButton list;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        initChangeDateButton();

		// 1. If the text changes, the listener executes the code to set the attribute that holds
		// the code in the currentContact object
        initTextChangedEvents();
		// SaveButton method
        initSaveButton();
        
        setForEditing(false);
		// Step2: Associate the currentContact variable with a new Contact object (p99)
        currentContact = new Contact();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_settings:
				return list.performClick();
			default:
				return super.onOptionsItemSelected(item);
		}

	}
    
	private void initListButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(ContactActivity.this, ContactListActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
            }
        });
	}
	
	private void initMapButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonMap);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(ContactActivity.this, ContactMapActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
            }
        });
	}
	private void initSettingsButton() {
        list = (ImageButton) findViewById(R.id.imageButtonSettings);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(ContactActivity.this, ContactSettingsActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
            }
        });
	}
	
	private void initToggleButton() {
		final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
		editToggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setForEditing(editToggle.isChecked());				
			}

		});
	}

	// Save button code (p102)
	private void initSaveButton() {
		Button saveButton = (Button) findViewById(R.id.buttonSave);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideKeyboard();
				// A new ContactDataSource object is instantiated.
				ContactDataSource ds = new ContactDataSource(ContactActivity.this);
				// Open database
				ds.open();
				
				boolean wasSuccessful;
				// currentContactID is -1, that means this is a new contact
				if (currentContact.getContactID()==-1) {
					wasSuccessful = ds.insertContact(currentContact);
					// (p105) Clicking Save button twice in a row doesn't create duplicate contacts
					// The first line uses the retrieval method in ContactDataSource.java to get
					// the newly inserted contact's ID.
					// The second line sets the currentContact object's ID to the retrieved value.
					int newId = ds.getLastContactId();
					currentContact.setContactID(newId);
				}
				// If contact already exists
				else {
					wasSuccessful = ds.updateContact(currentContact);
				}
				// Close database
				ds.close();
				// Check the return value. If successful, the ToggleButton is toggled to viewing mode.
				// if unsuccessful, the activity remains in editing mode.
				if (wasSuccessful) {
					ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
		    		editToggle.toggle();
					setForEditing(false);
				}
			}
		});
	}

	// 2. initTextChangeEvents method
	private void initTextChangedEvents(){
		final EditText contactName = (EditText) findViewById(R.id.editName);
		contactName.addTextChangedListener(new TextWatcher() {

			//This is a required method for the TextWatcher OBJECT.
			// This is the event that this app uses to capture the data the user entered.
			public void afterTextChanged(Editable s) {
				currentContact.setContactName(contactName.getText().toString());
			}
			//This is a required method for the TextWatcher METHOD.
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				//  Auto-generated method stub

			}
			//This is a required method for the TextWatcher METHOD.
			// This method is executed after each and every character change in an EditText.
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//  Auto-generated method stub		
			}
		});

		final EditText streetAddress = (EditText) findViewById(R.id.editAddress);
		streetAddress.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setStreetAddress(streetAddress.getText().toString());
			}
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				//  Auto-generated method stub

			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//  Auto-generated method stub		
			}
		});

		final EditText city = (EditText) findViewById(R.id.editCity);
		city.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setCity(city.getText().toString());
			}
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				//  Auto-generated method stub

			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//  Auto-generated method stub		
			}
		});

		final EditText state = (EditText) findViewById(R.id.editState);
		state.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setState(state.getText().toString());
			}
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				//  Auto-generated method stub

			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//  Auto-generated method stub		
			}
		});

		final EditText zipcode = (EditText) findViewById(R.id.editZipcode);
		zipcode.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setZipCode(zipcode.getText().toString());
			}
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				//  Auto-generated method stub

			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//  Auto-generated method stub		
			}
		});

		final EditText phoneNumber = (EditText) findViewById(R.id.editHome);
		phoneNumber.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setPhoneNumber(phoneNumber.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}
		});

		final EditText cellNumber = (EditText) findViewById(R.id.editCell);
		cellNumber.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setCellNumber(cellNumber.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}
		});

		final EditText eMail = (EditText) findViewById(R.id.editEMail);
		eMail.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setEMail(eMail.getText().toString());
			}
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				//  Auto-generated method stub

			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//  Auto-generated method stub		
			}
		});

		// These auto-formats the number as it's typed in phone# and cell EditText.
		// This code adds a listener to the phopne number EditTexts that calls the
		// 	PhoneNumberFormattingTextWatcher object, which in turn adds the appropriate
		// 		formatting as the user types.
		phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		cellNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
	}
	
	private void setForEditing(boolean enabled) {
		EditText editName = (EditText) findViewById(R.id.editName);
		EditText editAddress = (EditText) findViewById(R.id.editAddress);
		EditText editCity = (EditText) findViewById(R.id.editCity);
		EditText editState = (EditText) findViewById(R.id.editState);
		EditText editZipCode = (EditText) findViewById(R.id.editZipcode);
		EditText editPhone = (EditText) findViewById(R.id.editHome);
		EditText editCell = (EditText) findViewById(R.id.editCell);
		EditText editEmail = (EditText) findViewById(R.id.editEMail);
		Button buttonChange = (Button) findViewById(R.id.btnBirthday);
		Button buttonSave = (Button) findViewById(R.id.buttonSave);

		editName.setEnabled(enabled);
		editAddress.setEnabled(enabled);
		editCity.setEnabled(enabled);
		editState.setEnabled(enabled);
		editZipCode.setEnabled(enabled);
		editPhone.setEnabled(enabled);
		editCell.setEnabled(enabled);
		editEmail.setEnabled(enabled);
		buttonChange.setEnabled(enabled);
		buttonSave.setEnabled(enabled);

		// Change the ScrollView's focus to the top of the screen when switching to
		// Viewing mode(p103)
		if (enabled) {
			editName.requestFocus();
		}
		else {
			ScrollView s = (ScrollView) findViewById(R.id.scrollView1);
			s.fullScroll(ScrollView.FOCUS_UP);
			s.clearFocus();
		}
		
	}

	private void initChangeDateButton() {
		Button changeDate = (Button) findViewById(R.id.btnBirthday);
		changeDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		    	FragmentManager fm = getSupportFragmentManager();
		        DatePickerDialog datePickerDialog = new DatePickerDialog();
		        datePickerDialog.show(fm, "DatePick");
			}			
		});
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		EditText editName = (EditText) findViewById(R.id.editName);
		imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
		EditText editAddress = (EditText) findViewById(R.id.editAddress);
		imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
		EditText editCity = (EditText) findViewById(R.id.editCity);
		imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);
		EditText editState = (EditText) findViewById(R.id.editState);
		imm.hideSoftInputFromWindow(editState.getWindowToken(), 0);
		EditText editZipCode = (EditText) findViewById(R.id.editZipcode);
		imm.hideSoftInputFromWindow(editZipCode.getWindowToken(), 0);
		EditText editPhone = (EditText) findViewById(R.id.editHome);
		imm.hideSoftInputFromWindow(editPhone.getWindowToken(), 0);
		EditText editCell = (EditText) findViewById(R.id.editCell);
		imm.hideSoftInputFromWindow(editCell.getWindowToken(), 0);
		EditText editEmail = (EditText) findViewById(R.id.editEMail);
		imm.hideSoftInputFromWindow(editEmail.getWindowToken(), 0);
	}


	@Override
	public void didFinishDatePickerDialog(Time selectedTime) {
		TextView birthDay = (TextView) findViewById(R.id.textBirthday);
		birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime.toMillis(false)).toString());

		// Step3: Store the selected birthday in the Contact object (p99)
		// This code uses the Contact class's setBirthday method to assign the data selected
		// in the custom dialog to the currentContact object.
		currentContact.setBirthday(selectedTime);
	}
    
}
