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

/** 登录
 * 会将用户名和密码缓存在服务器上, 在接下来需要uid/pass的请求中, 不再需要这两个参数.
 * 需要客户端允许 cookies
 * <pre style="font-size:12px">

   <strong>请求</strong>
   POST /users/login

   <strong>参数</strong>
   <i>鉴权</i>
   uid:int, 用户id
   pass:string, 密码

   <strong>响应</strong>
   正确完成时返回 OK(200)
   <i>Set-Cookie</i>
   JSESSIONID:hex-string, 建立 session-id

   <strong>例外</strong>

   <strong>样例</strong>
   无
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
