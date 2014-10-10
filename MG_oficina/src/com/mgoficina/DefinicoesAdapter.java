package com.mgoficina;

import com.mgoficina.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DefinicoesAdapter extends ArrayAdapter<String>{
	 private final Context context;
     private final String[] values;

     public DefinicoesAdapter(Context context, String[] values) {
             super(context, R.layout.definicoes, values);
             this.context = context;
             this.values = values;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
             LayoutInflater inflater = (LayoutInflater) context
                             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             View rowView = inflater.inflate(R.layout.definicoes, parent, false);
             TextView textView = (TextView) rowView.findViewById(R.id.label);
             ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
             textView.setText(values[position]);

             // Change icon based on name
             String s = values[position];

             System.out.println(s);

             if (s.equals("sincronizar")) {
                     imageView.setImageResource(R.drawable.ic_action_about);
             } else if (s.equals("sincronizar")) {
                     imageView.setImageResource(R.drawable.ic_action_camera);
             } 

             return rowView;
     }
}



