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
public class Tablero {

	/// PROPIEDADES 
	
	private Casilla[][] casillas;
	

	public Tablero() {
		this.casillas = new Casilla[9][9];
	}
	
	public Casilla[][] getCasillas() {
		return this.casillas;
	}
	
	
	public void inicializarTablero() {
		
		for (int i = 0; i < this.casillas.length; i++) {
			for (int j = 0; j < this.casillas[i].length; j++) {
				Casilla casilla = new Casilla();
				casilla.setValor(0);
				casilla.setPosX(i);
				casilla.setPosY(j);
				casilla.setEditable(true);
				casilla.establecerSectorSegunPosicion();
				
				this.casillas[i][j] = casilla;
			}
		}
	}
	
	
	public void editarCasilla(Casilla casilla) {
		this.casillas[casilla.getPosX()][casilla.getPosY()] = casilla;
	}
	
	 public void reestablecerCasillaPorPosicion(int x, int y) {
		this.casillas[x][y].setValor(0);
	}
	
	
	public void insertarValores(int[][] matriz) {
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] != 0) {
					this.casillas[i][j].setEditable(false);
				}
				this.casillas[i][j].setValor(matriz[i][j]);
			}
		}
	}
}