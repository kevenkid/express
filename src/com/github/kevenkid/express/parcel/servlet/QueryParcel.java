package com.github.kevenkid.express.parcel.servlet;

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
import com.github.kevenkid.express.parcel.dao.*;
import com.github.kevenkid.express.parcel.core.*;

/** 查单
 * <pre style="font-size:12px">

   <strong>请求</strong>
   POST /parcel/query

   <strong>参数</strong>
   code:string, 必需, 单号

   <strong>响应</strong>
   application/json:
   checkouts[]:array, 历次的扫描记录, 按扫描时间序.
   checkouts[].checker:object, 扫描的快递员
   checkouts[].checker.id:int, ..的id
   checkouts[].checker.name:string(32), ..的名字
   checkouts[].checktime:iso8601date, 扫描时间
   checkouts[].id:long, checkout的id, 业务无关
   code:string(32), 快递单号
   id:int 快递单的id, 业务无关
   status:byte, 参见 com.kevenkid.express.parcel.dao.Parcel.status

   <strong>例外</strong>
   无此单号时返回 Forbidden(403):{"flag":"!notfound"}

   <strong>样例</strong>
   <code>
    curl "http://localhost:8080/express/deal/query?code=1"

    {
      "checkouts":[
        {"checker":{"id":1,"name":""},"checktime":"2014-03-31 23:28:58","id":1},
        {"checker":{"id":1,"name":""},"checktime":"2014-03-31 23:34:17","id":2}
      ],
      "code":"1",
      "id":1,
      "status":0
    }
   </code>
 * </pre>
 *
 */
public class QueryParcel extends HttpServlet
{
    private static final String FLAG = "flag";
    private static final String CODE = "code";

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

            Parcel d = ParcelMgr.get(code);
            if (d == null)
                throw(new EntityNotFoundException());

            HiberDao.commit();

            out.println(
                JSON.toJSONString(
                    d,
                    new PropertyFilter()
                    {
                        @Override
                        public boolean apply(Object object, String name, Object value)
                        {
                            if (User.class.isInstance(object) && "pass".equals(name))
                                return(false);

                            // else
                            return(true);
                        }
                    },
                    SerializerFeature.DisableCircularReferenceDetect
                )
            );
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
