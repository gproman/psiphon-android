package com.psiphon3.psiphonlibrary;

public class StandUpAppInfo {
    public final String name;
    public final int icon;
    public boolean installed;
    public final String blurb;
    public final String packageName;

    public StandUpAppInfo(String name, int icon, boolean installed, String blurb, String packageName) {
        this.name = name;
        this.icon = icon;
        this.installed = installed;
        this.blurb = blurb;
        this.packageName = packageName;
    }
}
