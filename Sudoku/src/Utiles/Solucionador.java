/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

/**
 *
 * @author Darwin W. Diaz Simon
 */

import java.util.ArrayList;

import Participantes.Casilla;
import Participantes.Tablero;

public class Solucionador {
	
	
	public Tablero solucionarSudoku(Tablero tablero) {
		
		ArrayList<Casilla> editables = this.extraerCasillasEditables(tablero.getCasillas());
		int i = 0;
		
		while (i < editables.size()) {
			
			// Control de salida. Si el primer resultado es igual a 0 y su valor superior al mximo permitido por el sudoku, 
			// error al solucionar el sudoku
			Casilla actual = editables.get(i);
			if (actual.getValor() > 9) {
				tablero.reestablecerCasillaPorPosicion(actual.getPosX(), actual.getPosY());
				i--;
				
				if (i < 0) {
					return null;
				}
				else {
					actual = editables.get(i);
					actual.setValor(actual.getValor() + 1);
					tablero.editarCasilla(actual);
				}
			}
			else {
				if (actual.getValor() == 0) {
					actual.setValor(1);
				}
				
				ArrayList<ArrayList<Casilla>> listasParaValidar = this.extraerCasillasComparables(tablero, actual);
				
				// Validar si listas cumplen reglas de Sudoku (salvo valor sin rellenar)
				if (validacionFCS(listasParaValidar)) {
					tablero.editarCasilla(actual);
					i++;
				}
				else
				{
					actual.setValor(actual.getValor() + 1);
					if (actual.getValor() > 9) {
						tablero.reestablecerCasillaPorPosicion(actual.getPosX(), actual.getPosY());
						if (i != 0) {
							i--;
							actual = editables.get(i);
							actual.setValor(actual.getValor() + 1);
							tablero.editarCasilla(actual);
						}
						else {
							return null;
						}
					}
				}
			}
			
		}
		
		return tablero;
	}
	
	
	private ArrayList<Casilla> extraerCasillasEditables(Casilla[][] casillas) {
		ArrayList<Casilla> editables = new ArrayList<Casilla>();
		
		for (int i = 0; i < casillas.length; i++ ) {
			for (int j = 0; j < casillas[i].length; j++) {
				if (casillas[i][j].isEditable()) {
					editables.add(casillas[i][j]);
				}
			}
		}
		
		return editables;
	}
	
	
	private ArrayList<ArrayList<Casilla>> extraerCasillasComparables(Tablero tablero, Casilla actual) {
		
		ArrayList<Casilla> fila 	= new ArrayList<Casilla>();
		ArrayList<Casilla> columna 	= new ArrayList<Casilla>();
		ArrayList<Casilla> sector 	= new ArrayList<Casilla>();
		
		for (int i = 0; i < tablero.getCasillas().length; i++) {
			for (int j = 0; j < tablero.getCasillas()[i].length; j++) {
				if (tablero.getCasillas()[i][j].getPosX() == actual.getPosX()) {
					fila.add(tablero.getCasillas()[i][j]);
				}
				
				if (tablero.getCasillas()[i][j].getPosY() == actual.getPosY()) {
					columna.add(tablero.getCasillas()[i][j]);
				}
				
				if (tablero.getCasillas()[i][j].getSector() == actual.getSector()) {
					sector.add(tablero.getCasillas()[i][j]);
				}
			}
		}
		
		ArrayList<ArrayList<Casilla>> resultado = new ArrayList<ArrayList<Casilla>>();
		resultado.add(fila);
		resultado.add(columna);
		resultado.add(sector);
		
		return resultado;
	}
	
	
	private boolean validacionFCS(ArrayList<ArrayList<Casilla>> listas) {
		
		for (ArrayList<Casilla> lista : listas) {
			
			ArrayList<Integer> valores = null;
			valores = new ArrayList<Integer>();
			
			for (Casilla casilla : lista) {
				if (casilla.getValor() != 0) {
					if (valores.contains(casilla.getValor())) {
						return false;
					}
					else {
						valores.add(casilla.getValor());
					}
				}
			}
		}
		
		return true;
	}
}
