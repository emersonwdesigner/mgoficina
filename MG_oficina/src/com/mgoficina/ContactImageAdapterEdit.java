package com.mgoficina;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import com.mgoficina.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactImageAdapterEdit extends ArrayAdapter<Contact>{
Context context;
int layoutResourceId;
ArrayList<Contact> data=new ArrayList<Contact>();
public ContactImageAdapterEdit(Context context, int layoutResourceId, ArrayList<Contact> data) {
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
holder.txtIdv = (TextView)row.findViewById(R.id.textID);
holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
holder.txtDescricao = (TextView)row.findViewById(R.id.txtDescricao);
holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
holder.txtCliente = (TextView)row.findViewById(R.id.txtCliente);
holder.txtLocal = (TextView)row.findViewById(R.id.textLocal);
holder.txtDefeito = (TextView)row.findViewById(R.id.txtDefeito);
holder.txtAcessorio = (TextView)row.findViewById(R.id.textAcessorio);


row.setTag(holder);
}
else
{
holder = (ImageHolder)row.getTag();
}
Contact picture = data.get(position);
holder.txtIdv.setText(picture ._idv); 
holder.txtTitle.setText(picture ._os);
holder.txtDescricao.setText(picture ._descricao);
holder.txtCliente.setText(picture ._cliente);
holder.txtLocal.setText(picture ._local);
holder.txtDefeito.setText(picture ._defeito);
holder.txtAcessorio.setText(picture ._acessorio);

//convert byte to bitmap take from contact class
byte[] outImage=picture._image;
ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
Bitmap theImage = BitmapFactory.decodeStream(imageStream);
holder.imgIcon.setImageBitmap(theImage);
/**
if (position % 2 == 0) {
	row.setBackgroundColor(Color.WHITE);
} else {
	row.setBackgroundColor(Color.parseColor("#DCE5E9"));
} **/
return row;
}
static class ImageHolder
{
ImageView imgIcon;
TextView txtIdv;
TextView txtTitle;
TextView txtDescricao;
TextView txtCliente;
TextView txtLocal;
TextView txtDefeito;
TextView txtAcessorio;
}
}
