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
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.mgoficina.R;

@SuppressLint("NewApi")
public class FechaLoteActivity extends SherlockActivity {
	final Context context = this;	
	DataBaseHandler db = new DataBaseHandler(this);
	ProgressBar carrega;
	HttpClient client = new DefaultHttpClient();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_fechar_lote);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.fechar_lote)+"</b></font>"));
   
	}

	

public void fecharLote(final View v){
	
	
	
				
		boolean senha = true;
		if(senha){
			digSenha();
		}else{
		
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
 		    @Override
 		    public void onClick(DialogInterface dialog, int which) {
 		        switch (which){
 		        case DialogInterface.BUTTON_POSITIVE:
 		        	
 		        	fechaOk();
 		            break;

 		        case DialogInterface.BUTTON_NEGATIVE:
 		            //No button clicked
 		            break;
 		        }
 		    }
 		};
 	 
 	 AlertDialog.Builder builder = new AlertDialog.Builder(context);
 	 builder.setMessage(getString(R.string.aviso_fechar_lote)).setPositiveButton(getString(R.string.sim), dialogClickListener)
 	     .setNegativeButton(getString(R.string.nao), dialogClickListener).show();
		
		//Toast.makeText(getBaseContext(), "Exportado!", Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	public void fechaOk(){
		
		startService(new Intent("MGO_FECHAR_LOTE"));
	}
	
	 private void digSenha(){
     	LayoutInflater li = LayoutInflater.from(context);
 		View promptsView = li.inflate(R.layout.popusenha, null);

 		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

 		// set prompts.xml to alertdialog builder
 		alertDialogBuilder.setView(promptsView);

 		final EditText Info_local 	= (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
 		final TextView Info_cod 	= (TextView) promptsView.findViewById(R.id.textViewCod);
 		carrega = (ProgressBar) promptsView.findViewById(R.id.progresso);
 		Info_cod.setText(db.getCodigo());
 		
 		
 		// set dialog message
 		alertDialogBuilder.setTitle(getString(R.string.seguranca)+" ("+ getString(R.string.fechar_lote)+")").setIcon(R.drawable.ic_action_secure)
 			.setCancelable(false)
 			.setPositiveButton("OK",
 			  new DialogInterface.OnClickListener() {

 				public void onClick(DialogInterface dialog,int id) {
 					int verSenha = db.VerSenha(Info_cod.getText().toString(), Info_local.getText().toString());    	
 			    	if(verSenha == 0 ){
 			    		Toast.makeText(FechaLoteActivity.this, getString(R.string.senha_errada) , Toast.LENGTH_SHORT).show();
 		    		}else{
 		    			fechaOk();
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
	 
	 public void esqueciSenha(View v){
     	
     	if(verificaConexao()){
     		Log.v("RESPONSE","envia senha");
     		
     		carrega.setVisibility(View.VISIBLE);
     		 new Thread(new Runnable() {
     	         @Override
     	         public void run() {
     		try {   
     		
     		Log.v("RESPONSE","envia senha2");
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
                 
                 Log.v("RESPONSE","Resposta"+responseBody);
                 if(responseBody.contains("NNNNNNNNNN")){
             	
             	Log.v("RESPONSE","não adicionou o code para o bd");
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
 		final AlertDialog alertDialog = new AlertDialog.Builder(FechaLoteActivity.this).create();
 		 alertDialog.setTitle(getString(R.string.aviso));
 	  	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
 	  	 public void onClick(final DialogInterface dialog, final int which) {

 	  		startActivity(new Intent(FechaLoteActivity.this, MainActivity.class));
 	  	 }
 	  	 });
 	  	alertDialog.setMessage(getString(R.string.sem_conexao));
         alertDialog.setIcon(R.drawable.ic_alert);
    	 	alertDialog.show();
 	}
}
