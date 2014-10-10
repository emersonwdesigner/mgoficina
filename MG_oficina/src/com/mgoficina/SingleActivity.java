package com.mgoficina;

import java.io.ByteArrayInputStream;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.mgoficina.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SingleActivity extends SherlockActivity {
	ImageView viewImage;
	ImageView icone;
	int liga;
	final DataBaseHandler db = new DataBaseHandler(this);
	final Funcoes funcoes = new Funcoes();
	
	TextView datahora, status, title, cliente, descricao, defeito, local, acessorio, obs, valorS, idItem, telCliente, endCliente; 
	
/** Called when the activity is first created. */
@SuppressLint("NewApi")
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.single_activity);


Bundle bundle = this.getIntent().getExtras();

String key = bundle.get("ID").toString();
icone 		= (ImageView) findViewById(R.id.imgIcon);
title 		= (TextView) findViewById(R.id.txtTitle);
datahora 	= (TextView) findViewById(R.id.datahora);
status 		= (TextView) findViewById(R.id.Status);
cliente 	= (TextView) findViewById(R.id.txtCliente);
descricao 	= (TextView) findViewById(R.id.txtDescricao);
defeito		= (TextView) findViewById(R.id.txtDefeito);
local 		= (TextView) findViewById(R.id.textLocal);
acessorio 	= (TextView) findViewById(R.id.textAcessorio);
obs 		= (TextView) findViewById(R.id.textObs);
valorS		= (TextView) findViewById(R.id.textValor);
idItem 		= (TextView) findViewById(R.id.textID);
telCliente 	= (TextView) findViewById(R.id.telefCliente);
endCliente	= (TextView) findViewById(R.id.endCliente);
//Log.v("aviso", "single 1");

Contact itens = db.single(key);
if(itens.getImage() == null){
Log.v("aviso", "single 1");
}else{
	ByteArrayInputStream imageStream = new ByteArrayInputStream(itens.getImage());
	Bitmap theImage = BitmapFactory.decodeStream(imageStream);

	icone.setImageBitmap(theImage);
}
/**
	if(itens.getImage() != null){
		ByteArrayInputStream imageStream = new ByteArrayInputStream(itens.getImage());
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);

		icone.setImageBitmap(theImage);
		}
	
**/

datahora.setText(getString(R.string.data_hora)+" "+itens.getDatahora());
status.setText(getString(R.string.situacao)+": "+itens.getStatus());
title.setText(String.valueOf(itens.getOs()));
cliente.setText(itens.getCliente());
descricao.setText(itens.getDescricao());
defeito.setText(itens.getDefeito());
local.setText(itens.getLocal());
acessorio.setText(itens.getAcessorio());
obs.setText(itens.getObs());
idItem.setText(itens.getIdv());
Log.v("aviso", "single 2");
String inTel 	="";
String fimTel 	= "";
if(!String.valueOf(itens.getTelefonecliente()).equals("0")){
		inTel = String.valueOf(itens.getTelefonecliente()).substring(0,4);
		fimTel = String.valueOf(itens.getTelefonecliente()).substring(4);
		telCliente.setText(getString(R.string.telefone)+": "+inTel+"-"+fimTel);
}

endCliente.setText(itens.getEnderecocliente());

if(itens.getEntrada()== 0){
	db.mudaEntrada(key);
}
  
valorS.setText(funcoes.moeda(itens.getValor()));
liga = itens.getLiga();

//Log.v("ligas", "lo"+String.valueOf(liga));
//Log.v("ligas", "ex"+String.valueOf(itens.getExporta()));
if(liga == 0){
	getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
	getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.title_activity_single) +"</b></font>"));
}else{
	getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
	getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#666666\"><b>"+getString(R.string.title_activity_single) + "</b></font>"));	
	status.setText(getString(R.string.situacao)+": "+getString(R.string.em_lote));
	getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#da0000\"><b>"+getString(R.string.em_lote)+"</b></font>"));
}
}

