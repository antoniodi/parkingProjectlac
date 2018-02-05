/**
 * 
 */
package com.dominio;

import java.math.BigDecimal;

/**
 * @author luis.cortes
 *
 */
public class Tarifa {

	private BigDecimal valorDia;
	private BigDecimal valorHora;
	/**
	 * @param valorDia
	 * @param valorHora
	 */
	public Tarifa(BigDecimal valorDia, BigDecimal valorHora) {
		this.valorDia = valorDia;
		this.valorHora = valorHora;
	}
	
	/**
	 * @return the valorDia
	 */
	public BigDecimal getValorDia() {
		return valorDia;
	}
	/**
	 * @return the valorHora
	 */
	public BigDecimal getValorHora() {
		return valorHora;
	}

}
