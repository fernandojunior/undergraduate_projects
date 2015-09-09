/**

  daVelhaGame.c is copyright Fernando Felix do N. Junior


 @author Fernando Felix do Nascimento Junior

*/

#include <stdio.h>
#include <stdlib.h>

#define MAX 3

typedef enum{
    false,
    true
}Boolean;

typedef int* Matrix[MAX][MAX];

/**
 * Envia msg para o console
 */
void msgeln(char *msg ){
    printf("%s\n", msg);
}

/**
 * Envia msg para o console e fecha o jogo
 */
void endmsge(char *msg){
    msgeln(msg);
    exit(NULL);
}

/**
 * Verifica se todas as celulas da matriz esparsa estao vazias,
 * ou seja, "celula[x][y] == NULL"
 */
Boolean test_matrixEmpity(Matrix matrix){

    int lin, col;

    for (lin=0; lin< MAX;lin++)
        for (col=0; col< MAX;col++)
            if(matrix[lin][col]!=NULL)
                return false;

    return true;

}



/**
 * Verifica se todas as celulas da matriz esparsa estao cheias,
 * ou seja, "celula[x][y] != NULL"
 */
Boolean test_matrixFull(Matrix matrix){
    
    int aux = 0;

    int lin, col;

    for (lin=0; lin< MAX;lin++)
        for (col=0; col< MAX;col++)
            if(matrix[lin][col]!=NULL)
                aux++;

    if (aux == 9)
        return true;
    else return false;

}

/**
 * Inicializa as celulas da matriz esparsa com NULL,
 * Obs: esse procedimento nao cria a a variavel matriz,
 * por isso tem que se passar ela como parametro Matrix
 */
Boolean initMatrix(Matrix matrix){

    int lin, col;

    for (lin=0; lin< MAX;lin++)
        for (col=0; col< MAX;col++)
            matrix[lin][col] = NULL;

    if (test_matrixEmpity(matrix))
        return true;
    else return false;

}

/**
 * Verifica se existe alguma sequencia valida
 */
Boolean test_sequences(Matrix matrix, int playerCOD){ //playerCOD: num 2 equivale ao jogador "O"; num 1 equivale ao jogador "X";

    int aux;

    Boolean sequenceTest = false;

    // Teste diagonais:

    /**se fizesse o teste dessas celulas e nao existisse essa linha_t e alguma(s) dessa(s) celulas tivesse valor 'NULL':

    daria erro no programa, famoso "Segment fault", pois estamos utilizando o conceito de matriz esparca*/

    if ((matrix[0][0] != NULL) && (matrix[1][1] != NULL) && (matrix[2][2] != NULL)) //linha_t
        if ((*matrix[0][0] == playerCOD) && (*matrix[1][1] == playerCOD) && (*matrix[2][2] == playerCOD))
            sequenceTest = true;

    if ((matrix[0][2] != NULL) && (matrix[1][1] != NULL) && (matrix[2][0] != NULL))
        if ((*matrix[0][2] == playerCOD) && (*matrix[1][1] == playerCOD) && (*matrix[2][0] == playerCOD))
            sequenceTest = true;

    //Teste linhas-colunas
    if (sequenceTest==false)
        for (aux=0;aux<MAX;aux++){
            //Teste colunas:
            if ((matrix[0][aux] != NULL) && (matrix[1][aux] != NULL) && (matrix[2][aux] != NULL))
                if ((*matrix[0][aux] == playerCOD) && (*matrix[1][aux] == playerCOD) && (*matrix[2][aux] == playerCOD))
                    {sequenceTest = true; break; }

            //Teste linhas:
            if ((matrix[aux][0] != NULL) && (matrix[aux][1] != NULL) && (matrix[aux][2] != NULL))
                if ((*matrix[aux][0] == playerCOD) && (*matrix[aux][1] == playerCOD) && (*matrix[aux][2] == playerCOD))
                    {sequenceTest = true; break;}

        }

    return sequenceTest;

}

/**
 * Procedimento auxiliar, atribui algum valor em uma das celulas da matriz esparsa conforme as coordenadas (lin e col)
 */
Boolean setCellAux(Matrix matrix, int playerCOD, int lin, int col ){

    if
    ((matrix[lin][col] !=NULL) || //Se a celula for diferente de NULL ou
    (lin < 0)                  || //Se a linha for menor que zero (-1, -2, etc) ou
    (lin > MAX - 1)            || //Se a linha for maior que o limite de numero de linhas da matriz ou
    (col < 0)                  || //Se a coluna for menor que zero (-1, -2, etc) ou
    (col > MAX -1))               //Se a coluna for maior que o limite de numero de colunas da matriz entao
        return false;             //a operacao nao poderar ser feita, ou seja, retorna false. =)

    //alocando uma celula[x][y] da matriz na memoria com a funcao malloc
    matrix[lin][col] = (int*)malloc(sizeof(int));

    if (matrix[lin][col] == NULL) // Se for igual a Null eh pq a alocacao nao foi feita
       return false;

    *matrix[lin][col] = playerCOD;

    return true;
    
}

