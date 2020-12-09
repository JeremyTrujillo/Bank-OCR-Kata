package bankocr;

import java.util.HashMap;
import java.util.Map;

public class IterativeBankOCR implements BankOCR {
    Map<Number, String> numbers;

    public IterativeBankOCR(){
        numbers = new HashMap<>();
        numbers.put(new Number(new String[]{" _ ",
                                            "| |",
                                            "|_|"}),"0");
        numbers.put(new Number(new String[]{"   ",
                                            "  |",
                                            "  |"}),"1");
        numbers.put(new Number(new String[]{" _ ",
                                              " _|",
                                              "|_ "}),"2");
        numbers.put(new Number(new String[]{" _ ",
                                            " _|",
                                            " _|"}),"3");
        numbers.put(new Number(new String[]{"   ",
                                            "|_|",
                                            "  |"}),"4");
        numbers.put(new Number(new String[]{" _ ",
                                            "|_ ",
                                            " _|"}),"5");
        numbers.put(new Number(new String[]{" _ ",
                                            "|_ ",
                                            "|_|"}),"6");
        numbers.put(new Number(new String[]{" _ ",
                                            "  |",
                                            "  |"}),"7");
        numbers.put(new Number(new String[]{" _ ",
                                            "|_|",
                                            "|_|"}),"8");
        numbers.put(new Number(new String[]{" _ ",
                                            "|_|",
                                            " _|"}),"9");

    }

    public String parseAccountNumber(String[] numbers){
        StringBuilder accountNumberBuilder = new StringBuilder();
        for (int i = 0; i < numbers[0].length(); i+=3) {
            String[] array = new String[]{numbers[0].substring(i,i+3),
                                          numbers[1].substring(i,i+3),
                                          numbers[2].substring(i,i+3)};
            accountNumberBuilder.append(getNumber(new Number(array)));
        }
        String validation = validateAccountNumber(accountNumberBuilder);
        return accountNumberBuilder.toString() + validation;
    }

    private String getNumber(Number number) { return numbers.getOrDefault(number,"?"); }

    private String validateAccountNumber(StringBuilder accountNumberBuilder) {
        String[] accountNumber = accountNumberBuilder.reverse().toString().split("");
        accountNumberBuilder.reverse();
        Integer checksum = calculateChecksum(accountNumber);
        if (checksum == null) return " ILL";
        if (checksum % 11 != 0) return " ERR";
        else return "";
    }

    private Integer calculateChecksum(String[] accountNumber) {
        int checksum = 0;
        for (int i = 0; i < accountNumber.length; i++) {
            if (accountNumber[i].equals("?")) return null;
            checksum += Integer.parseInt(accountNumber[i]) * (i+1);
        }
        return checksum;
    }
}
