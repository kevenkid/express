package com.github.kevenkid.express.users.filter;

/* filter */
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import com.github.cuter44.util.dao.*;
import com.github.cuter44.util.servlet.*;

import com.github.kevenkid.express.users.core.*;

/** 检查用户 id 和 pass 是否匹配
 * 如果无法匹配, 则拦截请求.
 * <br />
 * 需要在 web.xml 中配置两个参数:
 * userIdParamName 表示用于查找用户id的键名
 * userPassParamName 表示用于查找用户pass的键名
 *
 * <pre style="font-size:12px">
   <strong>例外</strong>
   参数不全时返回 Bad Request(400): {"flag":"!parameter"}
   uid不存在时, 返回 Unauthorized(401): {"flag":"!notfound"}
   密码不匹配时, 返回 Unauthorized(401): {"flag":"!incorrect"}
 * </pre>
 */
public class IsUserVerifier
    implements Filter
{
    private static final String USER_UID_PARAM_NAME = "userIdParamName";
    private static final String USER_PASS_PARAM_NAME = "userPassParamName";
    private String UID;
    private String PASS;

    private ServletContext context;

    @Override
    public void init(FilterConfig conf)
    {
        this.context = conf.getServletContext();

        this.UID = conf.getInitParameter(USER_UID_PARAM_NAME);
        this.PASS = conf.getInitParameter(USER_PASS_PARAM_NAME);

        return;
    }

    @Override
    public void destroy()
    {
        return;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
        throws IOException, ServletException
    {
      // PRE-PROCESS
        boolean flag = false;

        try
        {
            Integer uid   = (Integer)HttpUtil.notNull(HttpUtil.getIntParam(req, UID));
            String  pass  = (String) HttpUtil.notNull(HttpUtil.getParam(req, PASS));

            HiberDao.begin();

            flag = AuthController.verifyUidPass(uid, pass);

            HiberDao.commit();
        }
        catch (MissingParameterException ex)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().println("{\"flag\":\"!parameter\"}");
            return;
        }
        catch (EntityNotFoundException ex)
        {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().println("{\"flag\":\"!notfound\"}");
            return;
        }
        catch (Exception ex)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            this.context.log("", ex);
            return;
        }
        finally
        {
            HiberDao.close();
        }

        if (false == flag)
        {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().println("{\"flag\":\"!incorrect\"}");
            return;
        }

        // else
        chain.doFilter(req, resp);

      // POST-PROCESS
    }
}
