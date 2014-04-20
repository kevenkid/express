package com.github.kevenkid.express.users.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import javax.servlet.http.*;
import javax.servlet.ServletException;

import com.github.cuter44.util.dao.*;
import com.github.cuter44.util.servlet.*;
import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.*;

import com.github.kevenkid.express.users.dao.User;
import com.github.kevenkid.express.users.core.*;

/** ��¼
 * �Ὣ�û��������뻺���ڷ�������, �ڽ�������Ҫuid/pass��������, ������Ҫ����������.
 * ��Ҫ�ͻ������� cookies
 * <pre style="font-size:12px">

   <strong>����</strong>
   POST /users/login

   <strong>����</strong>
   <i>��Ȩ</i>
   uid:int, �û�id
   pass:string, ����

   <strong>��Ӧ</strong>
   ��ȷ���ʱ���� OK(200)
   <i>Set-Cookie</i>
   JSESSIONID:hex-string, ���� session-id

   <strong>����</strong>

   <strong>����</strong>
   ��
 * </pre>
 *
 */
public class Login extends HttpServlet
{
    private static final String FLAG = "flag";
    private static final String UID = "uid";
    private static final String PASS = "pass";

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
        HttpSession session = req.getSession();

        try
        {
            Integer uid = (Integer)HttpUtil.notNull(HttpUtil.getIntParam(req, UID));

            HiberDao.begin();

            User u = (User)HttpUtil.notNull(UserMgr.get(uid));

            HiberDao.commit();

            session.setAttribute(UID, u.id);
            session.setAttribute(PASS, u.pass);

            out.println(JSON.toJSONString(u));
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
