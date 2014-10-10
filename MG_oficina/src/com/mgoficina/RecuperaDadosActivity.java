package com.mgoficina;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.actionbarsherlock.app.SherlockActivity;
import com.mgoficina.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RecuperaDadosActivity extends SherlockActivity {
	LinearLayout jaTem; 
	Button jaTenho, enviaCodig, enviaCodigo;
	EditText email, digCodigo;
	TextView avisar;
	ProgressBar carrega, carregaCode;
	HttpClient client = new DefaultHttpClient();
	final DataBaseHandler db = new DataBaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recupera_dados);
		//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.azul_escuro));
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.title_activity_recupera_dados)+"</b></font>"));
		
		jaTem 		= (LinearLayout) findViewById(R.id.jaCodigo);
		jaTenho 	= (Button) findViewById(R.id.jaTem);
		enviaCodig 	= (Button) findViewById(R.id.enviaCodig);
		enviaCodigo 	= (Button) findViewById(R.id.enviaCodigo);
		email 		= (EditText) findViewById(R.id.editEmail);
		digCodigo 	= (EditText) findViewById(R.id.digCodigo);
		carrega 	= (ProgressBar) findViewById(R.id.carrega);
		carregaCode 	= (ProgressBar) findViewById(R.id.carregaCode);
		avisar 		= (TextView) findViewById(R.id.aviso);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	Handler handler;
		int aviso = 0;
		
@SuppressWarnings("deprecation")
public void enviaCodigo(final View v){
	 final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
	 alertDialog.setTitle(getString(R.string.aviso));
   	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
   	 public void onClick(final DialogInterface dialog, final int which) {
   	 // here you can add functions
   	 }
   	 });
	 carrega.setVisibility(View.VISIBLE);
	 enviaCodig.setVisibility(View.GONE);
	 new Thread(new Runnable() {
         @Override
         public void run() {
	try {   
		Thread.sleep(1000);
		
		
		//verifica se o email existe no bd
		
		final HttpPost post0 = new HttpPost("http://appoficina.atwebpages.com/verifica_email.php");
		List<NameValuePair> pairs0 = new ArrayList<NameValuePair>();	
	    pairs0.add(new BasicNameValuePair("email", email.getText().toString()));
		UrlEncodedFormEntity ent0 = new UrlEncodedFormEntity(pairs0);
        post0.setEntity(ent0);
        HttpResponse responsePOST0 = client.execute(post0);  
        
        String responseBody0 = EntityUtils.toString(responsePOST0.getEntity());
        Log.v("RESPONSE","Resposta"+responseBody0);
        if(responseBody0.contains("NNNNNNNNNN")){
        	Log.v("RESPONSE","email nao existe");
        	aviso = 1;
        	runOnUiThread(new Runnable() {
                public void run() {
                
                	carrega.setVisibility(View.GONE);
               	    enviaCodig.setVisibility(View.VISIBLE);
               	    alertDialog.setMessage(getString(R.string.houve_erro_email));
                    alertDialog.setIcon(R.drawable.ic_alert);
               	 	alertDialog.show(); 
                	
               	                }
            });
        	 
        }else{
		
		
		//envia código para email
		final HttpPost post = new HttpPost("http://fabianrepresentacoes.com.br/email.php");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();	
	    pairs.add(new BasicNameValuePair("email", email.getText().toString()));
		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(pairs);
        post.setEntity(ent);
        HttpResponse responsePOST = client.execute(post);  
        
        String responseBody = EntityUtils.toString(responsePOST.getEntity());
        //Log.v("RESPONSE","Resposta"+responseBody);
        if(responseBody.contains("NNNNNNNNNN")){
        	Log.v("RESPONSE","nao enviou");
        	aviso = 2;
        	
        	        	
        }else{
        	Log.v("RESPONSE",responseBody.replace(" ", ""));
            //envia código para bd
        	final HttpPost post2 = new HttpPost("http://appoficina.atwebpages.com/cria_codigo.php");
    		List<NameValuePair> pairs2 = new ArrayList<NameValuePair>();	
    	    pairs2.add(new BasicNameValuePair("email", email.getText().toString()));
    	    pairs2.add(new BasicNameValuePair("codigo", responseBody.replace(" ", "")));
    	    
    		UrlEncodedFormEntity ent2 = new UrlEncodedFormEntity(pairs2);
            post2.setEntity(ent2);
            HttpResponse responsePOST2 = client.execute(post2);  
            
            String responseBody2 = EntityUtils.toString(responsePOST2.getEntity());
            
            Log.v("RESPONSE",responseBody2);
            if(responseBody2.contains("NNNNNNNNNN")){
            	Log.v("RESPONSE","não adicionou o code para o bd");
            	
            	aviso = 3;         	
            	
            }      	
        	
        }
        runOnUiThread(new Runnable() {
            public void run() {
            
            	carrega.setVisibility(View.GONE);
           	    enviaCodig.setVisibility(View.VISIBLE);
           	    alertDialog.setMessage(getString(R.string.foi_enviado_email)+" "+email.getText().toString());
                alertDialog.setIcon(R.drawable.ic_ok);
           	 	alertDialog.show(); 
            	
           	                }
        });
        
        }        
    } catch (Exception e) {   
    	
    	
        Log.e("SendMail", e.getMessage(), e);   
    } 
	
         }}).start();
	
	 
	
	 Log.v("RESPONSE","aviso "+ aviso);
	 
}

