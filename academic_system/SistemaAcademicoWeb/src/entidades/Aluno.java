/**
 * 
 */
package entidades;

import java.util.Calendar;

/**
 * Classe que define um Aluno.
 * 
 * @author Fernando & Flavio
 * 
 */
public class Aluno extends Pessoa {

	private static final Integer codigoDaClasse = 0;

	/**
	 * Construtor de Aluno
	 * 
	 * @param nome
	 *            o nome do Aluno
	 * @param dataDeNascimento
	 *            a data de nascimento do Aluno
	 * @param endereco
	 *            o endereco do Aluno
	 * @param telefone
	 *            o telefone do Aluno
	 */
	public Aluno(String nome, Calendar dataDeNascimento, String endereco,
			String telefone) {
		super(nome, dataDeNascimento, endereco, telefone);
	}

	/**
	 * Retorna o codigo necessario para geracao da matricula de um aluno
	 * 
	 * @return o codigo necessario para geracao da matricula de um aluno
	 */
	public Integer getCodigoDaClasse() {
		return codigoDaClasse;
	}

}
