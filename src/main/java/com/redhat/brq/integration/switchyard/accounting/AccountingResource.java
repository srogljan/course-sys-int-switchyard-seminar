package com.redhat.brq.integration.switchyard.accounting;

import com.redhat.brq.integration.switchyard.models.InvoiceIssueReply;
import com.redhat.brq.integration.switchyard.models.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/accounting")
public interface AccountingResource {
    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    InvoiceIssueReply post(Order order);
}
