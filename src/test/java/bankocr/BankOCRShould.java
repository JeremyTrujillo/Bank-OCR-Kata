package bankocr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BankOCRShould {

    private final BankOCR functionalBankOCR = new FunctionalBankOCR();
    private final BankOCR iterativeBankOCR = new IterativeBankOCR();

    @Test
    void get_the_value_of_different_numbers_passed_as_seven_segments(){
        // GIVEN
        String[] numbers = new String[]{"    _  _     _  _  _  _  _ ",
                                        "  | _| _||_||_ |_   ||_||_|",
                                        "  ||_  _|  | _||_|  ||_| _|"};
        // WHEN
        String iterativeResponse = iterativeBankOCR.parseAccountNumber(numbers);
        String functionalResponse = functionalBankOCR.parseAccountNumber(numbers);

        // THEN
        assertThat(iterativeResponse).isEqualTo("123456789");
        assertThat(functionalResponse).isEqualTo("123456789");
    }

    @Test
    void add_ILL_when_an_account_number_has_not_numeric_values(){
        // GIVEN
        String[] numbers = new String[]{"     | _     _  _ |_  _  _ ",
                                        "  | _| _||_||_ |_   ||_||_|",
                                        "  ||_  _|| | _||_   ||_|| |"};
        // WHEN
        String iterativeResponse = iterativeBankOCR.parseAccountNumber(numbers);
        String functionalResponse = functionalBankOCR.parseAccountNumber(numbers);

        // THEN
        assertThat(iterativeResponse).isEqualTo("1?3?5??8? ILL");
        assertThat(functionalResponse).isEqualTo("1?3?5??8? ILL");    }

    @ParameterizedTest
    @MethodSource("provideInvalidAccountNumbers")
    void add_ERR_when_an_account_number_is_not_valid(String[] accountNumber) {
        String iterativeResponse = iterativeBankOCR.parseAccountNumber(accountNumber);
        String functionalResponse = functionalBankOCR.parseAccountNumber(accountNumber);

        assertThat(iterativeResponse).contains("ERR");
        assertThat(functionalResponse).contains("ERR");
    }

    private static Stream<Arguments> provideInvalidAccountNumbers() {
        return Stream.of(
                Arguments.of((Object) new String[]{"                           ",
                                                   "  |  |  |  |  |  |  |  |  |",
                                                   "  |  |  |  |  |  |  |  |  |"}),
                Arguments.of((Object) new String[]{" _  _  _  _  _  _  _  _  _ ",
                                                   " _| _| _| _| _| _| _| _| _|",
                                                   "|_ |_ |_ |_ |_ |_ |_ |_ |_ "}),
                Arguments.of((Object) new String[]{" _  _  _  _  _  _  _  _  _ ",
                                                   " _| _| _| _| _| _| _| _| _|",
                                                   " _| _| _| _| _| _| _| _| _|"}),
                Arguments.of((Object) new String[]{"                           ",
                                                   "|_||_||_||_||_||_||_||_||_|",
                                                   "  |  |  |  |  |  |  |  |  |"}),
                Arguments.of((Object) new String[]{" _  _  _  _  _  _  _  _  _ ",
                                                   "|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
                                                   " _| _| _| _| _| _| _| _| _|"}),
                Arguments.of((Object) new String[]{" _  _  _  _  _  _  _  _  _ ",
                                                   "|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
                                                   "|_||_||_||_||_||_||_||_||_|"}),
                Arguments.of((Object) new String[]{" _  _  _  _  _  _  _  _  _ ",
                                                   "  |  |  |  |  |  |  |  |  |",
                                                   "  |  |  |  |  |  |  |  |  |"}),
                Arguments.of((Object) new String[]{" _  _  _  _  _  _  _  _  _ ",
                                                   "|_||_||_||_||_||_||_||_||_|",
                                                   "|_||_||_||_||_||_||_||_||_|"}),
                Arguments.of((Object) new String[]{" _  _  _  _  _  _  _  _  _ ",
                                                   "|_||_||_||_||_||_||_||_||_|",
                                                   " _| _| _| _| _| _| _| _| _|"}),
                Arguments.of((Object) new String[]{" _  _     _  _        _  _ ",
                                                   "|_ |_ |_| _|  |  ||_||_||_ ",
                                                   "|_||_|  | _|  |  |  | _| _|"}));
    }
}