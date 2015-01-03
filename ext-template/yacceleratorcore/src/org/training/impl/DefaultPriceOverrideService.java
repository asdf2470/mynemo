/**
 * 
 */
package org.training.impl;

import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.yacceleratorcore.jalo.PriceOverride;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideModel;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideReasonCodeModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.training.PriceOverrideDao;
import org.training.PriceOverrideService;
import org.training.TelesalesCalculationService;

/**
 * @author D056437
 *
 */
public class DefaultPriceOverrideService implements PriceOverrideService
{

	@Resource( name = "modelService")
	private ModelService modelService;
	
	@Resource( name = "priceOverrideDao")
	private PriceOverrideDao priceOverrideDao;
	
	@Resource( name = "telesalesCalculationService")
	private TelesalesCalculationService telesalesCalculationService;
	
	@Resource( name = "commerceCommonI18NService")
	private CommerceCommonI18NService commerceCommonI18NService;
	
	private static final Logger LOG=Logger.getLogger(DefaultPriceOverrideService.class);
	
	@Override
	public PriceOverrideModel createAndApplyPriceOverride(AbstractOrderModel orderOrCart, AbstractOrderEntryModel entry, Double newTotalPrice, 
			PriceOverrideReasonCodeModel priceOverrideReasonCode, String reasonText)
	{
		PriceOverrideModel priceOverride=createPriceOverride(orderOrCart, entry, newTotalPrice, priceOverrideReasonCode, reasonText);
		try
		{
			applyPriceOverride(orderOrCart, entry);
		}
		catch (CalculationException e)
		{
			// TODO: adapt exception management for calculation as in hybris standard
			e.printStackTrace();
		}
		return priceOverride;
	}
	
	private PriceOverrideModel createPriceOverride(AbstractOrderModel orderOrCart, AbstractOrderEntryModel entry, Double newTotalPrice, 
			PriceOverrideReasonCodeModel priceOverrideReasonCode, String reasonText)
	{	
		PriceOverrideModel priceOverride=modelService.create(PriceOverrideModel.class);
		priceOverride.setCart(orderOrCart);
		priceOverride.setCartEntry(entry.getPk());
		priceOverride.setNewTotalPrice(newTotalPrice);
		priceOverride.setOldTotalPrice(entry.getTotalPrice());
		priceOverride.setCurrency(commerceCommonI18NService.getCurrentCurrency());
		priceOverride.setReasonCode(priceOverrideReasonCode);
		priceOverride.setReasonText(reasonText);
		priceOverride.setCreationtime(new Date());
		priceOverride.setIsApproved(Boolean.TRUE);
		
		List<PriceOverrideModel> priceOverrides=findPriceOverrides(entry);
		if(!priceOverrides.isEmpty())priceOverride.setInitialTotalPrice(priceOverrides.get(0).getInitialTotalPrice());
		else priceOverride.setInitialTotalPrice(entry.getTotalPrice());
		
		entry.setCurrentPriceOverride(priceOverride);
		
		//save the price override to DB. entry is saved during calculation (following directly from calculate).
		modelService.save(priceOverride);		
		return priceOverride;	
	}

	/**
	 * Apply price override to cart. The price override is performed by recalculation of the cart. In the calculation of the cart,
	 * the method for calculating individual cart entries has been enhanced (see {@link DefaultTelesalesCalculationService}).
	 */
	private void applyPriceOverride(AbstractOrderModel orderOrCart, AbstractOrderEntryModel entry) throws CalculationException
	{
		orderOrCart.setCalculated(Boolean.FALSE);
		entry.setCalculated(Boolean.FALSE);
		telesalesCalculationService.calculate(orderOrCart);
	}
	
	@Override
	public PriceOverrideModel findPriceOverride(AbstractOrderEntryModel entry)
	{
		List<PriceOverrideModel> priceOverrides=priceOverrideDao.findPriceOverrides(entry);
		
		if(priceOverrides.size() > 1){
			
			return priceOverrides.get(findLatestIndex(priceOverrides));
		}
		else if(priceOverrides.size() == 1)
		{
			return priceOverrides.get(0);
		}
		else return null;
	}
	
	@Override
	public List<PriceOverrideModel> findPriceOverrides(AbstractOrderEntryModel entry)
	{
		return priceOverrideDao.findPriceOverrides(entry);
	}
	
	@Override
	public List<PriceOverrideReasonCodeModel> findItemReasonCodes()
	{
		return priceOverrideDao.findItemReasonCodes();
	}
	

	@Override
	public PriceOverrideReasonCodeModel findReasonCodeForReasonNum(Integer reasonNum)
	{
		return priceOverrideDao.findReasonCodeForReasonNum(reasonNum);
	}
	
	private int findLatestIndex(List<PriceOverrideModel> priceOverrides)
	{
		Date latestDate=null;
		int latestIndex=-1;
		int index=-1;
		for(PriceOverrideModel priceOverride : priceOverrides)
		{
			index++;
			if(latestDate==null){
				latestDate = priceOverride.getCreationtime();
				latestIndex = 0;
			}
			else if(priceOverride.getCreationtime().after(latestDate)){
				latestDate = priceOverride.getCreationtime();
				latestIndex = index;
			}
		}
		LOG.debug("more than 1 price override found for cart entry " + priceOverrides.get(0).getCartEntry());
		return latestIndex;
	}
	
	@Deprecated
	@Override
	public HashMap<Integer, PriceOverrideModel> findPriceOverrides(AbstractOrderModel orderOrCart)
	{
		HashMap<Integer, PriceOverrideModel> priceOverrides=new HashMap<Integer, PriceOverrideModel>();
		for(AbstractOrderEntryModel entry: orderOrCart.getEntries())
		{
			PriceOverrideModel priceOverride=findPriceOverride(entry);
			if(priceOverride!=null)
			{
				priceOverrides.put(entry.getEntryNumber(), priceOverride);
			}
		}
		return (priceOverrides.isEmpty() ? null : priceOverrides);
	}

}
