package com.mgoficina;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import com.actionbarsherlock.app.SherlockActivity;
import com.mgoficina.R;
 
@SuppressLint("NewApi")
public class NovaOsActivity extends SherlockActivity implements OnItemClickListener, OnItemLongClickListener{
 
    ImageView viewImage;
    Button b;
    final DataBaseHandler db = new DataBaseHandler(this);
    EditText numero, cliente, novoCliente, novoTelefone, novoEndereco, mostraTelefone, mostraEndereco, clienteUnico;
    LinearLayout LayOs, LayCliente, LayNovoCliente;
    String[] from;
    int[] to;
    Cursor cursor;
    ListView list;
    Dialog listDialog;
    TextView  tId;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_os);
        
        tId 		= (TextView) findViewById(R.id.IdCliente);
        
        cliente 		= (EditText) findViewById(R.id.editCliente);
        mostraTelefone 	= (EditText) findViewById(R.id.mostraTelefone);
        mostraEndereco 	= (EditText) findViewById(R.id.mostraEndereco);
        clienteUnico 	= (EditText) findViewById(R.id.clienteUnico);
        
        novoCliente = (EditText) findViewById(R.id.editNovoCliente);
        novoTelefone = (EditText) findViewById(R.id.editNovoTelefone);
        novoEndereco = (EditText) findViewById(R.id.editNovoEndereco);
        numero 		= (EditText) findViewById(R.id.os);
        LayOs 		= (LinearLayout) findViewById(R.id.LayOs);
        LayCliente 	= (LinearLayout) findViewById(R.id.LayCliente);
        LayNovoCliente 	= (LinearLayout) findViewById(R.id.LayNovoCliente);
        b			= (Button)findViewById(R.id.btnSelectPhoto);
        viewImage	= (ImageView)findViewById(R.id.viewImage);
        
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage2();
            }
        });
        
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.action_add)+"</b></font>"));
        if(db.getDefInt("contagem").equals("y")){
        	
        	LayOs.setVisibility(View.GONE);
    		numero.setText("0");
    		
    	}
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
 
       
    private void selectImage2() {
    	final String [] items = new String[] {"Câmera", "Galeria","Cancelar"};
        final Integer[] icons = new Integer[] {R.drawable.ic_action_camera, R.drawable.ic_action_picture, R.drawable.ic_action_cancel};
        ListAdapter adapter = new ArrayAdapterWithIcon(this, items, icons);

        new AlertDialog.Builder(this).setTitle("Escolha uma opção")
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item ) {

                	 if (items[item].equals("Câmera"))
                     {
                		                		 
                         startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), 0);
                     }
                     else if (items[item].equals("Galeria"))
                     {
                         Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         startActivityForResult(intent, 2);
      
                     }
                     else if (items[item].equals("Cancelar")) {
                         dialog.dismiss();
                     }
                	
                }
        }).show();
    	
    }
 
   
@SuppressLint("SimpleDateFormat")

