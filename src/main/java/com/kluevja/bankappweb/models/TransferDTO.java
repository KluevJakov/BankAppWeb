package com.kluevja.bankappweb.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransferDTO {
    private Long transferDebtorAccountId;
    private Long transferCreditorAccountId;
    private Long transferAmount;
}
