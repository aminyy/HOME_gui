/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.casnw.home.modules.api;

import java.beans.*;

/**
 *
 * @author Administrator
 */
public class ModuleMetaObjBeanInfo extends SimpleBeanInfo {

    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( net.casnw.home.meta.ModuleMetaObj.class , null ); // NOI18N//GEN-HEADEREND:BeanDescriptor
    // Here you can add code for customizing the BeanDescriptor.

        return beanDescriptor;     }//GEN-LAST:BeanDescriptor


    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_applicationField = 0;
    private static final int PROPERTY_author = 1;
    private static final int PROPERTY_category = 2;
    private static final int PROPERTY_description = 3;
    private static final int PROPERTY_keyword = 4;
    private static final int PROPERTY_model = 5;
    private static final int PROPERTY_moduleClass = 6;
    private static final int PROPERTY_name = 7;
    private static final int PROPERTY_spaceScale = 8;
    private static final int PROPERTY_spaRefSys = 9;
    private static final int PROPERTY_themeColor = 10;
    private static final int PROPERTY_theory = 11;
    private static final int PROPERTY_timeScale = 12;
    private static final int PROPERTY_varMetaObjList = 13;
    private static final int PROPERTY_version = 14;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[15];
    
        try {
            properties[PROPERTY_applicationField] = new PropertyDescriptor ( "applicationField", net.casnw.home.meta.ModuleMetaObj.class, "getApplicationField", "setApplicationField" ); // NOI18N
            properties[PROPERTY_author] = new PropertyDescriptor ( "author", net.casnw.home.meta.ModuleMetaObj.class, "getAuthor", "setAuthor" ); // NOI18N
            properties[PROPERTY_category] = new PropertyDescriptor ( "category", net.casnw.home.meta.ModuleMetaObj.class, "getCategory", "setCategory" ); // NOI18N
            properties[PROPERTY_description] = new PropertyDescriptor ( "description", net.casnw.home.meta.ModuleMetaObj.class, "getDescription", "setDescription" ); // NOI18N
            properties[PROPERTY_keyword] = new PropertyDescriptor ( "keyword", net.casnw.home.meta.ModuleMetaObj.class, "getKeyword", "setKeyword" ); // NOI18N
            properties[PROPERTY_model] = new PropertyDescriptor ( "model", net.casnw.home.meta.ModuleMetaObj.class, "getModel", "setModel" ); // NOI18N
            properties[PROPERTY_moduleClass] = new PropertyDescriptor ( "moduleClass", net.casnw.home.meta.ModuleMetaObj.class, "getModuleClass", "setModuleClass" ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", net.casnw.home.meta.ModuleMetaObj.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_spaceScale] = new PropertyDescriptor ( "spaceScale", net.casnw.home.meta.ModuleMetaObj.class, null, "setSpaceScale" ); // NOI18N
            properties[PROPERTY_spaRefSys] = new PropertyDescriptor ( "spaRefSys", net.casnw.home.meta.ModuleMetaObj.class, null, "setSpaRefSys" ); // NOI18N
            properties[PROPERTY_themeColor] = new PropertyDescriptor ( "themeColor", net.casnw.home.meta.ModuleMetaObj.class, "getThemeColor", "setThemeColor" ); // NOI18N
            properties[PROPERTY_theory] = new PropertyDescriptor ( "theory", net.casnw.home.meta.ModuleMetaObj.class, "getTheory", "setTheory" ); // NOI18N
            properties[PROPERTY_timeScale] = new PropertyDescriptor ( "timeScale", net.casnw.home.meta.ModuleMetaObj.class, null, "setTimeScale" ); // NOI18N
            properties[PROPERTY_varMetaObjList] = new PropertyDescriptor ( "varMetaObjList", net.casnw.home.meta.ModuleMetaObj.class, "getVarMetaObjList", "setVarMetaObjList" ); // NOI18N
            properties[PROPERTY_version] = new PropertyDescriptor ( "version", net.casnw.home.meta.ModuleMetaObj.class, "getVersion", "setVersion" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties
    // Here you can add code for customizing the properties array.

        return properties;     }//GEN-LAST:Properties

    // EventSet identifiers//GEN-FIRST:Events

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[0];//GEN-HEADEREND:Events
    // Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events

    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_addVarMetaObj0 = 0;
    private static final int METHOD_clone1 = 1;
    private static final int METHOD_getSpaceScale2 = 2;
    private static final int METHOD_getSpaRefSys3 = 3;
    private static final int METHOD_getTimeScale4 = 4;
    private static final int METHOD_setSpaceScale5 = 5;
    private static final int METHOD_setSpaRefSys6 = 6;
    private static final int METHOD_setTimeScale7 = 7;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[8];
    
        try {
            methods[METHOD_addVarMetaObj0] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("addVarMetaObj", new Class[] {net.casnw.home.meta.VariableMetaObj.class})); // NOI18N
            methods[METHOD_addVarMetaObj0].setDisplayName ( "" );
            methods[METHOD_clone1] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("clone", new Class[] {})); // NOI18N
            methods[METHOD_clone1].setDisplayName ( "" );
            methods[METHOD_getSpaceScale2] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("getSpaceScale", new Class[] {})); // NOI18N
            methods[METHOD_getSpaceScale2].setDisplayName ( "" );
            methods[METHOD_getSpaRefSys3] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("getSpaRefSys", new Class[] {})); // NOI18N
            methods[METHOD_getSpaRefSys3].setDisplayName ( "" );
            methods[METHOD_getTimeScale4] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("getTimeScale", new Class[] {})); // NOI18N
            methods[METHOD_getTimeScale4].setDisplayName ( "" );
            methods[METHOD_setSpaceScale5] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("setSpaceScale", new Class[] {net.casnw.home.meta.SpacescaleEnum.class})); // NOI18N
            methods[METHOD_setSpaceScale5].setDisplayName ( "" );
            methods[METHOD_setSpaRefSys6] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("setSpaRefSys", new Class[] {net.casnw.home.meta.SparefsysEnum.class})); // NOI18N
            methods[METHOD_setSpaRefSys6].setDisplayName ( "" );
            methods[METHOD_setTimeScale7] = new MethodDescriptor(net.casnw.home.meta.ModuleMetaObj.class.getMethod("setTimeScale", new Class[] {net.casnw.home.meta.TimescaleEnum.class})); // NOI18N
            methods[METHOD_setTimeScale7].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
    // Here you can add code for customizing the methods array.

        return methods;     }//GEN-LAST:Methods

    private static java.awt.Image iconColor16 = null;//GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;//GEN-END:IconsDef
    private static String iconNameC16 = null;//GEN-BEGIN:Icons
    private static String iconNameC32 = null;
    private static String iconNameM16 = null;
    private static String iconNameM32 = null;//GEN-END:Icons

    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx


