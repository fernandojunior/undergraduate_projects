package model;

import interfaces.IEntidade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.naming.NamingException;

import entidades.Pessoa;
import exceptions.GetException;
import exceptions.IDException;

/**
 * Gerenciador de Pessoa
 * 
 * @author Flavio & Fernando
 * 
 */
public class PessoaManager extends ListManager {

	/**
	 * Especifica o tipo do gerenciado
	 */
	private String tipoDoManager;

	/**
	 * Construtor da classe
	 * 
	 * @param tipoDoManager
	 *            o tipo do gerenciador
	 */
	public PessoaManager(String tipoDoManager) {
		super(tipoDoManager);

		this.tipoDoManager = tipoDoManager;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean adicionarElemento(IEntidade pessoa) throws IDException,
			ClassNotFoundException {

		if (!pessoa.getClass().toString().equals(tipoDoManager))
			throw new ClassNotFoundException();

		if (pessoa != null && !existeElemento(pessoa)) {
			pessoa.setIdentificacao(gerarIdentificacao(pessoa));
			return lista.add(pessoa);
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String gerarIdentificacao(IEntidade elemento) throws IDException {
		int anoDaMatricula = ((Pessoa) elemento).getDataDaMatricula().get(
				Calendar.YEAR);

		String ultimaMatricula = getUltimaMatricula();

		String id = ((Pessoa) elemento).getCodigoDaClasse() + "";

		if (id.equals(""))
			throw new IDException("gerar id");

		int anoUltimaMatricula = Integer.parseInt(ultimaMatricula.substring(0,
				4));
		if (anoDaMatricula > anoUltimaMatricula)
			return anoDaMatricula + id + "000";
		int valorDaMatricula = Integer.parseInt(ultimaMatricula.substring(5)) + 1;
		return anoUltimaMatricula + id
				+ String.format("%03d", valorDaMatricula);
	}

	/**
	 * Retorna a ultima matricula efetuada no gerenciador
	 * 
	 * @return a ultima matricula efetuada no gerenciador
	 */
	private String getUltimaMatricula() {
		if (lista.size() > 0)
			return String.valueOf(procurarUltimaIdentificacao(lista, 0,
					lista.size() - 1));
		return "0000";
	}

	/**
	 * Retorna uma lista de pessoas, conforme o endereco passado como parametro
	 * 
	 * @param endereco
	 *            Endereco a ser passado como parametro
	 * @return Lista de pessoas conforme o endereco passado como parametro
	 * @throws GetException
	 *             Caso o endereco seja invalido
	 */
	public ArrayList<? extends Pessoa> getElementoPorEndereco(String endereco)
			throws GetException {
		if (endereco == null)
			throw new GetException();

		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			Pessoa pessoa = (Pessoa) iter.next();

			if (pessoa.getEndereco().contains(endereco))
				lista.add(pessoa);

		}

		return lista;

	}

	/**
	 * Retorna uma lista de pessoas, conforme o telefone passado como parametro
	 * 
	 * @param telefone
	 *            Telefone a ser passado como parametro
	 * @return Lista de pessoas conforme o telefone passado como parametro
	 * @throws GetException
	 *             Caso o telefone seja invalido
	 */
	public ArrayList<? extends Pessoa> getElementoPorTelefone(String telefone)
			throws GetException {
		if (telefone == null)
			throw new GetException();

		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			Pessoa pessoa = (Pessoa) iter.next();

			if (pessoa.getTelefone().contains(telefone))
				lista.add(pessoa);
		}

		return lista;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<? extends IEntidade> getElemento(String tipoDoMetodo,
			Object valor) throws GetException {
		if (tipoDoMetodo == null || valor == null)
			throw new GetException();

		ArrayList<? extends IEntidade> lista = new ArrayList<Pessoa>();

		if (tipoDoMetodo.equalsIgnoreCase("nome"))
			lista = getElementoPorNome((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("identificacao"))
			lista = getElementoPorIdentificacao((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("identificacao_exata"))
			lista = getElementoPorIdentificacaoExata((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("endereco"))
			lista = getElementoPorEndereco((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("telefone"))
			lista = getElementoPorTelefone((String) valor);
		else
			throw new GetException();

		if (lista.equals(new ArrayList<Pessoa>()))
			throw new GetException();

		return lista;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean atualizarElemento(IEntidade elemento, String tipoDoMetodo,
			Object valor) throws NamingException {
		if (tipoDoMetodo != null && valor != null && elemento != null
				&& lista.contains(elemento)) {

			Pessoa aux = (Pessoa) elemento;

			if (tipoDoMetodo.equalsIgnoreCase("nome"))
				aux.setNome((String) valor);

			else if (tipoDoMetodo.equalsIgnoreCase("endereco"))
				aux.setEndereco((String) valor);

			else if (tipoDoMetodo.equalsIgnoreCase("telefone"))
				aux.setTelefone((String) valor);

			else
				throw new NamingException("metodo nao encontrado");

			return true;

		}
		return false;
	}

}
