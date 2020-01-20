package com.psiphon3.psiphonlibrary;

public class StandUpAppInfo {
    public final String name;
    public final int icon;
    public final boolean installed;
    public final String blurb;

    public StandUpAppInfo(String name, int icon, boolean installed, String blurb) {
        this.name = name;
        this.icon = icon;
        this.installed = installed;
        this.blurb = blurb;
    }
}
