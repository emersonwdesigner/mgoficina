package com.mgoficina;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.mgoficina.R;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class Funcoes {

	
	@SuppressWarnings("deprecation")
	public void avisaSemConexao(Context c, String title, String message ){
		final AlertDialog alertDialog = new AlertDialog.Builder(c).create();
		 alertDialog.setTitle(title);
	  	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	  	 public void onClick(final DialogInterface dialog, final int which) {

	  		//startActivity(new Intent(c, MainActivity.class));
	  	 }
	  	 });
	  	alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_alert);
   	 	alertDialog.show();
	}
	
	public String moeda(double valor){
		DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));  
		formatoDois.setMinimumFractionDigits(2);   
		formatoDois.setParseBigDecimal (true);  
		
		return "R$ "+formatoDois.format(valor);
	}
	
	public String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public String limitaLetras(String s, int qtd){
		String r = "";
		if(s.length() < qtd ){
			return s;
		}else{
		if(s.length() == 0){
			
			return r;	
		}else{
		String qtdeStr = s.substring(0,qtd);
		
		return qtdeStr+"...";
		}
		
		}
	}
	
	public String telefoneFormat(String s){
		if(s.length() < 4 ){
			if(s.equals("0") || s.length() == 0 ){
				return "";
			}else{
			return s;
			}
		}
		if(s.equals("0") || s.length() == 0 ){
			return "";
			
			
	}else{
		String inTel = s.substring(0,4);
		String fimTel = s.substring(4);
		return inTel+"-"+fimTel;
	}
		
		
	}
	
	public String geraUnico(String s){
		
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        String i = s.substring(0,1);
		String f = s.substring(1);
        
        return i+sdf.format(d)+f;
	}
}
