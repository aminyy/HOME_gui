/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.casnw.home.hdata.io;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

/**
 *
 * @author minyufang
 */
public class TimeColumItem extends JMenuItem implements ActionListener {

    HashMap<String, Object[]> data = new HashMap<String, Object[]>();

    public TimeColumItem(String text, HashMap<String, Object[]> data) {
        super(text);
        this.data = data;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame jf = new JFrame("合并时间列");//实例化一个对象
        Container container = jf.getContentPane();//获取一个容器
        TimeColumPanel tcp = new TimeColumPanel(data);

        container.add(tcp);//将标签添加到容器中

        jf.setVisible(true);//可视化
        jf.setSize(476, 500);//设窗体大小
        jf.setLocation(400, 300);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
