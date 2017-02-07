package com.github.axiopisty.stackoverflow.jsr354;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMonetaryAmountFormat {

  private final String EXPECTED = "$12.99";

  private final CurrencyUnit USD = Monetary.getCurrency("USD");
  private final MonetaryAmount AMOUNT = Money.of(new BigDecimal("12.99"), USD);

  /**
   * See: http://www.programcreek.com/java-api-examples/index.php?api=javax.money.format.MonetaryAmountFormat
   */
  @Test
  @Ignore
  public void testFormatWithCurrencySymbol_1() {
    MonetaryAmountFormat maf = MonetaryFormats.getAmountFormat(
      AmountFormatQueryBuilder
        .of(Locale.US)
        .setFormatName("SYMBOL")
        .build()
    );
    String actual = maf.format(AMOUNT);
    assertEquals(EXPECTED, actual);
    /**
     * The previous assert statement results in the following:
     *
     * javax.money.MonetaryException: No MonetaryAmountFormat for AmountFormatQuery AmountFormatQuery (
     * {Query.formatName=SYMBOL, java.util.Locale=en_US})
     * at javax.money.spi.MonetaryFormatsSingletonSpi.getAmountFormat(MonetaryFormatsSingletonSpi.java:71)
     * at javax.money.format.MonetaryFormats.getAmountFormat(MonetaryFormats.java:110)
     * at com.github.axiopisty.stackoverflow.jsr354.TestMonetaryAmountFormat.testFormatWithCurrencySymbol_1(TestMonetaryAmountFormat.java:26)
     * ...
     **/
  }

  /**
   * See: https://github.com/JavaMoney/javamoney-examples/issues/25
   */
  @Test
  @Ignore
  public void testFormatWithCurrencySymbol_2() {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    MonetaryAmountFormat maf = MonetaryFormats.getAmountFormat(
      AmountFormatQueryBuilder
        .of(Locale.US)
        .set(symbols)
        .build()
    );
    String actual = maf.format(AMOUNT);

    assertEquals(EXPECTED, actual);
    /**
     * The previous assert statement results in the following:
     *
     * org.junit.ComparisonFailure:
     * Expected :$12.99
     * Actual   :USD12.99
     */
  }

  /**
   * See http://stackoverflow.com/q/40244526/328275
   */
  @Test
  public void testFormatWithCurrencySymbol_workingSolution() {
    final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(
      AmountFormatQueryBuilder.of(Locale.US)
        .set(CurrencyStyle.SYMBOL)
        .build()
    );
    final String actual = format.format(AMOUNT);
    assertEquals(EXPECTED, actual);
  }

}
