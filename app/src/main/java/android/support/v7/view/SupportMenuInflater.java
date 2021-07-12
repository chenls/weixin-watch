/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SupportMenuInflater
extends MenuInflater {
    private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    private static final String LOG_TAG = "SupportMenuInflater";
    private static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    private final Object[] mActionProviderConstructorArguments;
    private final Object[] mActionViewConstructorArguments;
    private Context mContext;
    private Object mRealOwner;

    static {
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class};
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    }

    public SupportMenuInflater(Context context) {
        super(context);
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Object findRealOwner(Object object) {
        if (object instanceof Activity || !(object instanceof ContextWrapper)) {
            return object;
        }
        return this.findRealOwner(((ContextWrapper)object).getBaseContext());
    }

    private Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = this.findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    /*
     * Unable to fully structure code
     */
    private void parseMenu(XmlPullParser var1_1, AttributeSet var2_2, Menu var3_3) throws XmlPullParserException, IOException {
        block9: {
            var10_4 = new MenuState((Menu)var3_3);
            var4_5 = var1_1.getEventType();
            var6_6 = 0;
            var9_7 = null;
            block5: while (true) {
                if (var4_5 != 2) ** GOTO lbl26
                var3_3 = var1_1.getName();
                if (!var3_3.equals("menu")) ** GOTO lbl25
                var4_5 = var1_1.next();
                block6: while (true) {
                    var5_8 = 0;
                    var8_9 = var4_5;
                    block7: while (var5_8 == 0) {
                        switch (var8_9) {
                            default: {
                                var3_3 = var9_7;
                                var7_10 = var5_8;
                                var4_5 = var6_6;
lbl19:
                                // 13 sources

                                while (true) {
                                    var8_9 = var1_1.next();
                                    var6_6 = var4_5;
                                    var5_8 = var7_10;
                                    var9_7 = var3_3;
                                    continue block7;
                                    break;
                                }
                            }
lbl25:
                            // 1 sources

                            throw new RuntimeException("Expecting menu, got " + (String)var3_3);
lbl26:
                            // 1 sources

                            var4_5 = var5_8 = var1_1.next();
                            if (var5_8 != 1) continue block5;
                            var4_5 = var5_8;
                            continue block6;
                            case 2: {
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                if (var6_6 != 0) ** GOTO lbl19
                                var3_3 = var1_1.getName();
                                if (!var3_3.equals("group")) ** GOTO lbl42
                                var10_4.readGroup(var2_2);
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                ** GOTO lbl19
lbl42:
                                // 1 sources

                                if (!var3_3.equals("item")) ** GOTO lbl48
                                var10_4.readItem(var2_2);
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                ** GOTO lbl19
lbl48:
                                // 1 sources

                                if (!var3_3.equals("menu")) ** GOTO lbl54
                                this.parseMenu(var1_1, var2_2, (Menu)var10_4.addSubMenuItem());
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                ** GOTO lbl19
lbl54:
                                // 1 sources

                                var4_5 = 1;
                                var7_10 = var5_8;
                                ** GOTO lbl19
                            }
                            case 3: {
                                var11_11 = var1_1.getName();
                                if (var6_6 == 0 || !var11_11.equals(var9_7)) ** GOTO lbl64
                                var4_5 = 0;
                                var3_3 = null;
                                var7_10 = var5_8;
                                ** GOTO lbl19
lbl64:
                                // 1 sources

                                if (!var11_11.equals("group")) ** GOTO lbl70
                                var10_4.resetGroup();
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                ** GOTO lbl19
lbl70:
                                // 1 sources

                                if (!var11_11.equals("item")) ** GOTO lbl87
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                if (var10_4.hasAddedItem()) ** GOTO lbl19
                                if (MenuState.access$000(var10_4) == null || !MenuState.access$000(var10_4).hasSubMenu()) ** GOTO lbl82
                                var10_4.addSubMenuItem();
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                ** GOTO lbl19
lbl82:
                                // 1 sources

                                var10_4.addItem();
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                ** GOTO lbl19
lbl87:
                                // 1 sources

                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                                if (!var11_11.equals("menu")) ** GOTO lbl19
                                var7_10 = 1;
                                var4_5 = var6_6;
                                var3_3 = var9_7;
                                ** continue;
                            }
                            case 1: {
                                break block5;
                            }
                        }
                    }
                    break block9;
                    break;
                }
                break;
            }
            throw new RuntimeException("Unexpected end of document");
        }
    }

    /*
     * Exception decompiling
     */
    public void inflate(int var1_1, Menu var2_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 3[TRYBLOCK] [7 : 103->114)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static class InflatedOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = new Class[]{MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object object, String string2) {
            this.mRealOwner = object;
            Class<?> clazz = object.getClass();
            try {
                this.mMethod = clazz.getMethod(string2, PARAM_TYPES);
                return;
            }
            catch (Exception exception) {
                string2 = new InflateException("Couldn't resolve menu item onClick handler " + string2 + " in class " + clazz.getName());
                string2.initCause((Throwable)exception);
                throw string2;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return (Boolean)this.mMethod.invoke(this.mRealOwner, menuItem);
                }
                this.mMethod.invoke(this.mRealOwner, menuItem);
                return true;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private boolean itemEnabled;
        private int itemIconResId;
        private int itemId;
        private String itemListenerMethodName;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private boolean itemVisible;
        private Menu menu;

        public MenuState(Menu menu) {
            this.menu = menu;
            this.resetGroup();
        }

        static /* synthetic */ ActionProvider access$000(MenuState menuState) {
            return menuState.itemActionProvider;
        }

        private char getShortcut(String string2) {
            if (string2 == null) {
                return '\u0000';
            }
            return string2.charAt(0);
        }

        private <T> T newInstance(String string2, Class<?>[] object, Object[] objectArray) {
            try {
                object = SupportMenuInflater.this.mContext.getClassLoader().loadClass(string2).getConstructor((Class<?>)object);
                object.setAccessible(true);
                object = object.newInstance(objectArray);
            }
            catch (Exception exception) {
                Log.w((String)SupportMenuInflater.LOG_TAG, (String)("Cannot instantiate class: " + string2), (Throwable)exception);
                return null;
            }
            return (T)object;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setItem(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
            boolean bl2 = this.itemCheckable >= 1;
            menuItem2.setCheckable(bl2).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut).setNumericShortcut(this.itemNumericShortcut);
            if (this.itemShowAsAction >= 0) {
                MenuItemCompat.setShowAsAction(menuItem, this.itemShowAsAction);
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                menuItem.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener)new InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (menuItem instanceof MenuItemImpl) {
                menuItem2 = (MenuItemImpl)menuItem;
            }
            if (this.itemCheckable >= 2) {
                if (menuItem instanceof MenuItemImpl) {
                    ((MenuItemImpl)menuItem).setExclusiveCheckable(true);
                } else if (menuItem instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS)menuItem).setExclusiveCheckable(true);
                }
            }
            boolean bl3 = false;
            if (this.itemActionViewClassName != null) {
                MenuItemCompat.setActionView(menuItem, (View)this.newInstance(this.itemActionViewClassName, ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
                bl3 = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (!bl3) {
                    MenuItemCompat.setActionView(menuItem, this.itemActionViewLayout);
                } else {
                    Log.w((String)SupportMenuInflater.LOG_TAG, (String)"Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if (this.itemActionProvider != null) {
                MenuItemCompat.setActionProvider(menuItem, this.itemActionProvider);
            }
        }

        public void addItem() {
            this.itemAdded = true;
            this.setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            this.setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        public void readGroup(AttributeSet attributeSet) {
            attributeSet = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
            this.groupId = attributeSet.getResourceId(R.styleable.MenuGroup_android_id, 0);
            this.groupCategory = attributeSet.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
            this.groupOrder = attributeSet.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
            this.groupCheckable = attributeSet.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.groupVisible = attributeSet.getBoolean(R.styleable.MenuGroup_android_visible, true);
            this.groupEnabled = attributeSet.getBoolean(R.styleable.MenuGroup_android_enabled, true);
            attributeSet.recycle();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void readItem(AttributeSet attributeSet) {
            int n2;
            attributeSet = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuItem);
            this.itemId = attributeSet.getResourceId(R.styleable.MenuItem_android_id, 0);
            this.itemCategoryOrder = 0xFFFF0000 & attributeSet.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory) | 0xFFFF & attributeSet.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder);
            this.itemTitle = attributeSet.getText(R.styleable.MenuItem_android_title);
            this.itemTitleCondensed = attributeSet.getText(R.styleable.MenuItem_android_titleCondensed);
            this.itemIconResId = attributeSet.getResourceId(R.styleable.MenuItem_android_icon, 0);
            this.itemAlphabeticShortcut = this.getShortcut(attributeSet.getString(R.styleable.MenuItem_android_alphabeticShortcut));
            this.itemNumericShortcut = this.getShortcut(attributeSet.getString(R.styleable.MenuItem_android_numericShortcut));
            if (attributeSet.hasValue(R.styleable.MenuItem_android_checkable)) {
                n2 = attributeSet.getBoolean(R.styleable.MenuItem_android_checkable, false) ? 1 : 0;
                this.itemCheckable = n2;
            } else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = attributeSet.getBoolean(R.styleable.MenuItem_android_checked, false);
            this.itemVisible = attributeSet.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible);
            this.itemEnabled = attributeSet.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled);
            this.itemShowAsAction = attributeSet.getInt(R.styleable.MenuItem_showAsAction, -1);
            this.itemListenerMethodName = attributeSet.getString(R.styleable.MenuItem_android_onClick);
            this.itemActionViewLayout = attributeSet.getResourceId(R.styleable.MenuItem_actionLayout, 0);
            this.itemActionViewClassName = attributeSet.getString(R.styleable.MenuItem_actionViewClass);
            this.itemActionProviderClassName = attributeSet.getString(R.styleable.MenuItem_actionProviderClass);
            n2 = this.itemActionProviderClassName != null ? 1 : 0;
            if (n2 != 0 && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider)this.newInstance(this.itemActionProviderClassName, ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (n2 != 0) {
                    Log.w((String)SupportMenuInflater.LOG_TAG, (String)"Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            attributeSet.recycle();
            this.itemAdded = false;
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }
    }
}

