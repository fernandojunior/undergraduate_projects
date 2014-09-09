package interfaces;

import java.util.Iterator;
import entidades.Turma;
import exceptions.GetException;

/**
 * Interface que define um Curso
 * 
 * @author Fernando & Flavio
 * 
 */
public interface Cursavel extends IEntidade {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean setIdentificacao(String identificacao);

	/**
	 * {@inheritDocs}
	 */
	@Override
	public String getIdentificacao();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNome(String nome);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNome();

	/**
	 * Adiciona uma turma no curso
	 * 
	 * @param turma
	 *            a turma que sera adicionada no curso
	 * @return true se adicionado com sucesso, false caso contrario
	 */
	public boolean adicionar(Turma turma);

	/**
	 * Remove uma turma do curso
	 * 
	 * @param codTurma
	 *            o codigo da turma a ser removida
	 * @return true se removido com sucesso, false caso contrario
	 */
	public boolean remover(String codTurma) throws GetException;

	/**
	 * Retorna uma turma do curso
	 * 
	 * @param codTurma
	 *            o codigo da turma
	 * @return a turma de codigo passado como parametro, null caso nao exista
	 *         turma com tal codigo
	 */
	public Turma getTurma(String codTurma) throws GetException;

	/**
	 * Retorna o total de turmas no curso
	 * 
	 * @return o total de turmas no curso
	 */
	public int getTotalDeTurmas();

	/**
	 * Retorna o iterator da lista de turmas
	 * 
	 * @return o iterator da lista de turmas
	 */
	public Iterator<Turma> getTurmasIterator();

	/**
	 * Lista as turmas du curso
	 * 
	 * @return uma string ordenada de todas as turmas do curso no formato
	 *         NumIdenficacao | NomeDaTurma
	 */
	public String listarTurmas();

	/**
	 * Checa se uma turma ja existe no curso
	 * 
	 * @param codTurma
	 *            o codigo da turma a ser checada
	 * @return true caso exista, false caso contrario
	 */
	public boolean existeTurma(String codTurma) throws GetException;

}
