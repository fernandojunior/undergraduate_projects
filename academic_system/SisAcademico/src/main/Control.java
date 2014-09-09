package main;

import interfaces.Cursavel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import entidades.Aluno;
import entidades.Curso;
import entidades.Pessoa;
import entidades.Professor;
import entidades.Turma;
import exceptions.GetException;
import exceptions.IDException;
import Util.Util;
import managers.Model;

public class Control {

	/**
	 * contem o indice corrente do vetor de sequencia
	 */
	private static int i = 0;

	/**
	 * Classe onde esta armazenado a logica do sistema (academico)
	 */
	private static Model model = new Model();

	/**
	 * Vetor de Sequencia que contem a linha de execucao de uma determinada
	 * requisicao enviada por um cliente. O primeiro indice deve conter a String
	 * que contem a sequencia (em string) que deve ser executada, os demais
	 * indeces sao os parametros necessario para execurar algum metodo do
	 * sistema
	 * 
	 */
	private static List<Integer> vetorDeSequencia = new ArrayList<Integer>();

	/**
	 * Vetor de parametros necessarios para executar um determinado metodo do
	 * sistema
	 */
	private static List<String> vetorDeParametros = new ArrayList<String>();

	/**
	 * Metodo para mapear uma sequencia em String em um Vetor de Sequencia
	 * 
	 * @param menu
	 *            Sequencia em formato de String a ser mapeado no Vetor de
	 *            Sequencia
	 */
	private static void mapearMenu(String menu) {
		for (int i = 0; i < menu.length(); i++)
			vetorDeSequencia.add(i, Integer.parseInt(menu.charAt(i) + ""));
	}

	/**
	 * Metodo para mapear um Vetor de Parametos de um browser cliente, recebidos
	 * pelo Servlet, em um Vetor de parametros do sistema que serao utilizados
	 * para executar um determinado metodo
	 * 
	 * @param parametrosDeRequisicao
	 *            Vetor de Parametos Recebidos pelo Servlet que serao mapeados
	 *            em um vetor de parametros do sistema
	 */
	private static void mapearParametros(
			ArrayList<String> parametrosDeRequisicao) {
		for (int i = 0; i < parametrosDeRequisicao.size(); i++)
			vetorDeParametros.add(i, parametrosDeRequisicao.get(i));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		miniBaseDeDados();

		/**
		 * escolher um dos exemplos de requisicao de um browser cliente Obs:
		 * executar um exemplo por vez. exemploConsultarCurso();
		 * exemploCriarCurso(); exemploEditarPessoa();
		 * exemploMatricularPessoa(); exemploConsultarCurso();
		 * exemploExcluirTurma(); exemploEditarTurma();...
		 * exemploConsultarTurma() exemploAdicionarAlunoNaTurma()
		 */

		ArrayList<String> getParametros = exemploExcluirAlunoDaTurma();

		mapearMenu(getParametros.get(0));

		mapearParametros(getParametros);

		boolean bool = true;

		// se existir opcoes no vetor de menu
		if (vetorDeSequencia.size() > 0)
			while (bool == true)

				switch (vetorDeSequencia.get(i++)) {
				case 1:
					menuPessoa(Aluno.class.toString());
					break;
				case 2:
					menuPessoa(Professor.class.toString());
					break;
				case 3:
					menuTurma();
					break;
				case 4:
					menuCurso();
					break;
				case 5:
					bool = false;
					break;

				}

	}

