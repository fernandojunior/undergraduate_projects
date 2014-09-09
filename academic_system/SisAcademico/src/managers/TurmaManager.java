package managers;

import interfaces.IEntidade;

import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.NamingException;

import entidades.Aluno;
import entidades.Professor;
import entidades.Turma;

import exceptions.GetException;
import exceptions.IDException;

/**
 * Gerenciador de Turma
 * 
 * @author Flavio & Fernando
 * 
 */
public class TurmaManager extends ListManager {
	/**
	 * Tipo do gerenciador
	 */
	private String tipoDoManager = Turma.class.toString();

	/**
	 * Construtor do gerenciador
	 */
	public TurmaManager() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean adicionarElemento(IEntidade turma) throws IDException,
			ClassNotFoundException {

		if (!turma.getClass().toString().equals(tipoDoManager))
			throw new ClassNotFoundException();

		if (turma != null && !existeElemento(turma)) {
			turma.setIdentificacao(gerarIdentificacao(turma));
			return lista.add(turma);
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String gerarIdentificacao(IEntidade elemento) throws IDException {
		if (lista.size() > 0)
			return String.valueOf(procurarUltimaIdentificacao(lista, 0,
					lista.size() - 1) + 1);
		return "0";
	}

	/**
	 * Retorna uma lista de turmas, conforme o Professor passado como parametro
	 * 
	 * @param professor
	 *            Professor a ser passado como parametro
	 * @return Lista de turmas conforme o Professor passado como parametro
	 * @throws GetException
	 *             Caso o Professor seja invalido
	 */
	public ArrayList<? extends Turma> getElementoPorProfessor(
			Professor professor) throws GetException {
		if (professor == null)
			throw new GetException();

		ArrayList<Turma> lista = new ArrayList<Turma>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			Turma turma = (Turma) iter.next();
			if (turma.getProfessor().equals(professor))
				lista.add(turma);

		}

		return lista;

	}

	/**
	 * Retorna uma lista de turmas, conforme o Aluno passado como parametro
	 * 
	 * @param aluno
	 *            Aluno a ser passado como parametro
	 * @return Lista de turmas conforme o Aluno passado como parametro
	 * @throws GetException
	 *             Caso o Aluno seja invalido
	 */
	public ArrayList<? extends Turma> getElementoPorAluno(Aluno aluno)
			throws GetException {
		if (aluno == null)
			throw new GetException();

		ArrayList<Turma> lista = new ArrayList<Turma>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			Turma turma = (Turma) iter.next();

			if (turma.existeAluno(aluno.getIdentificacao()))
				lista.add(turma);

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

		ArrayList<? extends IEntidade> lista = new ArrayList<Turma>();

		if (tipoDoMetodo.equalsIgnoreCase("nome"))
			lista = getElementoPorNome((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("identificacao"))
			lista = getElementoPorIdentificacao((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("identificacao_exata"))
			lista = getElementoPorIdentificacaoExata((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("professor"))
			lista = getElementoPorProfessor((Professor) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("aluno"))
			lista = getElementoPorAluno((Aluno) valor);
		else
			throw new GetException();

		if (lista.equals(new ArrayList<Turma>()))
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

			//Turma aux = (Turma) elemento;

			if (tipoDoMetodo.equalsIgnoreCase("nome"))
				elemento.setNome((String) valor);

			// else if (tipoDoMetodo.equalsIgnoreCase("identificacao"))
			// elemento.setIdentificacao((String) valor);

			else
				throw new NamingException("metodo nao encontrado");

			return true;

		}
		return false;
	}

}
