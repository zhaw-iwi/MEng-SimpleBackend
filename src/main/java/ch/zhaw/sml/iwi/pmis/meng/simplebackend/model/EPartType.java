package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

public enum EPartType {
    STANDARD, CUSTOM;

    @Override
    public String toString() {
        return name();
    }
    
}