	private static void menuPessoa(String tipo) {
		String mensagemDeRetorno = "";

		boolean bool = true;
		while (bool == true) {

			try {

				switch (vetorDeSequencia.get(i++)) {
				case 1:

					mensagemDeRetorno = matricularPessoa(tipo,
							vetorDeParametros.get(1), vetorDeParametros.get(2),
							vetorDeParametros.get(3), vetorDeParametros.get(4));

					break;
				case 2:

					mensagemDeRetorno = excluirPessoa(tipo,
							vetorDeParametros.get(1));

					break;
				case 3:

					mensagemDeRetorno = editarPessoa(tipo,
							vetorDeParametros.get(1), vetorDeParametros.get(2),
							vetorDeParametros.get(3));

					break;
				case 4:

					mensagemDeRetorno = consultarPessoa(tipo,
							vetorDeParametros.get(1), vetorDeParametros.get(2));

					break;
				case 5:
					bool = false;
					break;

				}

				if (bool == true)
					System.out.println(mensagemDeRetorno);

			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (NamingException e1) {
				e1.printStackTrace();
			} catch (IDException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (GetException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Metodo para Matricular uma pessoa conferme o seu tipo (existem apenas
	 * dois tipos: Professor e Aluno)
	 * 
	 * @param tipoDePessoa
	 *            O tipo de Pessoa, se eh professor ou aluno
	 * @param nome
	 *            O nome da pessoa
	 * @param dataDeNascimento
	 *            A data de nascimento da pessoa
	 * @param endereco
	 *            O endereco da pessoa
	 * @param telefone
	 *            O telefone da pessoa
	 * @return A mensagem de retorno
	 */
	private static String matricularPessoa(String tipoDePessoa, String nome,
			String dataDeNascimento, String endereco, String telefone)
			throws ParseException, NamingException, IDException,
			ClassNotFoundException {

		String mensagemDeRetorno = "";

		Pessoa novaPessoa = null;

		// criando instancia de Pessoa, conforme o seu tipo
		if (tipoDePessoa.equals(Aluno.class.toString()))
			novaPessoa = new Aluno(nome,
					Util.stringToCalendar(dataDeNascimento), endereco, telefone);
		if (tipoDePessoa.equals(Professor.class.toString()))
			novaPessoa = new Professor(nome,
					Util.stringToCalendar(dataDeNascimento), endereco, telefone);

		// adicionando a pessoa na escola "model", conforme o seu tipo
		if (!novaPessoa.equals(null)) {
			if (model.adicionar(novaPessoa))
				mensagemDeRetorno += " matriculado com sucesso!"
						+ " identificacao: " + novaPessoa.getIdentificacao();
			else
				mensagemDeRetorno += " nao foi matriculado com sucesso!";

			// configurando a mensagem de retorno
			if (tipoDePessoa.equals(Aluno.class.toString()))
				mensagemDeRetorno = "Aluno " + novaPessoa.getNome()
						+ mensagemDeRetorno;
			if (tipoDePessoa.equals(Professor.class.toString()))
				mensagemDeRetorno = "Professor " + novaPessoa.getNome()
						+ mensagemDeRetorno;

		}

		return mensagemDeRetorno;

	}

	/**
	 * Metodo para excluir uma pessoa, baseando-se no tipo de pessoa (Professor
	 * ou Aluno) e na identificacao exata do mesmo passada como parametro
	 * 
	 * @param tipoDePessoa
	 *            O tipo de Pessoa, se eh professor ou aluno
	 * @param identificacao_exata
	 *            A identificacao exata da Pessoa
	 */
	private static String excluirPessoa(String tipoDePessoa,
			String identificacao_exata) throws GetException, NamingException {

		String mensagemDeRetorno = "";

		Pessoa getPessoa = null;

		// lendo a pessoa da escola "model"
		if (tipoDePessoa.equals(Aluno.class.toString()))
			getPessoa = (Aluno) model.get(Aluno.class.toString(),
					"identificacao_exata", identificacao_exata).get(0);
		if (tipoDePessoa.equals(Professor.class.toString()))
			getPessoa = (Professor) model.get(Professor.class.toString(),
					"identificacao_exata", identificacao_exata).get(0);

		// removendo a pessoa lida da escola "model"
		if (!getPessoa.equals(null)) {

			if (model.remover(getPessoa))
				mensagemDeRetorno += " excluido com sucesso!"
						+ " identificacao: " + getPessoa.getIdentificacao();
			else
				mensagemDeRetorno += " nao foi excluido com sucesso!";

			// configurando a mensagem de retorno
			if (tipoDePessoa.equals(Aluno.class.toString()))
				mensagemDeRetorno = "Aluno " + getPessoa.getNome()
						+ mensagemDeRetorno;

			if (tipoDePessoa.equals(Professor.class.toString()))
				mensagemDeRetorno = "Professor " + getPessoa.getNome()
						+ mensagemDeRetorno;

		}

		return mensagemDeRetorno;

	}

	/**
	 * Metodo para editar um atributo de uma pessoa, baseando-se no tipo de
	 * pessoa (Professor ou Aluno), na identificacao exata do mesmo, no tipo de
	 * atributo a ser editado e o valor a ser atribuido no atributo escolhido
	 * passados como parametro
	 * 
	 * @param tipoDePessoa
	 *            O tipo de Pessoa, se eh professor ou aluno
	 * @param identificacao_exata
	 *            A identificacao exata da Pessoa
	 * @param tipoDeAtributo
	 *            O tipo de atributo a ser editado (nome, endereco, telefone)
	 * @param valor
	 *            O valor a ser atribuido no atributo escolhido (tipoDeAtributo)
	 * @return Mensagem de Retorno
	 */
	private static String editarPessoa(String tipoDePessoa,
			String identificacao_exata, String tipoDeAtributo, String valor)
			throws GetException, NamingException {

		String mensagemDeRetorno = "";

		Pessoa getPessoa = null;

		// lendo a pessoa da escola "model"
		if (tipoDePessoa.equals(Aluno.class.toString()))
			getPessoa = (Aluno) model.get(Aluno.class.toString(),
					"identificacao_exata", identificacao_exata).get(0);
		if (tipoDePessoa.equals(Professor.class.toString()))
			getPessoa = (Professor) model.get(Professor.class.toString(),
					"identificacao_exata", identificacao_exata).get(0);

		// atualizando a pessoa da escola "model"
		if (!getPessoa.equals("null")) {

			if (model.atualizar(getPessoa, tipoDeAtributo, valor))
				mensagemDeRetorno += " atualizado com sucesso!"
						+ " identificacao: " + getPessoa.getIdentificacao();
			else
				mensagemDeRetorno += " nao foi atualizado com sucesso!";

			// configurando a mensagem de retorno
			if (tipoDePessoa.equals(Aluno.class.toString()))
				mensagemDeRetorno = "Aluno " + getPessoa.getNome()
						+ mensagemDeRetorno;

			if (tipoDePessoa.equals(Professor.class.toString()))
				mensagemDeRetorno = "Professor " + getPessoa.getNome()
						+ mensagemDeRetorno;

		}

		return mensagemDeRetorno;

	}

	/**
	 * Metodo para consultar uma (lista) pessoa(s), baseando-se no tipo de
	 * pessoa (Professor ou Aluno), no tipo de atributo pelo qual se quer
	 * consultar e o valor a ser que serve de base para a consulta
	 * 
	 * @param tipoDePessoa
	 *            O tipo de Pessoa, se eh professor ou aluno
	 * @param tipoDeAtributo
	 *            O tipo de atributo pelo qual se quer consultar ([nome]
	 *            [telefone] [endereco] [identificacao] [identificacao_exata])
	 * @param valor
	 *            O valor que serve de base para a consulta
	 * @return Mensagem de retorno
	 */
	private static String consultarPessoa(String tipoDePessoa,
			String tipoDeAtributo, String valor) throws GetException,
			NamingException {
		String mensagemDeRetorno = "";

		// Lendo a(s) Pessoa(s) da escola "model"
		ArrayList<? extends Object> getListaDePessoas = null;

		if (tipoDePessoa.equals(Aluno.class.toString()))
			getListaDePessoas = model.get(Aluno.class.toString(),
					tipoDeAtributo, valor);

		if (tipoDePessoa.equals(Aluno.class.toString()))
			getListaDePessoas = model.get(Aluno.class.toString(),
					tipoDeAtributo, valor);

		// Configurando a mensagem de retorno
		if (!getListaDePessoas.equals(null)) {
			if (tipoDePessoa.equals(Aluno.class.toString()))
				mensagemDeRetorno = "Aluno(s): " + getListaDePessoas.toString();
			if (tipoDePessoa.equals(Professor.class.toString()))
				mensagemDeRetorno = "Professor(es): "
						+ getListaDePessoas.toString();
		}

		return mensagemDeRetorno;

	}

	private static void menuCurso() {
		String mensagemDeRetorno = "";

		boolean bool = true;
		while (bool == true) {

			try {

				switch (vetorDeSequencia.get(i++)) {
				case 1:

					mensagemDeRetorno = criarCurso(vetorDeParametros.get(1));

					break;
				case 2:
					mensagemDeRetorno = excluirCurso(vetorDeParametros.get(1));

					break;
				case 3:

					mensagemDeRetorno = editarCurso(vetorDeParametros.get(1),
							vetorDeParametros.get(2), vetorDeParametros.get(3));

					break;
				case 4:
					mensagemDeRetorno = consultarCurso(
							vetorDeParametros.get(1), vetorDeParametros.get(2));

					break;
				case 5:
					bool = false;
					break;

				}

				if (bool == true)
					System.out.println(mensagemDeRetorno);

			} catch (NamingException e) {
				e.printStackTrace();
			} catch (IDException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (GetException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Metodo para criar um cusro, passando o nome do mesmo como parametro
	 * 
	 * @param nomeDoCurso
	 *            Nome do Curso a ser criado
	 * @return Mensagem de retorno
	 */
	private static String criarCurso(String nomeDoCurso)
			throws NamingException, IDException, ClassNotFoundException {

		String mensagemDeRetorno = "";

		// instanciando um novo curso
		Curso novoCurso = new Curso(nomeDoCurso);

		// adicionando um curso na escola "model"
		if (model.adicionar(novoCurso))
			mensagemDeRetorno = " foi adicionado com sucesso! identificacao: "
					+ novoCurso.getIdentificacao();
		else
			mensagemDeRetorno = " nao foi adicionado com sucesso!";

		// configurando a mensagem de retorno
		mensagemDeRetorno = "Curso " + nomeDoCurso + mensagemDeRetorno;

		return mensagemDeRetorno;

	}

	/**
	 * Metodo para excluir um curso conforme a identificacao exata do mesmo
	 * 
	 * @param identificacao_exata
	 *            Identificacao exata do curso a ser excluido
	 * @return Mensagem de retorno
	 */
	private static String excluirCurso(String identificacao_exata)
			throws GetException, NamingException {
		String mensagemDeRetorno = "";

		// lendo curso da escola "model"
		Cursavel getCurso = (Curso) model.get(Curso.class.toString(),
				"identificacao_exata", identificacao_exata).get(0);

		// excluindo curso da escola "model"
		if (!getCurso.equals(null))
			if (model.remover((Curso) getCurso))
				mensagemDeRetorno += " excluido com sucesso!"
						+ " identificacao: " + getCurso.getIdentificacao();
			else
				mensagemDeRetorno += " nao foi excluido com sucesso!";

		// configurando a mensagem de retorno
		mensagemDeRetorno = "Curso " + getCurso.getNome() + mensagemDeRetorno;

		return mensagemDeRetorno;

	}

	/**
	 * Metodo para editar um curso conforme sua identificacao exata, o tipo de
	 * atributo a ser modificado e valor a ser atribuido nesse atributo
	 * 
	 * @param identificacao_exata
	 *            Identicacao exata do curso a ser editado
	 * @param tipoDeAtributo
	 *            Tipo do Atributo a ser modificado (nome)
	 * @param valor
	 *            Valor a ser atribuido no atributo escolhido
	 * @return Mensagem de retorno
	 */
	private static String editarCurso(String identificacao_exata,
			String tipoDeAtributo, String valor) throws GetException,
			NamingException {

		String mensagemDeRetorno = "";

		// lendo curso da escola "model"
		Cursavel getCurso = (Curso) model.get(Curso.class.toString(),
				"identificacao_exata", identificacao_exata).get(0);

		// editando curso da escola
		if (!getCurso.equals(null))

			if (model.atualizar((Curso) getCurso, tipoDeAtributo, valor))
				mensagemDeRetorno += " atualizado com sucesso!"
						+ " identificacao: " + getCurso.getIdentificacao();
			else
				mensagemDeRetorno += " nao foi atualizado com sucesso!";

		// configurando mensagem de retorno
		mensagemDeRetorno = "Curso " + getCurso.getNome() + mensagemDeRetorno;

		return mensagemDeRetorno;

	}

	/**
	 * Metodo para consultar uma (lista) curso(s), baseando-se no tipo de
	 * atributo pelo qual se quer consultar e o valor a ser que serve de base
	 * para a consulta
	 * 
	 * @param tipoDeAtributo
	 *            O tipo de atributo pelo qual se quer consultar ([nome]
	 *            [telefone] [endereco] [identificacao] [identificacao_exata])
	 * @param valor
	 *            O valor que serve de base para a consulta
	 * @return Mensagem de retorno
	 */
	private static String consultarCurso(String tipoDeAtributo, String valor)
			throws GetException, NamingException {
		// "Por qual parametro voce deseja consultar? [nome] [identificacao] [identificacao_exata] [turma] [aluno]"

		String mensagemDeRetorno = "";

		System.out.println("valor");

		Object getCursos = null;

		if (tipoDeAtributo.equalsIgnoreCase("turma")) {
			Turma getTurma = (Turma) model.get(Turma.class.toString(),
					"identificacao", valor).get(0);
			getCursos = model.get(Curso.class.toString(), tipoDeAtributo,
					getTurma);

		} else

		if (tipoDeAtributo.equalsIgnoreCase("aluno")) {
			Aluno getAluno = (Aluno) model.get(Aluno.class.toString(),
					"identificacao", valor).get(0);
			getCursos = model.get(Turma.class.toString(), tipoDeAtributo,
					getAluno);

		} else
			getCursos = (Curso) model.get(Curso.class.toString(),
					tipoDeAtributo, valor).get(0);

		if (!getCursos.equals(null))
			mensagemDeRetorno = "Curso(s): " + getCursos.toString();

		return mensagemDeRetorno;

	}

	private static void menuTurma() {
		// String entidade = "Turma";

		String mensagemDeRetorno = "";

		boolean bool = true;

		while (bool == true) {

			try {

				switch (vetorDeSequencia.get(i++)) {

				case 1:

					mensagemDeRetorno = criarTurma(vetorDeParametros.get(1),
							vetorDeParametros.get(2), vetorDeParametros.get(3));

					break;
				case 2:
					mensagemDeRetorno = excluirTurma(vetorDeParametros.get(1));
					break;
				case 3:
					mensagemDeRetorno = editarTurma(vetorDeParametros.get(1),
							vetorDeParametros.get(2), vetorDeParametros.get(3));
					break;
				case 4:
					mensagemDeRetorno = consultarTurma(
							vetorDeParametros.get(1), vetorDeParametros.get(2));
					break;
				case 5:
					mensagemDeRetorno = adicionarAlunoNaTurma(
							vetorDeParametros.get(1), vetorDeParametros.get(2));
					break;
				case 6:
					mensagemDeRetorno = removerAlunoDaTurma(
							vetorDeParametros.get(1), vetorDeParametros.get(2));
					break;
				case 7:
					bool = false;
					break;

				}

				if (bool == true)
					System.out.println(mensagemDeRetorno);

			} catch (GetException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (IDException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	private static String criarTurma(String nomeDaTurma, String professorID,
			String cursoID) throws GetException, NamingException, IDException,
			ClassNotFoundException {

		String mensagemDeRetorno = "";

		// lendo professor
		Professor getProfessor = (Professor) model.get(
				Professor.class.toString(), "identificacao_exata", professorID)
				.get(0);

		// lendo curso
		Curso getCurso = (Curso) model.get(Curso.class.toString(),
				"identificacao_exata", cursoID).get(0);

		// criando nova turma
		if (!getProfessor.equals(null) && !getCurso.equals(null)) {

			Turma novaTurma = new Turma(nomeDaTurma, getProfessor,
					(Curso) getCurso);

			if (model.adicionar(novaTurma)) {

				if (model.adicionar(novaTurma, getCurso))
					mensagemDeRetorno = " foi adicionado com sucesso! identificacao: "
							+ novaTurma.getIdentificacao();
				else {

					mensagemDeRetorno = " nao foi adicionado com sucesso!";
					model.remover(novaTurma);

				}
			}

			else
				mensagemDeRetorno = " nao foi adicionado com sucesso!";

			// configurando mensagem de retorno
			mensagemDeRetorno = "Turma " + nomeDaTurma + mensagemDeRetorno;

		}

		return mensagemDeRetorno;
	}

	private static String excluirTurma(String turmaID) throws GetException,
			NamingException {
		String mensagemDeRetorno = "";

		// lendo a turma
		Turma getTurma = (Turma) model.get(Turma.class.toString(),
				"identificacao_exata", turmaID).get(0);

		// removendo a turma
		if (!getTurma.equals(null))

			if (model.remover((Turma) getTurma))
				mensagemDeRetorno += " excluido com sucesso!"
						+ " identificacao: " + getTurma.getIdentificacao();
			else
				mensagemDeRetorno += " nao foi excluido com sucesso!";

		// configurando a mensagem de retorno
		mensagemDeRetorno = "Turma " + getTurma.getNome() + mensagemDeRetorno;

		return mensagemDeRetorno;

	}

	// atributo que pode ser editado: nome
	private static String editarTurma(String turmaID, String tipoDeAtributo,
			String valor) throws GetException, NamingException {
		String mensagemDeRetorno = "";

		// lendo turma
		Turma getTurma = (Turma) model.get(Turma.class.toString(),
				"identificacao_exata", turmaID).get(0);

		// atualizando turma
		if (!getTurma.equals(null)) {

			if (model.atualizar((Turma) getTurma, tipoDeAtributo, valor))
				mensagemDeRetorno += " atualizado com sucesso!"
						+ " identificacao: " + getTurma.getIdentificacao();
			else
				mensagemDeRetorno += " nao foi atualizado com sucesso!";

			// configurando mensagem de retorno
			mensagemDeRetorno = "Turma " + getTurma.getNome()
					+ mensagemDeRetorno;

		}

		return mensagemDeRetorno;

	}

	// atributos de consulta: [nome, identificacao, identificacao_exata,
	// professor, aluno]
	private static String consultarTurma(String tipoDeAtributo, String valor)
			throws GetException, NamingException {
		String mensagemDeRetorno = "";

		// lendo turmas
		Object getTurmas = null;

		if (tipoDeAtributo.equalsIgnoreCase("professor")) {
			Professor getProfessor = (Professor) model.get(
					Professor.class.toString(), "identificacao_exata", valor)
					.get(0);
			getTurmas = model.get(Turma.class.toString(), tipoDeAtributo,
					getProfessor);

		} else if (tipoDeAtributo.equalsIgnoreCase("aluno")) {

			Aluno getAluno = (Aluno) model.get(Aluno.class.toString(),
					"identificacao_exata", valor).get(0);

			getTurmas = model.get(Turma.class.toString(), tipoDeAtributo,
					getAluno);

		} else
			getTurmas = model
					.get(Turma.class.toString(), tipoDeAtributo, valor);

		// configurando mensagem de retorno
		if (!getTurmas.equals(null))
			mensagemDeRetorno = getTurmas.toString();

		return mensagemDeRetorno;

	}

	// adicionar aluno na turma
	private static String adicionarAlunoNaTurma(String alunoID, String turmaID)
			throws GetException, NamingException {

		String mensagemDeRetorno = "";

		// lendo aluno
		Aluno getAluno = (Aluno) model.get(Aluno.class.toString(),
				"identificacao_exata", alunoID).get(0);

		Turma getTurma = (Turma) model.get(Turma.class.toString(),
				"identificacao_exata", turmaID).get(0);

		// adicionando aluno na turma da escola "model"
		if (!getAluno.equals(null) && !getTurma.equals(null)) {

			if (model.adicionar(getAluno, getTurma))
				mensagemDeRetorno = " Adicionado com sucesso na turma ";
			else
				mensagemDeRetorno = " Nao foi adicionado com sucesso na turma ";

			// configurando mensagem de retorno
			mensagemDeRetorno = getAluno.getNome() + mensagemDeRetorno
					+ getTurma.getNome();

		}

		return mensagemDeRetorno;
	}

	private static String removerAlunoDaTurma(String alunoID, String turmaID)
			throws GetException, NamingException {

		String mensagemDeRetorno = "";

		// lendo aluno
		Aluno getAluno = (Aluno) model.get(Aluno.class.toString(),
				"identificacao_exata", alunoID).get(0);

		// lendo turma
		Turma getTurma = (Turma) model.get(Turma.class.toString(),
				"identificacao_exata", turmaID).get(0);

		// removendo aluno da turma
		if (!getAluno.equals(null) && !getTurma.equals(null)) {

			if (model.remover(getAluno, getTurma))
				mensagemDeRetorno = " foi removido com sucesso da turma ";
			else
				mensagemDeRetorno = " nao foi removido com sucesso da turma ";

			// configurando mensagem de retorno
			mensagemDeRetorno = getAluno.getNome() + mensagemDeRetorno
					+ getTurma.getNome();
		}

		return mensagemDeRetorno;
	}

	/*
	 * **************************** Area de exemplos
	 */

	/* mini base de dados */

	private static void miniBaseDeDados() {

		try {

			// Alunos

			System.out.println(matricularPessoa(Aluno.class.toString(), "Joao",
					"12/12/1212", "rua", "121212112"));
			System.out.println(matricularPessoa(Aluno.class.toString(), "Xico",
					"12/12/1212", "rua", "121212112"));
			System.out.println(matricularPessoa(Aluno.class.toString(),
					"Maria", "12/12/1212", "rua", "121212112"));
			System.out.println(matricularPessoa(Aluno.class.toString(), "Jose",
					"12/12/1212", "rua", "121212112"));

			// Professores

			System.out.println(matricularPessoa(Professor.class.toString(),
					"professor1", "12/12/1212", "rua", "121212112"));
			System.out.println(matricularPessoa(Professor.class.toString(),
					"professor2", "12/12/1212", "rua", "121212112"));
			System.out.println(matricularPessoa(Professor.class.toString(),
					"professor3", "12/12/1212", "rua", "121212112"));
			System.out.println(matricularPessoa(Professor.class.toString(),
					"professor4", "12/12/1212", "rua", "121212112"));

			// Cursos

			System.out.println(criarCurso("telematica"));
			System.out.println(criarCurso("matematica"));
			System.out.println(criarCurso("redes"));
			System.out.println(criarCurso("tecnologia"));

			// Turmas

			System.out.println(criarTurma("turma1", "20111000", "0"));
			System.out.println(criarTurma("turma2", "20111001", "0"));
			System.out.println(criarTurma("turma3", "20111001", "0"));
			System.out.println(criarTurma("turma4", "20111001", "0"));

			// Adicionando alunos nas turmas

			System.out.println(adicionarAlunoNaTurma("20110000", "0"));
			System.out.println(adicionarAlunoNaTurma("20110001", "1"));
			System.out.println(adicionarAlunoNaTurma("20110002", "2"));
			System.out.println(adicionarAlunoNaTurma("20110003", "3"));

			System.out.println("\n\n\n");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * *************** Exemplos de Requisicoes de um browser cliente
	 */

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploMatricularPessoa() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("1155");

		simulacaoGetRequestParams.add("fernando");
		simulacaoGetRequestParams.add("12/12/1212");
		simulacaoGetRequestParams.add("rua");
		simulacaoGetRequestParams.add("88128812");

		return simulacaoGetRequestParams;
	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploExcluirPessoa() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("1255");

		simulacaoGetRequestParams.add("20110000");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploEditarPessoa() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("1355");

		simulacaoGetRequestParams.add("20110000");
		simulacaoGetRequestParams.add("nome");
		simulacaoGetRequestParams.add("zezzin");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploConsultarPessoa() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("1455");

		simulacaoGetRequestParams.add("identificacao");
		simulacaoGetRequestParams.add("2011");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploCriarCurso() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("4155");

		simulacaoGetRequestParams.add("telematica");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploExcluirCurso() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("4255");

		simulacaoGetRequestParams.add("0");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploEditarCurso() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("4355");

		simulacaoGetRequestParams.add("0");
		simulacaoGetRequestParams.add("nome");
		simulacaoGetRequestParams.add("nomeeditado");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploConsultarCurso() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("4455");

		simulacaoGetRequestParams.add("identificacao");
		simulacaoGetRequestParams.add("0");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploCriarTurma() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("3175");

		simulacaoGetRequestParams.add("turma1");
		simulacaoGetRequestParams.add("20111000");
		simulacaoGetRequestParams.add("0");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploExcluirTurma() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("3275");

		simulacaoGetRequestParams.add("0");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploEditarTurma() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("3375");

		simulacaoGetRequestParams.add("0");
		simulacaoGetRequestParams.add("nome");
		simulacaoGetRequestParams.add("turmaABC");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploConsultarTurma() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("3475");

		simulacaoGetRequestParams.add("nome");
		simulacaoGetRequestParams.add("turma");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploAdicionarAlunoNaTurma() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("3575");

		simulacaoGetRequestParams.add("20110000");
		simulacaoGetRequestParams.add("0");

		return simulacaoGetRequestParams;

	}

	@SuppressWarnings("unused")
	private static ArrayList<String> exemploExcluirAlunoDaTurma() {
		ArrayList<String> simulacaoGetRequestParams = new ArrayList<String>();

		simulacaoGetRequestParams.add("3675");

		simulacaoGetRequestParams.add("20110000");
		simulacaoGetRequestParams.add("0");

		return simulacaoGetRequestParams;

	}

}
