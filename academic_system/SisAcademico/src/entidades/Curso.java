/**
 * 
 */
package entidades;

import interfaces.Cursavel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import exceptions.GetException;

/**
 * Classe que define um Curso
 * 
 * @author Fernando & Flavio
 * 
 */
public class Curso implements Cursavel {

	/**
	 * Lista onde vai ficar armazenado todas as turmas do curso.
	 */
	private List<Turma> turmas;
	/**
	 * Uma identificacao unica do curso
	 */
	private String identificacao;
	/**
	 * O nome do curso
	 */
	private String nome;

	/**
	 * Construtor de Curso
	 * 
	 * @param nome
	 *            o nome do curso
	 */
	public Curso(String nome) {
		turmas = new ArrayList<Turma>();
		this.nome = nome;
		this.identificacao = "";
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean adicionar(Turma turma) {

		if (turma != null)
			return turmas.add(turma);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remover(String codTurma) throws GetException {

		if (!existeTurma(codTurma))
			throw new GetException();

		for (Iterator<Turma> iter = turmas.iterator(); iter.hasNext();) {
			Turma t = iter.next();
			if (t.getIdentificacao().equals(codTurma))
				return turmas.remove(t);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Turma getTurma(String codTurma) throws GetException {

		if (!existeTurma(codTurma))
			throw new GetException();

		for (Iterator<Turma> iter = turmas.iterator(); iter.hasNext();) {
			Turma aux = iter.next();
			if (aux.getIdentificacao().equals(codTurma))
				return aux;

		}

		return null;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existeTurma(String codTurma) throws GetException {

		for (Iterator<Turma> iter = turmas.iterator(); iter.hasNext();) {
			if (iter.next().getIdentificacao().equals(codTurma))
				return true;

		}

		return false;

	}

	/**
	 * Retorna o total de turmas no curso
	 * 
	 * @return o total de turmas no curso
	 */
	public int getTotalDeTurmas() {
		return turmas.size();
	}

	/**
	 * Retorna o iterator da lista de turmas
	 * 
	 * @return o iterator da lista de turmas
	 */
	public Iterator<Turma> getTurmasIterator() {
		return turmas.iterator();
	}

	/**
	 * Lista as turmas du curso
	 * 
	 * @return uma string ordenada de todas as turmas do curso no formato
	 *         NumIdenficacao | NomeDaTurma
	 */
	public String listarTurmas() {
		String todasTurmas = "";
		Collections.sort(turmas);
		for (Iterator<Turma> iter = turmas.iterator(); iter.hasNext();) {
			Turma t = iter.next();
			todasTurmas += (t.getIdentificacao() + " |\t" + t.getNome() + "\n");
		}
		return todasTurmas;
	}


	/**
	 * Retorna uma string com o nome do curso e seu id
	 * 
	 * @return uma string com o nome do curso e seu id
	 */
	@Override
	public String toString() {
		return "Curso: " + nome + "\nCodigo: " + identificacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.parseInt(identificacao);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (!identificacao.equals(other.getIdentificacao()))
			return false;
		return true;
	}

}
