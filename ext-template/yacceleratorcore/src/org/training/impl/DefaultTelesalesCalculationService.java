/**
 * 
 */
package org.training.impl;

import de.hybris.platform.core.CoreAlgorithms;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.DiscountValue;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.training.PriceOverrideService;
import org.training.TelesalesCalculationService;


/**
 * @author D056437
 *
 */
public class DefaultTelesalesCalculationService extends DefaultCalculationService implements TelesalesCalculationService
{
	@Resource(name = "priceOverrideService")
	private PriceOverrideService priceOverrideService;

	@Resource(name = "cartService")
	private CartService cartService;

	@Override
	public void calculateTotals(AbstractOrderEntryModel entry, boolean recalculate)
	{
		if ((recalculate) || !entry.getCalculated().booleanValue())
		{
			if (entry instanceof OrderEntryModel)
			{
				maintainOrderEntry(entry);				
			}
			else
			{
				PriceOverrideModel priceOverride = priceOverrideService.findPriceOverride(entry);
				if (priceOverride != null)
				{
					entry.setCurrentPriceOverride(priceOverride);
					entry.setTotalPrice(entry.getCurrentPriceOverride().getNewTotalPrice());
				}
				else
					calculateTotalsForCartEntries(entry);
			}

			calculateTotalTaxValues(entry);
			entry.setCalculated(Boolean.TRUE);
			getModelService().save(entry);
		}
	}


	private void maintainOrderEntry(AbstractOrderEntryModel entry)
	{
		if (entry.getCurrentPriceOverride() != null)
		{					
			//order entry, do not re-calculate. 
			List<AbstractOrderEntryModel> cartEntries = cartService.getSessionCart().getEntries();
			AbstractOrderEntryModel rightEntry = null;
			for (AbstractOrderEntryModel cartEntry : cartEntries)
			{
				if (cartEntry.getPk().toString().equalsIgnoreCase(entry.getCurrentPriceOverride().getCartEntry().toString()))
				{
					rightEntry=cartEntry;
					break;
				}
			}
			if (rightEntry != null)
			{
				List<PriceOverrideModel> priceOverrides = priceOverrideService.findPriceOverrides(rightEntry);
				for (PriceOverrideModel priceOverride : priceOverrides)
				{
					priceOverride.setCartEntry(entry.getPk());
					priceOverride.setCart(entry.getOrder());
					modelService.save(priceOverride);
				}
			}
		}		
	}

	private void calculateTotalsForCartEntries(AbstractOrderEntryModel entry)
	{
		AbstractOrderModel order = entry.getOrder();
		CurrencyModel curr = order.getCurrency();
		int digits = curr.getDigits().intValue();
		double totalPriceWithoutDiscount = CoreAlgorithms.round(entry.getBasePrice().doubleValue()
				* entry.getQuantity().longValue(), digits);
		double quantity = entry.getQuantity().doubleValue();

		List appliedDiscounts = DiscountValue.apply(quantity, totalPriceWithoutDiscount, digits,
				convertDiscountValues(order, entry.getDiscountValues()), curr.getIsocode());
		entry.setDiscountValues(appliedDiscounts);
		double totalPrice = totalPriceWithoutDiscount;
		for (Iterator it = appliedDiscounts.iterator(); it.hasNext();)
		{
			totalPrice -= ((DiscountValue) it.next()).getAppliedValue();
		}
		entry.setTotalPrice(Double.valueOf(totalPrice));
	}
}

