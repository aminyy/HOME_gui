/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.hdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author minyufang
 */
public class DataTableModel extends DefaultTableModel {

    private List<Class> colTypes = new ArrayList<Class>();

    DataTableModel(Vector tableVec, Vector headers) {
        super(tableVec,headers);
    }

 

    public Class getColumnClass(int c) {
        return colTypes.get(c);
    }

    public void addColumnType(int i, Class columnType) {
        //this.addColumn(columnName);
        this.colTypes.add(i,columnType);
    }
}