public boolean add_os2(View v){  
		EditText descricao = (EditText) findViewById(R.id.descricao);
		String var1 = removerAcentos(descricao.getText().toString());
		
	   	String var0 = numero.getText().toString();
	   	
     if(var0.equals("")){
    	 
    	 Toast.makeText(getBaseContext(), getString(R.string.os_deve), Toast.LENGTH_LONG).show();
    	 return false;
     }
     if(var1.equals("")){
    	 
    	 Toast.makeText(getBaseContext(), getString(R.string.equipamento_deve), Toast.LENGTH_LONG).show();
    	 return false;
     }
     
     if(novoCliente.getText().toString().equals("") && cliente.getText().toString().equals("") ){
	   		Toast.makeText(getBaseContext(), getString(R.string.cliente_deve), Toast.LENGTH_LONG).show();
	    	 return false;
	   	}
      if(db.disp(var0) > 0){
    		
    		Toast.makeText(getBaseContext(), var0 +" "+ getString(R.string.ja_cadastrado), Toast.LENGTH_LONG).show();
   
   	}else{
    	
    	EditText foto = (EditText) findViewById(R.id.url);
    	EditText defeito = (EditText) findViewById(R.id.editDefeito);
    	EditText acessorio = (EditText) findViewById(R.id.editAcessorio);
    	
    	String var2 = foto.getText().toString();
    	String var3 = removerAcentos(cliente.getText().toString());
    	String var4 = removerAcentos(defeito.getText().toString());
    	String var5 = removerAcentos(acessorio.getText().toString());
    	String var6 = removerAcentos(novoCliente.getText().toString());
    	String var7 = novoTelefone.getText().toString();
    	String var8 = removerAcentos(novoEndereco.getText().toString());
    	String var9 = mostraTelefone.getText().toString();
    	String var10 = mostraEndereco.getText().toString();
    	
    	// data
    	Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
         int varInt = 0;
        if(!var7.equals("")){
        	try {
        		varInt = Integer.parseInt(var7);
        	} catch(NumberFormatException nfe) {
        	} 
    	}else{
    		varInt = 0;
    	}
if(var2.equals("")){
	/**
Bitmap image = BitmapFactory.decodeResource(getResources(),
			R.drawable.no_image);
ByteArrayOutputStream stream = new ByteArrayOutputStream();
image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
byte imageInByte[] = stream.toByteArray();
**/
if(var3.equals("")){
	Log.v("aviso", "nova3");
	db.criaCliente(var6, varInt, var8, 0);
	var3 = var6;
}else{
	int varInt9=0;
	try {
		varInt9 = Integer.parseInt(var9);
	} catch(NumberFormatException nfe) {
	} 
	varInt 	= varInt9;
	var8 	= var10;
}
int var0m = 0;
try {
	var0m = Integer.parseInt(var0);
} catch(NumberFormatException nfe) {
} 

//numero, descrição, foto, cliente, datahora, status(1 - bancada), localização, defeito, acessórios, obsservações, valor
long ultimo = db.addContact(new Contact(var0m, var1, null, var3, sdf.format(d), "1", "",0,var4,var5,"",0.00,0,0,0,0,varInt,var8));

Log.v("aviso", "ultimo inserido "+String.valueOf(ultimo));
if(db.getDefInt("contagem").equals("y")){
	db.mudaIdAuto(String.valueOf(ultimo));
	
}
Toast.makeText(getBaseContext(), R.string.aviso_nova_os, Toast.LENGTH_LONG).show();

}else{
Bitmap image = ShrinkBitmap(var2, 480, 320);
ByteArrayOutputStream stream = new ByteArrayOutputStream();
image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
byte imageInByte[] = stream.toByteArray();
if(var3.equals("")){
	Log.v("aviso", "nova3");
	db.criaCliente(var6, varInt, var8, 0);
	var3 	= var6;
	
}else{
	int varInt9=0;
	try {
		varInt9 = Integer.parseInt(var9);
	} catch(NumberFormatException nfe) {
	} 
	varInt 	= varInt9;
	var8 	= var10;
}

int var0m = 0;
try {
	var0m = Integer.parseInt(var0);
} catch(NumberFormatException nfe) {
}
//numero, descrição, foto, cliente, datahora, status(1 - bancada), localização, defeito, acessórios, obsservações, valor
long ultimo = db.addContact(new Contact(var0m, var1, imageInByte, var3, sdf.format(d), "1", "",0,var4,var5,"",0.00,0,0,0,0,varInt,var8));
if(db.getDefInt("contagem").equals("y")){
	db.mudaIdAuto(String.valueOf(ultimo));
	
}
Toast.makeText(getBaseContext(), R.string.aviso_nova_os, Toast.LENGTH_LONG).show();

}

Intent it = new Intent(NovaOsActivity.this,MainActivity.class);  
startActivity(it);

return true;
    	}
	return false;
    }


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    

    if (resultCode == RESULT_OK) {
        if (requestCode == 0) {
        	Bitmap bp = (Bitmap) data.getExtras().get("data");
        	viewImage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewImage.setImageBitmap(bp);

            // Chame este método pra obter a URI da imagem
            Uri uri = getImageUri(getApplicationContext(), bp);

            // Em seguida chame este método para obter o caminho do arquivo
            File file = new File(getRealPathFromURI(uri));

           // System.out.println(file.getPath());
            EditText url = (EditText) findViewById(R.id.url);
            url.setText(file.getPath());
            if(novoCliente.length() > 0){
            LayNovoCliente.setVisibility(View.VISIBLE);
            }
            if(cliente.length() > 0){
            	LayCliente.setVisibility(View.VISIBLE);
                }
            
            
        } else if (requestCode == 2) {

            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Log.w("path of image from gallery......******************.........", picturePath+"");
            
            EditText url = (EditText) findViewById(R.id.url);
            url.setText(picturePath);
           
            viewImage.setImageBitmap(thumbnail);
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

public void novoCliente(View v){
	cliente.setText("");
	LayNovoCliente.setVisibility(View.VISIBLE);
	LayCliente.setVisibility(View.GONE);
}
public void clientes(View v){
	from = new String[] {DataBaseHandler.KEY_CLIENTE_NAME, DataBaseHandler.KEY_CLIENTE_TELEFONE, DataBaseHandler.KEY_CLIENTE_ENDERECO, DataBaseHandler.KEY_CLIENTE_UNICO};
    
    // Ids of views in listview_layout
    to = new int[] { R.id.txt,R.id.txtTelefone, R.id.txtEndereco, R.id.keyId};        
    
    cursor = db.getAllClientes();
    if(cursor.getCount() > 0 ){
     showdialog();
    }
	
}

@SuppressWarnings("deprecation")
private void showdialog()
{
    listDialog = new Dialog(this);
    listDialog.setTitle(getString(R.string.clientes));
     LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     View v = li.inflate(R.layout.main, null, false);
     listDialog.setContentView(v);
     listDialog.setCancelable(true);
     //there are a lot of settings, for dialog, check them all out!

     final ListView list1 = (ListView) listDialog.findViewById(R.id.list);
     list1.setOnItemClickListener(this);
     registerForContextMenu(list1);
     list1.setAdapter(new SimpleCursorAdapter(this, R.layout.listview_clientes, cursor, from, to));
     //now that the dialog is set up, it's time to show it
     listDialog.show();
    
     list1.setOnItemLongClickListener(new OnItemLongClickListener() {

         public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                 int pos, long id) {
        	 onCreateContextMenu(null, arg1, null);
        	    final TextView nome 	= (TextView) arg1.findViewById(R.id.txt);
        	    final TextView ids 		= (TextView) arg1.findViewById(R.id.keyId);
        		final String idString = ids.getText().toString();
        		
        		Log.v("RESPONSE","pos: "+ nome.getText().toString()+ idString);
        		
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg1.getContext());
        		alertDialogBuilder.setTitle(getString(R.string.quer_excluir_cliente) + nome.getText().toString()+"?").setIcon(R.drawable.ic_action_discard)
        		.setCancelable(false)
        		.setPositiveButton("OK",
        		  new DialogInterface.OnClickListener() {

        			public void onClick(DialogInterface dialog,int id) {
        				db.deleteCliente(idString);
        				listDialog.cancel();
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

             return true;
         }
     }); 
}


public void onItemClick(AdapterView<?> arg0, final View arg1, final int arg2, long arg3)
{
	LayNovoCliente.setVisibility(View.GONE);
	novoCliente.setText("");  
	final TextView nome 	= (TextView)arg1.findViewById(R.id.txt);
	final TextView telefone = (TextView)arg1.findViewById(R.id.txtTelefone);
	final TextView endereco = (TextView)arg1.findViewById(R.id.txtEndereco);
	final TextView unico 	= (TextView)arg1.findViewById(R.id.keyId);
	
	mostraTelefone.setText(telefone.getText().toString());
	mostraEndereco.setText(endereco.getText().toString());
	cliente.setText(nome.getText().toString());
	
	LayCliente.setVisibility(View.VISIBLE);
	
	
    
                   listDialog.cancel();
             
}



@Override
public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
		long arg3) {
	// TODO Auto-generated method stub
	return false;
}

public static String removerAcentos(String str) {
    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
}
}
