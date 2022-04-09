import java.time.LocalDateTime;
import java.lang.StringBuilder;
import java.time.temporal.ChronoUnit;

public class SmartBulb{
	/**
	*@param modo da lãmpada expresso em inteiros, 1=Warm, 0=Neural, -1=Cold
	*/
	private int state; //on ou off 1 ou 0
	private int mode; 
	//perguntar ao professor quais as specs das dimensoes
	private int dimensions;
	private float consumoDiario;
	private LocalDateTime ultimaVezLigadaEmModo;

	//desenvolver formula do consumo para cada modo

	public SmartBulb(){
		this.state=1;
		this.mode=0;
		this.dimensions=0;
		this.consumoDiario=0;
		this.ultimaVezLigadaEmModo=LocalDateTime.now();
	}

	public SmartBulb(int state,int mode, int dimensions, float consumoDiario, LocalDateTime ultimaVezLigadaEmModo){
		this.state=state;
		this.mode=mode;
		this.dimensions=dimensions;
		this.consumoDiario=consumoDiario;
		//ver o clone para o ultima vez ligado???
		this.ultimaVezLigadaEmModo=ultimaVezLigadaEmModo;
	}

	public SmartBulb(SmartBulb lampada){
		this.state = lampada.getState();
		this.mode=lampada.getMode();
		this.dimensions = lampada.getDimensions();
		this.consumoDiario=lampada.getConsumoDiario();
		this.ultimaVezLigadaEmModo=lampada.getUltimaVezLigado();
	}

	public void setState(int state){this.state=state;}

	public void setMode(int mode){this.mode=mode;}

	public void setDimensions(int dimensions){this.dimensions=dimensions;}

	public void setConsumoDiario(int consumoDiario){this.consumoDiario=consumoDiario;}

	public void setUltimaVezLigado(LocalDateTime ult){this.ultimaVezLigadaEmModo=ult;}

	public int getState(){return this.state;}

	public int getMode(){return this.mode;}

	public int getDimensions(){return this.dimensions;}

	public float getConsumoDiario(){return this.consumoDiario;}

	public LocalDateTime getUltimaVezLigado(){ 
		return this.ultimaVezLigadaEmModo;
	}

	public boolean equals(Object obj){
		if (this==obj)
			return true;

		if (this.getClass()!=obj.getClass() || obj==null)
			return false;

		SmartBulb lampada = (SmartBulb) obj;
		return (this.state== lampada.getState() && this.mode==lampada.getMode() &&  
				this.dimensions==lampada.getDimensions()
				&& this.consumoDiario==lampada.getConsumoDiario()  &&  
				this.ultimaVezLigadaEmModo.equals(lampada.getUltimaVezLigado()));
	}


//trocar o modo e state de int para string talvez funcione melhor na interface gráfica
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Estado da lâmpada: ");
		sb.append(this.state);
		sb.append("\nModo de Funcionamento da lâmpada: ");
		sb.append(this.mode);
		sb.append("\nDimensões da lâmpada: ");
		sb.append(this.dimensions);
		sb.append("\nConsumo diário desta lâmpada: ");
		sb.append(this.consumoDiario);
		//Não sei se esta é relevante, talvez seja mais importante para nós para fazer cálculos do que para dar display
		sb.append("\nÚltima vez ligada: ");
		sb.append(this.ultimaVezLigadaEmModo.toString());
		sb.append("\n");
		return sb.toString();
	}

	public SmartBulb clone(){
		return new SmartBulb(this);
	}

	//Métodos específicos:

	//definir formula 
	public float formula(int mode){
		switch(mode){
			case(1):
				return 3;
			case(0):
				return 2;
			case(-1):
				return 1;
		}
		return 0;
	}

	public void turnOn(){
		//se estiver ligada ignora
		if (this.state==0){
			this.state=1;
			this.ultimaVezLigadaEmModo=LocalDateTime.now();
		}
	}

	public void turnOff(){
		//se ela já estiver desligada ignora
		if (this.state==1){
			this.state=0;//desliga a lampada
			long timeElapsed = ChronoUnit.HOURS.between(this.ultimaVezLigadaEmModo,LocalDateTime.now());
			this.consumoDiario+=timeElapsed*formula(this.mode);
		}
	}

}