/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.modules.api;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Administrator
 */
public class Utils {

    //判断类是否是context
    public static int isContext(String str) {
        int flag = 0;
        if (str.equalsIgnoreCase("net.casnw.home.model.Context")) {
            return flag = 1;
        } else if (str.equalsIgnoreCase("net.casnw.home.model.HRUContext")) {
            return flag = 1;
        } else if (str.equalsIgnoreCase("net.casnw.home.model.Model")) {
            return flag = 1;
        } else if (str.equalsIgnoreCase("net.casnw.home.model.SpatialContext")) {
            return flag = 1;
        } else if (str.equalsIgnoreCase("net.casnw.home.model.TemporalContext")) {
            return flag = 1;
        }
        return flag;
    }

    /**
     * 判断是否为数字
     *
     * @return Boolean
     */
    public static boolean isNumeric(String str) {
        try {
            //Integer.parseInt(str);
            Double.parseDouble(str);
            //Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block 
            return false;
        }
    }

}
