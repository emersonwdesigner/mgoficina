package com.mgoficina;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.Normalizer;
import com.actionbarsherlock.app.SherlockActivity;
import com.mgoficina.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SingleActivityEdit extends SherlockActivity {
	ImageView viewImage;
	ImageView icone, conta;
	final DataBaseHandler db = new DataBaseHandler(this);
	
	TextView title, cliente, descricao, defeito, local, acessorio, obs, valorS, idItem, editTel, editEnd; 
	
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.edit_item);

getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FA4B2A")));
getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.editar) +"</b></font>"));

//viewImage=(ImageView)findViewById(R.id.novaImg);

/**
* CRUD Operations
* */
Bundle bundle = this.getIntent().getExtras();

String key = bundle.get("ID").toString();

icone 		= (ImageView) findViewById(R.id.imgIcon);
conta 		= (ImageView) findViewById(R.id.novaImg);
title 		= (TextView) findViewById(R.id.txtTitle);
cliente 	= (TextView) findViewById(R.id.txtCliente);
descricao 	= (TextView) findViewById(R.id.txtDescricao);
defeito		= (TextView) findViewById(R.id.txtDefeito);
local 		= (TextView) findViewById(R.id.textLocal);
acessorio 	= (TextView) findViewById(R.id.textAcessorio);
obs 		= (TextView) findViewById(R.id.textObs);
valorS		= (TextView) findViewById(R.id.textValor);
idItem 		= (TextView) findViewById(R.id.textID);
editTel 	= (TextView) findViewById(R.id.editaTelef);
editEnd 	= (TextView) findViewById(R.id.editaEnder);

if(db.getDefInt("contagem").equals("y")){
	
	conta.setVisibility(View.GONE);
	
}

Contact itens = db.singleEdit(key);
if(itens.getImage() == null){
	
}else{
ByteArrayInputStream imageStream = new ByteArrayInputStream(itens.getImage());
Bitmap theImage = BitmapFactory.decodeStream(imageStream);
icone.setImageBitmap(theImage);
}
title.setText(String.valueOf(itens.getOs()));
cliente.setText(itens.getCliente());
descricao.setText(itens.getDescricao());
defeito.setText(itens.getDefeito());
local.setText(itens.getLocal());
acessorio.setText(itens.getAcessorio());
obs.setText(itens.getObs());
idItem.setText(itens.getIdv());
idItem.setText(itens.getIdv());


String inTel 	="";
String fimTel 	= "";
if(!String.valueOf(itens.getTelefonecliente()).equals("0")){
		inTel = String.valueOf(itens.getTelefonecliente()).substring(0,4);
		fimTel = String.valueOf(itens.getTelefonecliente()).substring(4);
		editTel.setText(getString(R.string.telefone)+": "+inTel+"-"+fimTel);
}

editEnd.setText(itens.getEnderecocliente());

String b; 
b = String.valueOf(itens.getValor());
b.replace('.', ',');
valorS.setText(b);

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

