web_server
==========

O que é
-------

Um servidor web simples. Implementa apenas o método GET do protocolo HTTP. Versão do protocolo 1.1.

Quando
------

Primeiro período de 2009.

Disciplina
----------

Protocolo de aplicações.

Como
----

Feito na linguagem de programação C. Uitlizando a biblioteca de sockets o compilador GCC (Linux).


Tutorial
--------

Configuração:
* Configurar um diretório root na linha onde se encontra o #define DIR_ROOT, por padrão o valor é /home/;
* Configurar uma porta valida para o Servidor Web na linha onde se encontra o #define PORTA, por padrão o valor é (7777);

Para compilar:
* No Linux/console digite o seguinte comando → gcc -o httpServer httpServer.c -pthread na pasta onde se encontra o arquivo httpServer.c. obs: Seguir exatamente essa sintaxe, se não pode haver algum erro.. como aconteceu comigo.

Para executar:
* No Linux/console acesse a pasta onde se encontra o arquivo httpServer.c depois execute-o com o comando ./httpServer

Explicação:
* Se você quer compartilhar os arquivos de uma pasta (ex.: /home/user/) no servidor, então a linha  #define DIR_ROOT  deve ser igual a  #define DIR_ROOT /home/user/ .

* Você pode alterar a porta do servidor para qualquer porta desde que seja maior que 1012 se não me
engano, exemplo:  #define PORTA 8888.

* Para testar se o servidor esta OK (com o diretório e porta devidamente configurados e o arquivo httpServer já executado) abra o seu browse e digite o endereço <http://localhost:7777/>; onde localhost = endereço local da sua maquina e :7777 = porta do servidor.

* Assim que acessar a página, a seguinte mensagem de erro vai aparecer: Audemar & Fernando WebServer Erro 404 Pagina nao encontrada, pois o servidor por padrão procura pela página index.html no diretório corrente, caso não seja definido nenhum pedido pelo browser.

Exemplificando:
* Exemplo 1: Acessando o endereço <http://localhost:7777/>, entao ele procurará pelo endereço <http://localhost:7777/index.html>;
* Exemplo 2: Se na pasta /home/user/ tiver a pasta imagens /home/user/imagens e você quer acessa-la então digite <http://localhost:7777/imagens> e o servidor irá procurar por <http://localhost:7777/imagens/index.html>.

Caso queira fazer algum pedido (arquivo/pagina.html), /home/user/imagens/foto.jpg por exemplo, digite o endereço <http://localhost:7777/imagens/foto.jpg>. Se o arquivo foto.jpg existir na pasta do servidor então sera feito o envio deste arquivo pelo cliente.

Os pré-requisitos para entender o código são:
* Ter o conhecimento da linguagem de programação C;
* Ter o conhecimento de estruturas de dados paradigma em C ;
* Ter o conhecimento de ponteiros em C;
* Ter conhecimento de Sockets e da biblioteca <sys/socket.h>;
* Ter conhecimento sobre Protocolos;
* e Conhecer o protocolo/cabecalho HTTP versão 1.1

Conclusão
---------

Esse programa funciona corretamente, o único problema que estou tendo é que quando mais de um arquivo esta sendo baixado (thread-http/1.1) simultaneamente e eu cancelo uma, todos os arquivos são cancelados também.

Autores
-------

Fernando Felix do Nascimento Junior, Audemar Ribeiro

Links
-----

http://codigofonte.uol.com.br/codigos/web-server-em-c