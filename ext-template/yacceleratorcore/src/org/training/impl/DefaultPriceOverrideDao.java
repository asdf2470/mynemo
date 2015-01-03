/**
 * 
 */
package org.training.impl;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import javax.annotation.Resource;

import org.training.PriceOverrideDao;

import de.hybris.platform.yacceleratorcore.model.PriceOverrideModel;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideReasonCodeModel;


/**
 * @author D056437
 *
 */
public class DefaultPriceOverrideDao implements PriceOverrideDao
{
	@Resource( name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;
	
	@Override
	public List<PriceOverrideModel> findPriceOverrides(AbstractOrderEntryModel entry)
	{
		String entryPk = entry.getPk().toString();
		String query = "SELECT {pk},{cartentry},{oldtotalprice},{newtotalprice}, {creationtime}";
		query += " from {PriceOverride} WHERE {cartentry}=" + entryPk;
		
		FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		SearchResult<PriceOverrideModel> searchResult = flexibleSearchService.search(flexibleSearchQuery);
		
		return searchResult.getResult();
	}


	@Override
	public List<PriceOverrideReasonCodeModel> findItemReasonCodes()
	{
		String query = "SELECT {pk}, {reasonCode}, {reasonNum} from {PriceOverrideReasonCode}";
		query+= "WHERE {isForItem} = 1";
		
		FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		SearchResult<PriceOverrideReasonCodeModel> searchResult = flexibleSearchService.search(flexibleSearchQuery);
		
		return searchResult.getResult();
	}

	@Override
	public PriceOverrideReasonCodeModel findReasonCodeForReasonNum(Integer reasonNum)
	{
		String query = "SELECT {pk}, {reasonCode}, {reasonNum} from {PriceOverrideReasonCode}";
		query+= "WHERE {reasonNum} = "+reasonNum.intValue();
		
		FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		SearchResult<PriceOverrideReasonCodeModel> searchResult = flexibleSearchService.search(flexibleSearchQuery);
		
		if(searchResult.getResult().size()!=1)
		{
			throw new RuntimeException("found more or less than one entry for PriceOverrideReasonCode "
						+ "for reason code  " + reasonNum.toString());
		}
		else
			return searchResult.getResult().get(0);
	}

}
