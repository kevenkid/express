package com.github.kevenkid.express.test;

import java.util.Scanner;

import com.alibaba.fastjson.*;
import com.github.cuter44.util.dao.*;

import com.github.kevenkid.express.users.dao.*;
import com.github.kevenkid.express.users.core.*;

/** Add worker
 * @warn server-only
 */
public class AddWorkerCLI
{
    /** add worker
     * @return entity content as json, or null if failed.
     */
    public static String addWorker(String name, String pass)
    {
        try
        {
            HiberDao.begin();

            Worker e = WorkerMgr.create(name, pass);

            HiberDao.commit();

            return(
                JSON.toJSONString(e)
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            HiberDao.close();
        }

        return(null);
    }

    public static void main(String[] args)
    {
        Scanner scn = new Scanner(System.in);

        System.out.println("name?");
        String name = scn.nextLine();

        System.out.println("pass?");
        String pass = scn.nextLine();

        System.out.println(
            addWorker(name, pass)
        );
    }
}