//GEN-FIRST:Superclass
    // Here you can add code for customizing the Superclass BeanInfo.

//GEN-LAST:Superclass
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable properties of this bean.
     * May return null if the information should be obtained by automatic
     * analysis.
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }

    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean. May return null if the information
     * should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will belong
     * to the IndexedPropertyDescriptor subclass of PropertyDescriptor. A client
     * of getPropertyDescriptors can use "instanceof" to check if a given
     * PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }

    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return An array of EventSetDescriptors describing the kinds of events
     * fired by this bean. May return null if the information should be obtained
     * by automatic analysis.
     */
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEdescriptor();
    }

    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return An array of MethodDescriptors describing the methods implemented
     * by this bean. May return null if the information should be obtained by
     * automatic analysis.
     */
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return getMdescriptor();
    }

    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are
     * customizing the bean.
     *
     * @return Index of default property in the PropertyDescriptor array
     * returned by getPropertyDescriptors.
     * <P>
     * Returns -1 if there is no default property.
     */
    @Override
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }

    /**
     * A bean may have a "default" event that is the event that will mostly
     * commonly be used by human's when using the bean.
     *
     * @return Index of default event in the EventSetDescriptor array returned
     * by getEventSetDescriptors.
     * <P>
     * Returns -1 if there is no default event.
     */
    @Override
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }

    /**
     * This method returns an image object that can be used to represent the
     * bean in toolboxes, toolbars, etc. Icon images will typically be GIFs, but
     * may in future include other formats.
     * <p>
     * Beans aren't required to provide icons and may return null from this
     * method.
     * <p>
     * There are four possible flavors of icons (16x16 color, 32x32 color, 16x16
     * mono, 32x32 mono). If a bean choses to only support a single icon we
     * recommend supporting 16x16 color.
     * <p>
     * We recommend that icons have a "transparent" background so they can be
     * rendered onto an existing background.
     *
     * @param iconKind The kind of icon requested. This should be one of the
     * constant values ICON_COLOR_16x16, ICON_COLOR_32x32, ICON_MONO_16x16, or
     * ICON_MONO_32x32.
     * @return An image object representing the requested icon. May return null
     * if no suitable icon is available.
     */
    @Override
    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
            case ICON_COLOR_16x16:
                if (iconNameC16 == null) {
                    return null;
                } else {
                    if (iconColor16 == null) {
                        iconColor16 = loadImage(iconNameC16);
                    }
                    return iconColor16;
                }
            case ICON_COLOR_32x32:
                if (iconNameC32 == null) {
                    return null;
                } else {
                    if (iconColor32 == null) {
                        iconColor32 = loadImage(iconNameC32);
                    }
                    return iconColor32;
                }
            case ICON_MONO_16x16:
                if (iconNameM16 == null) {
                    return null;
                } else {
                    if (iconMono16 == null) {
                        iconMono16 = loadImage(iconNameM16);
                    }
                    return iconMono16;
                }
            case ICON_MONO_32x32:
                if (iconNameM32 == null) {
                    return null;
                } else {
                    if (iconMono32 == null) {
                        iconMono32 = loadImage(iconNameM32);
                    }
                    return iconMono32;
                }
            default:
                return null;
        }
    }
    
}
