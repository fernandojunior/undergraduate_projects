package managers;

import interfaces.Cursavel;
import interfaces.IEntidade;

import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.NamingException;

import entidades.Aluno;
import entidades.Curso;
import entidades.Turma;
import exceptions.GetException;
import exceptions.IDException;

/**
 * Classe que gerencia uma lista de Cursos
 * 
 * @author Flavio & Fernando
 * 
 */
public class CursoManager extends ListManager {
	/**
	 * Define o tipo do gerenciador
	 */
	private String tipoDoManager = Curso.class.toString();

	/**
	 * Construtor do Gerenciador
	 */
	public CursoManager() {
		super();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean adicionarElemento(IEntidade curso) throws IDException,
			ClassNotFoundException {

		if (!curso.getClass().toString().equals(tipoDoManager))
			throw new ClassNotFoundException();

		if (curso != null && !existeElemento(curso)) {
			curso.setIdentificacao(gerarIdentificacao(curso));
			return lista.add(curso);
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
	 * Retorna uma lista de cursos, conforme a Turma passada como parametro
	 * 
	 * @param turma
	 *            Turma a ser passada como parametro
	 * @return Lista de cursos conforme a Turma passada como parametro
	 * @throws GetException
	 *             Caso a Turma seja invalida
	 */
	public ArrayList<? extends Cursavel> getElementoPorTurma(Turma turma)
			throws GetException {
		if (turma == null)
			throw new GetException();

		ArrayList<Cursavel> lista = new ArrayList<Cursavel>();

		for (Iterator<IEntidade> iter = this.lista.iterator(); iter.hasNext();) {

			Cursavel curso = (Cursavel) iter.next();

			if (curso.listarTurmas().contains(turma.getNome()))
				lista.add(curso);
		}

		return lista;
	}

	/**
	 * Retorna uma lista de cursos, conforme o Aluno passado como parametro
	 * 
	 * @param aluno
	 *            Aluno a ser passado como parametro
	 * @return Lista de cursos conforme o Aluno passado como parametro
	 * @throws GetException
	 *             Caso o Aluno seja invalido
	 */
	public ArrayList<? extends Cursavel> getElementoPorAluno(Aluno aluno)
			throws GetException {
		if (aluno == null)
			throw new GetException();

		ArrayList<Cursavel> lista = new ArrayList<Cursavel>();

		for (Iterator<IEntidade> iterX = this.lista.iterator(); iterX.hasNext();) {

			Cursavel curso = (Cursavel) iterX.next();

			for (Iterator<Turma> iterY = curso.getTurmasIterator(); iterY
					.hasNext();)
				if (iterY.next().getAluno(aluno.getIdentificacao()) != null)
					lista.add(curso);

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

		ArrayList<? extends IEntidade> lista = new ArrayList<Cursavel>();

		if (tipoDoMetodo.equalsIgnoreCase("nome"))
			lista = getElementoPorNome((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("identificacao"))
			lista = getElementoPorIdentificacao((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("identificacao_exata"))
			lista = getElementoPorIdentificacaoExata((String) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("turma"))
			lista = getElementoPorTurma((Turma) valor);
		else

		if (tipoDoMetodo.equalsIgnoreCase("aluno"))
			lista = getElementoPorAluno((Aluno) valor);
		else
			throw new GetException();

		if (lista.equals(new ArrayList<Cursavel>()))
			throw new GetException();

		return lista;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean atualizarElemento(IEntidade elemento, String tipoDoMetodo,
			Object valor) throws NamingException, GetException {
		if (tipoDoMetodo != null && valor != null && elemento != null
				&& lista.contains(elemento)) {

			// Cursavel aux = (Cursavel) elemento;

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
