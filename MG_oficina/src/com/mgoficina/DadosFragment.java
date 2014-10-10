package com.mgoficina;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.actionbarsherlock.app.SherlockListFragment;
import com.mgoficina.R;


@SuppressLint("ResourceAsColor")
public class DadosFragment extends SherlockListFragment{
    

    // Array of strings storing country names
String[] countries = new String[] {
        "Novos",
        "Sincronização das OS",
        "Sincronização dos lotes",
        "Sincronização do perfil",
        "Sincronização dos clientes"
       
};    

// Array of integers points to images stored in /res/drawable/
int[] flags = new int[]{
                R.drawable.ic_action_download,
                R.drawable.ic_action_about,
                R.drawable.ic_action_add_to_queue,
                R.drawable.ic_action_person,
                R.drawable.ic_action_group
                    
};

// Array of strings to store currencies
String[] currency = new String[]{
        "Últimos inseridos",
        "Ordens não sincronizados com o servidor",
        "Lotes não sincronizados com o servidor",
        "Dados do perfil no servidor",
        "Clientes no servidor"
};
TextView keys, txt;
Typeface font;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {        
    	final DataBaseHandler db = new DataBaseHandler(getSherlockActivity()); 
    	
    	font = Typeface.createFromAsset(getActivity().getBaseContext().getAssets(), "fonts/Roboto-Light.ttf");
            // Each row in the list stores country name, currency and flag
    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();        
    
    for(int i=0;i<countries.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
        hm.put("txt", countries[i]);
        hm.put("cur", currency[i]);
        hm.put("flag", Integer.toString(flags[i]) );            
        aList.add(hm);        
    }
    
    // Keys used in Hashmap
    String[] from = { "flag","txt","cur","txt" };
    
    // Ids of views in listview_layout
    int[] to = { R.id.flag,R.id.txt,R.id.cur, R.id.keys};        
    
    // Instantiating an adapter to store each items
    // R.layout.listview_layout defines the layout of each item
    SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.listview_layout, from, to);       
            
