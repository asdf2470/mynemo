/**
 * 
 */
package org.training;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideModel;

import org.training.PriceOverrideData;


/**
 * @author D056437
 *
 */
public class PriceOverridePopulator<SOURCE extends PriceOverrideModel, TARGET extends PriceOverrideData> implements
		Populator<SOURCE, TARGET>
{

	@Override
	public void populate(final SOURCE priceOverride, final TARGET priceOverrideData) throws ConversionException
	{
	if(priceOverride.getNewTotalPrice()!=null)
		priceOverrideData.setNewPrice(priceOverride.getNewTotalPrice());
	
	if(priceOverride.getOldTotalPrice()!=null)
		priceOverrideData.setOldPrice(priceOverride.getOldTotalPrice());
	
	if(priceOverride.getReasonCode()!=null)
		priceOverrideData.setReasonCode(priceOverride.getReasonCode());
	
	if(priceOverride.getReasonText()!=null)
		priceOverrideData.setReasonText(priceOverride.getReasonText());
	
	
	if(priceOverride.getInitialTotalPrice()!=null)
		priceOverrideData.setInitialTotalPrice(priceOverride.getInitialTotalPrice());
	

	if(priceOverride.getCurrency()!=null)
		priceOverrideData.setCurrency(priceOverride.getCurrency());


	}
}
