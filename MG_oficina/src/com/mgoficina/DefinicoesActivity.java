package com.mgoficina;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListActivity;

public class DefinicoesActivity extends SherlockListActivity{
    

@Override
public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);

 //setListAdapter(new ArrayAdapter<String>(this, R.layout.list_mobile,
 //                R.id.label, MOBILE_OS));
 
 

}

@Override
protected void onListItemClick(ListView l, View v, int position, long id) {

 //get selected items
 String selectedValue = (String) getListAdapter().getItem(position);
 Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

}
	
	 
}

