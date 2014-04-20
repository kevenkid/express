package com.github.kevenkid.express.parcel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import javax.servlet.http.*;
import javax.servlet.ServletException;

import com.github.cuter44.util.dao.*;
import com.github.cuter44.util.servlet.*;
import com.alibaba.fastjson.*;

import com.github.kevenkid.express.parcel.core.*;

/** ɨ��
 * <pre style="font-size:12px">

   <strong>����</strong>
   POST /parcel/disable

   <strong>����</strong>
   code:string, ����, ����
   <i>Ȩ��</i>
   uid:int, ����, �����������session�洢, ������id, ������"���Ա"���ϵĽ�ɫ
   pass:string, ����, �����������session�洢, ����������

   <strong>��Ӧ</strong>
   �ɹ�ʱ���� OK(200), û����Ӧ����

   <strong>����</strong>
   ƾ�ݴ���ʱ���� Unauthorized(401)
   ���ݲ�����ʱ���� Forbidden(403): {"flag":"!notfound"}
   ���ݲ���active״̬ʱ���� Forbidden(403):{"flag":"!status"}

   <strong>����</strong>����
 * </pre>
 *
 */
public class DisableParcel extends HttpServlet
{
    private static final String FLAG = "flag";
    private static final String CODE = "code";
    private static final String UID = "uid";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();

        try
        {
            String  code   = (String) HttpUtil.notNull(HttpUtil.getParam(req, CODE));

            HiberDao.begin();

            ParcelController.disable(code);

            HiberDao.commit();

            out.println("{}");
        }
        catch (IllegalStateException ex)
        {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.println("{\"flag\":\"!status\"}");
        }
        catch (EntityNotFoundException ex)
        {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.println("{\"flag\":\"!notfound\"}");
        }
        catch (MissingParameterException ex)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"flag\":\"!parameter\"}");
        }
        catch (Exception ex)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            this.log("", ex);
        }
        finally
        {
            HiberDao.close();
        }

        return;
    }
}
