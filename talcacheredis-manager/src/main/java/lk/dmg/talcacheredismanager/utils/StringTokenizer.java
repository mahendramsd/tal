/*
 *
 *   Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *   *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *      This software product contains information which is proprietary to
 *      and considered a trade secret The Data management Group Ltd .
 *      It is expressly agreed that it shall not be reproduced in whole or part,
 *      disclosed, divulged or otherwise made available to any third party directly
 *      or indirectly.  Reproduction of this product for any purpose is prohibited
 *      without written authorisation from the The Data Management Group Ltd
 *      All Rights Reserved.
 *
 *      E-Mail andyj@datam.co.uk
 *      URL : www.datam.co.uk
 *      Created By : Mahendra Sri Dayarathna
 *
 *
 */
package lk.dmg.talcacheredismanager.utils;


import java.util.ArrayList;

public class StringTokenizer {
    private ArrayList tokens = new ArrayList(0);
    private int tokenNum = 0;
    private String delimit = ";";

    public StringTokenizer(String[] s, String delimit) {
        this.delimit = delimit;
        setArray(s);

    }

    public StringTokenizer(String s, String d) {
        this.delimit = d;
        // Convert string to character array for better efficiency...
        String t;
        int charNum;

        t = "";

        for (charNum = 0; charNum < s.length(); charNum++) {
            if (d.indexOf(s.charAt(charNum)) != -1) {
                tokens.add(t);
                t = "";
            } else {
                t += s.charAt(charNum);
            }
        }

        tokens.add(t);
    }

    public int countTokens() {
        return tokens.size();
    }

    public boolean hasMoreTokens() {
        boolean hasMoreTokens;

        hasMoreTokens = tokenNum <= tokens.size() - 1;

        return hasMoreTokens;
    }

    public String nextToken() {
        return (String) tokens.get(tokenNum++);
    }

    public String getToken(int n) {
        try {
            String tmp = (String) tokens.get(n);
            if (tmp.equals("")) {
                return "";
            } else {
                return tmp;
            }
        } catch (RuntimeException e) {
            return null;
        }

    }

    public String[] getArray() {
        try {
            tokens.trimToSize();
            String[] arr = new String[tokens.size()];
            tokens.toArray(arr);
            return arr;
        } catch (Exception ex) {
            return new String[0];
        }
    }

    public String[] getArray(int noOfElements) {
        try {
            String s[] = getArray();
            String finalArray[] = new String[noOfElements];
            int i;
            int j = 0;
            if (s.length >= noOfElements) {
                for (i = 0; i < noOfElements; i++) {
                    finalArray[i] = s[i];
                    j = i;
                }

                String lastElements = finalArray[j];
                for (; i < s.length; i++) {
                    lastElements += "," + s[i];

                }
                finalArray[j] = lastElements;
            } else {
                for (i = 0; i < s.length; i++) {
                    finalArray[i] = s[i];
                }
                for (; i < noOfElements; i++) {
                    finalArray[i] = "";
                }
            }
            return finalArray;
        } catch (Exception ex) {
            return new String[0];
        }
    }

    public String[] getArray(int noOfElements, String fillChar) {
        try {
            String s[] = getArray();
            String finalArray[] = new String[noOfElements];
            int i;
            int j = 0;
            if (s.length >= noOfElements) {
                for (i = 0; i < noOfElements; i++) {
                    finalArray[i] = s[i];
                    j = i;
                }

                String lastElements = finalArray[j];
                for (; i < s.length; i++) {
                    lastElements += fillChar + s[i];

                }
                finalArray[j] = lastElements;
            } else {
                for (i = 0; i < s.length; i++) {
                    finalArray[i] = s[i];
                }
                for (; i < noOfElements; i++) {
                    finalArray[i] = "";
                }
            }
            return finalArray;
        } catch (Exception ex) {
            return new String[0];
        }
    }

    public void setArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            tokens.add(arr[i]);
        }
    }

    public String mergeArray() {
        StringBuilder s = new StringBuilder();

        while (hasMoreTokens()) {
            s.append(nextToken());
            if (hasMoreTokens()) {
                s.append(delimit);
            } else {

            }

        }
        return s.toString();
    }

    public static String fillSpace(String s, int needLength) {
        if (s == null) {
            return fillSpace("  ", needLength);
        }
        if (s.equals("")) {
            return fillSpace("  ", needLength);
        }

        if (s.length() < 1) {
            return fillSpace("  ", needLength);
        } else {
            char in[] = s.toCharArray();
            char[] aa = new char[needLength];
            if (s.length() < needLength) {
                for (int i = 0; i < aa.length; i++) {
                    if (i < in.length) {
                        aa[i] = in[i];
                    } else {
                        aa[i] = ' ';
                    }
                }
                return new String(aa);
            } else {
                if (s.length() == needLength) {
                    return s;
                } else {
                    String sm = s.substring(0, needLength);
                    return sm;
                }
            }
        }
    }

    public static String getKeyFromKeyNameStr(String s) throws Exception {

        int pos = 0;
        pos = s.indexOf("-");
        if (pos > 0) {
            return s.substring(0, pos);
        } else {
            throw new Exception("key name seperator not found");
        }


    }

    public static String getNameFromKeyNameStr(String s) throws Exception {

        int pos = 0;
        pos = s.indexOf("-");
        if (pos > 0) {
            return s.substring(pos + "-".length());
        } else {
            throw new Exception("key name seperator not found");
        }


    }
}