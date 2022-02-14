package com.yyds.billshare.ChatTeset;

import java.io.*;
import java.net.Socket;

public class ClientChat {
    public static void main(String[] args) throws IOException {
        Socket socket = new  Socket("127.0.0.1",8888);

        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world".getBytes());
        outputStream.flush();


        socket.close();

    }


}
