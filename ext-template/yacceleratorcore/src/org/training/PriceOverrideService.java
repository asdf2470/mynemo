/**
 * 
 */
package org.training;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideModel;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideReasonCodeModel;

import java.util.HashMap;
import java.util.List;


/**
 * @author D056437
 *
 */
public interface PriceOverrideService
{
	
	/**
	 * @param orderOrCart
	 * @param entry
	 * @param newTotalPrice
	 * @param priceOverrideReasonCode
	 * @param reasonText
	 * @return 
	 */
	public abstract PriceOverrideModel createAndApplyPriceOverride(AbstractOrderModel orderOrCart, AbstractOrderEntryModel entry, Double newTotalPrice,
			PriceOverrideReasonCodeModel priceOverrideReasonCode, String reasonText);

	/**
	 * @param entry
	 * @return Find price override for a cart entry and return the PriceOverrideModel or null if none exists.
	 */
	public abstract PriceOverrideModel findPriceOverride(AbstractOrderEntryModel entry);
	
	public abstract List<PriceOverrideReasonCodeModel> findItemReasonCodes();
	
	public abstract PriceOverrideReasonCodeModel findReasonCodeForReasonNum(final Integer reasonNum);
	
	public abstract List<PriceOverrideModel> findPriceOverrides(AbstractOrderEntryModel entry);
	
	/**
	 * NOT USED
	 * @param orderOrCart
	 * @return
	 */
	@Deprecated
	public abstract HashMap<Integer, PriceOverrideModel> findPriceOverrides(AbstractOrderModel orderOrCart);






}
