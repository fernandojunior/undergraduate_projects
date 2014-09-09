/**
 * 
 */
package entidades;

import interfaces.IEntidade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe abstrata, utilizada em Aluno e Professor
 * 
 * @author Fernando & Flavio
 * 
 */
public abstract class Pessoa implements Comparable<Pessoa>, IEntidade {

	/**
	 * Nome da pessoa
	 */
	private String nome;

	/**
	 * Data de Nascimento da Pessoa
	 */
	private Calendar dataDeNascimento;

	/**
	 * Data em que a pessoa foi matriculada
	 */
	private Calendar dataDaMatricula;
	/**
	 * Identificador unico da pessoa
	 */
	private String identificacao;
	/**
	 * Telefone da pessoa
	 */
	private String telefone;
	/**
	 * Endereco da Pessoa
	 */
	private String endereco;

	/**
	 * Construtor de Pessoa
	 * 
	 * @param nome
	 *            o nome da Pessoa
	 * @param dataDeNascimento
	 *            a data de nascimento da Pessoa
	 * @param endereco
	 *            o endereco da pessoa
	 * @param telefone
	 *            o telefone da pessoa
	 */
	public Pessoa(String nome, Calendar dataDeNascimento, String endereco,
			String telefone) {
		this.nome = nome;
		this.dataDeNascimento = dataDeNascimento;
		this.endereco = endereco;
		this.identificacao = "";
		this.telefone = telefone;
		this.dataDaMatricula = new GregorianCalendar();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNome() {
		return nome;
	}

	/**
	 * Retorna um Calendar referente a data de nascimento da Pessoa
	 * 
	 * @return um Calendar referente a data de nascimento da Pessoa
	 */
	public Calendar getDataDeNascimento() {
		return dataDeNascimento;
	}

	/**
	 * Altera a data de nascimento da pessoa
	 * 
	 * @param dataDeNascimento
	 *            um novo valor para a data de nascimento da pessoa
	 */
	public void setDataDeNascimento(Calendar dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	/**
	 * Retorna a data em que a pessoa foi matriculada
	 * 
	 * @return a data em que a pessoa foi matriculada
	 */
	public Calendar getDataDaMatricula() {
		return dataDaMatricula;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean setIdentificacao(String identificacao) {

		if (!this.identificacao.equals(""))
			return false;
		this.identificacao = identificacao;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentificacao() {
		return identificacao;
	}

	/**
	 * Altera o telefone da pessoa
	 * 
	 * @param telefone
	 *            o novo numero de telefone
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * Retorna o telefone da pessoa
	 * 
	 * @return o numero de telefone da pessoa
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * Altera o endereco da pessoa
	 * 
	 * @param endereco
	 *            o novo endereco da pessoa
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * Retorna o endereco da pessoa
	 * 
	 * @return o endereco da pessoa
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * Retorna uma String formatada com as informacoes da pessoa
	 * 
	 * @return uma String formatada com as informacoes da pessoa
	 */
	@Override
	public String toString() {
		return "Nome: " + getNome() + "\nData de nascimento: "
				+ formataData(getDataDeNascimento()) + "\nEndereco: "
				+ getEndereco() + "\nTelefone: " + getTelefone()
				+ "\nMatricula: " + getIdentificacao()
				+ "\nMatriculado desde: " + formataData(getDataDaMatricula());
	}

	/**
	 * Formata a data para ser exibido em uma string
	 * 
	 * @param data
	 *            a data a ser formatada
	 * @return a data formatada
	 */
	protected String formataData(Calendar data) {
		SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy");
		return formatBra.format(data.getTime());
	}

	/**
	 * Metodo utilizado na ordenacao de pessoas
	 * 
	 * @param p
	 *            uma pessoa
	 * @return se a pesssoa passada como parametro deve ficar antes ou depois em
	 *         ua lista, ordenando a partir do nome
	 */
	@Override
	public int compareTo(Pessoa p) {
		return this.nome.compareTo(p.getNome());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(identificacao);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (!identificacao.equals(other.getIdentificacao()))
			return false;
		return true;
	}

	/**
	 * Retorna o codigo necessario para geracao da matricula de uma pessoa
	 * 
	 * @return o codigo necessario para geracao da matricula de uma pessoa
	 */
	public abstract Integer getCodigoDaClasse();

}
