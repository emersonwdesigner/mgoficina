package com.mgoficina;

import java.util.List;
import com.mgoficina.R;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

public class FechaLoteService extends Service implements Runnable {

	
	DataBaseHandler db = new DataBaseHandler(this);
	
	private long idUltino;
	@Override
	public void onCreate() {
		
		
		
		new Thread(FechaLoteService.this).start();
		
	}

	@SuppressWarnings({"deprecation"})
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
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		List<Contact> contacts = db.getLotes(0, false); 
		
		int qtd = 0;
        if(contacts.isEmpty()){
        	Log.v("RESPONSE","vazio");
        	notifica(R.drawable.ic_alert, getString(R.string.app_name), getString(R.string.fechar_lote_sem_dados));
        }else{
        	//notifica(R.drawable.icon_32, getString(R.string.fechar_lote), getString(R.string.fechar_lote_andamento));
        	
        	int num = (int)Math.round(Math.random());
    		//Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    		Uri soundUri = Uri.parse("android.resource://"+ this.getPackageName() + "/" + R.raw.notifica2);
    		final NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			final Notification nt = new Notification(R.drawable.icon_32,getString(R.string.app_name), System.currentTimeMillis());
    		nt.flags |= Notification.FLAG_AUTO_CANCEL;
    		PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this.getApplicationContext(), MainActivity.class), 0);
    		nt.setLatestEventInfo(this, getString(R.string.fechar_lote), getString(R.string.fechar_lote_andamento), p);
    		nt.vibrate = new long[]{100,100};
     		nt.sound = soundUri;
    		notificationManager.notify(num, nt);
        	
        	idUltino = db.criaLote();
        	int strLote = (int) idUltino;
		double soma = 0;
        for (Contact cn : contacts) {
		try{
			
			Log.v("RESPONSE","resu "+ cn.getID());
			String idS = String.valueOf(cn.getID()); 
			soma+= cn.getValor();
        	db.mudaLiga(idS, 1, strLote, 0);
        	qtd++;
		}catch (Exception e) {
			
	    }
        
    }
        
        db.mudaValorLote(String.valueOf(strLote), soma);
        Log.v("ligas",String.valueOf(soma));
        final Notification nts = new Notification(R.drawable.ic_ok,getString(R.string.app_name), System.currentTimeMillis());
        nts.setLatestEventInfo(this, getString(R.string.app_name), getString(R.string.fechar_lote_termino) + " ("+qtd+" itens)", p);
		nts.vibrate = new long[]{100,100};
 		nts.sound = soundUri;
		notificationManager.notify(num, nts);
       // notifica(R.drawable.ic_ok, getString(R.string.app_name), getString(R.string.fechar_lote_termino) + "("+qtd+" itens");
        }
		//if(totalDB == totalReplicado){
			
		//}
		Log.v("aviso_ex2","exp2");
		stopService(new Intent("MGO_FECHAR_LOTE"));
	}


	
}
