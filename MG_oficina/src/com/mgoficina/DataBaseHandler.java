package com.mgoficina;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.mgoficina.R;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SimpleDateFormat")
public class DataBaseHandler extends SQLiteOpenHelper {
	Funcoes funcoes = new Funcoes();
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ordens_servico";

	// Tabelas
	private static final String TABLE_USER = "user";
	private static final String TABLE_CONTACTS = "os";
	private static final String TABLE_STATUS = "trabalhos";
	private static final String TABLE_LOTES = "lotes";
	private static final String TABLE_DEFINICOES = "definicoes";
	private static final String TABLE_CLIENTES = "clientes";

	// Tabela usuário
	static final String KEY_USER_ID = "_id";
	static final String KEY_USER_NAME = "user_nome";
	static final String KEY_USER_LOGIN = "user_login";
	static final String KEY_USER_PASSWORD = "password";
	static final String KEY_USER_STATUS = "user_status";
	static final String KEY_USER_EMAIL = "user_email";

	// Tabela de status
	static final String KEY_STATUS_ID = "_id";
	static final String KEY_STATUS_KEY = "key";
	static final String KEY_STATUS_NAME = "name";
	static final String KEY_STATUS_INFO = "info";
	static final String KEY_STATUS_ICON = "icon";

	// Tabela de os
	static final String KEY_ID = "_id";
	static final String KEY_OS = "name";
	static final String KEY_IMAGE = "image";
	static final String KEY_DESCRICAO = "descricao";
	static final String KEY_CLIENTE = "cliente";
	private static final String KEY_DATAHORA = "datahora";
	private static final String KEY_STATUS = "status";
	private static final String KEY_LOCAL = "local";
	private static final String KEY_LIGA = "liga";
	private static final String KEY_DEFEITO = "defeito";
	private static final String KEY_ACESSORIO = "acessorio";
	private static final String KEY_OBS = "obs";
	private static final String KEY_VALOR = "valor";
	private static final String KEY_LOTE = "lote";
	private static final String KEY_EXPORTA = "exporta";
	private static final String KEY_ENTRADA = "entrada";
	private static final String KEY_DELETADO = "deletado";
	private static final String KEY_TELEFONE_CLIENTE = "telefone_cliente";
	private static final String KEY_ENDERECO_CLIENTE = "endereco_cliente";

	// Tabela de lotes
	static final String KEY_LOTE_ID = "_id";
	static final String KEY_LOTE_DATA = "data_lote";
	static final String KEY_LOTE_STATUS = "status";
	static final String KEY_LOTE_VALOR = "valor_lote";

	// Tabela de definições
	static final String KEY_DEF_ID = "_id";
	static final String KEY_DEF_NAME = "def_name";
	static final String KEY_DEF_INT = "def_inteiro";
	static final String KEY_DEF_STRING = "def_string";

	// Tabela clientes
	static final String KEY_CLIENTE_ID = "_id";
	static final String KEY_CLIENTE_ID_OS = "cliente_id_os";
	static final String KEY_CLIENTE_NAME = "cliente_nome";
	static final String KEY_CLIENTE_TELEFONE = "cliente_telefone";
	static final String KEY_CLIENTE_ENDERECO = "cliente_endereco";
	static final String KEY_CLIENTE_EXPORTA = "cliente_exporta";
	static final String KEY_CLIENTE_UNICO = "cliente_unico";
	static final String KEY_CLIENTE_EMAIL = "cliente_email";
		
	public DataBaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_USER_ID + " INTEGER PRIMARY KEY," 
				+ KEY_USER_NAME + " TEXT, "
				+ KEY_USER_LOGIN + " TEXT, "
				+ KEY_USER_PASSWORD + " TEXT," 
				+ KEY_USER_STATUS
				+ " INTEGER, "
				+ KEY_USER_EMAIL + " TEXT)";
		db.execSQL(CREATE_USER_TABLE);

		String CREATE_LOTE_TABLE = "CREATE TABLE " + TABLE_LOTES + "("
				+ KEY_LOTE_ID + " INTEGER PRIMARY KEY, " 
				+ KEY_LOTE_DATA + " TEXT, "+KEY_LOTE_STATUS+" INTERGER, "+KEY_LOTE_VALOR+" DOUBLE (10,2))";
		db.execSQL(CREATE_LOTE_TABLE);					
	
				
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_OS + " INTEGER, "
				+ KEY_DESCRICAO + " TEXT," 
				+ KEY_IMAGE + " BLOB" + ", "
				+ KEY_CLIENTE + " TEXT, " 
				+ KEY_DATAHORA + " TEXT, "
				+ KEY_STATUS + " TEXT, " 
				+ KEY_LOCAL + " TEXT, " 
				+ KEY_LIGA	+ " INTEGER, " 
				+ KEY_DEFEITO + " TEXT, " 
				+ KEY_ACESSORIO	+ " TEXT, " 
				+ KEY_OBS + " TEXT, " 
				+ KEY_VALOR	+ " DOUBLE (10,2), " 
				+ KEY_LOTE + " INTEGER, " 
				+ KEY_EXPORTA + " INTEGER," 
				+ KEY_ENTRADA + " INTEGER,"
				+ KEY_DELETADO + " INTEGER,"
				+ KEY_TELEFONE_CLIENTE + " INTEGER,"
				+ KEY_ENDERECO_CLIENTE + " TEXT )";
		db.execSQL(CREATE_CONTACTS_TABLE);

		// tabela status

		String CREATE_STATUS_TABLE = "CREATE TABLE " + TABLE_STATUS + "("
				+ KEY_STATUS_ID + " INTEGER PRIMARY KEY," 
				+ KEY_STATUS_KEY + " TEXT, " 
				+ KEY_STATUS_NAME + " TEXT," 
				+ KEY_STATUS_INFO + " TEXT,"
				+ KEY_STATUS_ICON + " BLOB)";
		db.execSQL(CREATE_STATUS_TABLE);
		
		// tabela definições

