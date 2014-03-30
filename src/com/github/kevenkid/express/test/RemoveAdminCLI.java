package com.github.kevenkid.express.test;

import java.util.Scanner;

import com.alibaba.fastjson.*;
import com.github.cuter44.util.dao.*;

import com.github.kevenkid.express.users.dao.*;
import com.github.kevenkid.express.users.core.*;

/** Add administror
 * @warn server-only
 */
public class RemoveAdminCLI
{
    /** add admin
     * @return entity content as json, or null if failed.
     */
    public static void removeAdmin(Integer id)
    {
        try
        {
            HiberDao.begin();

            AdminMgr.delete(id);

            HiberDao.commit();

            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            HiberDao.close();
        }

        return;
    }

    public static void main(String[] args)
    {
        Scanner scn = new Scanner(System.in);

        System.out.println("id?");
        Integer id = scn.nextInt();

        removeAdmin(id);
    }
}
