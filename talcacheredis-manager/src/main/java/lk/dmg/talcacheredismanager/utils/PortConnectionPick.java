package lk.dmg.talcacheredismanager.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class PortConnectionPick {
    private Socket client;
    private OutputStream os;
    private InputStream is;
    private String server;
    private int port;
    static final String vbCr = (char) 13 + "";

    public PortConnectionPick(String server, int port) throws UnknownHostException, IOException {
        client = new Socket(server, port);
        this.server = server;
        this.port = port;
        this.init();
    }

    private void init() throws IOException {
        os = client.getOutputStream();
        is = client.getInputStream();
        client.setKeepAlive(true);
        // client.setSoTimeout(60000);
        client.setTcpNoDelay(true);
        //client.setTrafficClass(14);
        client.setOOBInline(true);
        client.setSendBufferSize(8192);
        client.setReceiveBufferSize(8192);
    }

    public PortConnectionPick(Socket socket) throws UnknownHostException, IOException {
        client = socket;
        this.init();
    }

    public void writeLine(String s) throws IOException {
        os.write((s + vbCr).getBytes());
        os.flush();
    }

    public void write(String s) throws IOException {
        os.write(s.getBytes());
        os.flush();
    }

    public String read() throws IOException {
        int datalen = is.available();
        if (datalen == 0) {
            try {
                Thread.sleep(10);
                return "";
            } catch (InterruptedException ex) {
            }
        }

        byte[] data = new byte[datalen];
        is.read(data);
        return new String(data);
    }

    public void closePortConnection() throws IOException {
        is.close();
        os.close();
        client.close();
        client = null;
    }


    public void shutdown() throws IOException {
        client.shutdownInput();
        client.shutdownOutput();
    }

    public void reStart() throws IOException {
        is = client.getInputStream();
        os = client.getOutputStream();
    }

}