            setListAdapter(adapter);
           
            
            adapter.setViewBinder(new SimpleAdapter.ViewBinder(){
    			
            	
 			   public boolean setViewValue(View view, Object data, String textRepresentation){
 			       if(view.getId() == R.id.keys){
 			    	  if(textRepresentation.equals("Novos")){ 
 			    	   keys = (TextView) view.findViewById(R.id.keys);
 			    	  int totalEntrada = db.getAllEntrada().getCount(); 
 			    	  String conta = String.valueOf(totalEntrada);
 			    	  
 			    	 String total="";
			    	   if(conta.equals("1")){
			    		total = getString(R.string.item);  
			    	   }else{
			    		total = getString(R.string.itens);     
			    	   }
			    	   keys.setText(" "+conta+" "+ total);
 			    	  }else if(textRepresentation.equals("Sincronização das OS")){ 
 	 			    	   keys = (TextView) view.findViewById(R.id.keys);
 	 			    	  int totalEntrada = db.getAlldados().getCount(); 
 	 			    	  String conta = String.valueOf(totalEntrada);
 	 			    	  
 	 			    	 String total="";
 	 			    	if(conta.equals("0")){
 	 			    		keys.setText(getString(R.string.sincronizado));
 	 			    		keys.setTextColor(getResources().getColor(R.color.branco));
 	 			    		keys.setBackgroundColor(getResources().getColor(R.color.verde));
 	 			    	}else{
 				    	   if(conta.equals("1")){
 				    		total = getString(R.string.item);  
 				    	   }else{
 				    		total = getString(R.string.itens);     
 				    	   }
 				    	   keys.setText(" "+conta+" "+ total);
 				    	  keys.setTextColor(getResources().getColor(R.color.opaque_red));
 	 			    	  }
 			    	  }
 			    	 else if(textRepresentation.equals("Sincronização dos lotes")){ 
	 			    	   keys = (TextView) view.findViewById(R.id.keys);
	 			    	  int totalEntrada = db.getLotesExporta().size(); 
	 			    	  String conta = String.valueOf(totalEntrada);
	 			    	  
	 			    	 String total="";
	 			    	if(conta.equals("0")){
	 			    		keys.setText(getString(R.string.sincronizado));
	 			    		keys.setTextColor(getResources().getColor(R.color.branco));
	 			    		keys.setBackgroundColor(getResources().getColor(R.color.verde));
	 			    	}else{
				    	   if(conta.equals("1")){
				    		total = getString(R.string.item); 
				    		
				    	   }else{
				    		total = getString(R.string.itens);  
				    		
				    	   }
				    	   keys.setTextColor(getResources().getColor(R.color.opaque_red));
				    	   keys.setText(" "+conta+" "+ total);
	 			    	  }
			    	  }else if(textRepresentation.equals("Sincronização do perfil")){ 
	 			    	   keys = (TextView) view.findViewById(R.id.keys);
	 			    	  int totalEntrada = db.getUserExporta(0,2).size(); 
	 			    	  String conta = String.valueOf(totalEntrada);
	 			    	  
	 			    	if(conta.equals("0")){
	 			    		keys.setText(getString(R.string.sincronizado));
	 			    		keys.setTextColor(getResources().getColor(R.color.branco));
	 			    		keys.setBackgroundColor(getResources().getColor(R.color.verde));
	 			    	}else{
	 			    		keys.setText(getString(R.string.nao_sincronizados));
	 			    		keys.setTextColor(getResources().getColor(R.color.branco));
	 			    		keys.setBackgroundColor(getResources().getColor(R.color.opaque_red));
	 			    	  }
			    	  }
			    	  else if(textRepresentation.equals("Sincronização dos clientes")){ 
	 			    	   keys = (TextView) view.findViewById(R.id.keys);
	 			    	  int totalEntrada = db.getClientesExporta().size(); 
	 			    	  String conta = String.valueOf(totalEntrada);
	 			    	  
	 			    	if(conta.equals("0")){
	 			    		keys.setText(getString(R.string.sincronizado));
	 			    		keys.setTextColor(getResources().getColor(R.color.branco));
	 			    		keys.setBackgroundColor(getResources().getColor(R.color.verde));
	 			    	}else{
	 			    		keys.setText(getString(R.string.nao_sincronizados));
	 			    		keys.setTextColor(getResources().getColor(R.color.branco));
	 			    		keys.setBackgroundColor(getResources().getColor(R.color.opaque_red));
	 			    	  }
			    	  }
 			    	  return true;
 			       } else if(view.getId() == R.id.txt){
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
	        final Funcoes funcoes = new Funcoes();
	        
	        OnItemClickListener listener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
					
					//String var1 = param.getText().toString();
					//Toast.makeText( getActivity().getBaseContext()  , String.valueOf(arg2), Toast.LENGTH_SHORT).show();
					 
	                if(arg2 == 0){
	                	 Intent it = new Intent(getActivity(),ListaTrabalhosActivity.class);
		            	it.putExtra("TIPO", "entrada");
		            	it.putExtra("STATUS", "1");
		                startActivity(it); 	
	                }else if(arg2 == 1){
	                	 Intent it = new Intent(getActivity(),ListaTrabalhosActivity.class);
		            	it.putExtra("TIPO", "dados");
		            	it.putExtra("STATUS", "1");
		                startActivity(it); 	
	                }else if(arg2 == 2 || arg2 == 3 || arg2 == 4){
	                	TextView param = (TextView) arg1.findViewById(R.id.keys);
	                	String var1 = param.getText().toString();
	                	if(!var1.equals(getString(R.string.sincronizado))){
	                		funcoes.avisaSemConexao(getSherlockActivity(), getString(R.string.aviso), getString(R.string.voce_deve_sinc));
	                	}
	                	
	                }
	               
	                
	                
	                
	                
	                
				}
	        };
	 
	        getListView().setOnItemClickListener(listener);
	 
	    }
	 

}

