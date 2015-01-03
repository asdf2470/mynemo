/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 30.12.2014 20:05:15
 * ----------------------------------------------------------------
 *
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package org.training;

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideReasonCodeModel;

public class PriceOverrideData  implements java.io.Serializable 
{

	/** <i>Generated property</i> for <code>PriceOverrideData.oldPrice</code> property defined at extension <code>commercefacades</code>. */
	private Double oldPrice;
	/** <i>Generated property</i> for <code>PriceOverrideData.newPrice</code> property defined at extension <code>commercefacades</code>. */
	private Double newPrice;
	/** <i>Generated property</i> for <code>PriceOverrideData.initialTotalPrice</code> property defined at extension <code>commercefacades</code>. */
	private Double initialTotalPrice;
	/** <i>Generated property</i> for <code>PriceOverrideData.reasonCode</code> property defined at extension <code>commercefacades</code>. */
	private PriceOverrideReasonCodeModel reasonCode;
	/** <i>Generated property</i> for <code>PriceOverrideData.reasonText</code> property defined at extension <code>commercefacades</code>. */
	private String reasonText;
	/** <i>Generated property</i> for <code>PriceOverrideData.currency</code> property defined at extension <code>commercefacades</code>. */
	private CurrencyModel currency;
		
	public PriceOverrideData()
	{
		// default constructor
	}
	
		
	public void setOldPrice(final Double oldPrice)
	{
		this.oldPrice = oldPrice;
	}
	
		
	public Double getOldPrice() 
	{
		return oldPrice;
	}
		
		
	public void setNewPrice(final Double newPrice)
	{
		this.newPrice = newPrice;
	}
	
		
	public Double getNewPrice() 
	{
		return newPrice;
	}
		
		
	public void setInitialTotalPrice(final Double initialTotalPrice)
	{
		this.initialTotalPrice = initialTotalPrice;
	}
	
		
	public Double getInitialTotalPrice() 
	{
		return initialTotalPrice;
	}
		
		
	public void setReasonCode(final PriceOverrideReasonCodeModel reasonCode)
	{
		this.reasonCode = reasonCode;
	}
	
		
	public PriceOverrideReasonCodeModel getReasonCode() 
	{
		return reasonCode;
	}
		
		
	public void setReasonText(final String reasonText)
	{
		this.reasonText = reasonText;
	}
	
		
	public String getReasonText() 
	{
		return reasonText;
	}
		
		
	public void setCurrency(final CurrencyModel currency)
	{
		this.currency = currency;
	}
	
		
	public CurrencyModel getCurrency() 
	{
		return currency;
	}
		
	
}