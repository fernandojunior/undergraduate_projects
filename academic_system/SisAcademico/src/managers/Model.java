package managers;

import interfaces.IEntidade;

import java.util.ArrayList;

import javax.naming.NamingException;

import entidades.Aluno;
import entidades.Curso;
import entidades.Professor;
import entidades.Turma;
import exceptions.GetException;

import exceptions.IDException;

/**
 * Classe que Administra os gerenciadores
 * 
 * @author Flavio & Fernando
 * 
 */
public class Model {

	/**
	 * Gerenciador de professores
	 */
	private PessoaManager professores;
	/**
	 * Gerenciador de alunos
	 */
	private PessoaManager alunos;
	/**
	 * Gerenciador de cursos
	 */
	private CursoManager cursos;
	/**
	 * Gerenciador de turmas
	 */
	private TurmaManager turmas;
	
	public Model(){
		professores = new PessoaManager(Professor.class.toString());
		alunos = new PessoaManager(Aluno.class.toString());
		cursos = new CursoManager();
		turmas = new TurmaManager();		
	}
	
	
	/**
	 * Adiciona uma entidade em um dos gerenciadores
	 * 
	 * @param entidade
	 *            a entidade a ser adicionada
	 * @return true se adicionado com sucesso, false caso contrario
	 * @throws NamingException
	 *             Se nao existe a entidade
	 * @throws IDException
	 *             Se a identificacao for invalida
	 * @throws ClassNotFoundException
	 *             Se nao existe gerenciador para o tipo do objeto passado como
	 *             parametro
	 */
	public boolean adicionar(IEntidade entidade) throws NamingException,
			IDException, ClassNotFoundException {

		if (entidade.getClass().toString().equals(Curso.class.toString()))
			return cursos.adicionarElemento(entidade);

		else if (entidade.getClass().toString().equals(Turma.class.toString()))
			return turmas.adicionarElemento(entidade);
		else if (entidade.getClass().toString().equals(Aluno.class.toString()))
			return alunos.adicionarElemento(entidade);
		else if (entidade.getClass().toString()
				.equals(Professor.class.toString()))
			return professores.adicionarElemento(entidade);
		else
			throw new NamingException();

	}

	/**
	 * Adiciona uma turma no curso
	 * 
	 * @param turma
	 *            a turma a ser adicionada no curso
	 * @param curso
	 *            o curso que recebera a turma
	 * @return true se adicionado com sucesso, false caso contrario
	 * @throws GetException
	 *             Caso nenhum dos objetos forem validos
	 * @throws NamingException
	 *             Se nao existe a entidade
	 */
	public boolean adicionar(Turma turma, Curso curso)
			throws GetException, NamingException {
		if (!cursos.existeElemento(curso)
				|| curso.existeTurma(turma.getIdentificacao())
				|| !professores.existeElemento(turma.getProfessor())
				|| !turmas.existeElemento(turma)

				|| turma.getCurso() != curso)

			return false;

		return curso.adicionar(turma);

	}

	/**
	 * Adiciona aluno na turma
	 * 
	 * @param aluno
	 *            o aluno que sera adicionado na turma
	 * @param turma
	 *            a turma que recebera o aluno
	 * @return true se adicionado com sucesso, false caso contrario
	 * @throws GetException
	 *             Caso nenhum dos objetos forem validos
	 * @throws NamingException
	 *             Se nao existe a entidade
	 */
	public boolean adicionar(Aluno aluno, Turma turma)
			throws GetException, NamingException {
		if (!alunos.existeElemento(aluno))
			return false;

		return turma.addAluno(aluno);

	}

	/**
	 * Retorna uma lista de entidades conforme o tipo de entidade, atributo e
	 * valor passado como parametro
	 * 
	 * @param tipoDeEntidade
	 *            o tipo da entidade
	 * @param tipoDeAtributo
	 *            o tipo do atributo
	 * @param valor
	 *            o valor do atributo
	 * @return uma lista de entidades conforme o tipo de entidade, atributo e
	 *         valor passado como parametro
	 * @throws GetException
	 *             Caso nenhum dos objetos forem validos
	 * @throws NamingException
	 *             Se nao existe a entidade
	 */
	public ArrayList<? extends Object> get(String tipoDeEntidade,
			String tipoDeAtributo, Object valor) throws GetException,
			NamingException {

		if (tipoDeEntidade.equals(Curso.class.toString()))
			return cursos.getElemento(tipoDeAtributo, valor);

		else if (tipoDeEntidade.equals(Turma.class.toString()))
			return turmas.getElemento(tipoDeAtributo, valor);
		else if (tipoDeEntidade.equals(Aluno.class.toString()))
			return alunos.getElemento(tipoDeAtributo, valor);
		else if (tipoDeEntidade.equals(Professor.class.toString()))
			return professores.getElemento(tipoDeAtributo, valor);
		else
			throw new NamingException();

	}

