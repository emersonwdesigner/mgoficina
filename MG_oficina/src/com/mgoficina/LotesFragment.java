package com.mgoficina;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
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

public class LotesFragment extends SherlockListFragment{
    
	TextView trab, icon, valor, txt;
	Typeface font;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		
		final DataBaseHandler db = new DataBaseHandler(getSherlockActivity());
		final Funcoes funcoes = new Funcoes();
		 
		String[] from = new String[] {DataBaseHandler.KEY_LOTE_ID, DataBaseHandler.KEY_LOTE_DATA, DataBaseHandler.KEY_LOTE_VALOR, DataBaseHandler.KEY_LOTE_ID};
		font = Typeface.createFromAsset(getActivity().getBaseContext().getAssets(), "fonts/Roboto-Light.ttf");
        // Ids of views in listview_layout
        int[] to = { R.id.txt,R.id.cur, R.id.valorL, R.id.keys };        
        
        Cursor cursor = db.getAllLotes();
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        //SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), R.layout.listview_layout, cursor, from, to);       
		
		//setListAdapter(adapter);
		
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.listview_lote, cursor, from, to);
		setListAdapter(adapter);
		adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
			
			   /** Binds the Cursor column defined by the specified index to the specified view */
			   public boolean setViewValue(View view, Cursor cursor, int columnIndex){
			       if(view.getId() == R.id.keys){
			    	   //Log.v("aviso",cursor.getString(columnIndex));
			    	   icon = (TextView) view.findViewById(R.id.keys);
			    	   String conta = String.valueOf(db.getTrabalhosOpcao(cursor.getString(columnIndex), 2).size());
			    	   String total="";
			    	   if(conta.equals("1")){
			    		total = getString(R.string.item);  
			    	   }else{
			    		total = getString(R.string.itens);     
			    	   }
			    	   icon.setText(" "+conta+" "+ total);
			           
			           return true; //true because the data was bound to the view
			       } 
			       if(view.getId() == R.id.valorL){
			    	   valor = (TextView) view.findViewById(R.id.valorL);
			    	   String moeda = funcoes.moeda(cursor.getDouble(columnIndex));
			    	   valor.setText(moeda);
			    	   return true;
			       }else if(view.getId() == R.id.txt){
			    	   txt = (TextView) view.findViewById(R.id.txt);
			    	   txt.setTypeface(font);
			       } 
			       return false;
			   }
			});			
		
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
					TextView param = (TextView) arg1.findViewById(R.id.txt);
					String var1 = param.getText().toString();
					//Toast.makeText( getActivity().getBaseContext()  , "Long Clicked " + var1 , Toast.LENGTH_SHORT).show();
					 
					
					Intent intent = new Intent(getActivity(), ListaTrabalhosActivity.class);
					
					intent.putExtra("STATUS", var1);
					intent.putExtra("TIPO", "lote");
	                startActivity(intent);
				}
	        };
	 
	        getListView().setOnItemClickListener(listener);
	 
	    }

}