@SuppressWarnings("deprecation")
public void iniciarBackup(View v){
	
	final String code = digCodigo.getText().toString();
	final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
	 alertDialog.setTitle(getString(R.string.aviso));
  	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
  	 public void onClick(final DialogInterface dialog, final int which) {
  	 // here you can add functions
  	 }
  	 });
	
	if(code.length() ==0){
		Toast.makeText(getBaseContext(), getString(R.string.digite_codigo), Toast.LENGTH_SHORT).show();
	}else{
		carregaCode.setVisibility(View.VISIBLE);
		enviaCodigo.setVisibility(View.GONE);
		 new Thread(new Runnable() {
	         @Override
	         public void run() {
		
		try{
			Thread.sleep(1000);
		final HttpPost post2 = new HttpPost("http://appoficina.atwebpages.com/verifica_code.php");
		List<NameValuePair> pairs2 = new ArrayList<NameValuePair>();	
	    pairs2.add(new BasicNameValuePair("codigo", code));
	    
		UrlEncodedFormEntity ent2 = new UrlEncodedFormEntity(pairs2);
        post2.setEntity(ent2);
        HttpResponse responsePOST2 = client.execute(post2);  
        
        String responseBody2 = EntityUtils.toString(responsePOST2.getEntity());
        
        Log.v("RESPONSE",responseBody2);
        if(responseBody2.contains("NNNNNNNNNN")){
        	Log.v("RESPONSE","Código nao esta no bd");
        	runOnUiThread(new Runnable() {
                public void run() {
                
                	carregaCode.setVisibility(View.GONE);
               	    enviaCodigo.setVisibility(View.VISIBLE);
               	    alertDialog.setMessage(getString(R.string.erro_codigo));
                    alertDialog.setIcon(R.drawable.ic_alert);
               	 	alertDialog.show(); 
                	
               	                }
            });	
        	
        }  else{
        	
        	JSONObject json = new JSONObject(responseBody2);

		     // Recupera a lista product do JSON
		    JSONArray products = json.getJSONArray("product");

		    int length = products.length();
		    int i =0;
		    for( i = 0; i < length; ++i) {
		        JSONObject product = products.getJSONObject(i);
		        
		        String codigo 			= product.getString("codigo");
		        String nome 			= product.getString("nome");
		        String email 			= product.getString("email");
		        String contagen			= product.getString("contagen");
				  
		        db.criaUser(codigo, nome, "", email);	
		        db.criaDefinicoes("contagem", 0, contagen);
			Log.v("RESPONSE","Inserido");
			startService(new Intent("MGO_RESPOSTA"));
			Intent it = new Intent(RecuperaDadosActivity.this,MainActivity.class);
    		startActivity(it);
		    }
        	      	
        	
        	Log.v("RESPONSE",responseBody2);
        }
        
	    } catch (Exception e) {   
	    	
	    	
	        Log.e("SendMail", e.getMessage(), e);   
	    }
	         }}).start();
	}
}

public void jaPossui(View v){
	jaTenho.setVisibility(View.GONE);
	jaTem.setVisibility(View.VISIBLE);
	
}
	
}
