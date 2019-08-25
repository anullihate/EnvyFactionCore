package dev.anullihate.envygamescore.utils;

import java.util.TreeMap;

public class EnchantmentUtils {

    public static int getIdByName(String value) throws NumberFormatException {
        switch (value) {
            case "protection":
                return 0;
            case "fire_protection":
                return 1;
            case "feather_falling":
                return 2;
            case "blast_protection":
                return 3;
            case "projectile_projection":
                return 4;
            case "thorns":
                return 5;
            case "respiration":
                return 6;
            case "aqua_affinity":
                return 7;
            case "depth_strider":
                return 8;
            case "sharpness":
                return 9;
            case "smite":
                return 10;
            case "bane_of_arthropods":
                return 11;
            case "knockback":
                return 12;
            case "fire_aspect":
                return 13;
            case "looting":
                return 14;
            case "efficiency":
                return 15;
            case "silk_touch":
                return 16;
            case "durability":
                return 17;
            case "fortune":
                return 18;
            case "power":
                return 19;
            case "punch":
                return 20;
            case "flame":
                return 21;
            case "infinity":
                return 22;
            case "luck_of_the_sea":
                return 23;
            case "lure":
                return 24;
            case "frost_walker":
                return 25;
            case "mending":
                return 26;
            case "binding_curse":
                return 27;
            case "vanishing_curse":
                return 28;
            case "impaling":
                return 29;
            case "loyality":
                return 30;
            case "riptide":
                return 31;
            case "channeling":
                return 32;
            default:
                return Integer.parseInt(value);
        }
    }

    public static String getNameById(int id) {
        switch (id) {
            case 0:
                return "Protection";
            case 1:
                return "Fire Protection";
            case 2:
                return "Feather Falling";
            case 3:
                return "Blast Protection";
            case 4:
                return "Projectile Projection";
            case 5:
                return "Thorns";
            case 6:
                return "Respiration";
            case 7:
                return "Aqua Affinity";
            case 8:
                return "Depth Strider";
            case 9:
                return "Sharpness";
            case 10:
                return "Smite";
            case 11:
                return "Bane of Arthropods";
            case 12:
                return "Knockback";
            case 13:
                return "Fire Aspect";
            case 14:
                return "Looting";
            case 15:
                return "Efficiency";
            case 16:
                return "Silk Touch";
            case 17:
                return "Durability";
            case 18:
                return "Fortune";
            case 19:
                return "Power";
            case 20:
                return "Punch";
            case 21:
                return "Flame";
            case 22:
                return "Infinity";
            case 23:
                return "Luck of the Sea";
            case 24:
                return "Lure";
            case 25:
                return "Frost Walker";
            case 26:
                return "Mending";
            case 27:
                return "Binding Curse";
            case 28:
                return "Vanishing Curse";
            case 29:
                return "Impaling";
            case 30:
                return "Loyality";
            case 31:
                return "Riptide";
            case 32:
                return "Channeling";
            default:
                return "Unknown";
        }
    }

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public final static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }
}
