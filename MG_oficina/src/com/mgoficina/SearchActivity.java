package com.mgoficina;

import java.util.ArrayList;
import java.util.List;
import com.actionbarsherlock.app.SherlockActivity;
import com.mgoficina.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends SherlockActivity  {
ArrayList<Contact> imageArry = new ArrayList<Contact>();
ListaSearchAdapter adapter;

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);

DataBaseHandler db = new DataBaseHandler(this);
/**
* CRUD Operations
* */
Bundle bundle = this.getIntent().getExtras();

String coluna = bundle.get("COLUNA").toString();
String key = bundle.get("KEY").toString();

getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.resultado_para) +": "+ key +"</b></font>"));

List<Contact> contacts = db.searchOs(coluna, key);
//List<Contact> contacts = db.getAllContacts("Pronto");
for (Contact cn : contacts) {

//add contacts data in arrayList
imageArry.add(cn);

}
adapter = new ListaSearchAdapter(this, R.layout.activity_lista_search,imageArry); 
final ListView dataList = (ListView) findViewById(R.id.list);
dataList.setAdapter(adapter);

dataList.setOnItemClickListener(new OnItemClickListener() {
public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	TextView param = (TextView) v.findViewById(R.id.ListaId);
	String var1 = param.getText().toString();

	Intent it = new Intent(SearchActivity.this,SingleActivity.class);
	
	it.putExtra("ID", var1);
    startActivity(it); 
}
});
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

//abrir o single

public void single(View v){
	
	TextView param = (TextView) findViewById(R.id.textID);
	String var1 = param.getText().toString();
	

	Intent it = new Intent(SearchActivity.this,SingleActivity.class);
	
	it.putExtra("ID", var1);
    startActivity(it); 
}


//Ações da lista

final Context context = this;


@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	this.finish();
}
}
