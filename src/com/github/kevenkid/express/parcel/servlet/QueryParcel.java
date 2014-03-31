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

/** �鵥
 * <pre style="font-size:12px">

   <strong>����</strong>
   POST /parcel/query

   <strong>����</strong>
   code:string, ����, ����

   <strong>��Ӧ</strong>
   application/json:
   checkouts[]:array, ���ε�ɨ���¼, ��ɨ��ʱ����.
   checkouts[].checker:object, ɨ��Ŀ��Ա
   checkouts[].checker.id:int, ..��id
   checkouts[].checker.name:string(32), ..������
   checkouts[].checktime:iso8601date, ɨ��ʱ��
   checkouts[].id:long, checkout��id, ҵ���޹�
   code:string(32), ��ݵ���
   id:int ��ݵ���id, ҵ���޹�
   status:byte, �μ� com.kevenkid.express.parcel.dao.Parcel.status

   <strong>����</strong>
   �޴˵���ʱ���� Forbidden(403):{"flag":"!notfound"}

   <strong>����</strong>
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
