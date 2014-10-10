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
import android.os.IBinder;
import android.util.Log;
import android.app.Service;
import android.content.Intent;

public class DeletaService extends Service implements Runnable {

	
	DataBaseHandler db = new DataBaseHandler(this);
	@Override
	public void onCreate() {
		new Thread(DeletaService.this).start();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		Log.v("aviso", "Inicia deletar");
		final String codigo = db.getCodigo();
		final HttpClient client = new DefaultHttpClient();
		final HttpPost post = new HttpPost("http://appoficina.atwebpages.com/deleta_iten.php");
		
		final List<NameValuePair> pairs = new ArrayList<NameValuePair>();	
		final List<Contact> itens = db.getLotesDeletar();
		
		
		new Thread(new Runnable() {
	         @Override
	         public void run() {
	        	 
	  	 for (Contact cn : itens) {
		try {
			pairs.add(new BasicNameValuePair("codigo", codigo));
			pairs.add(new BasicNameValuePair("id", String.valueOf(cn.getOs())));
			
			Log.v("aviso", "Item para deletar " + cn.getOs());
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(pairs);
			
			// RESULTADOS DOS ITENS
	        post.setEntity(ent);
	        HttpResponse responsePOST = client.execute(post);  
	        String responseBody = EntityUtils.toString(responsePOST.getEntity());
	        
	        Log.v("aviso", "Resposta " + responseBody);
	        
	        // "N" o iten não existe no BD do server
	        // "Y" o iten existe no BD do server e foi deletado
			if(responseBody.contains("Y")){
			db.delete(String.valueOf(cn.getOs()), true);	
			}else if(responseBody.contains("N")){
			db.delete(String.valueOf(cn.getOs()), true);		
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}	
	       } 
	  	 
	  	Log.v("aviso", "Deletados excluidos");
	         }}).start();
		
		
		stopService(new Intent("MGO_DELETA"));
        }	


}
