package entidades;

import interfaces.IEntidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Classe que define uma Turma
 * 
 * @author Flavio & Fernando
 * 
 */
public class Turma implements Comparable<Turma>, IEntidade {

	/**implements Comparable<Turma> 
	 * Professor da turma
	 */
	private Professor professor;
	/**
	 * Lista de alunos da turma
	 */
	private List<Aluno> alunos;
	/**
	 * Curso ao qual a turma pertence
	 */
	private Curso curso;
	/**
	 * Identificacao unica da turma
	 */
	private String identificacao;
	/**
	 * O nome da turma
	 */
	private String nome;

	/**
	 * Construtor de Turma
	 * 
	 * @param nome
	 *            o nome da turma
	 * @param professor
	 *            o professor da turma
	 * @param curso
	 *            o curso ao qual a turma pertence
	 */
	public Turma(String nome, Professor professor, Curso curso) {
		this.alunos = new ArrayList<Aluno>();
		this.professor = professor;
		this.nome = nome;
		this.identificacao="";
		this.curso = curso;
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
		
		//this.identificacao = identificacao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentificacao() {
		return identificacao;
	}

	/**
	 * Retorna o curso ao qual a turma pertence
	 * 
	 * @return o curso ao qual a turma pertence
	 */
	public Curso getCurso() {
		return curso;
	}

	/**
	 * Altera o curso ao qual a turma pertence
	 * 
	 * @param curso
	 *            o novo curso para turma
	 */
	public void setCurso(Curso curso) {
		this.curso = curso;
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
	 * Altera o professor da turma
	 * 
	 * @param professor
	 *            o novo professor da turma
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	/**
	 * Retorna o professor da turma
	 * 
	 * @return o professor da turma
	 */
	public Professor getProfessor() {
		return professor;
	}

	/**
	 * Adiciona um aluno a turma
	 * 
	 * @param aluno
	 *            o aluno que sera adicionado na turma
	 * @return true se adicionado com sucesso, false caso contrario
	 */
	public boolean addAluno(Aluno aluno) {
		if (aluno != null && !this.isFull() && !alunos.contains(aluno))
			return alunos.add(aluno);
		return false;
	}

	/**
	 * Checa se a turma esta completa, max 25 alunos por turma
	 * 
	 * @return true se esta completa, false caso contrario
	 */
	private boolean isFull() {
		return alunos.size() >= 25;
	}

	/**
	 * Remeve um alunno da turma
	 * 
	 * @param matricula
	 *            a matricula do aluno a ser removido
	 * @return true se removido com sucesso, false caso contrario
	 */
	public boolean removerAluno(String matricula) {
		for (Iterator<Aluno> iter = alunos.iterator(); iter.hasNext();) {
			Aluno a = iter.next();
			if (a.getIdentificacao().equals(matricula))
				return alunos.remove(a);
		}
		return false;
	}

	/**
	 * Retorna um aluno da turma
	 * 
	 * @param matricula
	 *            a matricula do aluno a ser consultado
	 * @return o aluno com a matricula passada como parametro
	 */
	public Aluno getAluno(String matricula) {
		for (Iterator<Aluno> iter = alunos.iterator(); iter.hasNext();) {
			Aluno a = iter.next();
			if (a.getIdentificacao().equals(matricula))
				return a;
		}
		return null;
	}

	/**
	 * Checa se o aluno ja esta na turma
	 * 
	 * @param identificacao
	 *            a matricula do aluno
	 * @return true se ja esta na turma, false caso contrario
	 */
	public boolean existeAluno(String identificacao) {

		for (Iterator<Aluno> iter = alunos.iterator(); iter.hasNext();) {
			if (iter.next().getIdentificacao().equals(identificacao))
				return true;

		}

		return false;

	}

	/**
	 * Retorna uma String ordenada pelo nome dos alunos com todos os alunos da
	 * turma
	 * 
	 * @return uma String ordenada pelo nome dos alunos com todos os alunos da
	 *         turma
	 */
	public String listarAlunos() {
		String todaTurma = "";
		Collections.sort(alunos);

		for (Iterator<Aluno> iter = alunos.iterator(); iter.hasNext();) {
			Aluno a = iter.next();
			todaTurma += (a.getIdentificacao() + " |\t" + a.getNome() + "\n");
		}

		return todaTurma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Turma: " + nome + "\nCodigo: " + identificacao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Turma turma) {
		return getNome().compareTo(turma.getNome());
	}

}
