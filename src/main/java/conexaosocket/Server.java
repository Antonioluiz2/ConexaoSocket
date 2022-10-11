package conexaosocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
		private ServerSocket serverSocket;

		// a exce��o vai ser tratado poorquem utilizar o metodo
		private void criarServerSocket(int porta) throws IOException {
			serverSocket=new ServerSocket(porta);
		}

		//espera conex�o
		private Socket esperaConexao() throws IOException {
			Socket socket=serverSocket.accept();
			return socket;
		}

		//fechar conex�o socket
		private void fechaSocket(Socket s) throws IOException  {
			s.close();
		}
		/*protocolo da aplica��o...responsavel pela comunica��o
		 * Cria stream de entrada e saida 
		 * Trata a conversa entre cliente e servidor(trata protocolo)*/

		/*input recebe informa��o do cliente
		  Output envia informa��es para o cliente*/
		private void trataConexao(Socket socket) throws IOException  {

			socket.getInetAddress();
			//socket.getPort();
			try {
				ObjectOutputStream output= new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream input= new ObjectInputStream (socket.getInputStream());
				/*Cliente envia um hello
				 * e o servido enviara um Hello World!*/
				String msg=input.readUTF();
				System.out.println("Mensagem Recebida... "+":"+ msg);

				output.writeUTF("Hello World!");
				System.out.println("Endere�o do cliente: "+ socket.getInetAddress());
				System.out.println(socket.getPort());
				output.flush();

				System.out.println("TRATANDO...");
//				Mensagem m=(Mensagem) input.readObject();
//				String opracao=m.getOperacao();
//				if(operacao.equals("HELLO")) {
//					String nome= (String)m.getParam("nome");
//					String sobreNome= (String)m.getParam("sobreNome");
//					
//					Mensagem reply=new Mensagem("HELLOREPLY");
//					reply.setStatus(OK);
//					reply.setParam("mensagem", "Hello World,"+ nome + sobrenome);
////					
//				}
				output.close();
				input.close();

			} catch (IOException e) {
				System.out.println("Problema no tratamento da conex�o com o cliente: "+ socket.getInetAddress());
				e.printStackTrace();
			}finally{

				//fecha a comunica��o entre servidor/cliente
				fechaSocket(socket);
			}


		}
	public static void main(String[] args) {
		try {
			//o while � para que fique em loop e possa atender outras requisi��es

			Server server=new Server();
			System.out.println("Aguardando conex�o...");
			server.criarServerSocket(5555);
			while(true) {
				Socket socket=server.esperaConexao();
				System.out.println("Cliente Conectado...");
				server.trataConexao(socket);
				System.out.println("Cliente Finalizado...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
