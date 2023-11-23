package com.sap.cloud.sdk.tutorial;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;

import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultPurchaseOrderService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.PurchaseOrderService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.purchaseorder.PurchaseOrder;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.purchaseorder.PurchaseOrderFluentHelper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;

@WebServlet("/hellopolist")

public class HelloPOLists extends HttpServlet {
    private PurchaseOrderService purchaseOrderService;
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HelloPOLists.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
            throws ServletException, IOException
    {
    
        logger.info("I am in!");

        HttpDestination httpDestination = DestinationAccessor.getDestination("S4H").asHttp();
        
        logger.info("httpDestination:" + httpDestination.toString());
        
        purchaseOrderService = new DefaultPurchaseOrderService();

        //SalesOrder salesOrder = new SalesOrder();
       // SalesOrderCreateFluentHelper chelper = salesOrderService.createSalesOrder(salesOrder);
        //chelper.


        PurchaseOrderFluentHelper helper = purchaseOrderService.getAllPurchaseOrder().top(5);
        
        List<PurchaseOrder> purchaseOrders = helper.executeRequest(httpDestination);
        
        logger.info("purchaseOrderItemTexts size:" + purchaseOrders.size());
        
        StringBuffer stringBuffer = new StringBuffer();
        for (PurchaseOrder item: purchaseOrders) {
            //logger.info(item.toString());
            //stringBuffer.append(item.toString());
            stringBuffer.append(item.getPurchaseOrder());
            stringBuffer.append(item.getPurchaseOrderType());
            stringBuffer.append(item.getPurchasingGroup());
            stringBuffer.append(item.getSupplier());
            stringBuffer.append("<br>");
            
        }
        
        logger.info("I am running!");
        //response.getWriter().write(stringBuffer.toString());
        request.setAttribute("model", stringBuffer.toString());

        request.getRequestDispatcher("hellopolist.jsp").forward(request, response);


    }
}