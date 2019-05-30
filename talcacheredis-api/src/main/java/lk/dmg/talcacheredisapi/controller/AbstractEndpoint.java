/*
 *  Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *  *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *    This software product contains information which is proprietary to
 *    and considered a trade secret The Data management Group Ltd .
 *    It is expressly agreed that it shall not be reproduced in whole or part,
 *    disclosed, divulged or otherwise made available to any third party directly
 *    or indirectly.  Reproduction of this product for any purpose is prohibited
 *    without written authorisation from the The Data Management Group Ltd
 *    All Rights Reserved.
 *
 *    E-Mail andyj@datam.co.uk
 *    URL : www.datam.co.uk
 *    Created By : Asanka Anthony
 *
 */

package lk.dmg.talcacheredisapi.controller;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractEndpoint {
    public abstract Logger getLogger();

    public void writeJsonString(HttpServletResponse httpServletResponse, String msg) {
        try {
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-HTTP-Method-Override");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE,OPTION");
            httpServletResponse.setHeader("Access-Control-Max-Age", "1728000");
            httpServletResponse.getWriter().write(msg);
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("Servlet response conversion pass");
            }
        } catch (IOException e) {
            getLogger().error("JSON writing error :" + e);
        }
    }

    public String createToken(String reqStr) {
        return reqStr;
    }

}
