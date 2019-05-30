package lk.dmg.talcacheredismanager.utils;


import lk.dmg.common.exception.TalcacheException;
import lk.dmg.talcacheredismanager.common.Constants;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PICKUtil {

    public String pickMessage(String querry, String talWebHost, String talWebPort, String talWebTimeOut)
            throws IOException, TalcacheException {

        PickConnection pc = new PickConnection(talWebHost, Integer.parseInt(talWebPort));

        querry = querry.replace("[{", "[\n{");
        querry = querry.replace("],", "\n],\n");
        querry = querry.replace("{", "\n{\n");
        querry = querry.replace("},", "@@@@");
        querry = querry.replace("}", "\n}\n");
        querry = querry.replace(",", ",\n");
        querry = querry.replace("@@@@", "\n},\n");
        querry = querry.replace("\n\n", "\n");
        querry = querry.replace("^^", ",");

        String result = pc.getResult(querry, Integer.parseInt(talWebTimeOut));
        int index = result.indexOf("PICKERROR");
        if (index >= 0) {
            String error = result.substring(30, result.length() - 6);
            throw new TalcacheException("PICKUtil001", error, Constants.PICK_ERROR);
        }
        return result.substring(19);

    }

}
