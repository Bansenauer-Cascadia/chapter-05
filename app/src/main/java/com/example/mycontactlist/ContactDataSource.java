package com.example.mycontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactDataSource {
	
	private SQLiteDatabase database;
	private ContactDBHelper dbHelper;

	public ContactDataSource(Context context) {
		dbHelper = new ContactDBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}


	// Listing 5.4 Insert and Upgrade Contact Methods (p97)
	// insertContact method
	public boolean insertContact(Contact c) 
	{
		boolean didSucceed = false;
		try {
			ContentValues initialValues = new ContentValues();

			initialValues.put("contactname", c.getContactName());
			initialValues.put("streetaddress", c.getStreetAddress());
			initialValues.put("city", c.getCity());    
			initialValues.put("state", c.getState());
			initialValues.put("zipcode", c.getZipCode());
			initialValues.put("phonenumber", c.getPhoneNumber());    
			initialValues.put("cellnumber", c.getCellNumber());    
			initialValues.put("email", c.getEMail());    
			initialValues.put("birthday", String.valueOf(c.getBirthday().toMillis(false)));

			didSucceed = database.insert("contact", null, initialValues) > 0;

		}
		catch (Exception e) {
			//Do Nothing -will return false if there is an exception
		}
		return didSucceed;
	}

	// updateContact method
	public boolean updateContact(Contact c)
	{
		boolean didSucceed = false;
		try {
			Long rowId = Long.valueOf(c.getContactID());
			ContentValues updateValues = new ContentValues();

			updateValues.put("contactname", c.getContactName());
			updateValues.put("streetaddress", c.getStreetAddress());
			updateValues.put("city", c.getCity());    
			updateValues.put("state", c.getState());
			updateValues.put("zipcode", c.getZipCode());
			updateValues.put("phonenumber", c.getPhoneNumber());    
			updateValues.put("cellnumber", c.getCellNumber());    
			updateValues.put("email", c.getEMail());    
			updateValues.put("birthday", String.valueOf(c.getBirthday().toMillis(false)));

			didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
		}
		catch (Exception e) {
			//Do Nothing -will return false if there is an exception
		}
		return didSucceed;
	}

	// Exercise 2 - updateAddress method
	public boolean updateAddress(ContactAddress ca)
	{
		boolean didSucceed = false;
		try {
			Long rowId = Long.valueOf(ca.getContactID());
			ContentValues updateValues = new ContentValues();

			updateValues.put("streetaddress", ca.getStreetAddress());
			updateValues.put("city", ca.getCity());
			updateValues.put("state", ca.getState());
			updateValues.put("zipcode", ca.getZipCode());

			didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
		}
		catch (Exception e) {
			//Do Nothing -will return false if there is an exception
		}
		return didSucceed;
	}

	// This code gets the new ID and sets the currentContact ContactID attribute to that value
	// to prevent adding another contact when user clicks Save button twice in a row (p104).
	public int getLastContactId() {
		int lastId = -1;
		try
		{
			// Get the maximum value for the _id field in the contact table
			String query = "Select MAX(_id) from contact";
			// A cursor is declared and assigned to hold the results of the execution of the query.
			// A cursor is an object that is used to hold and move through the results of a query.
			Cursor cursor = database.rawQuery(query, null);  

			cursor.moveToFirst();
			lastId = cursor.getInt(0);
			cursor.close();
		}
		catch (Exception e) {
			lastId = -1;
		}
		return lastId;
	}

}
