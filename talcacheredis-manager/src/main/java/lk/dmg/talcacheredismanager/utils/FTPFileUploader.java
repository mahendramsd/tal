/*
 *     Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *     *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *        This software product contains information which is proprietary to
 *        and considered a trade secret The Data management Group Ltd .
 *        It is expressly agreed that it shall not be reproduced in whole or part,
 *        disclosed, divulged or otherwise made available to any third party directly
 *        or indirectly.  Reproduction of this product for any purpose is prohibited
 *        without written authorisation from the The Data Management Group Ltd
 *        All Rights Reserved.
 *
 *        E-Mail andyj@datam.co.uk
 *        URL : www.datam.co.uk
 *
 */
package lk.dmg.talcacheredismanager.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FTPFileUploader {
    private final static Logger log = LoggerFactory.getLogger(FTPFileUploader.class);

    public static Boolean uploadFile(String fileName,
                                     File file,
                                     String serverPath,
                                     String server,
                                     int port,
                                     String userName,
                                     String password) {
        if (log.isDebugEnabled()) {
            log.debug("uploadFile :" + fileName + file + serverPath + port + userName + password);
        }
        FTPClient ftpClient = new FTPClient();
        Boolean isUpload = false;
        try {
            ftpClient.connect(server, port);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            InputStream inputStream = new FileInputStream(file);
            boolean dir = makeDirectory(ftpClient, serverPath);
            if (dir) {
                boolean done = ftpClient.storeFile(fileName, inputStream);
                inputStream.close();
                if (done) {
                    isUpload = true;
                }
            }
        } catch (Exception ex) {
            log.error("Error occured while Uploading file :" + ex);
            isUpload = false;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception ex) {
                log.error("Error occured while Uploading file and conneting ftpclient:" + ex);
            }
        }
        return isUpload;
    }

    private static boolean makeDirectory(FTPClient ftpClient, String dirToCreate) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("makeDirectory :" + ftpClient.toString() + dirToCreate);
        }
        String[] pathElements = dirToCreate.split("/");
        if (pathElements != null && pathElements.length > 0) {
            for (String singleDir : pathElements) {
                boolean existed = false;
                try {
                    existed = ftpClient.changeWorkingDirectory(singleDir);
                } catch (Exception e) {
                    log.error("Error occured while Making Directory:" + e);
                }
                if (!existed) {
                    boolean created = ftpClient.makeDirectory(singleDir);
                    if (created) {
                        ftpClient.changeWorkingDirectory(singleDir);
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