				String CREATE_DEF_TABLE =  "CREATE TABLE " + TABLE_DEFINICOES + "("
						+ KEY_DEF_ID + " INTEGER PRIMARY KEY," 
						+ KEY_DEF_NAME + " TEXT,"
						+ KEY_DEF_INT + " INTEGER,"
						+ KEY_DEF_STRING + " TEXT)";
				db.execSQL(CREATE_DEF_TABLE);

				
				String CREATE_CLIENTE_TABLE = "CREATE TABLE " + TABLE_CLIENTES + "("
						+ KEY_CLIENTE_ID + " INTEGER PRIMARY KEY," 
						+ KEY_CLIENTE_NAME + " TEXT,"
						+ KEY_CLIENTE_TELEFONE + " INTEGER,"
						+ KEY_CLIENTE_ENDERECO + " TEXT,"
						+ KEY_CLIENTE_EXPORTA + " INTEGER," 
						+ KEY_CLIENTE_UNICO + " TEXT,"
						+ KEY_CLIENTE_EMAIL+ " TEXT)";
				db.execSQL(CREATE_CLIENTE_TABLE);		
			//INSERIR  OU IGNORE INTO bookmarks ( users_id , lessoninfo_id )  VALUES ( 123 ,  456 )
			db.execSQL("INSERT INTO "+TABLE_STATUS+"("+KEY_STATUS_KEY+", "+KEY_STATUS_NAME+", "+KEY_STATUS_INFO+", "+KEY_STATUS_ICON+") VALUES(1, 'Para orçamento', 'Equipamento para orçamento','"+R.drawable.ic_action_paste +"')");
			db.execSQL("INSERT INTO "+TABLE_STATUS+"("+KEY_STATUS_KEY+", "+KEY_STATUS_NAME+", "+KEY_STATUS_INFO+", "+KEY_STATUS_ICON+") VALUES(2, 'Aguardando', 'Aguardando a autorização do cliente','"+R.drawable.ic_action_attachment +"')");
			db.execSQL("INSERT INTO "+TABLE_STATUS+"("+KEY_STATUS_KEY+", "+KEY_STATUS_NAME+", "+KEY_STATUS_INFO+", "+KEY_STATUS_ICON+") VALUES(6, 'Executando', 'Executando o reparo','"+R.drawable.ic_action_play_over_video+"')");
			db.execSQL("INSERT INTO "+TABLE_STATUS+"("+KEY_STATUS_KEY+", "+KEY_STATUS_NAME+", "+KEY_STATUS_INFO+", "+KEY_STATUS_ICON+") VALUES(3, 'Prontos', 'Reparados e prontos para entrega','"+R.drawable.ic_action_important +"')");
			db.execSQL("INSERT INTO "+TABLE_STATUS+"("+KEY_STATUS_KEY+", "+KEY_STATUS_NAME+", "+KEY_STATUS_INFO+", "+KEY_STATUS_ICON+") VALUES(4, 'Entregues', 'Reparado e entregue ao cliente','"+R.drawable.ic_action_good+"')");
			db.execSQL("INSERT INTO "+TABLE_STATUS+"("+KEY_STATUS_KEY+", "+KEY_STATUS_NAME+", "+KEY_STATUS_INFO+", "+KEY_STATUS_ICON+") VALUES(5, 'Devolvidos', 'Devolvido sem reparo','"+R.drawable.ic_action_undo+"')");
			
			
			db.execSQL("INSERT INTO "+TABLE_CLIENTES+"("+KEY_CLIENTE_NAME+", "+KEY_CLIENTE_TELEFONE+", "+KEY_CLIENTE_ENDERECO+", "+KEY_CLIENTE_EXPORTA+", "+KEY_CLIENTE_UNICO+", "+KEY_CLIENTE_EMAIL+") VALUES('Emerson', 99999999, 'Jose Gomes',0,'"+funcoes.geraUnico("Emerson")+"','' )");
			
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOTES);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFINICOES);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
	// Create tables again
	onCreate(db);
	
	
	}

	/**
	* All CRUD(Create, Read, Update, Delete) Operations
	*/
	//adicionar status
	public void addStatus(String var, String var1, String var2, int var3) {
		//Log.v("aviso", "Adicionar status");
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_STATUS_KEY, var); // Contact Name
	values.put(KEY_STATUS_NAME, var1);
	values.put(KEY_STATUS_INFO, var2); // Contact Phone
	values.put(KEY_STATUS_ICON, var3); // Contact Phone

	// Inserting Row
	db.insert(TABLE_STATUS, null, values);
	db.close(); // Closing database connection
	}
	
	//Cria lote
		public long criaLote() {
			//Log.v("aviso", "cria lote");
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		// data
    	Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		values.put(KEY_LOTE_DATA, sdf.format(d)); 
		values.put(KEY_LOTE_STATUS, 0); 

		// Inserting Row
		long id = db.insert(TABLE_LOTES, null, values);
		
		return id;
		}
		
		//--------------CLIENTES-------------------------//
		//Cria Cliente
				public long criaCliente(String nome, int telefone, String endereco, int tipo) {
				SQLiteDatabase db = this.getWritableDatabase();
				
				ContentValues values = new ContentValues();
				values.put(KEY_CLIENTE_NAME, nome);
				values.put(KEY_CLIENTE_TELEFONE, telefone);
				values.put(KEY_CLIENTE_ENDERECO, endereco);
				values.put(KEY_CLIENTE_EXPORTA, tipo);
				values.put(KEY_CLIENTE_UNICO, funcoes.geraUnico(nome));
				values.put(KEY_CLIENTE_EMAIL, tipo);

				// Inserting Row
				long id = db.insert(TABLE_CLIENTES, null, values);
				return id;
				}
	
				//edita cliente
				public int editarCliente(String id, String nome, String telefone, String endereco) {
					SQLiteDatabase db = this.getWritableDatabase();
					
					ContentValues values = new ContentValues();
					
					values.put(KEY_CLIENTE_NAME, nome);
					values.put(KEY_CLIENTE_TELEFONE, telefone);
					values.put(KEY_CLIENTE_ENDERECO, endereco);
					values.put(KEY_CLIENTE_EXPORTA, 0);
					
					return db.update(TABLE_CLIENTES, values, KEY_CLIENTE_ID + " = ?",
					new String[] { id });

					}
				// Deleta cliente
				public void deleteCliente(String id) {
				SQLiteDatabase db = this.getWritableDatabase();
				db.delete(TABLE_CLIENTES, KEY_CLIENTE_ID + " = ?",
				new String[] { id });
				db.close();
				}
				
				//Exibe todos clientes
				
				public Cursor getAllClientes() {
					
				// Select All Query
				String selectQuery = "SELECT * FROM "+TABLE_CLIENTES+" ORDER BY "+KEY_CLIENTE_NAME+" ASC";

				SQLiteDatabase db = this.getWritableDatabase();
				Cursor c = db.rawQuery(selectQuery, null);
					 if (c != null) {
					  c.moveToFirst();
					 }
					 db.close();
					 return c;
				
				}
				
				
				public List<Contact> getClientesExporta() {
					
				List<Contact> contactList = new ArrayList<Contact>();
				String 	selectQuery = "SELECT * FROM "+TABLE_CLIENTES +" WHERE "+KEY_CLIENTE_EXPORTA+ " = 0";  

				//Log.v("cliente", selectQuery);
				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
				do {
				Contact contact = new Contact();
				contact.setID(cursor.getInt(0));
				contact.setOs(cursor.getInt(1));
				contact.setLiga(cursor.getInt(2));
				contact.setDescricao(cursor.getString(3));
				// Adding contact to list
				contactList.add(contact);
				} while (cursor.moveToNext());
				}
				// close inserting data from database
				db.close();
				cursor.close();
				// return contact list
				return contactList;

				}
			
				public int mudaExportaCliente(String id, int tipo) {
					SQLiteDatabase db = this.getWritableDatabase();
					
					ContentValues values = new ContentValues();
					
					values.put(KEY_CLIENTE_EXPORTA, tipo);
					
						
					//String str = String.valueOf(id); 
					// updating row
					return db.update(TABLE_CLIENTES, values, KEY_CLIENTE_ID + " = ?",
					new String[] { id });

					}
				
		//--------------DEFINIÇÕES-------------------------//
		
		//Cria Definições
		public void criaDefinicoes(String data, int i, String s) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_DEFINICOES, KEY_DEF_NAME + " = ?", new String[] {data});
		ContentValues values = new ContentValues();
		values.put(KEY_DEF_NAME, data);
		values.put(KEY_DEF_INT, i);
		values.put(KEY_DEF_STRING, s);

		// Inserting Row
		db.insert(TABLE_DEFINICOES, null, values);
		
		}
		
		// Definições String
		
		public String getDefInt(String var) {
			
			String selectQuery = "SELECT * FROM "+TABLE_DEFINICOES+" WHERE "+KEY_DEF_NAME+"='"+var+"' LIMIT 1";
			//Log.v("aviso", selectQuery);
			    SQLiteDatabase db = this.getWritableDatabase();
			    
			    Cursor cursor = db.rawQuery(selectQuery, null);
			    StringBuilder sb = new StringBuilder();

			    cursor.moveToFirst();

			    /*********** Fazer isto por cada coluna ***************/
			    String nome_da_coluna_string = cursor.getString(cursor.getColumnIndex(KEY_DEF_STRING));
			    sb.append(nome_da_coluna_string);
			    /******************************************************/

			    cursor.close();

			    return sb.toString();
		}
		
		
		
		//Cria lote
				public void criaLoteBackup(String data, double valor) {
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(KEY_LOTE_DATA, data); 
				values.put(KEY_LOTE_STATUS, 1);
				values.put(KEY_LOTE_VALOR, valor);

				// Inserting Row
				db.insert(TABLE_LOTES, null, values);
				
				}
		//Cria user
				public void criaUser(String codigo, String nome, String senha, String email ) {
					//Log.v("aviso", codigo+nome+senha);
				SQLiteDatabase db = this.getWritableDatabase();

				ContentValues values = new ContentValues();
				
				values.put(KEY_USER_LOGIN, codigo); 
				values.put(KEY_USER_NAME, nome); 
				values.put(KEY_USER_PASSWORD, senha);  
				values.put(KEY_USER_STATUS, 0); 
				values.put(KEY_USER_EMAIL, email); 

				 db.insert(TABLE_USER, null, values);
				 //Log.v("aviso", "cria user 1");
							
				}
		
	public long addContact(Contact contact) {
		//Log.v("aviso", "Adicionar");
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_OS, contact._os); // Contact Name
	values.put(KEY_DESCRICAO, contact._descricao);
	values.put(KEY_IMAGE, contact._image); // Contact Phone
	values.put(KEY_CLIENTE, contact._cliente); // Contact Phone
	values.put(KEY_DATAHORA, contact._datahora);
	values.put(KEY_STATUS, contact._status);
	values.put(KEY_LOCAL, contact._local);
	values.put(KEY_LIGA, contact._liga);
	values.put(KEY_DEFEITO, contact._defeito);
	values.put(KEY_ACESSORIO, contact._acessorio);
	values.put(KEY_OBS, contact._obs);
	values.put(KEY_VALOR, contact._valor);
	values.put(KEY_LOTE, contact._lote);
	values.put(KEY_EXPORTA, contact._exporta);
	values.put(KEY_ENTRADA, contact._entrada);
	values.put(KEY_DELETADO, contact._deletado);
	values.put(KEY_TELEFONE_CLIENTE, contact._telefonecliente);
	values.put(KEY_ENDERECO_CLIENTE, contact._enderecocliente);

	// Inserting Row
	long id = db.insert(TABLE_CONTACTS, null, values);
	db.close(); // Closing database connection
	return id;
	}

	// Getting single contact
	Contact getContact(int id) {
	SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
	KEY_OS, KEY_DESCRICAO, KEY_IMAGE, KEY_CLIENTE, KEY_DATAHORA, KEY_STATUS, KEY_LOCAL }, KEY_ID + "=?",
	new String[] { String.valueOf(id) }, null, null, null, null);
	if (cursor != null)
	cursor.moveToFirst();

	Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
	cursor.getInt(1), cursor.getString(1), cursor.getBlob(1), cursor.getString(1),
	cursor.getString(1), cursor.getString(1), cursor.getString(1), cursor.getInt(1),
	cursor.getString(1), cursor.getString(1), cursor.getString(1), cursor.getDouble(1),
	cursor.getInt(1), cursor.getInt(1), cursor.getInt(1), cursor.getInt(1), cursor.getInt(1), cursor.getString(1));

	// return contact
	return contact;

	}
	
	
	
	// Exibe os trabalhos por seleção
			public Cursor getTrabalhos(String var) {
				
				//Log.v("aviso", "lISTA Trabalho");
			// Select All Query
			String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_STATUS+"='"+var+"' ORDER BY "+KEY_ID+" ASC";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
				 if (c != null) {
				  c.moveToFirst();
				 }
				 db.close();
				 c.close();
				 return c;
			
			}
			
			// Exibir lotes
			public Cursor getAllLotes() {
				
				//Log.v("aviso", "lISTA Lotes");
			// Select All Query
			String selectQuery = "SELECT * FROM "+TABLE_LOTES+" ORDER BY "+KEY_LOTE_ID+" ASC";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
				 if (c != null) {
				  c.moveToFirst();
				 }
				 db.close();
				 return c;
			
			}
			// Exibir dados
						public Cursor getAlldados() {
							
						String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_EXPORTA+"='0' ORDER BY "+KEY_OS+" DESC";

						SQLiteDatabase db = this.getWritableDatabase();
						Cursor c = db.rawQuery(selectQuery, null);
							 if (c != null) {
							  c.moveToFirst();
							 }
							 db.close();
							 return c;
						
						}
						
						// Exibir lotes
						public Cursor getAllEntrada() {
						String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_ENTRADA+"='0' AND "+KEY_LIGA+"='0' ORDER BY "+KEY_OS+" DESC";

						SQLiteDatabase db = this.getWritableDatabase();
						Cursor c = db.rawQuery(selectQuery, null);
							 if (c != null) {
							  c.moveToFirst();
							 }
							 db.close();
							 return c;
						
						}
	// Getting All sTATUS
		public Cursor getAllStatus() {
			
			//Log.v("aviso", "lISTA STATUS");
		// Select All Query
		String selectQuery = "SELECT * FROM "+TABLE_STATUS+" ORDER BY "+KEY_STATUS_ID+" ASC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
			 if (c != null) {
			  c.moveToFirst();
			 }
			 db.close();
			 return c;
		
		}
		
		// Getting All Trabalhos
		public List<Contact> getTrabalhosOpcao(String var, int tipo) {
			
			//Log.v("aviso", "Lista trabalhos");
			List<Contact> contactList = new ArrayList<Contact>();
			// Select All Query
			
			String selectQuery = null;
			
		if(tipo == 1){
			//Log.v("aviso", "Lista entrada");
			selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_ENTRADA+"='0' AND "+KEY_LIGA+"='0' AND "+KEY_DELETADO+"='0' ORDER BY "+KEY_OS+" DESC";
		}else if(tipo == 2){
		
			selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_LOTE+"='"+var+"' AND "+KEY_LIGA+"='1' AND "+KEY_DELETADO+"='0' ORDER BY "+KEY_OS+" DESC";
		}else if(tipo == 3){
			//Log.v("aviso", "Lista status");
			selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_STATUS+"='"+var+"'  AND "+KEY_LIGA+"='0' AND "+KEY_DELETADO+"='0' ORDER BY "+KEY_OS+" DESC";
		}else if(tipo == 4){
			//Log.v("aviso", "Lista trabalhos false");
			selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_EXPORTA+"='0' AND "+KEY_DELETADO+"='0' ORDER BY "+KEY_OS+" DESC";
		}

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
		do {
		Contact contact = new Contact();
		contact.setIdv(cursor.getString(0));
		contact.setOs(cursor.getInt(1));
		contact.setDescricao(cursor.getString(2));
		contact.setImage(cursor.getBlob(3));
		contact.setCliente(cursor.getString(4));
		// Adding contact to list
		contactList.add(contact);
		} while (cursor.moveToNext());
		}
		// close inserting data from database
		db.close();
		cursor.close();
		// return contact list
		return contactList;

		}
		
		// Getting All Trabalhos
				public List<Contact> getEntrada() {
					
					//Log.v("aviso", "Lista trabalhos");
					List<Contact> contactList = new ArrayList<Contact>();
					// Select All Query
					
					String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_ENTRADA+"='0' ORDER BY "+KEY_ID+" DESC";

				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
				do {
				Contact contact = new Contact();
				contact.setIdv(cursor.getString(0));
				contact.setOs(cursor.getInt(1));
				contact.setDescricao(cursor.getString(2));
				contact.setEntrada(cursor.getInt(15));
				// Adding contact to list
				contactList.add(contact);
				} while (cursor.moveToNext());
				}
				// close inserting data from database
				db.close();
				cursor.close();
				// return contact list
				return contactList;

				}
	// Getting All Contacts
	public List<Contact> getAllContacts(String var) {
		
		//Log.v("aviso", "List");
	List<Contact> contactList = new ArrayList<Contact>();
	// Select All Query
	String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_STATUS+"='"+var+"' ORDER BY "+KEY_OS+" DESC";

	SQLiteDatabase db = this.getWritableDatabase();
	Cursor cursor = db.rawQuery(selectQuery, null);
	// looping through all rows and adding to list
	if (cursor.moveToFirst()) {
	do {
	Contact contact = new Contact();
	contact.setID(Integer.parseInt(cursor.getString(0)));
	contact.setOs(cursor.getInt(1));
	contact.setIdv(cursor.getString(0));
	contact.setDescricao(cursor.getString(2));
	contact.setImage(cursor.getBlob(3));
	contact.setCliente(cursor.getString(4));
	contact.setDatahora(cursor.getString(5));
	contact.setStatus(cursor.getString(6));
	contact.setLocal(cursor.getString(7));
	// Adding contact to list
	contactList.add(contact);
	} while (cursor.moveToNext());
	}
	// close inserting data from database
	db.close();
	cursor.close();
	// return contact list
	return contactList;

	}
	
	// get itens (acao situação da liga 0=primeira açao, 1=está em lote fechado, 2 ) 
		public List<Contact> getLotes(int acao, boolean b) {
			
			////Log.v("aviso", "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_LIGA+"='0' ORDER BY "+KEY_NAME+" DESC");
		List<Contact> contactList = new ArrayList<Contact>();
		
		String 	selectQuery;
		if(b){
		// Select All Query
		selectQuery = "SELECT * FROM "+TABLE_CONTACTS +" WHERE "+KEY_EXPORTA+" = '"+acao+"' AND "+KEY_DELETADO+"='0'";  
		}else{
		selectQuery = "SELECT * FROM "+TABLE_CONTACTS +" WHERE "+KEY_LIGA+" = '"+acao+"' AND "+KEY_STATUS+" ='4' AND "+KEY_DELETADO+"='0' "; 	
		}
		
		//Log.v("ligas", selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		//Log.v("ligas", String.valueOf(cursor.getCount()));
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
		do {
		Contact contact = new Contact();
		contact.setID(Integer.parseInt(cursor.getString(0)));
		contact.setOs(cursor.getInt(cursor.getColumnIndex(KEY_OS)));
		contact.setCliente(cursor.getString(cursor.getColumnIndex(KEY_CLIENTE)));
		contact.setDescricao(cursor.getString(cursor.getColumnIndex(KEY_DESCRICAO)));
		contact.setDatahora(cursor.getString(cursor.getColumnIndex(KEY_DATAHORA)));
		contact.setStatus(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
		contact.setLocal(cursor.getString(cursor.getColumnIndex(KEY_LOCAL)));
		contact.setDefeito(cursor.getString(cursor.getColumnIndex(KEY_DEFEITO)));
		contact.setAcessorio(cursor.getString(cursor.getColumnIndex(KEY_ACESSORIO)));
		contact.setObs(cursor.getString(cursor.getColumnIndex(KEY_OBS)));
		contact.setValor(cursor.getDouble(cursor.getColumnIndex(KEY_VALOR)));
		contact.setLote(cursor.getInt(cursor.getColumnIndex(KEY_LOTE)));
		contact.setTelefonecliente(cursor.getInt(cursor.getColumnIndex(KEY_TELEFONE_CLIENTE)));
		contact.setEnderecocliente(cursor.getString(cursor.getColumnIndex(KEY_ENDERECO_CLIENTE)));
		// Adding contact to list
		contactList.add(contact);
		} while (cursor.moveToNext());
		}
		// close inserting data from database
		db.close();
		cursor.close();
		// return contact list
		return contactList;

		}
	
