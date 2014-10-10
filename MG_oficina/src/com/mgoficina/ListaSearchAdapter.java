package com.mgoficina;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import com.mgoficina.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaSearchAdapter extends ArrayAdapter<Contact>{
Context context;
int layoutResourceId;
ArrayList<Contact> data=new ArrayList<Contact>();
public ListaSearchAdapter(Context context, int layoutResourceId, ArrayList<Contact> data) {
super(context, layoutResourceId, data);
this.layoutResourceId = layoutResourceId;
this.context = context;
this.data = data;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
		
	
View row = convertView;

ImageHolder holder = null;
if(row == null)
{
LayoutInflater inflater = ((Activity)context).getLayoutInflater();
row = inflater.inflate(layoutResourceId, parent, false);
holder = new ImageHolder();
holder.numOrdem = (TextView)row.findViewById(R.id.numOrdem);
holder.Descricao = (TextView)row.findViewById(R.id.Descricao);
holder.thumb = (ImageView)row.findViewById(R.id.thumb);
holder.ListaCliente = (TextView)row.findViewById(R.id.ListaCliente);
holder.ListaStatus = (TextView)row.findViewById(R.id.ListaSearchStatus);
holder.listaId = (TextView)row.findViewById(R.id.ListaId);


row.setTag(holder);
}
else
{
holder = (ImageHolder)row.getTag();
}
Contact picture = data.get(position);
holder.numOrdem.setText("Ordem de serviço: "+picture ._os);
holder.Descricao.setText(""+picture ._descricao);
holder.ListaCliente.setText("Clientes: "+picture ._cliente);
int liga = picture ._liga;
if(liga ==1){
	holder.ListaStatus.setText("Status: Em lote");
	row.setBackgroundColor(Color.parseColor("#60FD3300"));
}else{
	holder.ListaStatus.setText("Status: "+picture ._status);	
}
holder.listaId.setText(picture ._idv);

//convert byte to bitmap take from contact class
byte[] outImage=picture._image;
ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
Bitmap theImage = BitmapFactory.decodeStream(imageStream);
holder.thumb.setImageBitmap(theImage);
/**
if (position % 2 == 0) {
	row.setBackgroundColor(Color.WHITE);
} else {
	row.setBackgroundColor(Color.parseColor("#80f6f6f6"));
}
**/
return row;
}
static class ImageHolder
{
ImageView thumb;
TextView listaId;
TextView numOrdem;
TextView txtTitle;
TextView Descricao;
TextView ListaCliente;
TextView ListaStatus;
}


}




