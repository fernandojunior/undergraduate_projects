package main;

import interfaces.Cursavel;

import java.text.ParseException;
import java.util.Calendar;

import javax.naming.NamingException;

import entidades.Aluno;
import entidades.Curso;
import entidades.Pessoa;
import entidades.Professor;
import entidades.Turma;
import exceptions.DataException;
import exceptions.GetException;
import exceptions.IDException;
import exceptions.TelefoneException;
import Util.Util;
import managers.EscolaManager_Linhadecomando;

@Deprecated
public class Main_Linhadecomando extends EscolaManager_Linhadecomando {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean bool = true;

		while (bool == true)

			switch (menuPrincipal()) {
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

	private static int menuPrincipal() {
		System.out
				.println("Digite uma das opcoes: \n 1) Gerenciar Alunos; \n 2) Gerenciar Professores; \n 3) Gerenciar Turmas; \n 4) Gerenciar Cursos;\n 5) Sair.");

		return Util.lerInteiro();
	}

	private static void menuPessoa(String tipo) {
		boolean bool = true;

		String entidade = "";

		if (tipo.equals(Aluno.class.toString()))
			entidade = "Aluno";
		else if (tipo.equals(Professor.class.toString()))
			entidade = "Professor";

		while (bool == true) {

			System.out.println("Digite uma das opcoes: \n 1) Matricular "
					+ entidade + "; \n 2) Excluir " + entidade
					+ "; \n 3) Editar " + entidade + "; \n 4) Consultar "
					+ entidade + "; \n 5) Voltar.");

			switch (Util.lerInteiro()) {
			case 1:
				matricularPessoa(tipo);
				break;
			case 2:
				excluirPessoa(tipo);
				break;
			case 3:
				editarPessoa(tipo);
				break;
			case 4:
				consultarPessoa(tipo);
				break;
			case 5:
				bool = false;
				break;

			}

			System.out.println();

		}

	}

	private static void matricularPessoa(String tipo) {

		String msg = "";

		try {

			System.out.println("nome");
			String nome = Util.lerString();

			System.out.println("nascimento");
			Object dataDeNascimento = null;

			while (dataDeNascimento == null)
				try {

					dataDeNascimento = Util.lerData();

					// if (dataDeNascimento.equals("voltar"))
					// break;

				} catch (DataException e) {
					System.out
							.println("Data invalida! Entre com a data novamente! ex: 12/12/1992");
				}

			if (!dataDeNascimento.equals("voltar")) {

				System.out.println("endereco");
				String endereco = Util.lerString();

				System.out.println("telefone");
				String telefone = null;
				while (telefone == null)
					try {
						telefone = Util.lerTelefone();

						// if (telefone.equals("voltar"))
						// break;

					} catch (TelefoneException e) {
						System.out
								.println("Telefone invalido! Entre com o telefone novamente!");

					}

				if (!telefone.equals("voltar")) {

					if (tipo.equals(Aluno.class.toString())) {
						Aluno novo = new Aluno(nome,
								(Calendar) dataDeNascimento, endereco, telefone);

						if (adicionar(novo))
							msg += " matriculado com sucesso!"
									+ " identificacao: "
									+ novo.getIdentificacao();
						else
							msg += " nao foi matriculado com sucesso!";

						System.out.println("Aluno " + novo.getNome() + msg);
					} else if (tipo.equals(Professor.class.toString())) {
						Professor novo = new Professor(nome,
								(Calendar) dataDeNascimento, endereco, telefone);

						if (adicionar(novo))
							msg += " matriculado com sucesso!"
									+ " identificacao: "
									+ novo.getIdentificacao();
						else
							msg += " nao foi matriculado com sucesso!";

						System.out.println("Professor " + novo.getNome() + msg);
					}
				}
			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void excluirPessoa(String tipo) {

		try {

			System.out.println("identificacao_exata");
			String identificacao = null;
			Pessoa get = null;

			while (get == null) {

				identificacao = Util.lerString();

				if (identificacao.equals("voltar"))
					break;
				try {

					if (tipo.equals(Aluno.class.toString()))
						get = (Aluno) get(Aluno.class.toString(),
								"identificacao_exata", identificacao).get(0);
					if (tipo.equals(Professor.class.toString()))
						get = (Professor) get(Professor.class.toString(),
								"identificacao_exata", identificacao).get(0);

				} catch (GetException e) {
					System.out
							.println("Identificacao invalida! Entre com a identificacao novamente! Para sair, digite 'voltar'");

					get = null;
				}

			}

			String msg = "";

			if (!identificacao.equals("voltar"))

				if (tipo.equals(Aluno.class.toString())) {

					if (remover((Aluno) get))
						msg += " excluido com sucesso!" + " identificacao: "
								+ get.getIdentificacao();
					else
						msg += " nao foi excluido com sucesso!";

					System.out.println("Aluno " + get.getNome() + msg);

				} else if (tipo.equals(Professor.class.toString())) {

					if (remover((Professor) get))
						msg += " excluido com sucesso!" + " identificacao: "
								+ get.getIdentificacao();
					else
						msg += " nao foi excluido com sucesso!";

					System.out.println("Professor " + get.getNome() + msg);

				}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void editarPessoa(String tipo) {

		try {

			System.out.println("identificacao_exata");
			String identificacao = null;
			Pessoa get = null;

			while (get == null) {

				identificacao = Util.lerString();

				if (identificacao.equals("voltar"))
					break;

				try {

					if (tipo.equals(Aluno.class.toString()))
						get = (Aluno) get(Aluno.class.toString(),
								"identificacao_exata", identificacao).get(0);
					if (tipo.equals(Professor.class.toString()))
						get = (Professor) get(Professor.class.toString(),
								"identificacao_exata", identificacao).get(0);

				} catch (GetException e) {
					System.out
							.println("Identificacao invalida! Entre com a identificacao novamente! Para sair, digite 'voltar'");

					get = null;
				}

			}

			if (!identificacao.equals("voltar")) {

				System.out
						.println("O que voce deseja alterar? [nome] [telefone] [endereco]");
				String tipoDeMetodo = Util.lerString();
				System.out.println("Digite o valor");
				String valor = Util.lerString();

				String msg = "";

				if (tipo.equals(Aluno.class.toString())) {

					if (atualizar((Aluno) get, tipoDeMetodo, valor))
						msg += " atualizado com sucesso!" + " identificacao: "
								+ get.getIdentificacao();
					else
						msg += " nao foi atualizado com sucesso!";

					System.out.println("Aluno " + get.getNome() + msg);

				} else if (tipo.equals(Professor.class.toString())) {

					if (atualizar((Professor) get, tipoDeMetodo, valor))
						msg += " atualizado com sucesso!" + " identificacao: "
								+ get.getIdentificacao();
					else
						msg += " nao foi atualizado com sucesso!";

					System.out.println("Professor " + get.getNome() + msg);

				}
			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void consultarPessoa(String tipo) {

		try {

			System.out
					.println("Por qual parametro voce deseja consultar? [nome] [telefone] [endereco] [identificacao] [identificacao_exata]");
			String tipoDeMetodo = Util.lerString();

			if (Util.getListaDeAtributosPessoa().contains(tipoDeMetodo)) {

				System.out.println("valor");
				String valor = null;

				Object get = null;

				while (get == null) {

					valor = Util.lerString();

					if (valor.equals("voltar"))
						break;

					try {

						if (tipo.equals(Aluno.class.toString()))

							if (tipoDeMetodo.equalsIgnoreCase("telefone")
									|| tipoDeMetodo.equalsIgnoreCase("nome")
									|| tipoDeMetodo
											.equalsIgnoreCase("endereco")
									|| tipoDeMetodo
											.equalsIgnoreCase("identificacao")) {
								get = get(Aluno.class.toString(), tipoDeMetodo,
										valor);

							} else

								get = (Aluno) get(Aluno.class.toString(),
										tipoDeMetodo, valor).get(0);
						if (tipo.equals(Professor.class.toString()))

							if (tipoDeMetodo.equalsIgnoreCase("telefone")
									|| tipoDeMetodo
											.equalsIgnoreCase("endereco")
									|| tipoDeMetodo
											.equalsIgnoreCase("identificacao")) {
								get = get(Professor.class.toString(),
										tipoDeMetodo, valor);

							} else

								get = (Professor) get(
										Professor.class.toString(),
										tipoDeMetodo, valor).get(0);

					} catch (GetException e) {
						System.out
								.println("Operacao invalida! Entre com um valor novamente! para sair, digite 'voltar'");

						get = null;
					}

				}

				if (!valor.equals("voltar"))
					if (tipo.equals(Aluno.class.toString()))
						System.out.println("Aluno " + get.toString());
					else if (tipo.equals(Professor.class.toString()))
						System.out.println("Professor " + get.toString());

			} else
				System.out.println("Atencao! Metodo invalido");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void menuCurso() {
		String entidade = "Curso";

		boolean bool = true;

		while (bool == true) {
			System.out.println("Digite uma das opcoes: \n 1) Criar " + entidade
					+ "; \n 2) Excluir " + entidade + "; \n 3) Editar "
					+ entidade + "; \n 4) Consultar " + entidade
					+ "; \n 5) Voltar.");

			switch (Util.lerInteiro()) {
			case 1:
				criarCurso();
				break;
			case 2:
				excluirCurso();
				break;
			case 3:
				editarCurso();
				break;
			case 4:
				consultarCurso();
				break;
			case 5:
				bool = false;
				break;

			}

			System.out.println();

		}

	}

	private static void criarCurso() {

		System.out.println("Digite o nome do curso");
		String nomeDoCurso = Util.lerString();

		Curso novo = new Curso(nomeDoCurso);

		String msg = "";

		try {

			if (adicionar(novo))
				msg = " foi adicionado com sucesso! identificacao: "
						+ novo.getIdentificacao();
			else
				msg = " nao foi adicionado com sucesso!";
			System.out.println("Curso " + nomeDoCurso + msg);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void excluirCurso() {

		try {

			System.out.println("identificacao_exata");
			String identificacao = null;
			Cursavel get = null;

			while (get == null) {

				identificacao = Util.lerString();

				if (identificacao.equals("voltar"))
					break;
				try {

					get = (Curso) get(Curso.class.toString(),
							"identificacao_exata", identificacao).get(0);

				} catch (GetException e) {
					System.out
							.println("Identificacao invalida! Entre com a identificacao novamente! Para sair, digite 'voltar'");

					get = null;
				}

			}

			String msg = "";

			if (!identificacao.equals("voltar")) {

				if (remover((Curso) get))
					msg += " excluido com sucesso!" + " identificacao: "
							+ get.getIdentificacao();
				else
					msg += " nao foi excluido com sucesso!";

				System.out.println("Curso " + get.getNome() + msg);

			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void editarCurso() {
		try {

			System.out.println("identificacao_exata");
			String identificacao = null;
			Cursavel get = null;

			while (get == null) {

				identificacao = Util.lerString();

				if (identificacao.equals("voltar"))
					break;

				try {

					get = (Curso) get(Curso.class.toString(),
							"identificacao_exata", identificacao).get(0);

				} catch (GetException e) {
					System.out
							.println("Identificacao invalida! Entre com a identificacao novamente! Para sair, digite 'voltar'");

					get = null;
				}

			}

			if (!identificacao.equals("voltar")) {

				System.out.println("nome");
				String tipoDeMetodo = "nome";

				String valor = Util.lerString();

				String msg = "";

				if (atualizar((Curso) get, tipoDeMetodo, valor))
					msg += " atualizado com sucesso!" + " identificacao: "
							+ get.getIdentificacao();
				else
					msg += " nao foi atualizado com sucesso!";

				System.out.println("Curso " + get.getNome() + msg);

			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void consultarCurso() {

		try {

			System.out
					.println("Por qual parametro voce deseja consultar? [nome] [identificacao] [identificacao_exata] [turma] [aluno]");
			String tipoDeMetodo = Util.lerString();

			if (Util.getListaDeAtributosCurso().contains(tipoDeMetodo)) {

				System.out.println("valor");
				String valor = null;

				Object get = null;

				while (get == null) {

					valor = Util.lerString();

					if (valor.equals("voltar"))
						break;

					try {

						if (tipoDeMetodo.equalsIgnoreCase("nome")) {
							get = get(Curso.class.toString(), tipoDeMetodo,
									valor);

						} else

						if (tipoDeMetodo.equalsIgnoreCase("turma")) {
							Turma getTurma = (Turma) get(
									Turma.class.toString(), "identificacao",
									valor).get(0);
							get = get(Curso.class.toString(), tipoDeMetodo,
									getTurma);

						} else

						if (tipoDeMetodo.equalsIgnoreCase("aluno")) {
							Aluno getAluno = (Aluno) get(
									Aluno.class.toString(), "identificacao",
									valor).get(0);
							get = get(Turma.class.toString(), tipoDeMetodo,
									getAluno);

						} else

							get = (Curso) get(Curso.class.toString(),
									tipoDeMetodo, valor).get(0);

					} catch (GetException e) {
						System.out
								.println("Operacao invalida! Entre com um valor novamente! para sair, digite 'voltar'");

						get = null;
					}

				}

				if (!valor.equals("voltar"))

					System.out.println(" " + get.toString());

			} else
				System.out.println("Atencao! Metodo invalido");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void menuTurma() {
		String entidade = "Turma";

		boolean bool = true;

		while (bool == true) {
			System.out
					.println("Digite uma das opcoes: \n 1) Criar " + entidade
							+ "; \n 2) Excluir " + entidade + "; \n 3) Editar "
							+ entidade + "; \n 4) Consultar " + entidade
							+ "; \n 5) Adicionar Aluno a " + entidade
							+ "; \n 6) Remover Aluno da " + entidade
							+ "; \n 7) Voltar");

			switch (Util.lerInteiro()) {
			case 1:
				criarTurma();
				break;
			case 2:
				excluirTurma();
				break;
			case 3:
				editarTurma();
				break;
			case 4:
				consultarTurma();
				break;
			case 5:
				adicionarAluno();
				break;
			case 6:
				removerAluno();
				break;
			case 7:
				bool = false;
				break;

			}

			System.out.println();

		}

	}

	private static void criarTurma() {

		// Turma m = new Turma(nome, professor, curso);

		System.out.println("Digite o nome da turma");
		String nomeDaTurma = Util.lerString();

		System.out.println("Digite o ID do professor");
		String professorID = null;

		Professor getProfessor = null;

		while (getProfessor == null) {

			professorID = Util.lerString();

			if (professorID.equals("voltar"))
				break;

			try {

				getProfessor = (Professor) get(Professor.class.toString(),
						"identificacao_exata", professorID).get(0);

			} catch (GetException e) {
				System.out
						.println("Professor,Operacao invalida! Entre com um valor novamente! para sair, digite 'voltar'");

				getProfessor = null;
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (!professorID.equals("voltar")) {

			System.out.println("Digite o ID do curso");

			String cursoID = null;

			Curso getCurso = null;

			while (getCurso == null) {

				cursoID = Util.lerString();

				if (cursoID.equals("voltar"))
					break;

				try {

					getCurso = (Curso) get(Curso.class.toString(),
							"identificacao_exata", cursoID).get(0);

					System.out.println(getCurso.toString());

				} catch (GetException e) {
					System.out
							.println("Curso! Operacao invalida! Entre com um valor novamente! para sair, digite 'voltar'");

					getCurso = null;
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (!cursoID.equals("voltar")) {

				Turma novo = new Turma(nomeDaTurma, getProfessor,
						(Curso) getCurso);

				String msg = "";

				try {

					if (adicionar(novo)) {						

						if (adicionar(novo, getCurso))
							msg = " foi adicionado com sucesso! identificacao: "
									+ novo.getIdentificacao();
						else {
							msg = " nao foi adicionado com sucesso!";
							remover(novo);
						}
					}

					else
						msg = " nao foi adicionado com sucesso!";
					System.out.println("Turma " + nomeDaTurma + msg);

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

		}
	}

	private static void excluirTurma() {
		try {

			System.out.println("identificacao_exata");
			String identificacao = null;
			Turma get = null;

			while (get == null) {

				identificacao = Util.lerString();

				if (identificacao.equals("voltar"))
					break;
				try {

					get = (Turma) get(Turma.class.toString(),
							"identificacao_exata", identificacao).get(0);

				} catch (GetException e) {
					System.out
							.println("Identificacao invalida! Entre com a identificacao novamente! Para sair, digite 'voltar'");

					get = null;
				}

			}

			String msg = "";

			if (!identificacao.equals("voltar"))

				if (remover((Turma) get))
					msg += " excluido com sucesso!" + " identificacao: "
							+ get.getIdentificacao();
				else
					msg += " nao foi excluido com sucesso!";

			System.out.println("Turma " + get.getNome() + msg);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void editarTurma() {

		try {

			System.out.println("identificacao_exata");
			String identificacao = null;
			Turma get = null;

			while (get == null) {
				identificacao = Util.lerString();
				if (identificacao.equals("voltar"))
					break;

				try {

					get = (Turma) get(Turma.class.toString(),
							"identificacao_exata", identificacao).get(0);

				} catch (GetException e) {
					System.out
							.println("Identificacao invalida! Entre com a identificacao novamente! Para sair, digite 'voltar'");

					get = null;
				}

			}

			if (!identificacao.equals("voltar")) {

				System.out.println("nome");
				String tipoDeMetodo = "nome";

				String valor = Util.lerString();

				String msg = "";

				if (atualizar((Turma) get, tipoDeMetodo, valor))
					msg += " atualizado com sucesso!" + " identificacao: "
							+ get.getIdentificacao();
				else
					msg += " nao foi atualizado com sucesso!";

				System.out.println("Turma " + get.getNome() + msg);

			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void consultarTurma() {

		try {

			System.out
					.println("Por qual parametro voce deseja consultar? [nome, identificacao, identificacao_exata, professor, aluno]");

			String tipoDeMetodo = Util.lerString();

			if (Util.getListaDeAtributosTurma().contains(tipoDeMetodo)) {

				System.out.println("valor");
				String valor = null;

				Object get = null;

				while (get == null) {

					valor = Util.lerString();

					if (valor.equals("voltar"))
						break;

					try {

						if (tipoDeMetodo.equalsIgnoreCase("professor")) {
							Professor getProfessor = (Professor) get(
									Professor.class.toString(),
									"identificacao_exata", valor).get(0);
							get = get(Turma.class.toString(), tipoDeMetodo,
									getProfessor);

						} else

						if (tipoDeMetodo.equalsIgnoreCase("aluno")) {

							Aluno getAluno = (Aluno) get(
									Aluno.class.toString(),
									"identificacao_exata", valor).get(0);

							get = get(Turma.class.toString(), tipoDeMetodo,
									getAluno);

						} else

							get = (Turma) get(Turma.class.toString(),
									tipoDeMetodo, valor).get(0);

					} catch (GetException e) {
						System.out
								.println("Operacao invalida! Entre com um valor novamente! para sair, digite 'voltar'");

						get = null;
					}

				}

				if (!valor.equals("voltar"))

					System.out.println(get.toString());

			} else
				System.out.println("Atencao! Metodo invalido");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void adicionarAluno() {
		System.out.println("identificacao_exata do aluno");
		String alunoID = null;
		Aluno getAluno = null;

		while (alunoID == null) {

			alunoID = Util.lerString();

			try {

				getAluno = (Aluno) get(Aluno.class.toString(),
						"identificacao_exata", alunoID).get(0);

			} catch (GetException e1) {
				// TODO Auto-generated catch block
				System.out.println("Digite o ID correto!");
				alunoID = null;
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (!alunoID.equals("voltar")) {
			System.out.println("identificacao_exata da turma");

			String turmaID = null;
			Turma getTurma = null;

			while (turmaID == null) {

				turmaID = Util.lerString();

				if (turmaID.equals("voltar"))
					break;

				try {
					getTurma = (Turma) get(Turma.class.toString(),
							"identificacao_exata", turmaID).get(0);
				} catch (GetException e) {
					System.out
							.println("Digite o ID da turma correto! Para sair digite 'voltar'");
					turmaID = null;
				} catch (NamingException e) {

					e.printStackTrace();
				}
			}

			if (!turmaID.equals("voltar"))
				try {

					if (adicionar(getAluno, getTurma))
						System.out.println(getAluno.getNome()
								+ " Adicionado com sucesso na turma "
								+ getTurma.getNome());
					else
						System.out
								.println("Nao foi possivel adicionar o aluno na turma");

				} catch (GetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private static void removerAluno() {
		System.out.println("identificacao_exata do aluno");
		String alunoID = null;
		Aluno getAluno = null;

		while (alunoID == null) {

			alunoID = Util.lerString();

			try {

				getAluno = (Aluno) get(Aluno.class.toString(),
						"identificacao_exata", alunoID).get(0);

			} catch (GetException e1) {
				// TODO Auto-generated catch block
				System.out.println("Digite o ID correto!");
				alunoID = null;
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (!alunoID.equals("voltar")) {
			System.out.println("identificacao_exata da turma");

			String turmaID = null;
			Turma getTurma = null;

			while (turmaID == null) {

				turmaID = Util.lerString();

				if (turmaID.equals("voltar"))
					break;

				try {
					getTurma = (Turma) get(Turma.class.toString(),
							"identificacao_exata", turmaID).get(0);
				} catch (GetException e) {
					System.out
							.println("Digite o ID da turma correto! Para sair digite 'voltar'");
					turmaID = null;
				} catch (NamingException e) {

					e.printStackTrace();
				}
			}

			if (!turmaID.equals("voltar"))
				try {

					if (remover(getAluno, getTurma))
						System.out.println(getAluno.getNome()
								+ " removido com sucesso na turma "
								+ getTurma.getNome());
					else
						System.out
								.println("Nao foi possivel remover o aluno na turma");

				} catch (GetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
