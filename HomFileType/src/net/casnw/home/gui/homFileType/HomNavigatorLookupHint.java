package net.casnw.home.gui.homFileType;

import org.netbeans.spi.navigator.NavigatorLookupHint;

public class HomNavigatorLookupHint implements NavigatorLookupHint {
    
    @Override
    public String getContentType() {
        return "text/hom+xml";
    }
    
}