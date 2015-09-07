import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class fileserver {
	private static HttpServer fileserver;
	public static void hostfiles(String[] files,int nof){
		try {
			fileserver = HttpServer.create(new InetSocketAddress(80),0);
			//System.out.println("hello");
			fileserver.createContext("/",new HttpHandler() {
				@Override
				public void handle(HttpExchange arg0) throws IOException {
				      Headers h = arg0.getResponseHeaders();
				      
				      File file = new File ("index.html");
				      byte [] bytearray  = new byte [(int)file.length()];
				      FileInputStream fis = new FileInputStream(file);
				      BufferedInputStream bis = new BufferedInputStream(fis);
				      bis.read(bytearray, 0, bytearray.length);
				      arg0.sendResponseHeaders(200, file.length());
				      OutputStream os = arg0.getResponseBody();
				      os.write(bytearray,0,bytearray.length);
				      os.close();
				}
			});
			for(int i=0;i<nof;i++){
				File file = new File(files[i]);
				fileserver.createContext("/"+file.getName(), new HttpHandler() {

					@Override
					public void handle(HttpExchange arg0) throws IOException {
						Headers h = arg0.getResponseHeaders();
						// TODO Auto-generated method stub
						byte [] bytearray  = new byte [(int)file.length()];
						FileInputStream fis = new FileInputStream(file);
						BufferedInputStream bis = new BufferedInputStream(fis);
						bis.read(bytearray, 0, bytearray.length);
						arg0.sendResponseHeaders(200, file.length());
						OutputStream os = arg0.getResponseBody();
						os.write(bytearray,0,bytearray.length);
						os.close();
					}
				});
			}
			fileserver.setExecutor(null);
			fileserver.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void stophost(){
		fileserver.stop(0);
	}
}