public List<Contact> getLotesDeletar() {
			
			////Log.v("aviso", "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_LIGA+"='0' ORDER BY "+KEY_NAME+" DESC");
		List<Contact> contactList = new ArrayList<Contact>();
		
		String 	selectQuery;
		// Select All Query
		selectQuery = "SELECT * FROM "+TABLE_CONTACTS +" WHERE "+KEY_DELETADO+" = '1'";  
		
		//Log.v("ligas", selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		//Log.v("ligas", String.valueOf(cursor.getCount()));
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
		do {
		Contact contact = new Contact();
		contact.setID(Integer.parseInt(cursor.getString(0)));
		contact.setOs(cursor.getInt(cursor.getColumnIndex(KEY_OS)));
		// Adding contact to list
		contactList.add(contact);
		} while (cursor.moveToNext());
		}
		// close inserting data from database
		db.close();
		cursor.close();
		// return contact list
		return contactList;

		} 
public Contact getUser() {
		
		String 	selectQuery = "SELECT * FROM "+TABLE_USER +"  LIMIT 1 ";  
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		Contact contact = new Contact();
		if (cursor.moveToFirst()) {
			contact.setID(cursor.getInt(0));
			contact.setEnderecocliente(cursor.getString(1));//nome
			contact.setCliente(cursor.getString(2));//login
			contact.setDatahora(cursor.getString(3));//senha
			contact.setDefeito(cursor.getString(5));//email
			
		}
		// close inserting data from database
		db.close();
		cursor.close();
		
		// return contact list
		return contact;

		}

