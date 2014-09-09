package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import exceptions.DataException;
import exceptions.TelefoneException;

/**
 * Classe para auxliar operacaoes basicas
 * 
 * @author Flavio & Fernando
 * 
 */
public class Util {

	/**
	 * Scanner usado para ler a entrada do usuario
	 */
	private static Scanner sc = new Scanner(System.in);
	/**
	 * Lista de atributos que podem ser utilizados em consultas e atualizcoes
	 * dos alunos e professores
	 */
	private static List<String> listaDeAtributosPessoa = new ArrayList<String>();
	/**
	 * Lista de atributos que podem ser utilizados em consultas e atualizcoes
	 * dos cursos
	 */
	private static List<String> listaDeAtributosCurso = new ArrayList<String>();
	/**
	 * Lista de atributos que podem ser utilizados em consultas e atualizcoes
	 * das turmas
	 */
	private static List<String> listaDeAtributosTurma = new ArrayList<String>();

	/**
	 * Construtor privado para impedir instanciacao
	 */
	private Util() {
	}

	/**
	 * Metodo para ler entrada do usuario como inteiro
	 * 
	 * @return a entrada do usuario
	 */
	public static int lerInteiro() {
		sc = new Scanner(System.in);
		return sc.nextInt();
	}

	/**
	 * Metodo para ler entrada do usuario como string
	 * 
	 * @return a entrada do usuario
	 */
	public static String lerString() {
		sc = new Scanner(System.in);
		return sc.next();
	}

	/**
	 * Metodo para ler entrada do usuario como uma data
	 * 
	 * @return Calendar referente a data digitada pelo usuario
	 * @throws DataException
	 *             se o formato da data for diferente de dd/mm/aaaa
	 * @throws ParseException
	 *             se nao for possivel converter a data em um calendar
	 */
	public static Calendar lerData() throws DataException, ParseException {
		sc = new Scanner(System.in);

		String data = sc.next();
		if (data.length() != 10)
			throw new DataException();

		int dia = Integer.parseInt(data.substring(0, 2));
		int mes = Integer.parseInt(data.substring(3, 5));
		if (dia > 31 || dia < 1 || mes > 12 || mes < 1)
			throw new DataException();
		return stringToCalendar(data);

	}

	/**
	 * Metodo para ler entrada do usuario como um numero de telefone
	 * 
	 * @return uma string representando o numero de telefone digitado pelo
	 *         usuario
	 * @throws TelefoneException
	 *             caso o telefone contenha carcteres diferentes de numeros
	 */
	public static String lerTelefone() throws TelefoneException {
		Scanner sc = new Scanner(System.in);
		try {
			return String.valueOf(Integer.parseInt(sc.next()));
		} catch (NumberFormatException e) {
			throw new TelefoneException();
		}
	}

	/**
	 * Transforma uma string no formato "dd/MM/yyyy" em um objeto Calendar com a
	 * data correspondente.
	 * 
	 * @param data
	 *            uma string no formato de data "dd/MM/yyyy".
	 * @return um Calendar com a data correspondente a string.
	 * @throws ParseException
	 */
	public static Calendar stringToCalendar(String data) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		date = df.parse(data);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * Retorna uma lista de atributos de professores e alunos
	 * 
	 * @return uma lista de atributos de professores e alunos
	 */
	public static ArrayList<String> getListaDeAtributosPessoa() {

		listaDeAtributosPessoa.add("nome");
		listaDeAtributosPessoa.add("identificacao");
		listaDeAtributosPessoa.add("identificacao_exata");
		listaDeAtributosPessoa.add("endereco");
		listaDeAtributosPessoa.add("telefone");

		return (ArrayList<String>) listaDeAtributosPessoa;

	}

	/**
	 * Retorna uma lista de atributos de cursos
	 * 
	 * @return uma lista de atributos de cursos
	 */
	public static final ArrayList<String> getListaDeAtributosCurso() {

		listaDeAtributosCurso.add("nome");
		listaDeAtributosCurso.add("identificacao");
		listaDeAtributosCurso.add("identificacao_exata");
		listaDeAtributosCurso.add("turma");
		listaDeAtributosCurso.add("aluno");

		return (ArrayList<String>) listaDeAtributosCurso;
	}

	/**
	 * Retorna uma lista de atributos de turmas
	 * 
	 * @return uma lista de atributos de turmas
	 */
	public static final ArrayList<String> getListaDeAtributosTurma() {

		listaDeAtributosTurma.add("nome");
		listaDeAtributosTurma.add("identificacao");
		listaDeAtributosTurma.add("identificacao_exata");
		listaDeAtributosTurma.add("professor");
		listaDeAtributosTurma.add("aluno");

		return (ArrayList<String>) listaDeAtributosTurma;
	}

}
