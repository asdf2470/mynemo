/**
 * 
 */
package de.hybris.platform.yacceleratorstorefront.controllers.pages;

import de.hybris.platform.validation.annotations.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author D056437
 *	Telesales
 */
public class UpdatePriceForm
{
	@NotNull(message = "Price cannot be empty")
	@Min(value = 0, message = "Price cannot be negative")
	@Digits(fraction = 2, integer = 10, message = "Wrong number format for a price!")
	private Double totalPrice;
	
	@NotNull(message = "Please provide a reason text!")
	@NotEmpty(message = "Please provide a reason text!")
	private String reasonText;


	public void setReasonText(String reasonText)
	{
		this.reasonText = reasonText;
	}

	public void setTotalPrice(final Double totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public Double getTotalPrice()
	{
		return totalPrice;
	}


	public String getReasonText()
	{
		return reasonText;
	}

}
