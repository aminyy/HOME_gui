/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.casnw.home.gui.project;

/**
 *
 * @author dhs
 */
class  FileString implements Comparable<Object>{
    public  String FilePath;
    public  String FileName;
                  
    public FileString(String p,String n){
        FilePath = p;
        FileName = n;
    }
                  
    @Override
    public int compareTo(Object o) {
        if (Integer.valueOf(this.FileName.substring(this.FileName.length()-2,this.FileName.length())) 
            > Integer.valueOf(((FileString)o).FileName.substring((((FileString)o).FileName.length()-2),((FileString) o).FileName.length()))) {
            return 1;
        }
        return -1;
    }
}