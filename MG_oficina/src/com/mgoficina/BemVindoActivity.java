package com.mgoficina;

import com.mgoficina.R;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class BemVindoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bem_vindo);
	}

	
	public void tenho(View v){
		if(verificaConexao()){
	        	Intent intents = new Intent(BemVindoActivity.this, RecuperaDadosActivity.class);
             startActivity(intents);		            
	        	}else{
	        		avisaSemConexao();
	        		
	        	}
	}
	
	public void naoTenho(View v){
		
		if(verificaConexao()){
	        	Intent intent = new Intent(BemVindoActivity.this, UserActivity.class);
             startActivity(intent);
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
		final AlertDialog alertDialog = new AlertDialog.Builder(BemVindoActivity.this).create();
		 alertDialog.setTitle(getString(R.string.aviso));
	  	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	  	 public void onClick(final DialogInterface dialog, final int which) {

	  		startActivity(new Intent(BemVindoActivity.this, MainActivity.class));
	  	 }
	  	 });
	  	alertDialog.setMessage(getString(R.string.sem_conexao));
        alertDialog.setIcon(R.drawable.ic_alert);
   	 	alertDialog.show();
	}
    
}