public List<Contact> getUserExporta(int acao, int acao2) {
	
	////Log.v("aviso", "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_LIGA+"='0' ORDER BY "+KEY_NAME+" DESC");
List<Contact> contactList = new ArrayList<Contact>();

String 	selectQuery = "SELECT * FROM "+TABLE_USER +" WHERE "+KEY_USER_STATUS+" = '"+acao+"' OR "+KEY_USER_STATUS+" = '"+acao2+"' LIMIT 1 "; 
//Log.v("RESPONSE", selectQuery);
SQLiteDatabase db = this.getWritableDatabase();
Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
if (cursor.moveToFirst()) {
do {
	Contact contact = new Contact();
	contact.setID(Integer.parseInt(cursor.getString(0)));
	contact.setEnderecocliente(cursor.getString(1));//nome
	contact.setCliente(cursor.getString(2));//login
	contact.setDescricao(cursor.getString(5));//email
	contact.setDatahora(cursor.getString(3));//senha
	contact.setLote(cursor.getInt(4));//status
// Adding contact to list
contactList.add(contact);
} while (cursor.moveToNext());
}
// close inserting data from database
db.close();
cursor.close();
// return contact list
return contactList;

}

public List<Contact> getLotesExporta() {
	
	////Log.v("aviso", "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_LIGA+"='0' ORDER BY "+KEY_NAME+" DESC");
List<Contact> contactList = new ArrayList<Contact>();

String 	selectQuery = "SELECT * FROM "+TABLE_LOTES +" WHERE "+KEY_LOTE_STATUS+" = 0 ";  


//Log.v("lote", selectQuery);
SQLiteDatabase db = this.getWritableDatabase();
Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
if (cursor.moveToFirst()) {
do {
Contact contact = new Contact();
contact.setID(cursor.getInt(0));
contact.setDatahora(cursor.getString(1));
contact.setValor(cursor.getDouble(3));
// Adding contact to list
contactList.add(contact);
} while (cursor.moveToNext());
}
// close inserting data from database
db.close();
cursor.close();
// return contact list
return contactList;

}
	// Search igual ao list contacts
		public List<Contact> searchOs(String var, String var1) {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" INNER JOIN "+TABLE_STATUS+" ON "+TABLE_CONTACTS+"."+KEY_STATUS+" = "+TABLE_STATUS+"."+KEY_STATUS_KEY+" WHERE "+var+" LIKE '%"+var1+"%' AND "+KEY_DELETADO+"='0' ORDER BY "+KEY_OS+" ASC";
		//String selectQuery = "SELECT * FROM contacts WHERE status='"+var+"' ORDER BY name ASC";
		//Log.v("aviso", selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
		do {
		Contact contact = new Contact();
		contact.setOs(cursor.getInt(1));
		contact.setIdv(cursor.getString(0));
		contact.setDescricao(cursor.getString(2));
		contact.setLiga(cursor.getInt(8));
		contact.setImage(cursor.getBlob(3));
		contact.setCliente(cursor.getString(4));
		contact.setStatus(cursor.getString(21));
		// Adding contact to list
		contactList.add(contact);
		} while (cursor.moveToNext());
		}
		// close inserting data from database
		db.close();
		cursor.close();
		// return contact list
		return contactList;

		}
		
					
				// Editar Single
				
				public Contact single(String var) {
					// Select All Query
					String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" INNER JOIN "+TABLE_STATUS+" ON "+TABLE_CONTACTS+"."+KEY_STATUS+" = "+TABLE_STATUS+"."+KEY_STATUS_KEY+" WHERE "+TABLE_CONTACTS+"."+KEY_ID+"='"+var+"' AND "+KEY_DELETADO+"='0'";

					SQLiteDatabase db = this.getWritableDatabase();
					Cursor cursor = db.rawQuery(selectQuery, null);
					// looping through all rows and adding to list
					Contact contact = new Contact();
					if (cursor.moveToFirst()) {
						contact.setImage(cursor.getBlob(3));
						contact.setDatahora(cursor.getString(5));
						contact.setStatus(cursor.getString(21));
						contact.setOs(cursor.getInt(1));
						contact.setCliente(cursor.getString(4));
						contact.setDescricao(cursor.getString(2));
						contact.setLocal(cursor.getString(7));
						contact.setDefeito(cursor.getString(9));
						contact.setAcessorio(cursor.getString(10));
						contact.setObs(cursor.getString(11));
						contact.setValor(cursor.getDouble(12));
						contact.setIdv(cursor.getString(0));
						contact.setLiga(cursor.getInt(8));
						contact.setExporta(cursor.getInt(14));
						contact.setEntrada(cursor.getInt(15));
						contact.setTelefonecliente(cursor.getInt(17));
						contact.setEnderecocliente(cursor.getString(18));
						
						
					}
					// close inserting data from database
					db.close();
					cursor.close();
					// return contact list
					return contact;

					}
				
				public Contact singleEdit(String var) {
				// Select All Query
				String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" INNER JOIN "+TABLE_STATUS+" ON "+TABLE_CONTACTS+"."+KEY_STATUS+" = "+TABLE_STATUS+"."+KEY_STATUS_KEY+" WHERE "+TABLE_CONTACTS+"."+KEY_ID+"='"+var+"' AND "+KEY_DELETADO+"='0'";

				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				Contact contact = new Contact();
				if (cursor.moveToFirst()) {
					contact.setImage(cursor.getBlob(3));
					contact.setOs(cursor.getInt(1));
					contact.setCliente(cursor.getString(4));
					contact.setDescricao(cursor.getString(2));
					contact.setLocal(cursor.getString(7));
					contact.setDefeito(cursor.getString(9));
					contact.setAcessorio(cursor.getString(10));
					contact.setObs(cursor.getString(11));
					contact.setValor(cursor.getDouble(12));
					contact.setIdv(cursor.getString(0));
					contact.setTelefonecliente(cursor.getInt(17));
					contact.setEnderecocliente(cursor.getString(18));
					
				}
				// close inserting data from database
				db.close();
				cursor.close();
				// return contact list
				return contact;

				}
	// Updating single contact
	public int updateContact(Contact contact, String coluna) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_OS, contact.getOs());
	values.put(KEY_IMAGE, contact.getImage());

	// updating row
	return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
	new String[] { String.valueOf(contact.getID()) });

	}

	
	
	
	// Deleting single contact
	public void deleteContact(Contact contact) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
	new String[] { String.valueOf(contact.getID()) });
	db.close();
	}

	// Getting contacts Count
	public int getContactsCount(String var) {
	String countQuery = "SELECT * FROM " + TABLE_CONTACTS+ "WHERE "+KEY_STATUS+"='"+var+"' AND "+KEY_DELETADO+"='0'";
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();

	// return count
	return cursor.getCount();
	}

	
	public int disp(String var) {
		Cursor c = null;
		SQLiteDatabase db = this.getWritableDatabase();
	    try {
	    	
	        String query = "select count(*) from " + TABLE_CONTACTS+ " where "+KEY_DELETADO+"='0' and "+KEY_OS+" = ?";
	        c = db.rawQuery(query, new String[] {var});
	        if (c.moveToFirst()) {
	            return c.getInt(0);
	        }
	        return 0;
	    }
	    finally {
	        if (c != null) {
	            c.close();
	        }
	        if (db != null) {
	            db.close();
	        }
	    }
		
	}
	
	// atualizar
		public int editar(String id, CharSequence coluna, String dados, byte[] imageInByte, String telefone, String endereco) {
		SQLiteDatabase db = this.getWritableDatabase();
		double valor;
		ContentValues values = new ContentValues();
		if(telefone != null){
			int novoTel = 0;
			try {
				novoTel = Integer.parseInt(telefone.replaceAll("[^0-9]", ""));
	    	} catch(NumberFormatException nfe) {
	    	}
		values.put(KEY_TELEFONE_CLIENTE,  novoTel);
		}
		
		if(endereco != null){
		
		values.put(KEY_ENDERECO_CLIENTE, endereco);
		}
				if(coluna.equals("title")){
					int os = 0;
					try {
						 os = Integer.parseInt(dados);
			    	} catch(NumberFormatException nfe) {
			    	}			
					
		values.put(KEY_OS, os);
		}else 	if(coluna.equals("cliente")){
			values.put(KEY_CLIENTE, dados);
			
		}else 	if(coluna.equals("descricao")){
			values.put(KEY_DESCRICAO, dados);
		}else 	if(coluna.equals("defeito")){
			values.put(KEY_DEFEITO, dados);
		}else 	if(coluna.equals("local")){
			values.put(KEY_LOCAL, dados);
		}else 	if(coluna.equals("acessorio")){
			values.put(KEY_ACESSORIO, dados);
		}else 	if(coluna.equals("foto")){
			values.put(KEY_IMAGE, imageInByte);
		}else 	if(coluna.equals("obs")){
			values.put(KEY_OBS, dados);
		}else 	if(coluna.equals("valor")){
			valor = Double.parseDouble(dados);
			values.put(KEY_VALOR, valor);
		}
		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
		new String[] { id });

		}
		
		// atualizar
		
		public int mudaValorLote(String id, double total) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			values.put(KEY_LOTE_VALOR, total);
			
				
			//String str = String.valueOf(id); 
			// updating row
			return db.update(TABLE_LOTES, values, KEY_ID + " = ?",
			new String[] { id });

			}
		
		
		public int mudaIdAuto(String id) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			int os = 0;
			try {
				 os = Integer.parseInt(id);
	    	} catch(NumberFormatException nfe) {
	    	}
			
			values.put(KEY_OS, os);
			
				
			//String str = String.valueOf(id); 
			// updating row
			return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
			new String[] { id });

			}
		public int mudaStatusLote(String id, int tipo) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			values.put(KEY_USER_STATUS, tipo);
			
				
			//String str = String.valueOf(id); 
			// updating row
			return db.update(TABLE_USER, values, KEY_ID + " = ?",
			new String[] { id });

			}
		public int mudaEntrada(String id) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			values.put(KEY_ENTRADA,1);
			
				
			//String str = String.valueOf(id); 
			// updating row
			return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
			new String[] { id });

			}
		
				public int mudaLiga(String id, int liga, int lote, int expo) {
				SQLiteDatabase db = this.getWritableDatabase();
				
				ContentValues values = new ContentValues();
				
				values.put(KEY_LIGA, liga);
				values.put(KEY_LOTE, lote);
				values.put(KEY_EXPORTA, expo);
				
					
				//String str = String.valueOf(id); 
				// updating row
				return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { id });

				}
				public int mudaExporta(String id, int expo) {
					SQLiteDatabase db = this.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put(KEY_EXPORTA, expo);
					return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
					new String[] { id });

					}
				
				public int mudaLoteExporta(String id, int expo) {
					SQLiteDatabase db = this.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put(KEY_LOTE_STATUS, expo);
					return db.update(TABLE_LOTES, values, KEY_ID + " = ?",
					new String[] { id });

					}
				public int mudaUser(String id, int tipo) {
					SQLiteDatabase db = this.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put(KEY_USER_STATUS, tipo);
					//String str = String.valueOf(id); 
					// updating row
					return db.update(TABLE_USER, values, KEY_USER_ID + " = ?", new String[] { id });

					}
				
				public int mudaDadosUser(int tipo,  int senha, String nome, String id) {
					
					SQLiteDatabase db = this.getWritableDatabase();
					ContentValues values = new ContentValues();
					if(tipo == 0){
						values.put(KEY_USER_NAME, nome);
					}else if(tipo ==1){
						values.put(KEY_USER_PASSWORD, senha);	
					}
					//String str = String.valueOf(id); 
					// updating row
					return db.update(TABLE_USER, values, KEY_USER_ID + " = ?", new String[] { id });

					}
	
	public int conta(String var) {

		Cursor c = null;
		SQLiteDatabase db = this.getWritableDatabase();
	    try {
	    	if(var.equals("total")){
	    		c = db.rawQuery("select count(*) from " + TABLE_CONTACTS+ " WHERE "+KEY_DELETADO+"='0'", null);
		        if (c.moveToFirst()) {
		            return c.getInt(0);
		        }
	    	}else{
	        String query = "select count(*) from " + TABLE_CONTACTS+ " where "+KEY_LIGA+"= '0' AND "+KEY_STATUS+" = '"+var+"' AND "+KEY_DELETADO+"='0'";
	        c = db.rawQuery(query, null);
	        if (c.moveToFirst()) {
	            return c.getInt(0);
	        }
	    	}
	        return 0;
	    }
	    finally {
	        if (c != null) {
	            c.close();
	        }
	        if (db != null) {
	            db.close();
	        }
	    }
		
	}
	
