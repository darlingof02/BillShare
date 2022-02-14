package com.yyds.billshare.ChatTeset;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();

        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = bf.readLine()) != null){
            System.out.println(line);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        socket.close();
    }
}
