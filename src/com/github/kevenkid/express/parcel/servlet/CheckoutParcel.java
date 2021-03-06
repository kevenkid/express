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

/** 扫单
 * <pre style="font-size:12px">

   <strong>请求</strong>
   POST /parcel/checkout

   <strong>参数</strong>
   code:string, 必需, 单号
   finish:boolean, 可选, 为 true 时同时将快递单标记为完成状态
   action:string, 可选, checkout 时的附注
   <i>权限</i>
   uid:int, 必需, 参数或服务器session存储, 操作人id, 必须是"快递员"以上的角色
   pass:string, 必需, 参数或服务器session存储, 操作人密码

   <strong>响应</strong>
   成功时返回 OK(200), 没有响应正文
   成功且快递单号在第一次使用时将被自动创建

   <strong>例外</strong>
   凭据错误时返回 Unauthorized(401)
   单据不是active状态时返回 Forbidden(403):{"flag":"!status"}

   <strong>样例</strong>暂无
 * </pre>
 *
 */
public class CheckoutParcel extends HttpServlet
{
    private static final String FLAG = "flag";
    private static final String CODE = "code";
    private static final String ACTION = "action";
    private static final String FINISH = "finish";
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
            Integer uid    = (Integer)HttpUtil.notNull(HttpUtil.getIntParam(req, UID));

            String action  = HttpUtil.getParam(req, ACTION);

            Boolean finish = HttpUtil.getBooleanParam(req, FINISH);
            finish = (finish!=null ? finish : Boolean.FALSE);

            HiberDao.begin();

            ParcelController.checkout(code, action, uid, finish);

            HiberDao.commit();
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