public int contaTrabalhos(String var) {
		

		Cursor c = null;
		SQLiteDatabase db = this.getWritableDatabase();
	    try {
	    	
	    	if(var.equals("total")){
	    		c = db.rawQuery("select count(*) from " + TABLE_CONTACTS, null);
		        if (c.moveToFirst()) {
		            return c.getInt(0);
		        }
	    	}else{
	        
	        String query = "SELECT COUNT(*) FROM "+TABLE_CONTACTS+" INNER JOIN "+TABLE_STATUS+" ON "+TABLE_STATUS+"."+KEY_STATUS_KEY+"="+TABLE_CONTACTS+"."+KEY_STATUS+"  WHERE "+KEY_DELETADO+"='0' AND "+KEY_STATUS+" = ? ORDER BY "+TABLE_CONTACTS+"."+KEY_OS+" DESC";
	        c = db.rawQuery(query, new String[] {var});
	        if (c.moveToFirst()) {
	            return c.getInt(0);
	        }
	    	}
	        
	        return 0;
	    }
	    finally {
	        if (c != null) {
	            c.close();
	        }
	        if (db != null) {
	            db.close();
	        }
	    }
		
	}
	
public int contaHome(int tipo) {

	Cursor c = null;
	SQLiteDatabase db = this.getWritableDatabase();
    try {
    	String query = null;
    	if(tipo ==1){
    		query = "select count(*) from " + TABLE_CONTACTS+ " where "+KEY_ENTRADA+"= '0' AND "+KEY_DELETADO+"='0'";	
    	}else if(tipo ==2){
    		query = "select count(*) from " + TABLE_CONTACTS+ " where "+KEY_EXPORTA+"= '0' AND "+KEY_DELETADO+"='0'";
    	}else if(tipo ==3){
    		//Log.v("aviso", "user conta");
    		query = "select count(*) from " + TABLE_USER;
    	}
        
        c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            return c.getInt(0);
    	}
        return 0;
    }
    finally {
        if (c != null) {
            c.close();
        }
        if (db != null) {
            db.close();
        }
    }
	
}
	
	public void delete(String var, boolean tipo) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		if(tipo){
			//Log.v("aviso", "deleta de vez");
		db.delete(TABLE_CONTACTS, KEY_OS + " = ?",
		new String[] { var});
		
		
		}else{
			//Log.v("aviso", "deleta do telefone");
			ContentValues values = new ContentValues();
			
			values.put(KEY_DELETADO, 1);

			// updating row
			db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] { var});
		}
		db.close();
	}
	//id,status,local
	public void atualiza(String var, String var2, String var3, String var4, double var5) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if(var5 != 0){
			values.put(KEY_VALOR, ""+var5+"");
		}
		values.put(KEY_STATUS, ""+var2+"");
		values.put(KEY_LOCAL, ""+var3+"");
		values.put(KEY_OBS, ""+var4+"");
		

		// updating row
		db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] { var});
	}

	public Cursor rawQuery() {
		String selectQuery = "SELECT * FROM "+TABLE_CONTACTS+" WHERE "+KEY_DELETADO+"='0'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		return cursor;
	}

	// Getting All Trabalhos
			public String getTitleTrabalhos(String var) {
				
				//Log.v("aviso", "title trabalhos");
				String selectQuery = 
				        "SELECT * FROM "+TABLE_STATUS+" WHERE "+KEY_STATUS_KEY+"='"+var+"' LIMIT 1";

				    SQLiteDatabase db = this.getWritableDatabase();
				    Cursor cursor = db.rawQuery(selectQuery, null);

				    StringBuilder sb = new StringBuilder();

				    cursor.moveToFirst();

				    /*********** Fazer isto por cada coluna ***************/
				    String nome_da_coluna_string = cursor.getString(cursor.getColumnIndex(KEY_STATUS_NAME));

				    sb.append(nome_da_coluna_string);
				    /******************************************************/

				    cursor.close();

				    return sb.toString();

			}
			
			public String getCodigo() {
				
				String selectQuery =   "SELECT * FROM "+TABLE_USER+" LIMIT 1";

				    SQLiteDatabase db = this.getWritableDatabase();
				    Cursor cursor = db.rawQuery(selectQuery, null);
				    StringBuilder sb = new StringBuilder();
				    cursor.moveToFirst();
				    String nome_da_coluna_string = cursor.getString(cursor.getColumnIndex(KEY_USER_LOGIN));
				    sb.append(nome_da_coluna_string);
				    cursor.close();
				    return sb.toString();
			}
			
				public String getEmail() {
				
				String selectQuery =   "SELECT * FROM "+TABLE_USER+" LIMIT 1";

				    SQLiteDatabase db = this.getWritableDatabase();
				    Cursor cursor = db.rawQuery(selectQuery, null);
				    StringBuilder sb = new StringBuilder();
				    cursor.moveToFirst();
				    String nome_da_coluna_string = cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL));
				    sb.append(nome_da_coluna_string);
				    cursor.close();
				    return sb.toString();
			}
				public String getSenha() {
					
					String selectQuery =   "SELECT * FROM "+TABLE_USER+" LIMIT 1";

					    SQLiteDatabase db = this.getWritableDatabase();
					    Cursor cursor = db.rawQuery(selectQuery, null);
					    StringBuilder sb = new StringBuilder();
					    cursor.moveToFirst();
					    String nome_da_coluna_string = cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD));
					    sb.append(nome_da_coluna_string);
					    cursor.close();
					    return sb.toString();
				}
			
			public int VerSenha(String codigo, String senha) {

				//Log.v("aviso", senha);
				Cursor c = null;
				SQLiteDatabase db = this.getWritableDatabase();
			    try {
			    	String query = "select count(*) from " + TABLE_USER+ " where "+KEY_USER_ID+"= 1 AND "+KEY_USER_PASSWORD+"= '"+senha+"'";
			    				        
			        c = db.rawQuery(query, null);
			        if (c.moveToFirst()) {
			            return c.getInt(0);
			    	}
			        return 0;
			    }
			    finally {
			        if (c != null) {
			            c.close();
			        }
			        if (db != null) {
			            db.close();
			        }
			    }
				
			}
			public int VerSenhaVazia() {

				Cursor c = null;
				SQLiteDatabase db = this.getWritableDatabase();
			    try {
			    	String query = "select count(*) from " + TABLE_USER+ " where "+KEY_USER_ID+" = '1' AND "+KEY_USER_PASSWORD+"='' LIMIT 1";
			    				        
			        c = db.rawQuery(query, null);
			        
			        
			        if (c.moveToFirst()) {
			        	//Log.v("aviso", "tem senha" + String.valueOf(c.getInt(0)));
			        	
			        	
			            return c.getInt(0);
			        }
			            
			        return 0;
			        
			    }
			    finally {
			       
			    }
				
			}
			
			public int porcentagem(int total, int qtd){
				int div = 85 / 153;
				int resu = (int)div;
				//int resu = (int)((85 / 153) * 100);
				return resu;
			}
	
	}