public boolean onCreateOptionsMenu(Menu menu) {
	
	if(liga == 0){
    SubMenu sub = menu.addSubMenu("").setIcon(R.drawable.ic_action_settings);
    sub.add(0, 1, 0, getString(R.string.enviar_para)).setIcon(R.drawable.ic_action_import_export);
    sub.add(0, 2, 0, getString(R.string.editar)).setIcon(R.drawable.ic_action_edit);
    sub.add(0, 3, 0, getString(R.string.excluir_item)).setIcon(R.drawable.ic_action_discard);
    
    sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    
    
    return true;
    
	}
	return false;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	
	final TextView idItem = (TextView) findViewById(R.id.textID);  
	final String var = idItem.getText().toString();
	
	switch (item.getItemId()) {
    case 0:
    	return false;
    case 1:
    	menuAcao(null);
    	return true;
    case 2:
    	Intent it = new Intent(SingleActivity.this,SingleActivityEdit.class);  
    	it.putExtra("ID", var);
        startActivity(it);
        return true;
    case 3:
    	boolean senha = true;
		if(senha){
			digSenha();
			return true;
		}else{
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
 		    @Override
 		    public void onClick(DialogInterface dialog, int which) {
 		        switch (which){
 		        case DialogInterface.BUTTON_POSITIVE:
 		        	
 		        	db.delete(var, false);

               	 Toast.makeText(getBaseContext(), "Item removido!", Toast.LENGTH_LONG).show();
               	 Intent it = new Intent(SingleActivity.this,MainActivity.class);  
			        startActivity(it);
               	 dialog.dismiss();
 		            break;

 		        case DialogInterface.BUTTON_NEGATIVE:
 		            //No button clicked
 		            break;
 		        }
 		    }
 		};
 	 
 	 AlertDialog.Builder builder = new AlertDialog.Builder(context);
 	 builder.setMessage(getString(R.string.aviso_excluir)).setPositiveButton(getString(R.string.sim), dialogClickListener)
 	     .setNegativeButton(getString(R.string.nao), dialogClickListener).show();
    	
 	return true;
		}
    default:
    	
}
    
    return true;
}
Bitmap ShrinkBitmap(String file, int width, int height){
	  
    BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
       bmpFactoryOptions.inJustDecodeBounds = true;
       Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
       
       int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
       int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);
       
       if (heightRatio > 1 || widthRatio > 1)
       {
        if (heightRatio > widthRatio)
        {
         bmpFactoryOptions.inSampleSize = heightRatio;
        } else {
         bmpFactoryOptions.inSampleSize = widthRatio; 
        }
       }
       
       bmpFactoryOptions.inJustDecodeBounds = false;
       bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
    return bitmap;
   }


//Ações da lista

final Context context = this;
private double valor;

public void menuAcao(View v) {
	final TextView btn = (TextView) findViewById(R.id.textID);  
	final String var = btn.getText().toString();
	
	

	final String [] items = new String[] {getString(R.string.Title_bancada), getString(R.string.Title_aguardando), getString(R.string.Title_prontos), getString(R.string.Title_entregues), getString(R.string.Title_devolvido), getString(R.string.Title_executando)};
    final Integer[] icons = new Integer[] {R.drawable.ic_action_paste, R.drawable.ic_action_attachment, R.drawable.ic_action_important, R.drawable.ic_action_good, R.drawable.ic_action_undo, R.drawable.ic_action_play_over_video};
    ListAdapter adapter = new ArrayAdapterWithIcon(this, items, icons);

    new AlertDialog.Builder(this).setAdapter(adapter, new DialogInterface.OnClickListener() {
    	
            public void onClick(DialogInterface dialog, int item ) {
            	
            	 if (items[item].equals(getString(R.string.Title_bancada)))
                 {
			     // status 0 bancada
               	  menuSituacao(var, "1");
               	  
                
                 }else if (items[item].equals(getString(R.string.Title_executando)))
                 {
                	 // status 6 executando
                	 menuSituacao(var, "6");
                	
  
                 }
                 else if (items[item].equals(getString(R.string.Title_aguardando)))
                 {
                	 // status 2 aguardando
                	 menuSituacaoValor(var, "2");
                	
  
                 } else if (items[item].equals(getString(R.string.Title_prontos)))
                 {
                	 //status 3 pronto
                	 menuSituacaoValor(var, "3");
                 }
            	 
            	 //status 4 entregue
                 else if (items[item].equals(getString(R.string.Title_entregues)))
                 {

                	 menuSituacaoValor(var, "4");
  
                 }else if (items[item].equals(getString(R.string.Title_devolvido)))
                 {
                	 //status 5 devolvido
                	 menuSituacao(var, "5");
                 }
            	
            }
    }).show();
}

public void menuSituacao(final String var, final String var2){
	
	
	// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.populocal, null);

		AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder1.setView(promptsView);

		final EditText Info_local 	= (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		final EditText Info_obs 	= (EditText) promptsView.findViewById(R.id.infoObs);
		String obsV = obs.getText().toString();
		String localV = local.getText().toString();
		Info_obs.setText(obsV);
		Info_local.setText(localV);
		// set dialog message
		alertDialogBuilder1
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				
				// get user input and set it to result
				// edit text
				//result.setText(userInput.getText());
			    	
			    	String local = Info_local.getText().toString();
			    	String obs = Info_obs.getText().toString();
			    	// id_item, status, local, observações, valor
			    	db.atualiza(var, var2, local, obs, 0.00);	
			    	
			    	db.mudaExporta(var, 0);
			    			if (var2.equals("1")) {
			    		Toast.makeText(getBaseContext(), getString(R.string.envia_bancada), Toast.LENGTH_LONG).show();
			    	}else 	if (var2.equals("2")) {
			    		Toast.makeText(getBaseContext(), getString(R.string.envia_aguarda), Toast.LENGTH_LONG).show();
			    	}else 	if (var2.equals("5")) {
			    		Toast.makeText(getBaseContext(), getString(R.string.envia_devolvido), Toast.LENGTH_LONG).show();
			    	}else 	if (var2.equals("6")) {
			    		Toast.makeText(getBaseContext(), getString(R.string.envia_executando), Toast.LENGTH_LONG).show();
			    	}
			    	
			    	
			    	
			    	Intent it = new Intent(SingleActivity.this,MainActivity.class);  
			        startActivity(it);
			    }
			  })
			.setNegativeButton("Cancelar",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder1.create();

		// show it
		alertDialog.show();
}
 EditText  Info_local, Info_obs, Info_valor;
