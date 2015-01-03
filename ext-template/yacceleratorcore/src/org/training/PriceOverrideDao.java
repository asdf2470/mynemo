/**
 * 
 */
package org.training;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideModel;
import de.hybris.platform.yacceleratorcore.model.PriceOverrideReasonCodeModel;

import java.util.List;

/**
 * @author D056437
 *
 */
public interface PriceOverrideDao
{
	public abstract List<PriceOverrideModel> findPriceOverrides(AbstractOrderEntryModel entry);

	public abstract List<PriceOverrideReasonCodeModel> findItemReasonCodes();
	
	public abstract PriceOverrideReasonCodeModel findReasonCodeForReasonNum(Integer reasonNum);
}