/**
 * Atribuindo valor a uma celula
*/
Boolean setCell(Matrix matrix, int playerCOD, int cell){

    Boolean test = false;

    switch (cell){
        case 1:{test = setCellAux(matrix, playerCOD, 0, 0); break;}
        case 2:{test = setCellAux(matrix, playerCOD, 0, 1); break;}
        case 3:{test = setCellAux(matrix, playerCOD, 0, 2); break;}
        case 4:{test = setCellAux(matrix, playerCOD, 1, 0); break;}
        case 5:{test = setCellAux(matrix, playerCOD, 1, 1); break;}
        case 6:{test = setCellAux(matrix, playerCOD, 1, 2); break;}
        case 7:{test = setCellAux(matrix, playerCOD, 2, 0); break;}
        case 8:{test = setCellAux(matrix, playerCOD, 2, 1); break;}
        case 9:{test = setCellAux(matrix, playerCOD, 2, 2); break;}
        default: endmsge("Erro: cellCOD invalido");

    }

    return test;

}

/**
 * retorna um numero randomico
 */
int getRandom(){

    int *num=NULL;
    srand(time(NULL));
    num = (int*)malloc(sizeof(int));
    *num= rand() % 8;
    return (*num+1);

}

/**
 * Imprime o cabecalho do jogo no console
 */
void printHeader(){

    system("clear || cls");//printf("\e[H\e[2J"); // limpa tela
    msgeln("Area de processamentos POG =p, Powered by Fernando Junior");
    msgeln("Jogo inicializado com sucesso");
    msgeln("");

}

/**
 * Imprime, no console, os  codigos de cada celula,
 * para que  o jogador possa escolher um e jogar
 */
void printMatrixTutor(){

    msgeln("Matrix tutorial - Cells Cod:\n");
    int lin, col, aux=1;
    for (lin=0; lin< MAX;lin++){
        for (col=0; col< MAX;col++)
            printf("[ %d ] ", aux++);
            msgeln("");
        }

    msgeln("\n");

}

/**
 * Imprime a matrix com os seus valores atribuidos (ou 'X' ou 'O' ou nada )
 */
void printMatrix(Matrix matrix){

    int lin, col;

    for (lin=0; lin< MAX;lin++){
        for (col=0; col< MAX;col++)
            if (matrix[lin][col] == NULL)
                printf("[   ]");else

            if (*matrix[lin][col] == 2)
                printf("[ O ]"); else

            if (*matrix[lin][col] == 1)
             printf("[ X ]");

         printf("\n");

      }

}

/**
 * Atualiza a tela no console
 */
void updade_scr(Matrix matrix){

    printHeader();
    printMatrixTutor();
    printMatrix(matrix);

}

/**
 * Logica utilizada para se ter jogador real
 */
void plyrlogic(Matrix matrix, int playerCOD){

    char msge[111];

    if (playerCOD > 2) endmsge("Erro: playerCOD invalido ");

    while(true){

        updade_scr(matrix);
        sprintf(msge, "Player %d, onde voce quer jogar?", (playerCOD));
        msgeln(msge);

        if (test_matrixFull(matrix) != true){
            fflush(stdin);//limpando buffer
            int plyr_cellCOD;
            scanf("%d",&plyr_cellCOD);

            if (setCell(matrix, playerCOD, plyr_cellCOD) == true){
                updade_scr(matrix);

                if (test_sequences(matrix, playerCOD) == true){
                    sprintf(msge, "Player %d ganhou!", (playerCOD));
                    endmsge(msge);
                }

                break;

             }

         }else endmsge("Fim do jogo!");

    }

}

/**
 * Logica a ser colocada no 'main', computador (burro/random) x jogador
 */
void PcVPlogic(Matrix matrix){

    int player = 1;
    int pc = 2;

    while (true){
        plyrlogic(matrix,player);

        //vez do PC
        if (test_matrixFull(matrix) != true){
            updade_scr(matrix);

            while (true){//laco_y
                int pc_cellCOD = getRandom();
                
                if (setCell(matrix, pc, (pc_cellCOD)) == true){
                    updade_scr(matrix);

                    if (test_sequences(matrix, pc) == true)
                        endmsge("PC ganhou!");

                    break;//quebra laco y
                    
                 }

             }//laco_y_end

         }else endmsge("Fim do jogo!");

    }

}

/**
 * Logica a ser colocada no 'main', player x player
 */
void PVPlogic(Matrix matrix){

    int player1 = 1;//"x"
    int player2 = 2;//"o"

    while (true){
        //updade_scr(matrix);
        plyrlogic(matrix,player1);
        plyrlogic(matrix,player2);
    }

}

/**
 * Principal
 */

int main(){

    msgeln("Area de processamentos POG =)");

    Matrix matrix;

    if (initMatrix(matrix) == true)
        msgeln("Jogo inicializado com sucesso");

    msgeln("Escolha o modo como voce quer jogar:\n1- Player X Player \n2- Pc X Player?");

    fflush(stdin);//limpando buffer

    int gamemod;

    scanf("%d",&gamemod);

    if (gamemod == 1)
        PcVPlogic(matrix);else

    if (gamemod == 2)
        PVPlogic(matrix); else

    endmsge("Erro: numero invalido");

}
