/**
 * 
 */
package entidades;

import java.util.Calendar;

/**
 * Classe que define um Professor
 * 
 * @author Fernando & Flavio
 * 
 */
public class Professor extends Pessoa {

	private static final Integer codigoDaClasse = 1;

	/**
	 * Construtor de Professor
	 * 
	 * @param nome
	 *            o nome do professor
	 * @param dataDeNascimento
	 *            a data de nascimento do professor
	 * @param endereco
	 *            o endereco do professor
	 * @param telefone
	 *            o telefone do professor
	 */
	public Professor(String nome, Calendar dataDeNascimento, String endereco,
			String telefone) {
		super(nome, dataDeNascimento, endereco, telefone);
	}

	/**
	 * Retorna o codigo necessario para geracao da matricula de um professor
	 * 
	 * @return o codigo necessario para geracao da matricula de um professor
	 */
	public Integer getCodigoDaClasse() {
		return codigoDaClasse;
	}

}