public void editaItem(final View v){
	final DataBaseHandler db = new DataBaseHandler(this);
	// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView;
		if(v.getContentDescription().equals("cliente")){
			promptsView = li.inflate(R.layout.popup_edit_cliente, null);
		}else{
			promptsView = li.inflate(R.layout.popup_edit, null);
		}
		
		

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText ItemEditavel 	= (EditText) promptsView.findViewById(R.id.ItemEditavel);
		final EditText ItemEditavelT 	= (EditText) promptsView.findViewById(R.id.editTelefone);
		final EditText ItemEditavelE 	= (EditText) promptsView.findViewById(R.id.editEndereco);
		final TextView TituloEdit		= (TextView) promptsView.findViewById(R.id.titPopupEdit);
		
		//mantem os mesmos tel e enderco do cliente
		//ItemEditavelT.setText(editTel.getText().toString());
		//ItemEditavelE.setText(editEnd.getText().toString());
		
		//setar o tem a ser editado
				if(v.getContentDescription().equals("title")){
		TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.orden_servico));			
		TextView ItemEditado 	= (TextView) findViewById(R.id.txtTitle);	
		String TxtEditado = ItemEditado.getText().toString();
		ItemEditavel.setText(TxtEditado);
		
		}else 	if(v.getContentDescription().equals("cliente")){
			TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.cliente));
			TextView ItemEditado 	= (TextView) findViewById(R.id.txtCliente);	
			String TxtEditado = ItemEditado.getText().toString();
			String TxtTelefone = editTel.getText().toString();
			String TxtEndereco = editEnd.getText().toString();
			
			ItemEditavel.setInputType(InputType.TYPE_CLASS_TEXT);
			ItemEditavel.setText(TxtEditado);
			ItemEditavelT.setText(TxtTelefone.replace("-", "").replace(getString(R.string.telefone)+": ", ""));
			ItemEditavelE.setText(TxtEndereco);
			
		}else 	if(v.getContentDescription().equals("descricao")){
			TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.descricao));
			TextView ItemEditado 	= (TextView) findViewById(R.id.txtDescricao);	
			String TxtEditado = ItemEditado.getText().toString();
			ItemEditavel.setInputType(InputType.TYPE_CLASS_TEXT);
			ItemEditavel.setText(TxtEditado);
			
		}else 	if(v.getContentDescription().equals("defeito")){
			TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.defeito));
			TextView ItemEditado 	= (TextView) findViewById(R.id.txtDefeito);	
			String TxtEditado = ItemEditado.getText().toString();
			ItemEditavel.setInputType(InputType.TYPE_CLASS_TEXT);
			ItemEditavel.setText(TxtEditado);
			
		}else 	if(v.getContentDescription().equals("local")){
			TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.local));
			TextView ItemEditado 	= (TextView) findViewById(R.id.textLocal);	
			String TxtEditado = ItemEditado.getText().toString();
			ItemEditavel.setText(TxtEditado);
			
		}else 	if(v.getContentDescription().equals("acessorio")){
			TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.acessorios));
			TextView ItemEditado 	= (TextView) findViewById(R.id.textAcessorio);	
			String TxtEditado = ItemEditado.getText().toString();
			ItemEditavel.setInputType(InputType.TYPE_CLASS_TEXT);
			ItemEditavel.setText(TxtEditado);
			
		}else 	if(v.getContentDescription().equals("obs")){
			TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.obs));
			TextView ItemEditado 	= (TextView) findViewById(R.id.textObs);	
			String TxtEditado = ItemEditado.getText().toString();
			ItemEditavel.setInputType(InputType.TYPE_CLASS_TEXT);
			ItemEditavel.setText(TxtEditado);
			
		}else 	if(v.getContentDescription().equals("valor")){
			TituloEdit.setText(getString(R.string.editar) +" "+getString(R.string.valor));
			TextView ItemEditado 	= (TextView) findViewById(R.id.textValor);	
			String TxtEditado = ItemEditado.getText().toString();
			ItemEditavel.setText(TxtEditado);
			
			}
		
		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int id) {
			    	String TxtEdit = removerAcentos(ItemEditavel.getText().toString());
			    	final String telfinal, endfinal;
			    	
			    	if(v.getContentDescription().equals("cliente")){
			    		telfinal = ItemEditavelT.getText().toString();
			    		endfinal = removerAcentos(ItemEditavelE.getText().toString());
			    	}else{
			    		telfinal = null;
			    		endfinal = null;
			    	}
			    	
					if(v.getContentDescription().equals("title")){
					if(db.disp(TxtEdit) > 0){
			    		
			    		Toast.makeText(getBaseContext(), TxtEdit +" "+ getString(R.string.ja_cadastrado), Toast.LENGTH_LONG).show();
			    //  }	
			   	}else{
					
			   		String TxtID = idItem.getText().toString();
			   		db.editar(TxtID, v.getContentDescription(), TxtEdit, null,telfinal,endfinal);
			    	Toast.makeText(getBaseContext(), getString(R.string.iten_editado_para)+ TxtEdit, Toast.LENGTH_LONG).show();
			    	db.mudaExporta(TxtID, 0);
			    	Intent it = new Intent(SingleActivityEdit.this,SingleActivityEdit.class);  
			    	it.putExtra("ID", TxtID);
			        startActivity(it);
			   	}
			    }else{
			    	String TxtID = idItem.getText().toString();
			    	
			    	db.editar(TxtID, v.getContentDescription(), TxtEdit, null,telfinal,endfinal);
			    	Toast.makeText(getBaseContext(), getString(R.string.iten_editado_para)+ TxtEdit, Toast.LENGTH_LONG).show();
			    	db.mudaExporta(TxtID, 0);
			    	Intent it = new Intent(SingleActivityEdit.this,SingleActivityEdit.class);  
			    	it.putExtra("ID", TxtID);
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

public void novaImagem(View v) {
	final String [] items = new String[] {getString(R.string.camera), getString(R.string.galeria),getString(R.string.excluir_foto),getString(R.string.cancelar)};
    final Integer[] icons = new Integer[] {R.drawable.ic_action_camera, R.drawable.ic_action_picture, R.drawable.ic_action_discard, R.drawable.ic_action_cancel};
    ListAdapter adapter = new ArrayAdapterWithIcon(this, items, icons);

    new AlertDialog.Builder(this).setTitle(getString(R.string.escolha_opcao))
        .setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item ) {

            	 if (items[item].equals(getString(R.string.camera)))
                 {
            		 startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), 1);
                 }
                 else if (items[item].equals(getString(R.string.galeria)))
                 {
                	
                     Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                     startActivityForResult(intent, 2);
  
                 }else if (items[item].equals(getString(R.string.excluir_foto))) {
                	/**
                	 Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.no_image);
                	ByteArrayOutputStream stream = new ByteArrayOutputStream();
                	image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                	byte imageInByte[] = stream.toByteArray();
                	**/
                	
                	String TxtID = idItem.getText().toString();
                	db.editar(TxtID, "foto" , null, null,null, null);
                	////Log.v("aviso", "Edita erro 5");
                    Intent it = new Intent(SingleActivityEdit.this,SingleActivityEdit.class);  
        	    	it.putExtra("ID", TxtID);
        	        startActivity(it);
                 }
                 else if (items[item].equals(getString(R.string.cancelar))) {
                     dialog.dismiss();
                 }
            	
            }
    }).show();
	
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
        if (requestCode == 1) {
        	Bitmap bp = (Bitmap) data.getExtras().get("data");
            // Chame este método pra obter a URI da imagem
            Uri uri = getImageUri(getApplicationContext(), bp);

            // Em seguida chame este método para obter o caminho do arquivo
            File file = new File(getRealPathFromURI(uri));
        	
        	Bitmap image = ShrinkBitmap(file.getPath(), 480, 320);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
        	
            String TxtID = idItem.getText().toString();
            //Log.v("aviso", "Edita erro 2");
            db.editar(TxtID, "foto" , "foto", imageInByte, null, null);
            Intent it = new Intent(SingleActivityEdit.this,SingleActivityEdit.class);  
	    	it.putExtra("ID", TxtID);
	        startActivity(it);
            
        } else if (requestCode == 2) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Toast.makeText(getBaseContext(),getString(R.string.foto_trocada), Toast.LENGTH_LONG).show();
            
            Bitmap image = ShrinkBitmap(picturePath, 480, 320);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
            
            String TxtID = idItem.getText().toString();
            //Log.v("aviso", "Edita erro 2");
            db.editar(TxtID, "foto" , picturePath, imageInByte, null, null);
            Intent it = new Intent(SingleActivityEdit.this,SingleActivityEdit.class);  
	    	it.putExtra("ID", TxtID);
	        startActivity(it);
           
        }
    }
} 

public Uri getImageUri(Context inContext, Bitmap inImage) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    return Uri.parse(path);
}

public String getRealPathFromURI(Uri uri) {
    Cursor cursor = getContentResolver().query(uri, null, null, null, null); 
    cursor.moveToFirst(); 
    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
    return cursor.getString(idx); 
}

public void onBackPressed() {
	Intent it = new Intent(SingleActivityEdit.this,SingleActivity.class);  
	String TxtID = idItem.getText().toString();
	it.putExtra("ID", TxtID);
    startActivity(it);
}
@SuppressLint("NewApi")
public static String removerAcentos(String str) {
    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
}
}
