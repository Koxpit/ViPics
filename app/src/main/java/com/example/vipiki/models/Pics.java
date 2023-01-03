package com.example.vipiki.models;

public class Pics {
    private final int allocation;
    private final int selection;

    private final int allocationOs;
    private final int selectionOs;

    private final int allocationMez;
    private final int selectionMez;

    public Pics(int allocationOs, int selectionOs, int allocationMez, int selectionMez) {
        this.allocation = 0;
        this.selection = 0;
        this.allocationOs = allocationOs;
        this.selectionOs = selectionOs;
        this.allocationMez = allocationMez;
        this.selectionMez = selectionMez;
    }

    public Pics(int allocation, int selection) {
        this.allocation = allocation;
        this.selection = selection;
        this.allocationOs = 0;
        this.selectionOs = 0;
        this.allocationMez = 0;
        this.selectionMez = 0;
    }

    public int getAllocationOs() {
        return allocationOs;
    }

    public int getSelectionOs() {
        return selectionOs;
    }

    public int getAllocationMez() {
        return allocationMez;
    }

    public int getSelectionMez() {
        return selectionMez;
    }

    public int getAllocation() {
        return allocation;
    }

    public int getSelection() {
        return selection;
    }
}
