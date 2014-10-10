package com.mgoficina;

public class Contact {

	// private variables
	int _id;
	String _idv;
	int    _os;
	byte[] _image;
	String _descricao;
	String _cliente;
	String _datahora;
	String _status;
	String _local;
	int _liga;
	String _defeito;
	String _acessorio;
	String _obs;
	double _valor;
	int _lote;
	int _exporta;
	int _entrada;
	int _deletado;
	int _telefonecliente;
	String _enderecocliente;

	// Empty constructor
	public Contact() {

	}

	// constructor
	public Contact(int keyId, int os, String descricao, byte[] image, String cliente, String datahora, String status, String local, int liga, String defeito, String acessorio, String obs, double valor, int lote, int exporta, int entrada, int deletado, int telefonecliente, String enderecocliente) {
	this._id = keyId;
	this._os 		= os;
	this._descricao = descricao;
	this._image 	= image;
	this._cliente 	= cliente;
	this._datahora 	= datahora;
	this._status 	= status;
	this._local 	= local;
	this._liga		= liga;
	this._defeito 	= defeito;
	this._acessorio	= acessorio;
	this._obs		= obs;
	this._valor	  	= valor;
	this._lote	  	= lote;
	this._exporta	= exporta;
	this._entrada	= entrada;
	this._deletado	= deletado;
	this._telefonecliente	= telefonecliente;
	this._enderecocliente	= enderecocliente;
	

	}

	// constructor
	public Contact(int os, String descricao, byte[] image, String cliente, String datahora, String status, String local, int liga, String defeito, String acessorio, String obs, double valor, int lote, int exporta, int entrada, int deletado, int telefonecliente, String enderecocliente) {
	this._os = os;
	this._descricao = descricao;
	this._image = image;
	this._cliente = cliente;
	this._datahora = datahora;
	this._status = status;
	this._local = local;
	this._liga= liga;
	this._defeito 	= defeito;
	this._acessorio	= acessorio;
	this._obs		= obs;
	this._valor	  	= valor;
	this._lote	  	= lote;
	this._exporta	= exporta;
	this._entrada	= entrada;
	this._deletado	= deletado;
	this._telefonecliente	= telefonecliente;
	this._enderecocliente	= enderecocliente;
	}

	// getting ID
	public int getID() {
	return this._id;
	}

	// setting id
	public void setID(int keyId) {
	this._id = keyId;
	}

	// getting name
	public int getOs() {
	return this._os;
	}
	public String getDescricao() {
		return this._descricao;
		}
	
	public String getCliente() {
		return this._cliente;
		}
	public String getStatus() {
		return this._status;
		}
	public String getDatahora() {
		return this._datahora;
		}
	public String getLocal() {
		return this._local;
		}
	public int getLiga() {
		return this._liga;
		}
	
	public String getDefeito() {
		return this._defeito;
		}
	public String getAcessorio() {
		return this._acessorio;
		}
	
	public String getObs() {
		return this._obs;
		}
	public double getValor() {
		return this._valor;
		}
	
	public int getLote() {
		return this._lote;
		}
	
	public int getExporta() {
		return this._exporta;
		}
	public int getEntrada() {
		return this._entrada;
		}
	public int getDeletado() {
		return this._deletado;
		}
	public int getTelefonecliente() {
		return this._telefonecliente;
		}
	public String getEnderecocliente() {
		return this._enderecocliente;
		}
	public String getIdv() {
		return this._idv;
		}

	public void setIdv(String Idv) {
		this._idv = Idv;
		
	}
	
	// setting name
	public void setOs(int os) {
	this._os = os;
	}

	// getting phone number
	public byte[] getImage() {
	return this._image;
	}

	// setting phone number
	public void setImage(byte[] image) {
	this._image = image;
	}

	public void setDescricao(String descricao) {
		
		this._descricao = descricao;
		
	}

	public void setCliente(String cliente) {
		this._cliente = cliente;
		
	}
	public void setDatahora(String datahora) {
		this._datahora = datahora;
		
	}
	public void setStatus(String status) {
		this._status = status;
		
	}
	public void setLocal(String local) {
		this._local = local;
		
	}
	public void setLiga(int liga) {
		this._liga = liga;
		
	}
	
	public void setDefeito(String defeito) {
		this._defeito = defeito;
		
	}
	public void setAcessorio(String acessorio) {
		this._acessorio = acessorio;
		
	}
	
	public void setObs(String obs) {
		this._obs = obs;
		
	}
	
	public void setValor(double valor) {
		this._valor = valor;
		
	}
	public void setLote(int lote) {
		this._lote = lote;
		
	}
	public void setExporta(int exporta) {
		this._exporta = exporta;
		
	}
	
	public void setEntrada(int entrada) {
		this._entrada = entrada;
		
	}
	public void setDeletado(int deletado) {
		this._deletado = deletado;
		
	}
	public void setTelefonecliente(int telefonecliente) {
		this._telefonecliente =telefonecliente;
		
	}
	public void setEnderecocliente(String enderecocliente) {
		this._enderecocliente =enderecocliente;
		
	}
	
	
	}
	