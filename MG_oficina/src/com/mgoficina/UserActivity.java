package com.mgoficina;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UserActivity extends SherlockActivity {
	EditText Info_nome, Info_senha, Info_re_senha, Info_email;
	TextView Info_login;
	LinearLayout carrega;
	final DataBaseHandler db = new DataBaseHandler(this);
	HttpClient client = new DefaultHttpClient();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_novo);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
		getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.primeiros_passos)+"</b></font>"));
		getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#CCD3D6\"><b>"+getString(R.string.dados_para_uso)+"</b></font>"));
		
		carrega 		= (LinearLayout) findViewById(R.id.carregando);
		Info_login 		= (TextView) findViewById(R.id.txtCodigo);
		Info_nome 		= (EditText) findViewById(R.id.txtNome);
		Info_senha 		= (EditText) findViewById(R.id.txtSenha);
		Info_re_senha 	= (EditText) findViewById(R.id.txtResenha);
		Info_email 	= (EditText) findViewById(R.id.editTextEmail);
		
		Info_login.setText(gerarSenhaAleatoria());
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		db.criaDefinicoes("contagem", 0, "n");
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if(checkedId == R.id.radioButton1){
					db.criaDefinicoes("contagem", 0, "n");
				}else if(checkedId == R.id.radioButton2){
					
					db.criaDefinicoes("contagem", 1, "y");
					
					
				}
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		}); 
	}


	 private static String gerarSenhaAleatoria() {
	        int qtdeMaximaCaracteres = 6;
	        String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8",
	                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
	                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
	                "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
	                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
	                "V", "W", "X", "Y", "Z" };
	       
	        StringBuilder senha = new StringBuilder();

	        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
	            int posicao = (int) (Math.random() * caracteres.length);
	            senha.append(caracteres[posicao]);
	        }
	        return senha.toString();
	    }
	  
public void criaUser(final View v){
	if(verificaConexao()){
		
		carrega.setVisibility(View.VISIBLE);
	final String var0 = Info_login.getText().toString();
	final String var1 = removerAcentos(Info_nome.getText().toString());
	final String var2 = Info_senha.getText().toString();
	final String var3 = Info_re_senha.getText().toString();
	final String var4 = Info_email.getText().toString();
	
	if(var1.equals("") || var2.equals("") || var3.equals("") || var4.equals("") ){
		 Toast.makeText(getBaseContext(), getString(R.string.deve_preencher), Toast.LENGTH_LONG).show();
		 carrega.setVisibility(View.GONE);
	}else{
		//valida email
		if (validar(var4))   
	    { 
			if(var2.equals(var3)){
				//verifica se o email existe no bd
				new Thread(new Runnable() {
			         @Override
			         public void run() {
				try {  
					Log.v("RESPONSE",db.getDefInt("contagem"));
				final HttpPost post0 = new HttpPost("http://appoficina.atwebpages.com/verifica_email_cadastro.php");
				List<NameValuePair> pairs0 = new ArrayList<NameValuePair>();	
			    pairs0.add(new BasicNameValuePair("login", var0));
			    pairs0.add(new BasicNameValuePair("nome", var1));
			    pairs0.add(new BasicNameValuePair("email", var4));
			    pairs0.add(new BasicNameValuePair("senha", var3));
			    pairs0.add(new BasicNameValuePair("contagen", db.getDefInt("contagem")));
				UrlEncodedFormEntity ent0 = new UrlEncodedFormEntity(pairs0);
		        post0.setEntity(ent0);
		        HttpResponse responsePOST0 = client.execute(post0);  
		        
		        String responseBody0 = EntityUtils.toString(responsePOST0.getEntity());
		        Log.v("RESPONSE","Resposta"+responseBody0);
		        if(responseBody0.contains("S")){
		        	Log.v("RESPONSE","email já existe");
		        	
		        	runOnUiThread(new Runnable() {
		                public void run() {
		                	carrega.setVisibility(View.GONE);
		                	DialogInterface.OnClickListener dialogClickListene = new DialogInterface.OnClickListener() {
		             		    @Override
		             		    public void onClick(DialogInterface dialog, int which) {
		             		        switch (which){
		             		        case DialogInterface.BUTTON_POSITIVE:
		             		        	
		             		            break;

		             		        case DialogInterface.BUTTON_NEGATIVE:
		             		        	if(verificaConexao()){
		                 		        	Intent intents = new Intent(UserActivity.this, RecuperaDadosActivity.class);
		                 	                startActivity(intents);		            
		                 		        	}else{
		                 		        		avisaSemConexao();
		                 		        		
		                 		        	}
		             		        	
		             		            break;
		             		            
		             		       
		             		        }
		             		    }
		             		};
		             	 
		             	 AlertDialog.Builder builde = new AlertDialog.Builder(v.getContext());
		             	 builde.setMessage(getString(R.string.erro_email_existe)).setIcon(R.drawable.ic_alert).setPositiveButton(getString(R.string.escolher_outro_email), dialogClickListene)
		             	     .setNegativeButton(getString(R.string.title_activity_recupera_dados), dialogClickListene).show();
		                	
		               	                }
		            });
		        	
		        }else{
		        	//Log.v("RESPONSE","salva");
					//salva 
					db.criaUser(var0, var1, var2, var4);
					startActivity(new Intent(UserActivity.this,MainActivity.class));
			        }
		        
				}catch (Exception e) {   
		            	
		            	
		                Log.e("SendMail", e.getMessage(), e);   
		            } 
		        	
		                 }}).start();	 
		        
			}else{
			 
			 Toast.makeText(getBaseContext(), getString(R.string.senha_incopativeis), Toast.LENGTH_LONG).show();
			 carrega.setVisibility(View.GONE);
			}
			
	    } else{
	    	Toast.makeText(getBaseContext(), getString(R.string.email_invalido), Toast.LENGTH_LONG).show();  
	    	carrega.setVisibility(View.GONE);
		
	}
	}
	}else{
		avisaSemConexao();
	}
}
public static boolean validar(String email)  
{  
    boolean isEmailIdValid = false;  
    if (email != null && email.length() > 0) {  
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(email);  
        if (matcher.matches()) {  
            isEmailIdValid = true;  
        }  
    }  
    return isEmailIdValid;  
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
	final AlertDialog alertDialog = new AlertDialog.Builder(UserActivity.this).create();
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
@SuppressLint("NewApi")
public static String removerAcentos(String str) {
    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
}
}
