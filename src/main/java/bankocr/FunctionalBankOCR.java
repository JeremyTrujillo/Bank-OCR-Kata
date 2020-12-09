package bankocr;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FunctionalBankOCR implements BankOCR {
    Integer checksumCalculation = 1;
    Map<Number, String> numbers;

    public FunctionalBankOCR(){
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
        resetStatus(accountNumberBuilder);
        return accountNumberBuilder.toString() + validation;
    }

    private String getNumber(Number number) { return numbers.getOrDefault(number,"?"); }

    private String validateAccountNumber(StringBuilder accountNumberBuilder) {
        String[] accountNumber = accountNumberBuilder.reverse().toString().split("");
        Integer checksum = calculateChecksum(Arrays.stream(accountNumber));
        if (checksum == null) return " ILL";
        if (checksum % 11 != 0) return " ERR";
        else return "";
    }

    private Integer calculateChecksum(Stream<String> stream) {
        try{
            return stream.mapToInt(Integer::parseInt)
                    .reduce(0,this::increaseChecksum);
        } catch (NumberFormatException ex){
            return null;
        }
    }

    private int increaseChecksum(int firstElement, int secondElement) {
        int sum = firstElement + secondElement*checksumCalculation;
        checksumCalculation++;
        return sum;
    }

    private void resetStatus(StringBuilder accountNumberBuilder) {
        accountNumberBuilder.reverse();
        checksumCalculation = 1;
    }
}