	/**
	 * Atualiza uma entidades conforme o tipo de entidade, atributo e valor
	 * passado como parametro
	 * 
	 * @param entidade
	 *            o tipo da entidade
	 * @param tipoDeAtributo
	 *            o tipo do atributo
	 * @param valor
	 *            o valor do atributo
	 * @return true se atualizado com sucesso, false caso contrario
	 * @throws GetException
	 *             Caso nenhum dos objetos forem validos
	 * @throws NamingException
	 *             Se nao existe a entidade
	 */
	public boolean atualizar(IEntidade entidade, String tipoDoAtributo,
			Object valor) throws GetException, NamingException {

		if (entidade.getClass().toString().equals(Curso.class.toString()))
			return cursos.atualizarElemento(entidade, tipoDoAtributo, valor);
		else if (entidade.getClass().toString().equals(Turma.class.toString()))
			return turmas.atualizarElemento(entidade, tipoDoAtributo, valor);
		else if (entidade.getClass().toString().equals(Aluno.class.toString()))
			return alunos.atualizarElemento(entidade, tipoDoAtributo, valor);
		else if (entidade.getClass().toString()
				.equals(Professor.class.toString()))
			return professores.atualizarElemento(entidade, tipoDoAtributo,
					valor);
		else
			throw new NamingException();

	}

	/**
	 * Remove uma turma do curso
	 * 
	 * @param turma
	 *            a turma a ser removida
	 * @param curso
	 *            o curso ao qual a turma sera removida
	 * @return true se removido com sucesso, false caso contrario
	 * @throws GetException
	 *             Caso nenhum dos objetos forem validos
	 * @throws NamingException
	 *             Se nao existe a entidade
	 */
	public boolean remover(Turma turma, Curso curso) throws GetException,
			NamingException {
		if (!cursos.existeElemento(curso)
				|| !curso.existeTurma(turma.getIdentificacao())
				|| !professores.existeElemento(turma.getProfessor())
				|| !turmas.existeElemento(turma) || turma.getCurso() != curso)
			return false;

		turma.setCurso(null);

		return curso.remover(turma.getIdentificacao());

	}

	/**
	 * Remove um aluno da turma
	 * 
	 * @param aluno
	 *            o aluno a ser removido
	 * @param turma
	 *            a turma ao qual o aluno sera removido
	 * @return true se removido com sucesso, false caso contrario
	 * @throws GetException
	 *             Caso nenhum dos objetos forem validos
	 * @throws NamingException
	 *             Se nao existe a entidade
	 */
	public boolean remover(Aluno aluno, Turma turma)
			throws GetException, NamingException {
		if (!alunos.existeElemento(aluno) || !turmas.existeElemento(turma))
			return false;

		return turma.removerAluno(aluno.getIdentificacao());

	}

	/**
	 * Remove uma entidade de um dos gerenciadores da lista
	 * 
	 * @param entidade
	 *            a entidade a ser removida
	 * @return true se removido com sucesso, false caso contrario
	 * @throws NamingException
	 *             Se nao existe o gerenciador relacionado a entidade
	 */
	public boolean remover(IEntidade entidade) throws NamingException {

		if (entidade.getClass().toString().equals(Curso.class.toString()))
			return cursos.removerElemento(entidade);

		else if (entidade.getClass().toString().equals(Turma.class.toString()))
			return turmas.removerElemento(entidade);
		else if (entidade.getClass().toString().equals(Aluno.class.toString()))
			return alunos.removerElemento(entidade);
		else if (entidade.getClass().toString()
				.equals(Professor.class.toString()))
			return professores.removerElemento(entidade);
		else
			throw new NamingException();

	}

}
