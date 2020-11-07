package xyz.wagyourtail.jsmacros.config;

import xyz.wagyourtail.jsmacros.api.sharedinterfaces.IRawMacro;

import java.util.Comparator;

public class RawMacro implements IRawMacro {
    public MacroType type;
    public String eventkey;
    public String scriptFile;
    public boolean enabled;
    
    public RawMacro(MacroType type, String eventkey, String scriptFile, boolean enabled) {
        this.type = type;
        this.eventkey = eventkey;
        this.scriptFile = scriptFile;
    }
    
    public boolean equals(RawMacro macro) {
        return type == macro.type && eventkey.equalsIgnoreCase(macro.eventkey) && scriptFile.equals(macro.scriptFile);
    }
    
    public String toString() {
        return String.format("RawMacro:{\"type\": \"%s\", \"eventkey\": \"%s\", \"scriptFile\": \"%s\", \"enabled\": %b}", type.toString(), eventkey, scriptFile, enabled);
    }
    
    public static IRawMacro copy(RawMacro m) {
        return new RawMacro(m.type, m.eventkey, m.scriptFile, m.enabled);
    }
    
    public IRawMacro copy() {
        return copy(this);
    }
    
    public static class SortByEnabled implements Comparator<RawMacro> {
        @Override
        public int compare(RawMacro a, RawMacro b) {
            if (a.enabled ^ b.enabled) {
                return a.enabled ? -1 : 1;
            } else {
                return a.toString().compareTo(b.toString());
            }
        }
    }
    public static class SortByTriggerName implements Comparator<RawMacro> {
        @Override
        public int compare(RawMacro a, RawMacro b) {
            int comp = a.eventkey.compareTo(b.eventkey);
            if (comp != 0) return comp;
            if (a.enabled ^ b.enabled) return a.enabled ? -1 : 1;
            return a.toString().compareTo(b.toString());
        }
    }
    public static class SortByFileName implements Comparator<RawMacro> {
        @Override
        public int compare(RawMacro a, RawMacro b) {
            int comp = a.scriptFile.compareTo(b.scriptFile);
            if (comp != 0) return comp;
            if (a.enabled ^ b.enabled) return a.enabled ? -1 : 1;
            return a.toString().compareTo(b.toString());
        }
    }
    
    public static enum SortMethod {
        Enabled,
        TriggerName,
        FileName
    }

    @Override
    public MacroType getType() {
        return type;
    }

    @Override
    public String getEventKey() {
        return eventkey;
    }

    @Override
    public String getScriptFile() {
        return scriptFile;
    }

    @Override
    public boolean getEnabled() {
        return enabled;
    }
}