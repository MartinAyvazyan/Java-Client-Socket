import java.net.Socket;
import java.util.Scanner;

import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException {
        String ip = args[0];
        int port = 6666;

        Socket socket = new Socket(ip, port);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your name: ");

        String name = scanner.nextLine();

        OutputStream os = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(os, true);

        writer.println(name);

        InputProcessor inputProc = new InputProcessor(
                socket.getInputStream()
        );

        new Thread(inputProc).start();

        while(true) {
            writer.println(scanner.nextLine());
        }
    }

}

class InputProcessor implements Runnable {

    private final BufferedReader reader;

    public InputProcessor(InputStream is) {
        this.reader = new BufferedReader(
            new InputStreamReader(is)
        );
    }

    @Override
    public void run() {
        while(true) {
            try {
                System.out.println(reader.readLine());
            } catch(IOException e) {
                System.out.println("Connection error");
                break;
            }
        }
    }

}
