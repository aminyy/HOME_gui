
import java.io.File;
import java.util.List;
import org.dom4j.Attribute;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DocumentException {
        String xmlFileName = "D:\\test\\platform\\HOMEGUI\\HomFileType\\test\\unit\\src\\newHomFileTemplate2.hom";
        SAXReader reader = new SAXReader();
        Document d = reader.read(new File(xmlFileName));
        Element root = d.getRootElement();
        Element c = root.element("context");
        List<Element> varList = c.elements("var");
        System.out.println(varList.size());

    }
    
    

}
