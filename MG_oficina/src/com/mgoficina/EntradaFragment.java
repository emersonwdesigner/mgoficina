package com.mgoficina;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.actionbarsherlock.app.SherlockListFragment;
import com.mgoficina.R;


public class EntradaFragment extends SherlockListFragment{
    
	TextView trab;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		
		DataBaseHandler db = new DataBaseHandler(getSherlockActivity());
		 
      
		
		String[] from = new String[] {DataBaseHandler.KEY_OS, DataBaseHandler.KEY_DESCRICAO, DataBaseHandler.KEY_ID};
        
        // Ids of views in listview_layout
        int[] to = { R.id.txt,R.id.cur,  R.id.keyStatus};        
        
        Cursor cursor = db.getAllEntrada();
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        //SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), R.layout.listview_layout, cursor, from, to);       
		
		//setListAdapter(adapter);
		
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.listview_entrada, cursor, from, to);
		setListAdapter(adapter);
				
		
		return super.onCreateView(inflater, container, savedInstanceState);	
		
	}
	
	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onActivityCreated(savedInstanceState);
	 
	        
	        OnItemClickListener listener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					TextView param = (TextView) arg1.findViewById(R.id.keyStatus);
					String var1 = param.getText().toString();
					//Toast.makeText( getActivity().getBaseContext()  , "Long Clicked " + var1 , Toast.LENGTH_SHORT).show();
					 
	                
	                Intent it = new Intent(getActivity(),SingleActivity.class);
	            	it.putExtra("ID", var1);
	                startActivity(it); 
				}
	        };
	 
	        getListView().setOnItemClickListener(listener);
	 
	    }

}

