import interfaces.Cursavel;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;

import javax.servlet.http.*;

import util.Util;

import model.SistemaAcademicoModel;
import entidades.*;
import exceptions.*;

/**
 * Servlet implementation class SistemaAcademicoServlet
 */
// @WebServlet("/SistemaAcademicoServlet")
public class SistemaAcademicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Variavel que recebe o Parameter Map da requisicao , ou seja, recebe o
	 * mapeamento dos parametros de um requisicao
	 */
	private Map<String, String[]> getParameterMap;

	/**
	 * Retorna o valor de algum parametro (na qual, a seu nome eh passado como
	 * parametro), que esta contido na variavel global getParameterMap
	 * 
	 * @param name
	 *            Nome que identifica um parametro contido na variavel global
	 *            getParameterMap
	 * @return O valor do parametro para uma determinada requisicao
	 */
	private String getParameter(String name) {
		String value = "" + getParameterMap.get(name)[0];
		return value;
	}

	private PrintWriter out;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// iniciando mini base de dados
		miniBaseDeDados();

		// iniciando o mapeamento de parametros de uma requisicao
		getParameterMap = request.getParameterMap();

		// iniciando variavel de mensagem de retorno
		String mensagemDeRetorno = "";

		// iniciando variavel de resposta para um determinada requisicao
		out = response.getWriter();

		// Metodos referentes ao gerenciamento da lista de Professores

		try {

			if (getParameter("formulario").equals("2155"))

				mensagemDeRetorno = matricularPessoa(
						Professor.class.toString(), getParameter("nome"),
						getParameter("datadenascimento"),
						getParameter("endereco"), getParameter("telefone"));

			if (getParameter("formulario").equals("2255"))

				mensagemDeRetorno = excluirPessoa(Professor.class.toString(),
						getParameter("identificacao"));

			if (getParameter("formulario").equals("2355"))

				mensagemDeRetorno = editarPessoa(Professor.class.toString(),
						getParameter("identificacao"),
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			if (getParameter("formulario").equals("2455"))

				mensagemDeRetorno = consultarPessoa(Professor.class.toString(),
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			// Metodos referentes ao gerenciamento da lista de Alunos

			if (getParameter("formulario").equals("1155"))

				mensagemDeRetorno = matricularPessoa(Aluno.class.toString(),
						getParameter("nome"), getParameter("datadenascimento"),
						getParameter("endereco"), getParameter("telefone"));

			if (getParameter("formulario").equals("1255"))

				mensagemDeRetorno = excluirPessoa(Aluno.class.toString(),
						getParameter("identificacao"));

			if (getParameter("formulario").equals("1355"))

				mensagemDeRetorno = editarPessoa(Aluno.class.toString(),
						getParameter("identificacao"),
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			if (getParameter("formulario").equals("1455"))

				mensagemDeRetorno = consultarPessoa(Aluno.class.toString(),
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			// Metodos referentes ao gerenciamento da lista de Cursos

			if (getParameter("formulario").equals("4155"))

				mensagemDeRetorno = criarCurso(getParameter("nome"));

			if (getParameter("formulario").equals("4255"))

				mensagemDeRetorno = excluirCurso(getParameter("identificacao"));

			if (getParameter("formulario").equals("4355"))

				mensagemDeRetorno = editarCurso(getParameter("identificacao"),
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			if (getParameter("formulario").equals("4455"))

				mensagemDeRetorno = consultarCurso(
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			// Metodos referentes ao gerenciamento da lista de Turmas

			if (getParameter("formulario").equals("3175"))

				mensagemDeRetorno = criarTurma(getParameter("nome"),
						getParameter("professorID"), getParameter("cursoID"));

			if (getParameter("formulario").equals("3275"))

				mensagemDeRetorno = excluirTurma(getParameter("identificacao"));

			if (getParameter("formulario").equals("3375"))

				mensagemDeRetorno = editarTurma(getParameter("identificacao"),
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			if (getParameter("formulario").equals("3475"))

				mensagemDeRetorno = consultarTurma(
						getParameter("tipodeatributo"),
						getParameter("valordoatributo"));

			if (getParameter("formulario").equals("3575"))
				mensagemDeRetorno = adicionarAlunoNaTurma(
						getParameter("alunoid"), getParameter("turmaid"));

			if (getParameter("formulario").equals("3675"))

				mensagemDeRetorno = removerAlunoDaTurma(
						getParameter("alunoid"), getParameter("turmaid"));

		} catch (ParseException e) {
			mensagemDeRetorno = "erro, digite os dados corretamente";
		} catch (NamingException e) {
			mensagemDeRetorno = "erro, digite os dados corretamente";
		} catch (IDException e) {
			mensagemDeRetorno = "erro, digite os dados corretamente";
		} catch (ClassNotFoundException e) {
			mensagemDeRetorno = "erro, digite os dados corretamente";
		} catch (GetException e) {
			mensagemDeRetorno = "erro, digite os dados corretamente";
		}

		out.print(mensagemDeRetorno);

	}

	/**
	 * Classe onde esta armazenado a logica do sistema (academico)
	 */
	private static SistemaAcademicoModel model = new SistemaAcademicoModel();

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
	private String matricularPessoa(String tipoDePessoa, String nome,
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
	private String excluirPessoa(String tipoDePessoa, String identificacao_exata)
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
	private String editarPessoa(String tipoDePessoa,
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
	private String consultarPessoa(String tipoDePessoa, String tipoDeAtributo,
			String valor) throws GetException, NamingException {
		String mensagemDeRetorno = "";

		// Lendo a(s) Pessoa(s) da escola "model"
		ArrayList<? extends Object> getListaDePessoas = null;

		if (tipoDePessoa.equals(Aluno.class.toString()))
			getListaDePessoas = model.get(Aluno.class.toString(),
					tipoDeAtributo, valor);

		if (tipoDePessoa.equals(Professor.class.toString()))
			getListaDePessoas = model.get(Professor.class.toString(),
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

	/**
	 * Metodo para criar um cusro, passando o nome do mesmo como parametro
	 * 
	 * @param nomeDoCurso
	 *            Nome do Curso a ser criado
	 * @return Mensagem de retorno
	 */
	private String criarCurso(String nomeDoCurso) throws NamingException,
			IDException, ClassNotFoundException {

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
	private String excluirCurso(String identificacao_exata)
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
	private String editarCurso(String identificacao_exata,
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
	 *            [identificacao] [identificacao_exata] [turma] [aluno])
	 * @param valor
	 *            O valor que serve de base para a consulta
	 * @return Mensagem de retorno
	 */
	private String consultarCurso(String tipoDeAtributo, String valor)
			throws GetException, NamingException {

		String mensagemDeRetorno = "";

		// lendo os cursos
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
			getCursos = model
					.get(Curso.class.toString(), tipoDeAtributo, valor);

		// configurando a mensagem de retorno
		if (!getCursos.equals(null))
			mensagemDeRetorno = "Curso(s): " + getCursos.toString();

		return mensagemDeRetorno;

	}

	private String criarTurma(String nomeDaTurma, String professorID,
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

	private String excluirTurma(String turmaID) throws GetException,
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
	private String editarTurma(String turmaID, String tipoDeAtributo,
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
	private String consultarTurma(String tipoDeAtributo, String valor)
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
	private String adicionarAlunoNaTurma(String alunoID, String turmaID)
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

	private String removerAlunoDaTurma(String alunoID, String turmaID)
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

	private static int flag = 0;

	private void miniBaseDeDados() {

		try {

			if (flag == 0) {

				// Alunos

				System.out.println(matricularPessoa(Aluno.class.toString(),
						"Joao", "12/12/1212", "rua", "121212112"));
				System.out.println(matricularPessoa(Aluno.class.toString(),
						"Xico", "12/12/1212", "rua", "121212112"));
				System.out.println(matricularPessoa(Aluno.class.toString(),
						"Maria", "12/12/1212", "rua", "121212112"));
				System.out.println(matricularPessoa(Aluno.class.toString(),
						"Jose", "12/12/1212", "rua", "121212112"));

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
				System.out.println(adicionarAlunoNaTurma("20110000", "1"));
				System.out.println(adicionarAlunoNaTurma("20110000", "2"));
				System.out.println(adicionarAlunoNaTurma("20110001", "1"));
				System.out.println(adicionarAlunoNaTurma("20110002", "2"));
				System.out.println(adicionarAlunoNaTurma("20110003", "3"));

				System.out.println("\n\n\n");

				flag = 1;

			}

		} catch (ParseException e) {
			e.printStackTrace();
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