public void menuSituacaoValor(final String var, final String var2){
	
	final DataBaseHandler db = new DataBaseHandler(this);
	
	// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.populocal_valor, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		 Info_local 	= (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		 Info_obs 	= (EditText) promptsView.findViewById(R.id.infoObs_valor);
		 Info_valor 	= (EditText) promptsView.findViewById(R.id.infoValor_valor);
		
		String obsV = obs.getText().toString();
		String localV = local.getText().toString();
		String valorV = valorS.getText().toString();
		
		
		Info_obs.setText(obsV);
		Info_local.setText(localV);
		String valorVF;
		valorVF = valorV.substring(3);
		Info_valor.setText(valorVF.replace(",", "."));
		
		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int id) {
			    	
			    	String local 		= Info_local.getText().toString();
			    	String obs 			= Info_obs.getText().toString();
			    	String valorString 	= Info_valor.getText().toString();
			    	int valorInt = 0;
			    	try {
						valorInt = Integer.parseInt(valorString.replace(".", ""));
						
					} catch (NumberFormatException nfe) {
						// TODO: handle exception
					}
			    	db.mudaExporta(var, 0);
			    	if(valorInt > 0){
			    		valor = Double.parseDouble(valorString);
			    		//Log.v("valor",String.valueOf(valor));
			    		db.atualiza(var, var2, local, obs, valor);	
			    		
			    		
			    		if (var2.equals("3")) {
				    		Toast.makeText(getBaseContext(), getString(R.string.envia_pronto), Toast.LENGTH_LONG).show();
				    	}else if (var2.equals("4")) {
				    	
				    		Toast.makeText(getBaseContext(), getString(R.string.envia_entregue), Toast.LENGTH_LONG).show();
					    }else if (var2.equals("2")) {
				    	
					    	Toast.makeText(getBaseContext(), getString(R.string.envia_aguarda), Toast.LENGTH_LONG).show();
					    }
			    		
			    		Intent it = new Intent(SingleActivity.this,MainActivity.class);  
				        startActivity(it);
			    		
					}else{
						//Log.v("aviso", var2);
			    		if(var2.equals("2") || var2.equals("3") || var2.equals("4")){
			    	//Toast.makeText(getBaseContext(), getString(R.string.valor_deve), Toast.LENGTH_LONG).show();
			    	
			    	funcoes.avisaSemConexao(SingleActivity.this, getString(R.string.aviso), getString(R.string.valor_deve));
							
						}else{	
			    	db.atualiza(var, var2, local, obs, 0);	
			    	Intent it = new Intent(SingleActivity.this,MainActivity.class);  
			        startActivity(it);
						}
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

@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	this.finish();
}

public void onBackPressed() {
	Intent it = new Intent(SingleActivity.this,MainActivity.class);  
	String TxtID = idItem.getText().toString();
	it.putExtra("ID", TxtID);
    startActivity(it);
}
private void digSenha(){
	
 	LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.popusenha, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText Info_local 	= (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		final TextView Info_cod 	= (TextView) promptsView.findViewById(R.id.textViewCod);
		final TextView idItem = (TextView) findViewById(R.id.textID);  
		final String var = idItem.getText().toString();
		Info_cod.setText(db.getCodigo());
		
		
		// set dialog message
		alertDialogBuilder.setTitle(getString(R.string.seguranca)).setIcon(R.drawable.ic_action_secure)
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int id) {
					int verSenha = db.VerSenha(Info_cod.getText().toString(), Info_local.getText().toString());    	
			    	if(verSenha == 0 ){
			    		Toast.makeText(SingleActivity.this, getString(R.string.senha_errada) , Toast.LENGTH_SHORT).show();
		    		}else{
		    			db.delete(var, false);

		               	 Toast.makeText(getBaseContext(), "Item removido!", Toast.LENGTH_LONG).show();
		               	 Intent it = new Intent(SingleActivity.this,MainActivity.class);  
					        startActivity(it);
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
}
