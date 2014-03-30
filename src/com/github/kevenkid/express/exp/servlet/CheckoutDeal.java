package com.github.kevenkid.express.exp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import javax.servlet.http.*;
import javax.servlet.ServletException;

import com.github.cuter44.util.dao.*;
import com.github.cuter44.util.servlet.*;
import com.alibaba.fastjson.*;

import com.github.kevenkid.express.users.dao.*;
import com.github.kevenkid.express.users.core.*;
import com.github.kevenkid.express.exp.dao.*;
import com.github.kevenkid.express.exp.core.*;

/** ɨ��
 * <pre style="font-size:12px">

   <strong>����</strong>
   POST /exp/checkout

   <strong>����</strong>
   code:string, ����, ����
   finish:boolean, ��ѡ, Ϊtrueʱͬʱ����ݵ����Ϊ���״̬
   <i>Ȩ��</i>
   uid:int, ����, �����������session�洢, ������id, ������"���Ա"���ϵĽ�ɫ
   pass:string, ����, �����������session�洢, ����������

   <strong>��Ӧ</strong>
   �ɹ�ʱ���� OK(200), û����Ӧ����
   �ɹ��ҿ�ݵ����ڵ�һ��ʹ��ʱ�����Զ�����

   <strong>����</strong>
   ƾ�ݴ���ʱ���� Unauthorized(401)
   ���ݲ���active״̬ʱ���� Forbidden(403):{"flag":"!status"}

   <strong>����</strong>����
 * </pre>
 *
 */
public class CheckoutDeal extends HttpServlet
{
    private static final String FLAG = "flag";
    private static final String CODE = "code";
    private static final String FINISH = "finish";
    private static final String UID = "uid";

    private static JSONObject jsonize(Book b)
    {
        JSONObject json = new JSONObject();

        json.put(ID, b.getId());
        json.put(ISBN, b.getIsbn());
        json.put(OWNER, b.getOwner().getId());

        return(json);
    }

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
            String  code = (String) HttpUtil.notNull(HttpUtil.getParam(req, CODE));
            Integer uid  = (Integer)HttpUtil.notNull(HttpUtil.getIntParam(req, UID));

            HiberDao.begin();

            User u = (User)HttpUtil.notNull(UserMgr.get(uid));

            Checkout c = CheckoutMgr.create(u);

            Deal d = DealMgr.get(code);
            if (d == null)
                d = DealMgr.create(code);
            if (!Deal.ACTIVE.equals(d.status))
                throws(new IllegalStatusException());

            d.checkouts.add(c);

            HiberDao.commit();

            JSONObject json = jsonize(d);
            out.println(json.toJSONString());
        }
        catch (IllegalStateException ex)
        {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.println("{\"flag\":\"!status\"}");
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
