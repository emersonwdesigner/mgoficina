package com.mgoficina;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.mgoficina.R;

public class FormBuscaActivity extends SherlockActivity {
	EditText entradaTexto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
		getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.title_activity_form_busca)+"</b></font>"));
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.activity_form_busca);
		
		entradaTexto = (EditText) findViewById(R.id.editBuscar);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				entradaTexto.setEnabled(true);
				
				if(checkedId == R.id.radioButton1){
					entradaTexto.setText("");
					entradaTexto.setInputType(InputType.TYPE_CLASS_NUMBER);
					
				}else if(checkedId == R.id.radioButton2){
					entradaTexto.setText("");
					entradaTexto.setInputType(InputType.TYPE_CLASS_TEXT);
				}else if(checkedId == R.id.radioButton3){
					entradaTexto.setText("");
					entradaTexto.setInputType(InputType.TYPE_CLASS_TEXT);
				}
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}

	
	public void Buscar(View v){
		
					RadioGroup radioGroupId = (RadioGroup) findViewById(R.id.radioGenderGroup);
					
					int id = radioGroupId.getCheckedRadioButtonId();
					if (id == -1){
						Toast.makeText(getBaseContext(), "Selecione o tipo de pesquisa!", Toast.LENGTH_LONG).show();
					}else{
		        
					int selectedOption = radioGroupId.getCheckedRadioButtonId();
		            RadioButton radioGenderButton = (RadioButton) findViewById(selectedOption);
		            	EditText param = (EditText) findViewById(R.id.editBuscar);
		            	String var1 = param.getText().toString();
		            	
		        		
		            	Intent it = new Intent(FormBuscaActivity.this,SearchActivity.class);
		        		if(radioGenderButton.getText().equals("Orden de serviço")){
		        			it.putExtra("COLUNA", "os.name");
		        		}else if(radioGenderButton.getText().equals("Cliente")){
		        			it.putExtra("COLUNA", "cliente");
		        		}else if(radioGenderButton.getText().equals("Descrição")){ 
		        			it.putExtra("COLUNA", "descricao");
		        		}
		        		
		        		
		        		it.putExtra("KEY", var1);
		                startActivity(it); 	
					}
		
	}

	 @Override
 	protected void onStop() {
 		// TODO Auto-generated method stub
 		super.onStop();
 		this.finish();
 	}
}
