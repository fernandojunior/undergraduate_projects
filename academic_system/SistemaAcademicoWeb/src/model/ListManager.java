package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import entidades.*;
import exceptions.GetException;
import exceptions.IDException;

import interfaces.Cursavel;
import interfaces.IEntidade;
import interfaces.IManager;

/**
 * Classe que gerencia uma lista
 * 
 * @author Flavio & Fernando
 * 
 */
public abstract class ListManager implements IManager {
	/**
	 * Lista a ser gerenciada
	 */
	protected List<IEntidade> lista;

	/**
	 * Construtor do gerenciador
	 */
	public ListManager() {
		lista = new ArrayList<IEntidade>();
	}

	/**
	 * Construtor especifico para um tipo de gerenciador
	 * 
	 * @param tipoDoManager
	 *            o tipo ao qual o gerenciador ira gerenciar
	 */
	public ListManager(String tipoDoManager) {
		lista = new ArrayList<IEntidade>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract boolean adicionarElemento(IEntidade elemento)
			throws IDException, ClassNotFoundException;

	/**
	 * Checa se um dado elemento ja existe na lista
	 * 
	 * @param elemento
	 *            o elemento a ser checado
	 * @return true se ja existe, false caso contrario
	 */
	public boolean existeElemento(IEntidade elemento) {
		return lista.contains(elemento);
	}

	/**
	 * Gera uma idenficacao unica para o elemento passado commo parametro
	 * 
	 * @param elemento
	 *            o elemento ao qual sera gerado uma identificacao
	 * @return uma string com a identificacao do elemento
	 * @throws IDException
	 *             se nao for possivel gerar a identificacao
	 */
	public abstract String gerarIdentificacao(IEntidade elemento)
			throws IDException;

	/**
	 * Retorna a identificacao da ultima entidade da lista.
	 * 
	 * @param lista
	 *            lista de entidades.
	 * @param inicio
	 *            indice de onde comeca a procura.
	 * @param fim
	 *            indice de onde termina a procura.
	 * @return a identificacao da ultima entidade na lista.
	 */
	protected static int procurarUltimaIdentificacao(List<IEntidade> lista,
			int inicio, int fim) {

		if (fim - inicio <= 1)
				return Math.max(Integer.parseInt(lista.get(fim)
						.getIdentificacao()), Integer.parseInt(lista.get(inicio).getIdentificacao()));

		int meio = (inicio + fim) / 2;
		int max1 = procurarUltimaIdentificacao(lista, inicio, meio);
		int max2 = procurarUltimaIdentificacao(lista, meio + 1, fim);
		return Math.max(max1, max2);
	}

	protected static int procurarUltimaIdentificacao_backup(
			List<IEntidade> lista, int inicio, int fim) {

		if (fim - inicio <= 1)

			if (lista.get(fim).getClass().toString().contains("Pessoa"))
				return Math.max(Integer.parseInt(((Pessoa) lista.get(fim))
						.getIdentificacao()), Integer.parseInt(((Pessoa) lista
						.get(inicio)).getIdentificacao()));

		if (lista.get(fim).getClass().toString().contains("Aluno"))
			return Math.max(Integer.parseInt(((Aluno) lista.get(fim))
					.getIdentificacao()), Integer.parseInt(((Aluno) lista
					.get(inicio)).getIdentificacao()));

		if (lista.get(fim).getClass().toString().contains("Professor"))
			return Math.max(Integer.parseInt(((Professor) lista.get(fim))
					.getIdentificacao()), Integer.parseInt(((Professor) lista
					.get(inicio)).getIdentificacao()));

		else if (lista.get(fim).getClass().toString().contains("Curso"))
			return Math.max(Integer.parseInt(((Cursavel) lista.get(fim))
					.getIdentificacao()), Integer.parseInt(((Cursavel) lista
					.get(inicio)).getIdentificacao()));

		else if (lista.get(fim).getClass().toString().contains("Turma"))
			return Math.max(Integer.parseInt(((Turma) lista.get(fim))
					.getIdentificacao()), Integer.parseInt(((Turma) lista
					.get(inicio)).getIdentificacao()));

		int meio = (inicio + fim) / 2;
		int max1 = procurarUltimaIdentificacao_backup(lista, inicio, meio);
		int max2 = procurarUltimaIdentificacao_backup(lista, meio + 1, fim);
		return Math.max(max1, max2);
	}

	/**
	 * Retorna uma lista de elementos, conforme o valor do nome passado como
	 * parametro
	 * 
	 * @param nome
	 *            Valor do nome a ser passado como parametro
	 * @return Lista de elementos conforme o valor do nome passado como
	 *         parametro
	 * @throws GetException
	 *             Caso o valor seja invalido
	 */
	public ArrayList<IEntidade> getElementoPorNome(String nome)
			throws GetException {
		if (nome == null)
			throw new GetException();

		ArrayList<IEntidade> lista = new ArrayList<IEntidade>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			IEntidade entidade = iter.next();

			if (entidade.getNome().contains(nome))
				lista.add(entidade);

		}

		return lista;
	}

	/**
	 * Retorna uma lista de elementos, conforme o valor da identificacao passada
	 * como parametro
	 * 
	 * @param identificacao
	 *            Valor da identificacao a ser passado como parametro
	 * @return Lista de elementos conforme o valor da identificacao passada como
	 *         parametro
	 * @throws GetException
	 *             Caso o valor seja invalido
	 */
	public ArrayList<? extends IEntidade> getElementoPorIdentificacao(
			String identificacao) throws GetException {
		if (identificacao == null)
			throw new GetException();

		ArrayList<IEntidade> lista = new ArrayList<IEntidade>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			IEntidade entidade = iter.next();

			if (entidade.getIdentificacao().contains(identificacao.toString()))
				lista.add(entidade);
		}

		return lista;
	}

	/**
	 * Retorna um unico elemento, conforme o valor da identificacao passada como
	 * parametro
	 * 
	 * @param identificacaoExata
	 *            Valor da identificacao a ser passada como parametro
	 * @return Elemento conforme o valor da identificacao passada como parametro
	 * @throws GetException
	 *             Caso o valor seja invalido
	 */
	public ArrayList<? extends IEntidade> getElementoPorIdentificacaoExata(
			String identificacaoExata) throws GetException {
		if (identificacaoExata == null)
			throw new GetException();

		ArrayList<IEntidade> lista = new ArrayList<IEntidade>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			IEntidade entidade = iter.next();

			if (entidade.getIdentificacao().equals(
					identificacaoExata.toString()))
				lista.add(entidade);
		}

		return lista;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract ArrayList<? extends IEntidade> getElemento(
			String tipoDoMetodo, Object valor) throws GetException;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract boolean atualizarElemento(IEntidade elemento,
			String tipoDoMetodo, Object valor) throws NamingException,
			GetException;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removerElemento(IEntidade elemento) {
		if (elemento != null)
			return lista.remove(elemento);
		return false;

	}

}
