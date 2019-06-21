package com.java.demomp.util;

public class PlanUtil {

    public static String getRankStrByRank(Integer rank) {
        if (rank == 1) {
            return "D";
        } else if (rank == 2) {
            return "C";
        } else if (rank == 3) {
            return "B";
        } else if (rank == 4) {
            return "A";
        } else if (rank == 5) {
            return "S";
        } else {
            return "NO";
        }
    }
}
