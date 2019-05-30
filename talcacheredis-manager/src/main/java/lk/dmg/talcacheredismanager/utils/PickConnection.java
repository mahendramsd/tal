package lk.dmg.talcacheredismanager.utils;

import lk.dmg.common.exception.TalcacheException;
import lk.dmg.talcacheredismanager.common.Constants;

import java.io.IOException;


public class PickConnection {
    private PortConnectionPick pc;

    static final String vbCrLf = (char) 13 + "" + (char) 10;

    public PickConnection(String host, int port) throws IOException {
        pc = new PortConnectionPick(host, port);

    }

    public String getResult(String querry, long timeout) throws IOException, TalcacheException {
        StringBuilder mTxt = new StringBuilder();
        TalWebModule talwebModule = new TalWebModule();
        String key = talwebModule.createKey(0);

        querry = key + TalWebModule.vbCrLf + querry;

        if (talwebModule.verifyKey(key, 0)) {

        } else {

        }

        try {
            pc.writeLine(querry);
            String readLine = "";
            long inCurrentTime = System.currentTimeMillis();

            while (true) {
                readLine = pc.read();
                //if(mTxt.indexOf("UNAUTHORIZED ACCESS")>=0){
                // TALWEBJERROR]xxx errror desc ]zzz
                if (readLine.equals("")) {
                    int d = mTxt.indexOf("WWWMODEEN");
                    int c = mTxt.indexOf("zzz");
                    if (d >= 0 || c >= 0) {
                        break;
                    }
                } else {
                    mTxt.append(readLine);
                    mTxt.append(vbCrLf);
                    inCurrentTime = System.currentTimeMillis();
                }

                int pos = mTxt.indexOf("TALWEBJERROR");
                if (pos >= 0) {
                    pos = pos + "TALWEBJERROR".length() + 1;
                    int endpos = mTxt.indexOf("}", pos + 1);
                    if (endpos >= 0) {
                        String error = mTxt.substring(pos, endpos);
                        throw new TalcacheException("PICKCON8001", error, Constants.COMMUNICATION_ERROR);
                    } else {
                        throw new TalcacheException("PICKCON8002", "PICK String error ", Constants.COMMUNICATION_ERROR);
                    }
                }

                if ((System.currentTimeMillis() - inCurrentTime) >= (timeout * 1000)) {
                    throw new TalcacheException("PICKCON8003", Constants.PICK_TIMEOUT_ERROR, "(" + timeout + " Seconds )");
                }

            }
        } catch (IOException ex) {
            throw ex;

        } finally {
            pc.closePortConnection();
        }
        String mT = mTxt.toString();
        mT = mT.replaceAll("" + '\u0000', "");
        mT = mT.replace('^', ' ');
        mT = mT.replaceAll("@@@", "" + (char) 129);
        mT = mT.replaceAll("\r", "");
        mT = mT.replaceAll("\n", "");
        return mT;
    }


}

