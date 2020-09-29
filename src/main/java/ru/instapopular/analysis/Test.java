package ru.instapopular.analysis;

public class Test {
    public static void main(String[] args) {
        String maxPageSizeString;

        if (check(maxPageSizeString = "queryVerifyMaxPageSize.get()")){
            System.out.println(maxPageSizeString);
        }
    }

    private static boolean check(String s){
        System.out.println(s);
        return s != null;
    }
}
