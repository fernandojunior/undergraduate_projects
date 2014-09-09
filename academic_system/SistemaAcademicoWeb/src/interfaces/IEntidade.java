package interfaces;

public interface IEntidade {
	
	/**
	 * Altera o nome da Entidade
	 * 
	 * @param nome
	 *            o novo nome da Entidade
	 */
	void setNome(String nome);
	
	/**
	 * Retorna o nome da Entidade
	 * 
	 * @return o nome da Entidade
	 */
	public String getNome();
	
	/**
	 * Altera a identificacao de uma Entidade
	 * 
	 * @param identificacao
	 *            o novo valor de identificacao da Entidade
	 */
	public Boolean setIdentificacao(String identificacao);
	
	/**
	 * Retorna a identificacao da Entidade
	 * 
	 * @return a identificacao da Entidade
	 */	
	public String getIdentificacao();





}
