package com.receiptfinanceapi.projection;

import java.util.Date;

public interface IReceiptListProjection {
    Long getId();
    Date getCreateDate();
    String getName();
    String getDescription();
}
