package com.mgoficina;

import java.util.ArrayList;
import java.util.List;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.mgoficina.R;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListaTrabalhosActivity extends SherlockActivity {
ArrayList<Contact> imageArry = new ArrayList<Contact>();
ListaTrabalhosAdapter adapter;
String trab;

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);

DataBaseHandler db = new DataBaseHandler(this);
Bundle bundle = this.getIntent().getExtras();

String coluna = bundle.get("STATUS").toString();
String tipo = bundle.get("TIPO").toString();
//Log.v("aviso", coluna);
getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));

getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Inserting Contacts
List<Contact> contacts = null;

if(tipo.equals("lote")){
	contacts = db.getTrabalhosOpcao(coluna, 2);
	getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.lote_n)+ coluna+"</b></font>"));
	
	//Log.v("aviso", "lote");
}else if(tipo.equals("os")) {
	trab = db.getTitleTrabalhos(coluna);
	contacts = db.getTrabalhosOpcao(coluna, 3);
	getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+trab+"</b></font>"));
	getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#CCD3D6\"><b>"+String.valueOf(db.conta(coluna))+"</b></font>"));
	//Log.v("aviso", "false");
}else if(tipo.equals("entrada")) {
	trab = db.getTitleTrabalhos(coluna);
	contacts = db.getTrabalhosOpcao(coluna, 1);
	getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.caixa_entrada)+"</b></font>"));
	
	getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#CCD3D6\"><b>"+String.valueOf(db.contaHome(1))+"</b></font>"));
	//Log.v("aviso", "false");

}else if(tipo.equals("dados")) {
	trab = db.getTitleTrabalhos(coluna);
	contacts = db.getTrabalhosOpcao(coluna, 4);
	getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.dados_nao)+"</b></font>"));
	getSupportActionBar().setSubtitle(Html.fromHtml("<font color=\"#CCD3D6\"><b>"+String.valueOf(db.contaHome(2))+"</b></font>"));
	//Log.v("aviso", "false");

}

for (Contact cn : contacts) {

//add contacts data in arrayList 
imageArry.add(cn);

}
adapter = new ListaTrabalhosAdapter(this, R.layout.activity_lista_trabalhos,imageArry); 
final ListView dataList = (ListView) findViewById(R.id.list);
dataList.setAdapter(adapter);

dataList.setOnItemClickListener(new OnItemClickListener() {
public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	TextView param = (TextView) v.findViewById(R.id.ListaId);
	String var1 = param.getText().toString();

	Intent it = new Intent(ListaTrabalhosActivity.this,SingleActivity.class);
	it.putExtra("STATUS", trab);
	it.putExtra("ID", var1);
    startActivity(it); 
}
});
}

public boolean onOptionsItemSelected(MenuItem item) {
	Intent it = new Intent(ListaTrabalhosActivity.this, MainActivity.class);
	startActivity(it);  
        return true;
    
}
@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	this.finish();
}
}
