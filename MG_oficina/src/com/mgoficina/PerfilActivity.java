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
import com.actionbarsherlock.app.SherlockActivity;
import com.mgoficina.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilActivity extends SherlockActivity {
	final DataBaseHandler db = new DataBaseHandler(this);
	final Funcoes funcoes = new Funcoes();
	TextView nome, codigo, email; 
	ProgressBar carrega;
	Button esqueci;
	
/** Called when the aa;ctivity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.perfil);

getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.perfil) +"</b></font>"));

//viewImage=(ImageView)findViewById(R.id.novaImg);

/**
* CRUD Operations
* */
carrega 	= (ProgressBar) findViewById(R.id.progressBar1);
nome 		= (TextView) findViewById(R.id.nomePerfil);
email 		= (TextView) findViewById(R.id.emailPerfil);
codigo 		= (TextView) findViewById(R.id.codigoPerfil);
esqueci 	= (Button) findViewById(R.id.buttonShowDropDown);

Contact itens = db.getUser();

nome.setText(itens.getEnderecocliente());
codigo.setText(itens.getCliente());
email.setText(itens.getDefeito());

if(db.VerSenhaVazia()==1){
	esqueci.setVisibility(View.GONE);
}

}

//Ações da lista

final Context context = this;

public void editaItem(final View v){
	final DataBaseHandler db = new DataBaseHandler(this);
	// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.popup_edit, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText ItemEditavel 	= (EditText) promptsView.findViewById(R.id.ItemEditavel);
		final TextView TituloEdit	= (TextView) promptsView.findViewById(R.id.titPopupEdit);
		
		//setar o tem a ser editado
				if(v.getContentDescription().equals("nome")){
		TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.user_nome));			
		TextView ItemEditado 	= (TextView) findViewById(R.id.nomePerfil);	
		String TxtEditado = ItemEditado.getText().toString();
		ItemEditavel.setText(TxtEditado);
		ItemEditavel.setInputType(InputType.TYPE_CLASS_TEXT);
		
		}
		
		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int id) {
			    	String TxtEdit = ItemEditavel.getText().toString();
			    	
					if(v.getContentDescription().equals("nome")){
			    	
			    	db.mudaDadosUser(0, 0, funcoes.removerAcentos(TxtEdit), "1");
			    	Toast.makeText(getBaseContext(), getString(R.string.iten_editado)+ TxtEdit, Toast.LENGTH_LONG).show();
			    	db.mudaUser("1", 2);
			        startActivity(new Intent(PerfilActivity.this,PerfilActivity.class));
					}
				}
			  })
			.setNegativeButton(getString(R.string.cancelar),
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

public void definirSenha(final View v){
	final DataBaseHandler db = new DataBaseHandler(this);
	// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.popup_define_senha, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView).setTitle(getString(R.string.definir_senha));

		final EditText ItemSenhaAtual 	= (EditText) promptsView.findViewById(R.id.senhaAtual);
		final EditText ItemDigite 		= (EditText) promptsView.findViewById(R.id.digiteSenha);
		final EditText ItemConfirme 	= (EditText) promptsView.findViewById(R.id.confirmeSenha);
		final LinearLayout temSenha 	= (LinearLayout) promptsView.findViewById(R.id.MostraAtual);
		
		if(db.VerSenhaVazia() == 1){
			temSenha.setVisibility(View.GONE);
		}
		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int id) {
					int habilita = 0;
					if(db.VerSenhaVazia() != 1){
						
						String TxtAtual 	= ItemSenhaAtual.getText().toString();
						if(TxtAtual.length()==0){
							Toast.makeText(getBaseContext(), getString(R.string.deve_preencher), Toast.LENGTH_LONG).show();
						
							habilita = 1;
							}else{
						int RspSenha = db.VerSenha("1", TxtAtual);
						
						Log.v("aviso", "Ver senha"+String.valueOf(RspSenha));
						if(RspSenha == 0){
							Toast.makeText(getBaseContext(), getString(R.string.senha_errada), Toast.LENGTH_LONG).show();
							habilita = 1;
							
						}
					}
					}							
					
					
					//se habilita for 0 salva
					
					if(habilita == 0){
					String TxtDigite 	= ItemDigite.getText().toString();
					String TxtConfirme 	= ItemConfirme.getText().toString();
					if(TxtDigite.length()==0 || TxtConfirme.length() ==0 ){
						Toast.makeText(getBaseContext(), getString(R.string.deve_preencher), Toast.LENGTH_LONG).show();
					}else{
					if(TxtDigite.equals(TxtConfirme)){
						db.mudaDadosUser(1, Integer.parseInt(TxtDigite) ,"0", "1");
				    	Toast.makeText(getBaseContext(), getString(R.string.senha_definida), Toast.LENGTH_LONG).show();
				        startActivity(new Intent(PerfilActivity.this,PerfilActivity.class));
					}else{
						Toast.makeText(getBaseContext(), getString(R.string.senha_incopativeis), Toast.LENGTH_LONG).show();
					}
					}
					}
				}
			  })
			.setNegativeButton(getString(R.string.cancelar),
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
	final AlertDialog alertDialog = new AlertDialog.Builder(PerfilActivity.this).create();
	 alertDialog.setTitle(getString(R.string.aviso));
  	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
  	 public void onClick(final DialogInterface dialog, final int which) {

            
  	 }
  	 });
  	alertDialog.setMessage(getString(R.string.sem_conexao));
    alertDialog.setIcon(R.drawable.ic_alert);
	 	alertDialog.show();
}
@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	this.finish();
}
}
