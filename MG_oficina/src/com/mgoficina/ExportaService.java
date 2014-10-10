package com.mgoficina;

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
import com.mgoficina.R;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

@SuppressLint("NewApi")
public class ExportaService extends Service implements Runnable {

	
	DataBaseHandler db = new DataBaseHandler(this);
	@Override
	public void onCreate() {
		
		
		new Thread(ExportaService.this).start();
		
		
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	private void notifica(final int icon, final String titulo, final String descricao){
		int num = (int)Math.round(Math.random());
		//Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Uri soundUri = Uri.parse("android.resource://"+ this.getPackageName() + "/" + R.raw.notifica2);
		final NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		final Notification nt = new Notification(icon,titulo, System.currentTimeMillis());
		nt.flags |= Notification.FLAG_AUTO_CANCEL;
		PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this.getApplicationContext(), MainActivity.class), 0);
		nt.setLatestEventInfo(this, titulo, descricao, p);
		nt.vibrate = new long[]{100,100};
 		nt.sound = soundUri;
		notificationManager.notify(num, nt);
		
	}
	
	@SuppressWarnings({ "deprecation" })
	@SuppressLint("NewApi")
	@Override
	public void run() {
		HttpClient client = new DefaultHttpClient();
		final HttpPost post 		= new HttpPost("http://appoficina.atwebpages.com/exportalote.php");
		final HttpPost postLotes 	= new HttpPost("http://appoficina.atwebpages.com/exportalotefechado.php");
		final HttpPost postUser 	= new HttpPost("http://appoficina.atwebpages.com/exportauser.php");
		final HttpPost postclientes = new HttpPost("http://appoficina.atwebpages.com/exporta_cliente.php");
		List<Contact> user  = db.getUserExporta(0,2);
		List<Contact> lotes = db.getLotesExporta();
		List<Contact> clientes = db.getClientesExporta();
		List<Contact> itens = db.getLotes(0, true); 
		
		int total_lotes 	= lotes.size();
		int total_clientes 	= clientes.size();
		int total_user 		= user.size();
		int total 			= itens.size();
		
		int totalParaExportar = total_user + total_lotes + total + total_clientes ;
		int qtde = 0;
		Log.v("RESPONSE","total"+ totalParaExportar );
		
		
        if(totalParaExportar == 0){
        	Log.v("RESPONSE","vazio");
        	notifica(R.drawable.ic_alert, getString(R.string.app_name), getString(R.string.sincronizar_sem_dados));
        }else{
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
    		String codigo = db.getCodigo();
    		
    		
    		//COMEÇA EXPORTAR o USER
    		Log.v("RESPONSE","Qtde user" + total_user);
    		if(total_user > 0){
    			for (Contact cnUser : user) {
    	        List<NameValuePair> pairsUser = new ArrayList<NameValuePair>();	
    	        pairsUser.add(new BasicNameValuePair("nome",cnUser.getEnderecocliente()));
    	        pairsUser.add(new BasicNameValuePair("login", codigo));
    	        pairsUser.add(new BasicNameValuePair("email", cnUser.getDescricao()));
    	        pairsUser.add(new BasicNameValuePair("senha", cnUser.getDatahora()));
    	        
    	        Log.v("RESPONSE","codigo user "+codigo);
    			try{
    			    // Retorna o resultado
    				UrlEncodedFormEntity entUser = new UrlEncodedFormEntity(pairsUser);
    	            postUser.setEntity(entUser);
    	            HttpResponse responsePOSTUser = client.execute(postUser);  
    	            
    	            String responseBodyUser = EntityUtils.toString(responsePOSTUser.getEntity());
    	            Log.v("RESPONSE","Resposta user "+responseBodyUser);
    	            if(responseBodyUser.equals("Y")){
    	            	//String idS = String.valueOf(cnLotes.getID()); 
    	            	//db.mudaExporta(idS, 1 );
    	            	Log.v("RESPONSE","sim user");
    	            	
    	            	db.mudaUser("1", 1);
    	            	
    	            	qtde++;
    	            }else{
    	            	Log.v("RESPONSE","não user");
    	            }
    	           
    			}catch (ClientProtocolException e) {
    				
    		    } catch (IOException e) {
    		        // TODO Auto-generated catch block
    		    }
    			}
    	        }
    		
    //COMEÇA EXPORTAR OS ITENS		
    		if(total > 0){
        for (Contact cn : itens) {
		
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();	
        pairs.add(new BasicNameValuePair("os", String.valueOf(cn.getOs())));
        pairs.add(new BasicNameValuePair("cliente", cn.getCliente()));
        pairs.add(new BasicNameValuePair("descricao", cn.getDescricao()));
        pairs.add(new BasicNameValuePair("datahora", cn.getDatahora()));
        pairs.add(new BasicNameValuePair("status", cn.getStatus()));
        pairs.add(new BasicNameValuePair("local", cn.getLocal()));
        pairs.add(new BasicNameValuePair("defeito", cn.getDefeito()));
        pairs.add(new BasicNameValuePair("acessorio", cn.getAcessorio()));
        pairs.add(new BasicNameValuePair("obs", cn.getObs()));
        pairs.add(new BasicNameValuePair("TelCliente", String.valueOf(cn.getTelefonecliente())));
        pairs.add(new BasicNameValuePair("EndCliente", cn.getEnderecocliente()));
        String str = String.valueOf(cn.getValor());
        
        Log.v("valor",str);
        pairs.add(new BasicNameValuePair("valor", str));
        pairs.add(new BasicNameValuePair("lote", String.valueOf(cn.getLote())));
        pairs.add(new BasicNameValuePair("codigo", codigo));
        
        
		 
        Log.v("RESPONSE","resu "+ cn.getID() + codigo + String.valueOf(cn.getLote()) );
        
		try{
			
		    // Retorna o resultado
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(pairs);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);  
            
            String responseBody = EntityUtils.toString(responsePOST.getEntity());
            Log.v("RESPONSE","Resposta"+responseBody);
            if(responseBody.equals("Y")){
            	String idS = String.valueOf(cn.getID()); 
            	db.mudaExporta(idS, 1 );
            	Log.v("RESPONSE","sim");
            	qtde++;
            }else{
            	Log.v("RESPONSE","não");
            }
           
		}catch (ClientProtocolException e) {
			
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
        }
    		}
        
        
        //COMEÇA EXPORTAR LOTES
    		Log.v("RESPONSE","Qtde Lotes" + total_lotes);
    		if(total_lotes > 0){
    	        for (Contact cnLotes : lotes) {
    			
    	        List<NameValuePair> pairsLotes = new ArrayList<NameValuePair>();	
    	        pairsLotes.add(new BasicNameValuePair("data", cnLotes.getDatahora()));
    	        pairsLotes.add(new BasicNameValuePair("valor", String.valueOf(cnLotes.getValor())));
    	        pairsLotes.add(new BasicNameValuePair("codigo", codigo));
    	        pairsLotes.add(new BasicNameValuePair("id_lote", String.valueOf(cnLotes.getID())));
    			 
    			try{
    			    // Retorna o resultado
    				UrlEncodedFormEntity entLotes = new UrlEncodedFormEntity(pairsLotes);
    	            postLotes.setEntity(entLotes);
    	            HttpResponse responsePOSTLotes = client.execute(postLotes);  
    	            
    	            String responseBodyLotes = EntityUtils.toString(responsePOSTLotes.getEntity());
    	            Log.v("RESPONSE","Resposta lotes "+responseBodyLotes);
    	            if(responseBodyLotes.equals("Y")){
    	            	String idS = String.valueOf(cnLotes.getID()); 
    	            	db.mudaLoteExporta(idS, 1 );
    	            	Log.v("RESPONSE","sim Lotes");
    	            	db.mudaStatusLote(String.valueOf(cnLotes.getID()), 1);
    	            	qtde++;
    	            }else{
    	            	Log.v("RESPONSE","não lotes");
    	            }
    	           
    			}catch (ClientProtocolException e) {
    				
    		    } catch (IOException e) {
    		        // TODO Auto-generated catch block
    		    }
    	        }
    	    		}
    		
    		//COMEÇA EXPORTAR CLIENTES
    		Log.v("RESPONSE","Qtde clientes" + total_clientes);
    		if(total_clientes > 0){
    	        for (Contact cnclientes : clientes) {
    			
    	        List<NameValuePair> pairsclientes = new ArrayList<NameValuePair>();	
    	        
    	        pairsclientes.add(new BasicNameValuePair("id", String.valueOf(cnclientes.getID())));
    	        pairsclientes.add(new BasicNameValuePair("nome", String.valueOf(cnclientes.getOs())));
    	        pairsclientes.add(new BasicNameValuePair("telefone", String.valueOf(cnclientes.getLiga())));
    	        pairsclientes.add(new BasicNameValuePair("endereco", cnclientes.getDescricao()));
    	        pairsclientes.add(new BasicNameValuePair("codigo", codigo)); 
    	        
    	      //  Log.v("RESPONSE","quem " + removerAcentos(cnclientes.getDescricao()) );
    			try{
    			    // Retorna o resultado
    				UrlEncodedFormEntity entclientes = new UrlEncodedFormEntity(pairsclientes);
    	            postclientes.setEntity(entclientes);
    	            HttpResponse responsePOSTclientes = client.execute(postclientes);  
    	            
    	            String responseBodyclientes = EntityUtils.toString(responsePOSTclientes.getEntity());
    	            Log.v("RESPONSE","Resposta cliente "+responseBodyclientes);
    	            if(responseBodyclientes.equals("Y")){
    	            	//String idS = String.valueOf(cnLotes.getID()); 
    	            	db.mudaExportaCliente(String.valueOf(cnclientes.getID()), 1 );
    	            	Log.v("RESPONSE","sim cliente");
    	            	qtde++;
    	            }else{
    	            	Log.v("RESPONSE","não cliente");
    	            }
    	           
    			}catch (ClientProtocolException e) {
    				
    		    } catch (IOException e) {
    		        // TODO Auto-generated catch block
    		    }
    	        }
    	    		}
    		
    		
        
        if(totalParaExportar == qtde){
			Notification nts = new Notification(R.drawable.ic_ok,getString(R.string.app_name), System.currentTimeMillis());
	        nts.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.sincronizar_fechamento) + " ("+qtde+" itens)", p);
			nts.vibrate = new long[]{100,100};
	 		nts.sound = soundUri;
			notificationManager.notify(num, nts);
		}else{
			Notification nts;
			
				if(qtde > 0){
					 nts = new Notification(R.drawable.ic_alert,getString(R.string.app_name), System.currentTimeMillis());
					nts.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.sincronizar_erro_alguns ) + " ("+qtde+" itens)", p);
			}else{
					 nts = new Notification(R.drawable.ic_erro,getString(R.string.app_name), System.currentTimeMillis());
					nts.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.sincronizar_erro ) + " ("+qtde+" itens)", p);
				
			}
	        
	        nts.vibrate = new long[]{100,100};
	 		nts.sound = soundUri;
			notificationManager.notify(num, nts);
		}
    }
		
		
		
		
		Log.v("aviso_ex2","exp2");
		
		stopService(new Intent("MGO_INICIAR_EXPORTACAO"));
	}

}
