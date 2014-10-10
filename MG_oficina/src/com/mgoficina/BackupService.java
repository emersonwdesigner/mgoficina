package com.mgoficina;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mgoficina.R;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackupService extends Service implements Runnable {

	
	DataBaseHandler db = new DataBaseHandler(this);
	@Override
	public void onCreate() {
		
		
		new Thread(BackupService.this).start();
		
		
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		//notifica o início
		
		int num = (int)Math.round(Math.random());
		//Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Uri soundUri = Uri.parse("android.resource://"+ this.getPackageName() + "/" + R.raw.notifica2);
		final NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		final Notification nt = new Notification(R.drawable.icon_32,getString(R.string.app_name), System.currentTimeMillis());
		nt.flags |= Notification.FLAG_AUTO_CANCEL;
		PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this.getApplicationContext(), MainActivity.class), 0);
		nt.setLatestEventInfo(this, getString(R.string.sincronizar), getString(R.string.sincronizar_andamento), p);
		nt.vibrate = new long[]{100,100};
 		nt.sound = soundUri;
		notificationManager.notify(num, nt);
		
		//busca dados no server
		String codigo = db.getCodigo();
		HttpClient client = new DefaultHttpClient();
		
		final HttpPost post = new HttpPost("http://appoficina.atwebpages.com/listar.php");
		final HttpPost postLotes = new HttpPost("http://appoficina.atwebpages.com/listarlotes.php");
		final HttpPost postClientes = new HttpPost("http://appoficina.atwebpages.com/listarclientes.php");
		
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();	
        pairs.add(new BasicNameValuePair("codigo", codigo));
        Log.v("RESPONSE","Resposta");
        
        
	    // Retorna o resultado
		
        
		try{
			
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(pairs);
			
			// RESULTADOS DOS ITENS
	        post.setEntity(ent);
	        HttpResponse responsePOST = client.execute(post);  
	        String responseBody = EntityUtils.toString(responsePOST.getEntity());
	        
			JSONObject json = new JSONObject(responseBody);

		     // Recupera a lista product do JSON
		    JSONArray products = json.getJSONArray("product");

		    int length = products.length();
		    Log.v("RESPONSE","conta ITENS "+String.valueOf(length));
		    int i =0;
		    for( i = 0; i < length; ++i) {
		        JSONObject product = products.getJSONObject(i);
		        
		        int os 				= product.getInt("os");
		        String cliente 		= product.getString("cliente");
		        String descricao 	= product.getString("descricao");
		        String datahora 	= product.getString("datahora");
		        String status 		= product.getString("status");
		        String local 		= product.getString("local");
		        String defeito 		= product.getString("defeito");
		        String acessorio 	= product.getString("acessorio");
		        String obs 			= product.getString("obs");
		        double valor 		= product.getDouble("valor");
		        int lote 			= product.getInt("lote");
		        int TelCliente		= product.getInt("telefone_cliente");
		        String EndCliente	= product.getString("endereco_cliente");
				
		        
		        Bitmap image = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_image);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte imageInByte[] = stream.toByteArray();  
				int liga = 0;
				if(lote != 0){  liga = 1;}
				
			db.addContact(new Contact(os, descricao,imageInByte, cliente, datahora, status, local, liga, defeito, acessorio, obs, valor, lote, 1, 1, 0, TelCliente,EndCliente));
			Log.v("RESPONSE","ITEN Inserido");
		    }
		    
		 // RESULTADOS DOS LOTES
	        postLotes.setEntity(ent);
	        HttpResponse responsePOSTLotes = client.execute(postLotes);  
	        String responseBodyLotes = EntityUtils.toString(responsePOSTLotes.getEntity());
	        
			JSONObject jsonLotes = new JSONObject(responseBodyLotes);

		     // Recupera a lista product do JSON
		    JSONArray productsLotes = jsonLotes.getJSONArray("product");

		    int lengthLotes = productsLotes.length();
		    Log.v("RESPONSE","conta lotes"+String.valueOf(lengthLotes));
		     i =0;
		    for( i = 0; i < lengthLotes; ++i) {
		        JSONObject productLotes = productsLotes.getJSONObject(i);
		        
		        String datahora 	= productLotes.getString("data");
		        double valor 		= productLotes.getDouble("valor");
				
		        db.criaLoteBackup(datahora, valor);
			Log.v("RESPONSE","lote Inserido");
		    }
		    
		 // RESULTADOS DOS CLIENTES
	        postClientes.setEntity(ent);
	        HttpResponse responsePOSTClientes = client.execute(postClientes);  
	        String responseBodyClientes = EntityUtils.toString(responsePOSTClientes.getEntity());
	        
			JSONObject jsonClientes = new JSONObject(responseBodyClientes);

		     // Recupera a lista product do JSON
		    JSONArray productsClientes = jsonClientes.getJSONArray("product");

		    int lengthClientes = productsClientes.length();
		    Log.v("RESPONSE","conta clientes"+String.valueOf(lengthClientes));
		     i =0;
		    for( i = 0; i < lengthClientes; ++i) {
		        JSONObject productClientes = productsClientes.getJSONObject(i);
		        
		        String nomeCliente		= productClientes.getString("nome");
		        int telefoneCliente 	= productClientes.getInt("telefone");
		        String enderecoCliente 	= productClientes.getString("endereco");
		        Log.v("RESPONSE","cliente:"+nomeCliente+telefoneCliente+enderecoCliente);
		        
		        db.criaCliente(nomeCliente, telefoneCliente, enderecoCliente, 1);
			Log.v("RESPONSE","cliente Inserido");
		    }
		    
		    
		    //notifica o fim
		    
		    Notification nts = new Notification(R.drawable.ic_ok,getString(R.string.app_name), System.currentTimeMillis());
	        nts.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.sincronizar_fechamento), p);
			nts.vibrate = new long[]{100,100};
	 		nts.sound = soundUri;
			notificationManager.notify(num, nts);
		    Log.v("RESPONSE","Todos Inserido");
		}catch (ClientProtocolException e) {
			
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stopService(new Intent("MGO_RESPOSTA"));
        }	


}
