/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Participantes;

/**
 *
 * @author Darwin W. Diaz Simon
 */
public class Casilla {

	/// PROPIEDADES
	private int valor;
	private int posX;
	private int posY;
	private Sector sector;
	private boolean editable;
	
	
	public Casilla() {}
	
	public Casilla(int valor, int posX, int posY, Sector sector, boolean editable) {
		
		this.valor = valor;
		this.posX = posX;
		this.posY = posY;
		this.sector = sector;
		this.editable	= editable;
	}
	
	public void establecerSectorSegunPosicion() {
		switch(posX) {
		
		case 0:
		case 1:
		case 2:
			switch(posY) {
			case 0:
			case 1:
			case 2:
				this.setSector(Sector.PRIMERO);
				break;
			case 3:
			case 4:
			case 5:
				this.setSector(Sector.SEGUNDO);
				break;
			case 6:
			case 7: 
			case 8:
				this.setSector(Sector.TERCERO);
				break;
			default:
				break;
			}
			break;
		case 3:
		case 4:
		case 5:
			switch(posY) {
			case 0:
			case 1:
			case 2:
				this.setSector(Sector.CUARTO);
				break;
			case 3:
			case 4:
			case 5:
				this.setSector(Sector.QUINTO);
				break;
			case 6:
			case 7: 
			case 8:
				this.setSector(Sector.SEXTO);
				break;
			default:
				break;
			}
			break;
		case 6:
		case 7:
		case 8:
			switch(posY) {
			case 0:
			case 1:
			case 2:
				this.setSector(Sector.SEPTIMO);
				break;
			case 3:
			case 4:
			case 5:
				this.setSector(Sector.OCTAVO);
				break;
			case 6:
			case 7: 
			case 8:
				this.setSector(Sector.NOVENO);
				break;
			default:
				break;
			}
			break;
		}
	}

	
	public int getValor() {
		return valor;
	}

	
	public void setValor(int valor) {
		this.valor = valor;
	}

	
	public int getPosX() {
		return posX;
	}

	
	public void setPosX(int posX) {
		this.posX = posX;
	}

	
	public int getPosY() {
		return posY;
	}

	
	public void setPosY(int posY) {
		this.posY = posY;
	}

	
	public Sector getSector() {
		return sector;
	}


	public void setSector(Sector sector) {
		this.sector = sector;
	}

	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}