package com.github.kevenkid.express.test;

import java.util.Scanner;

import org.apache.http.client.fluent.*;

/** Evaluate a checkout action
 * @warn server-only
 */
public class CheckoutCLI
{
    /**
     * @param code
     * @param uid
     * @param pass
     * @return response body
     */
    public static String checkout(String code, Long uid, String pass)
    {
        String resp = "";
        try
        {
            String baseurl = "http://localhost:8080/express/parcel/checkout";

            resp = Request.Post(baseurl)
                .bodyForm(
                    Form.form()
                    .add("code", code)
                    .add("uid", uid.toString())
                    .add("pass", pass)
                    .build()
                )
                .execute()
                .returnContent()
                .asString();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return(resp);
    }

    public static void main(String[] args)
    {
        Scanner scn = new Scanner(System.in);

        System.out.println("code?");
        String code = scn.nextLine();

        System.out.println("uid?");
        Long uid = scn.nextLong();
        scn.nextLine();

        System.out.println("pass?");
        String pass = scn.nextLine();

        System.out.println(
            checkout(code, uid, pass)
        );
    }
}
