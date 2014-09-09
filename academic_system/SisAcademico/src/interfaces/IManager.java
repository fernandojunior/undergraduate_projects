package interfaces;

import java.util.ArrayList;

import javax.naming.NamingException;

import exceptions.GetException;
import exceptions.IDException;

/**
 * Interface que define um gerenciado de lista de entidades
 * 
 * @author Flavio & Fernando
 * 
 */
public interface IManager {

	/**
	 * Adiciona um elemento na lista do gerenciador
	 * 
	 * @param o
	 *            o elemento
	 * @return true se adicionado com sucesso, false caso contrario
	 * @throws IDException
	 *             caso o a identificacao seja invalida
	 * @throws ClassNotFoundException
	 *             caso o elemento nao seja valido
	 */
	public boolean adicionarElemento(IEntidade o) throws IDException,
			ClassNotFoundException;

	/**
	 * Retorna o elemento da lista do gerenciador
	 * 
	 * @param tipoDoAtributo
	 *            o tipo do atributo que sera utilizado na consulta
	 * @param valor
	 *            o valor do atributo
	 * @return uma lista contendo os elementos validos conforme o atributo e seu
	 *         valor
	 * @throws GetException
	 *             caso nao seja possivel o retorno de nenhum elemento
	 */
	public ArrayList<? extends IEntidade> getElemento(String tipoDoAtributo,
			Object valor) throws GetException;

	/**
	 * Atualiza o elemento conforme o tipo do atributo e seu valor
	 * 
	 * @param elemento
	 *            o elemento a ser atualizado
	 * @param tipoDoAtributo
	 *            o tipo do atributo do elemento a ser atualizado
	 * @param valor
	 *            o novo valor do atributo que sera atualizado
	 * @return true se atualizado com sucesso, false caso contrario
	 * @throws NamingException
	 *             Se nao existe o tipo do atributo
	 * @throws GetException
	 *             caso nao seja possivel o retorno de nenhum elemento
	 */
	public boolean atualizarElemento(IEntidade elemento, String tipoDoAtributo,
			Object valor) throws NamingException, GetException;

	/**
	 * Remove o elemento passado como parametro da lista do gerenciador
	 * 
	 * @param elemento
	 *            o elemento que sera removido
	 * @return true se removido com sucesso, false caso contrario
	 */
	public boolean removerElemento(IEntidade elemento);

}
