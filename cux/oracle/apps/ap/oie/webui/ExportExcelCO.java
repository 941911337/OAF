/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package cux.oracle.apps.ap.oie.webui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;


import java.io.FileInputStream;
import java.io.InputStream;

import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.cabo.ui.data.DataObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * Controller for ...
 */
public class ExportExcelCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    
    super.processRequest(pageContext, webBean);
    OAApplicationModule am = pageContext.getApplicationModule(webBean);
    am.invokeMethod("query");
    
  }

  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processFormRequest(pageContext, webBean);
    String l_enent = pageContext.getParameter(EVENT_PARAM);
    System.out.println(l_enent);
    if(l_enent.equals("export"))
    {
        OAApplicationModule am = pageContext.getApplicationModule(webBean);
        byte abyte0[] = (byte [])am.invokeMethod("export");
        System.out.println(abyte0.length);
        try{
         DataObject dataobject = pageContext.getNamedDataObject("_SessionParameters");
         HttpServletResponse httpservletresponse = (HttpServletResponse)dataobject.selectValue(null, "HttpServletResponse");
        this.download(httpservletresponse,abyte0);
        }
      catch (Exception e)
       {
         e.printStackTrace();
         throw OAException.wrapperException(e);
       }
    }
  }
  
  
  
    private void download( HttpServletResponse response,byte[] abyte0) { 
         
            try {  
                // path是指欲下载的文件的路径。  
//                File file = new File(path);  
//                // 取得文件名。  
//                String filename = file.getName();  
//                // 以流的形式下载文件。  
//                InputStream fis = new BufferedInputStream(new FileInputStream(path));  
//                byte[] buffer = new byte[fis.available()];  
//                fis.read(buffer);  
//                fis.close();  
                // 清空response  
                response.reset();  
                // 设置response的Header  
                response.addHeader("Content-Disposition", "attachment;filename=Export.xls");  
                response.addHeader("Content-Length", "" + abyte0.length);  
                OutputStream toClient = new BufferedOutputStream(  
                        response.getOutputStream());  
                response.setContentType("application/vnd.ms-excel;charset=gb2312");  
                toClient.write(abyte0);  
                toClient.flush();  
                toClient.close();  
            } catch (Exception ex) {  
                ex.printStackTrace();  
            }  
        }  

}
