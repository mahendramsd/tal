package lk.dmg.talcacheredismanager.utils;


import java.util.Calendar;
import java.util.Date;

public class TalWebModule {
    public static String RcvString = "";
    public static final Date ExDate = new Date();
    public static final String ExUser = "TWB-TRAVCO";
    public static String MyIniFilename = "";
    public static boolean gFirstTime = false;

    public static final int TELCMD_IAC = 255;
    public static final int TELCMD_DONT = 254;
    public static final int TELCMD_DO = 253;
    public static final int TELCMD_WONT = 252;
    public static final int TELCMD_WILL = 251;
    public static final int TELCMD_SB = 250;
    public static final int TELCMD_NOP = 241;
    public static final int TELCMD_SE = 240;
    public static final int TELOPT_BINARY = 0;
    public static final int TELOPT_ECHO = 1;
    public static final int TELOPT_TTYPE = 24;
    public static final int TELQUAL_IS = 0;
    public static final int TELQUAL_SEND = 1;
    public static final String gBusy = "BUSY";
    public static final String gFree = "FREE";
    public static final String gStarted = "STARTED";
    public static final String gStopped = "STOPPED";
    public static final String gUnused = "UNUSED";


    /**
     * static variable for method: Verify_Key
     */
    public static final int mGAP = 600;

    public static final String UNAUTHORIZED_ACCESS = "TALWEBJERROR]UNAUTHORIZED ACCESS}zzz";
    public static final String REQUEST_EMPTY = "TALWEBJERROR]CLIENT REQUEST STRING EMPTY}zzz";

    public static final String vbCrLf = (char) 13 + "" + (char) 10;
    public static final String vbCr = (char) 13 + "";
    public static final String vbChr10 = (char) 10 + "";

    public String createKey(int mTGap) {
        long mFKey = 0;
        long mTimeKey = 0;
        int mFmlNo = 0;
        String mTmp = "";
        String mKey = "";

        try {
            mFmlNo = (int) (Math.random() * 10);
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);

            long time = hours * 3600 + minutes * 60 + seconds;
            mTimeKey = time + mTGap;

            if (mTimeKey > 86400) {
                mTimeKey = mTimeKey - 86400;
            } else {
                if (mTimeKey < 0) {
                    mTimeKey = 86400 + mTimeKey;
                }
            }

            mTimeKey = mTimeKey + 9913391; //9915391

            mFKey = Long.parseLong(getFormulaKey(mFmlNo, mTimeKey));

            mTmp = "" + mTimeKey;

            mKey = "" + mFmlNo + mTmp.substring(0, 3) + " " + mTmp.substring(mTmp.length() - 4, mTmp.length());

            mTmp = "" + mFKey;
            mKey = mKey + " " + mTmp.substring(0, 4) + " " + mTmp.substring(mTmp.length() - 4, mTmp.length());
        } catch (RuntimeException ex) {
            return "Error in createKey ";
        }
        return mKey;
    }

    private static String getFormulaKey(int mFormula, long mTKey) {

        long mKey = 0;
        String mTmp = "";

        try {
            switch (mFormula) {
                case 0:
                    mKey = ((((mTKey * 8 - 109) * 4) - 4578) / 3);
                    break;
                case 1:
                    mKey = ((mTKey + 231) / 7) + 90523;
                    break;
                case 2:
                    mKey = ((mTKey / 2) + 908564) * 9;
                    break;
                case 3:
                    mKey = ((mTKey - 984532) * 7) + 6473123;
                    break;
                case 4:
                    mKey = (((mTKey * 32) - 8765254) / 11);
                    break;
                case 5:
                    mKey = (((mTKey * 43) + 986754) / 41) + 56;
                    break;
                case 6:
                    mKey = (((mTKey / 2) + 86432985) * 9) + 8730909;
                    break;
                case 7:
                    mKey = (mTKey * 97) - 56484527;
                    break;
                case 8:
                    mKey = ((mTKey - 56753512) * 7) + 7659;
                    break;
                case 9:
                    mKey = ((mTKey + 763) / 5) + 6867423;
                    break;
            }

            if (mKey < 0) {
                mKey = mKey * (-1);
            }
            mTmp = "39865494" + mKey;
            mTmp = mTmp.substring(mTmp.length() - 8, mTmp.length());
            mKey = Long.parseLong(mTmp);//' to remove leading zeros
            mTmp = "39865494" + mKey;
            mTmp = mTmp.substring(mTmp.length() - 8, mTmp.length());
        } catch (RuntimeException ex) {
            return "Error in getFormulaKey ";
        }

        return mTmp;
    }

    public boolean verifyKey(String mKey, int mTGap) {

        final int mGap = 600;
        long mFKey = 0;
        long mTimeKey = 0;
        long mATime = 0;
        int mFmlNo = 0;
        String mTmp = "";
        long mTimer = 0;

        int mAsc = 0;
        boolean verify = false;


        try {
            mKey = mKey.trim();

            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);

            long time = hours * 3600 + minutes * 60 + seconds;

            mTimer = time;
            if (mKey.length() != 19) {
                return verify;
            }
            mTmp = mKey.substring(0, 1);
            mFmlNo = Integer.parseInt(mTmp);
            mTmp = "";

            for (int i = 1; i <= 8; i++) {
                mAsc = mKey.charAt(i);
                if (mAsc >= '0' && mAsc <= '9') {
                    mTmp = mTmp + mKey.charAt(i);
                }
            }
            mTimeKey = Long.parseLong(mTmp);
            mATime = mTimeKey - 9913391 - mTGap; //'9915391

            if (mATime > 86400) {
                mATime = mATime - 86400;
            } else {
                if (mATime < 0) {
                    mATime = 86400 + mATime;
                }
            }

            mTmp = "";
            for (int i = 10; i <= 18; i++) {
                mAsc = mKey.charAt(i);
                if (mAsc >= '0' && mAsc <= '9') {
                    mTmp = mTmp + mKey.charAt(i);
                }
            }
            mFKey = Long.parseLong(mTmp);

            if (mTimer > mGap) { //' no checking in first 10 minutes of the day
                if (((mATime + mGap) < mTimer) || (mATime > (mTimer + mGap))) {
                    return verify;
                }
            }

            mTmp = getFormulaKey(mFmlNo, mTimeKey);
            if (Integer.parseInt(mTmp) != mFKey) {
                return verify;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;

    }


}


