package com.example.shop.checkout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CheckoutActivityPresenterTest {
  CheckoutActivityPresenter presenter;

  @Mock CheckoutActivity activity;

  @Mock CheckoutActivityView view;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    presenter = new CheckoutActivityPresenter(activity, view);
  }

  /** Check if the calculator has included tax and discount correctly. */
  @Test
  public void testCalculateOne() {
    presenter.calculate(100.00, true, true);
    Mockito.verify(view).setSubtotal("100.00");
    Mockito.verify(view).setDiscount("-10.00");
    Mockito.verify(view).setTax("11.70");
    Mockito.verify(view).setTotal("101.70");
  }

  /** Check if the calculator has included discount and excluded tax correctly. */
  @Test
  public void testCalculateTwo() {
    presenter.calculate(100.00, false, true);
    Mockito.verify(view).setSubtotal("100.00");
    Mockito.verify(view).setDiscount("-10.00");
    Mockito.verify(view).setTax("-");
    Mockito.verify(view).setTotal("90.00");
  }

  /** Check if the calculator has included tax and excluded discount correctly. */
  @Test
  public void testCalculateThree() {
    presenter.calculate(100.00, true, false);
    Mockito.verify(view).setSubtotal("100.00");
    Mockito.verify(view).setDiscount("-");
    Mockito.verify(view).setTax("13.00");
    Mockito.verify(view).setTotal("113.00");
  }

  /** Check if the calculator has excluded tax and discount correctly. */
  @Test
  public void testCalculateFour() {
    presenter.calculate(100.00, false, false);
    Mockito.verify(view).setSubtotal("100.00");
    Mockito.verify(view).setDiscount("-");
    Mockito.verify(view).setTax("-");
    Mockito.verify(view).setTotal("100.00");
  }
}
