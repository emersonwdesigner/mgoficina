package com.mgoficina;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.mgoficina.R;

public class MainActivity extends SherlockFragmentActivity {
	DataBaseHandler db = new DataBaseHandler(this);
	Funcoes funcoes = new Funcoes();
		final Context context = this;
        private ActionBar actionBar;
        private ViewPager viewPager;
        ProgressBar carrega;

        @SuppressWarnings("deprecation")
		@Override
        protected void onCreate(Bundle savedInstanceState) {
        	
        	//ver se tem excluídos para tirar do server
        	if(verificaConexao()){
        		int excluidos = db.getLotesDeletar().size();
        		//Log.v("aviso", "Tem excliudos"+ String.valueOf(excluidos));
        		if(excluidos > 0){
        			
        		startService(new Intent("MGO_DELETA"));
        	}
        	}
        	
        	
        	// ver se tem user
        	int user 		= db.contaHome(3);
    		
    		if(user == 0){
    			
    			startActivity(new Intent(MainActivity.this, BemVindoActivity.class));
    		}else{
    			if(db.VerSenhaVazia()==1){
    				final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
    	    		 alertDialog.setTitle(getString(R.string.aviso));
    	    	  	 alertDialog.setButton(getString(R.string.definir_senha), new DialogInterface.OnClickListener() {
    	    	  	 public void onClick(final DialogInterface dialog, final int which) {
    	    	  		startActivity(new Intent(MainActivity.this, PerfilActivity.class));
    	    	  	 }
    	    	  	 });
    	    	  	alertDialog.setMessage(getString(R.string.deve_ter_senha));
    	            alertDialog.setIcon(R.drawable.ic_alert);
    	       	 	alertDialog.show();
    			}
    		}
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                
                
                viewPager = (ViewPager) findViewById(R.id.pager);
                viewPager.setOnPageChangeListener(onPageChangeListener);
                viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
                addActionBarTabs();

                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
                actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.servico)+"</b></font>"));
                getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#CCD3D6\"><b>"+getString(R.string.app_name)+"</b></font>"));
                
                
                
                
        }
        
        public int contagem(){
int soma = 
        	db.getAllEntrada().getCount()+
        	db.getAlldados().getCount()+
        	db.getLotesExporta().size()+
        	db.getUserExporta(0,2).size()+
        	db.getClientesExporta().size();
        	
        	return soma;
        }
        /* Função para verificar existência de conexão com a internet
    	 */
    	public  boolean verificaConexao() {
    	    boolean conectado;
    		ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	    if (conectivtyManager.getActiveNetworkInfo() != null
    	            && conectivtyManager.getActiveNetworkInfo().isAvailable()
    	            && conectivtyManager.getActiveNetworkInfo().isConnected()) {
    	    	conectado = true;
    	    } else {
    	        conectado = false;
    	    }
    	    return conectado;
    	}
    	
    	// avisa que está sem conexão
    	
    	@SuppressWarnings("deprecation")
		private void avisaSemConexao(){
    		final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
    		 alertDialog.setTitle(getString(R.string.aviso));
    	  	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    	  	 public void onClick(final DialogInterface dialog, final int which) {

    	  		startActivity(new Intent(MainActivity.this, MainActivity.class));
    	  	 }
    	  	 });
    	  	alertDialog.setMessage(getString(R.string.sem_conexao));
            alertDialog.setIcon(R.drawable.ic_alert);
       	 	alertDialog.show();
    	}
        
        private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        actionBar.setSelectedNavigationItem(position);
                }
        };

        private void addActionBarTabs() {
                actionBar = getSupportActionBar();
                int dados = contagem();
                ActionBar.Tab tab = actionBar.newTab().setIcon(R.drawable.ic_action_storage).setTabListener(tabListener);
            	actionBar.addTab(tab);
                	ActionBar.Tab tab3;
                	
                	if(dados > 0){
                		 tab3 = actionBar.newTab().setText(String.valueOf(dados)).setIcon(R.drawable.ic_action_import_export).setTabListener(tabListener);
                	}else{
                		 tab3 = actionBar.newTab().setIcon(R.drawable.ic_action_import_export).setTabListener(tabListener);
                	}
                	actionBar.addTab(tab3);
                    ActionBar.Tab tab4 = actionBar.newTab().setIcon(R.drawable.ic_action_add_to_queue).setTabListener(tabListener);
                	actionBar.addTab(tab4);
                
                
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }

        private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                        switch (tab.getPosition()) {
                        case 0:
                        	actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.servico)+"</b></font>"));
                        	getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#CCD3D6\"><b>"+getString(R.string.app_name)+"</b></font>"));
                        	break;
                      
                        case 1:
                        	actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.dados)+"</b></font>"));
                        	
                        	
                        	break;
                    	case 2:
                    		actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.lotes)+"</b></font>"));
                    		getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#CCD3D6\"><b>"+getString(R.string.app_name)+"</b></font>"));
                        }
                        
                        viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                }
        };
        
        @Override
               
        public boolean onCreateOptionsMenu(Menu menu) {
        	SubMenu busca = menu.addSubMenu("refresh").setIcon(R.drawable.ic_action_refresh);
        	
        	SubMenu acao = menu.addSubMenu("").setIcon(R.drawable.ic_action_settings);
        	acao.add(0, 1, 0, R.string.action_add).setIcon(R.drawable.ic_action_edit);
        	acao.add(0, 3, 0, R.string.fechar_lote).setIcon(R.drawable.ic_action_add_to_queue);
            acao.add(0, 4, 0, R.string.sincronizar).setIcon(R.drawable.ic_action_import_export);
            
            SubMenu sub = menu.addSubMenu("").setIcon(R.drawable.ic_action_overflow);
            sub.add(0, 2, 0, R.string.perfil).setIcon(R.drawable.ic_action_person);
            sub.add(0, 10, 0, R.string.clientes).setIcon(R.drawable.ic_action_group);
            sub.add(0, 7, 0, "Busca").setIcon(R.drawable.ic_action_search);
            sub.add(0, 6, 0, R.string.ver_servidor).setIcon(R.drawable.ic_action_cloud);
            sub.add(0, 9, 0, R.string.enviar_link).setIcon(R.drawable.ic_action_send_now);
            sub.add(0, 8, 0, R.string.sobre).setIcon(R.drawable.ic_action_about);
            
            sub.add(0, 5, 0, R.string.action_sair).setIcon(R.drawable.ic_action_return_from_full_screen);
            
            busca.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            acao.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            
            
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        	if(item.getTitle().equals("refresh")){
        		Intent it = new Intent(MainActivity.this,MainActivity.class);
        		startActivity(it);
        	}
        	
        	
        	switch (item.getItemId()) {
            case 0:
            	return false;
            case 1:
            	Intent it = new Intent(MainActivity.this,NovaOsActivity.class);
        		startActivity(it);
            	return true;
            case 2:
            	startActivity(new Intent(MainActivity.this, PerfilActivity.class));
            	return true;
            case 3:
            	Intent its = new Intent(MainActivity.this,FechaLoteActivity.class);
        		startActivity(its);
            	return true;
            	
            	
        	case 4:
        		if(verificaConexao()){
        		digSenha();} 
        		else{
        		avisaSemConexao();	
        		}
        		
 		        	break;
        	case 5:	
        	DialogInterface.OnClickListener dialogClickListene = new DialogInterface.OnClickListener() {
     		    @Override
     		    public void onClick(DialogInterface dialog, int which) {
     		        switch (which){
     		        case DialogInterface.BUTTON_POSITIVE:
     		        	Intent startMain = new Intent(Intent.ACTION_MAIN); 
     		        	startMain.addCategory(Intent.CATEGORY_HOME); 
     		        	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
     		        	startActivity(startMain); 
     		        	finish();    		            
     		        	
                   	 dialog.dismiss();
     		            break;

     		        case DialogInterface.BUTTON_NEGATIVE:
     		            //No button clicked
     		            break;
     		            
     		       
     		        }
     		    }
     		};
     	 
     	 AlertDialog.Builder builde = new AlertDialog.Builder(context);
     	 builde.setMessage(getString(R.string.aviso_sair)).setPositiveButton(getString(R.string.sim), dialogClickListene)
     	     .setNegativeButton(getString(R.string.nao), dialogClickListene).show();
        	
     	return true;
        	case 6:
	        		site();
	        		//startActivity(new Intent(MainActivity.this,ServidorActivity.class));
	 		break;
        	case 7:
        		Intent it1 = new Intent(MainActivity.this,FormBuscaActivity.class);
        		startActivity(it1);
	 		break;
        	case 8:
        		startActivity(new Intent(MainActivity.this,SobreActivity.class));
	 		break;
	 		
        	case 9:
        		UUID uuid = UUID.randomUUID();  
        		String myRandom = uuid.toString();  
        		
        		Intent email = new Intent(Intent.ACTION_SEND);
        		//email.putExtra(Intent.EXTRA_EMAIL, new String[]{"emersonwdesigner@bol.com.br"});		  
        		email.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.link_servidor) +" ("+myRandom.substring(0,6)+")");
        		email.putExtra(Intent.EXTRA_TEXT, "http://appoficina.atwebpages.com/dados?codigo="+db.getCodigo());
        		email.setType("message/rfc822");
        		startActivity(Intent.createChooser(email, getString(R.string.modo_envio)));
	 		break;
        	case 10:
        		startActivity(new Intent(MainActivity.this,ClientesActivity.class));
	 		break;
    	}
        	
        	        	           
           
            return true;
        }
        
       public void adicionar(View v){
    	   Intent it = new Intent(MainActivity.this,NovaOsActivity.class);
   		startActivity(it);
       }
        public void onBackPressed() {
        	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
     		    @Override
     		    public void onClick(DialogInterface dialog, int which) {
     		        switch (which){
     		        case DialogInterface.BUTTON_POSITIVE:
     		        	/**
     		        	MainActivity.this.finish();
     		            System.exit(0);
                   	 dialog.dismiss();
     		        	**/
     		        	
     		        	Intent startMain = new Intent(Intent.ACTION_MAIN); 
     		        	startMain.addCategory(Intent.CATEGORY_HOME); 
     		        	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
     		        	startActivity(startMain); 
     		        	finish();
     		            break;

     		        case DialogInterface.BUTTON_NEGATIVE:
     		            //No button clicked
     		            break;
     		        }
     		    }
     		};
     	 
     	 AlertDialog.Builder builder = new AlertDialog.Builder(context);
     	 builder.setMessage(getString(R.string.aviso_sair)).setPositiveButton(getString(R.string.sim), dialogClickListener)
     	     .setNegativeButton(getString(R.string.nao), dialogClickListener).show();
        }
        
        
        private void digSenha(){
        	LayoutInflater li = LayoutInflater.from(context);
    		final View promptsView = li.inflate(R.layout.popusenha, null);

    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

    		// set prompts.xml to alertdialog builder
    		alertDialogBuilder.setView(promptsView);

    		final EditText Info_local 	= (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
    		final TextView Info_cod 	= (TextView) promptsView.findViewById(R.id.textViewCod);
    		carrega = (ProgressBar) promptsView.findViewById(R.id.progresso);
    		
    		Info_cod.setText(db.getCodigo());
    		
    		
    		// set dialog message
    		alertDialogBuilder.setTitle(getString(R.string.seguranca)).setIcon(R.drawable.ic_action_secure)
    			.setCancelable(false)
    			.setPositiveButton("OK",
    			  new DialogInterface.OnClickListener() {

    				public void onClick(DialogInterface dialog,int id) {
    					int verSenha = db.VerSenha(Info_cod.getText().toString(), Info_local.getText().toString());    	
    			    	if(verSenha == 0 ){
    			    		Toast.makeText(MainActivity.this, getString(R.string.senha_errada) , Toast.LENGTH_SHORT).show();
    		    		}else{
    		    			startService(new Intent("MGO_INICIAR_EXPORTACAO"));
    		    		}
    			    	
    			    }
    			  })
    			.setNegativeButton("Cancelar",
    			  new DialogInterface.OnClickListener() {
    			    public void onClick(DialogInterface dialog,int id) {
    				dialog.cancel();
    			    }
    			  });

    		// create alert dialog
    		AlertDialog alertDialog = alertDialogBuilder.create();

    		// show it
    		alertDialog.show();
        }
        HttpClient client = new DefaultHttpClient();
        
        
        public void esqueciSenha(View v){
        	
        	if(verificaConexao()){
        		//Log.v("RESPONSE","envia senha");
        		
        		carrega.setVisibility(View.VISIBLE);
        		 new Thread(new Runnable() {
        	         @Override
        	         public void run() {
        		try {   
        		
        		//Log.v("RESPONSE","envia senha2");
        		final String seuEmail = db.getEmail();
                	
                	//envia código para email
            		final HttpPost post = new HttpPost("http://fabianrepresentacoes.com.br/esqueci_senha.php");
            		List<NameValuePair> pairs = new ArrayList<NameValuePair>();	
            	    pairs.add(new BasicNameValuePair("email", seuEmail));
            	    pairs.add(new BasicNameValuePair("senha", db.getSenha()));
            	    
            	    
            		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(pairs);
                    post.setEntity(ent);
                    HttpResponse responsePOST = client.execute(post);  
                    
                    String responseBody = EntityUtils.toString(responsePOST.getEntity());
                    
                    //Log.v("RESPONSE","Resposta"+responseBody);
                    if(responseBody.contains("NNNNNNNNNN")){
                	
                	//Log.v("RESPONSE","não adicionou o code para o bd");
                	runOnUiThread(new Runnable() {
                        public void run() {
                        	carrega.setVisibility(View.GONE);
                        	Toast.makeText(getBaseContext(), getString(R.string.erro_enviar_email)+ seuEmail, Toast.LENGTH_LONG).show();                	
                       	                }
                    });
                	
                }else{
                	
                	runOnUiThread(new Runnable() {
                        public void run() {
                        	carrega.setVisibility(View.GONE);
                        	Toast.makeText(getBaseContext(), getString(R.string.foi_enviado_senha_email)+ seuEmail, Toast.LENGTH_LONG).show();
                        	
                       	                }
                    });
                	
                }
        	} catch (Exception e) {
        		// TODO: handle exception
        	}
        	         }}).start();
        	}else{
        		avisaSemConexao();
        	}
        }
        
        public void site(){
        	Uri uri = Uri.parse("http://appoficina.atwebpages.com/dados?codigo="+db.getCodigo());
        	 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        	 startActivity(intent);
        }
}