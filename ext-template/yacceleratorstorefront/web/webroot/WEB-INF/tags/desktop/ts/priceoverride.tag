<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${isAsmShown}">
	<c:url value="/cart/priceUpdate" var="cartPriceUpdateFormAction" />
	<form:form id="updatePriceForm${entry.entryNumber}"
		action="${cartPriceUpdateFormAction}" method="post"
		commandName="updatePriceForm${entry.entryNumber}"
		data-cart='{"cartCode" : "${cartData.code}",
						           	"productTotalPrice":"${entry.totalPrice.value}","productName":"${entry.product.name}"}'>
		<input type="hidden" name="entryNumber" value="${entry.entryNumber}" />
		<input type="hidden" name="productCode" value="${entry.product.code}" />
		<input type="text" name="totalPrice" value="${entry.totalPrice.value}"
			style="text-align: right; width: 50px;" />
		<select name="reasonNum" id="reasonCode${entry.entryNumber}">
			<c:forEach items="${priceOverrideReasonCodes}"
				var="priceOverrideReasonCode">
				<c:if test="${not empty entry.currentPriceOverride }">
					<option value="${priceOverrideReasonCode.reasonNum}" 
						${priceOverrideReasonCode.pk == entry.currentPriceOverride.reasonCode.pk
											? 'selected="selected"' : ''}>
						<c:out value="${ priceOverrideReasonCode.reasonNum} ${ priceOverrideReasonCode.reasonCode}" />
					</option>
				</c:if>
				<c:if test="${empty entry.currentPriceOverride }">
					<option value="${priceOverrideReasonCode.reasonNum}">
						<c:out value="${ priceOverrideReasonCode.reasonNum} ${ priceOverrideReasonCode.reasonCode}" />
					</option>
				</c:if>
			</c:forEach>
		</select>
		<input type="text" name="reasonText" value="${empty entry.currentPriceOverride 
			? 'Enter reason.' : entry.currentPriceOverride.reasonText}"
			style="text-align: right;"
			onFocus="if(this.value=='Enter reason.')this.value=''" />
		<input type="submit" value="Perform price override" />

	</form:form>

</c:if>
<c:if test="${not isAsmShown}">
	<ycommerce:testId code="cart_totalProductPrice_label">
		<format:price priceData="${entry.totalPrice}"
			displayFreeForZero="true" />
	</ycommerce:testId>
</c:if>